package com.lenovo.atopic09;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 编码实现金币收支管理
 */
public class MainActivity extends BaseActivity {
    private TextView mTvOut;
    private TextView mTvIn;
    private Button mTvCreate;
    private ListView mLv;
    private int inTotalMoney = 0;
    private int outTotalMoney = 0;

    private ApiService apiService;
    private Map<Integer, String> typeMap;
    private MyAdapter adapter;
    private AlertDialog alertDialog;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvOut = (TextView) findViewById(R.id.tv_out);
        mTvIn = (TextView) findViewById(R.id.tv_in);
        mTvCreate = (Button) findViewById(R.id.tv_create);
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {
        //新增
        mTvCreate.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, CreateActivity.class), 20));

        //列表的长按事件
        mLv.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
            return true;
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == 20) {
            //新增数据后，更新界面
            query();
        }
    }

    //显示删除对话框
    private void showDeleteDialog(int position) {
        //找到要删除的对象
        PriceLog.DataBean dataBean = adapter.getDataBeans().get(position);
        TextView mTvContent;
        Button mBtnCancel;
        Button mBtnTrue;
        View view = View.inflate(this, R.layout.dialog, null);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
        mBtnTrue = (Button) view.findViewById(R.id.btn_true);
        mTvContent.setText("确定删除ID为" + dataBean.getId() + "的金币日志记录吗？");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        mBtnCancel.setOnClickListener(v -> alertDialog.dismiss());
        mBtnTrue.setOnClickListener(v -> {
            //进行删除的操作
            if (dataBean.getType() == 5) {
                //只有5售出是收入日志
                apiService.deleteUserInPriceLog(dataBean.getId())
                        .map(PriceLog::getData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(this.bindToLifecycle())
                        .subscribe(dataBeans -> {
                            //删除成功，更新列表
                            query();
                        }, Throwable::printStackTrace);
            } else {
                apiService.deleteUserOutPriceLog(dataBean.getId())
                        .map(PriceLog::getData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(this.bindToLifecycle())
                        .subscribe(dataBeans -> {
                            //删除成功，更新列表
                            query();
                        }, Throwable::printStackTrace);
            }
            alertDialog.dismiss();
        });

        //设置对话框背景透明
        Window window = alertDialog.getWindow();
        if (window != null)
            window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        //类型集
        //(0、原材料，1、人员，2、生产线，3、维修生产环节，4、维修车 辆，5、售出)
        typeMap = new HashMap<>();
        String[] stringArray = getResources().getStringArray(R.array.type);
        for (int i = 0; i < stringArray.length; i++) {
            typeMap.put(i, stringArray[i]);
        }
        query();
    }

    @SuppressLint("CheckResult")
    private void query() {
        inTotalMoney = 0;
        outTotalMoney = 0;
        Observable
                .zip(apiService.getAllUserInPriceLog().map(PriceLog::getData)
                                .map(dataBeans -> {
                                    //计算收入总额
                                    dataBeans.forEach(dataBean -> inTotalMoney += dataBean.getPrice());
                                    return dataBeans;
                                }),
                        apiService.getAllUserOutPriceLog().map(PriceLog::getData).map(dataBeans -> {
                            //计算支出总额
                            dataBeans.forEach(dataBean -> outTotalMoney += dataBean.getPrice());
                            return dataBeans;
                        }),
                        ((dataBeans, dataBeans2) -> {
                            dataBeans.addAll(dataBeans2);
                            return dataBeans;
                        }))
                .map(dataBeans -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //更改数据格式
                    dataBeans.forEach(dataBean -> {
                        dataBean.setTypeStr(typeMap.get(dataBean.getType()));
                        Date date = new Date(1000L * Long.parseLong(dataBean.getTime() == null ? "0" : dataBean.getTime()));
                        dataBean.setTimeStr(sdf.format(date));
                    });
                    return dataBeans;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    DecimalFormat df = new DecimalFormat("###,###");
                    //设置收支总额
                    mTvIn.setText("收入总额：" + df.format(inTotalMoney));
                    mTvOut.setText("支出总额：" + df.format(outTotalMoney));
                    //设置适配器
                    adapter = new MyAdapter(dataBeans);
                    mLv.setAdapter(adapter);
                }, Throwable::printStackTrace);

    }


    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<PriceLog.DataBean> dataBeans;

        public MyAdapter(List<PriceLog.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<PriceLog.DataBean> getDataBeans() {
            return dataBeans;
        }

        @Override
        public int getCount() {
            return dataBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PriceLog.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(dataBean.getPrice() + "");
            holder.mTv3.setText(dataBean.getEndPrice() + "");
            holder.mTv4.setText(dataBean.getTypeStr() + "");
            holder.mTv5.setText(dataBean.getTimeStr() + "");
            return convertView;
        }

        class ViewHolder {
            TextView mTv1;
            TextView mTv2;
            TextView mTv3;
            TextView mTv4;
            TextView mTv5;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mTv4 = (TextView) view.findViewById(R.id.tv4);
                mTv5 = (TextView) view.findViewById(R.id.tv5);
            }
        }
    }
}
