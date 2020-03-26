package com.lenovo.atopic14;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.Base2Activity;
import com.lenovo.basic.utils.Network;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LogActivity extends Base2Activity {
    private ListView mLv;
    private Button mBtnBack;
    private ApiSevices apiSevices;
    private Map<Integer, Car.DataBean> carMap;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_log;
    }

    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mBtnBack = (Button) findViewById(R.id.btn_back);
    }

    @Override
    protected void initEvent() {
        //返回上个界面
        mBtnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        apiSevices = Network.remote(ApiSevices.class);
        //查询全部卖出记录
        query();
    }

    @SuppressWarnings("CheckResult")
    private void query() {
        carMap = new HashMap<>();
        carMap.clear();
        apiSevices.getAllCar().map(Car::getData)
                .flatMap(dataBeans -> {
                    //保存车辆信息
                    dataBeans.forEach(dataBean -> carMap.put(dataBean.getId(), dataBean));
                    //查询全部学生卖出记录
                    return apiSevices.getAllUserSellOutLog().map(UserSellOutLog::getData);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);
    }

    private class MyAdapter extends BaseAdapter {
        private List<UserSellOutLog.DataBean> dataBeans;
        private SimpleDateFormat sdf;

        public MyAdapter(List<UserSellOutLog.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
                convertView = View.inflate(LogActivity.this, R.layout.item_log, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            UserSellOutLog.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(carMap.get(dataBean.getCarId()).getCarName());
            holder.mTv2.setText(dataBean.getGold() + "");
            holder.mTv3.setText(dataBean.getNum() + "");
            holder.mTv4.setText(sdf.format(dataBean.getTime() * 1000L));
            holder.mBtn.setText("删除");
            //删除按钮
            holder.mBtn.setOnClickListener(v -> {
                apiSevices.deleteUserSellOutLog(dataBean.getId())
                        .map(UserSellOutLog::getData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(LogActivity.this.bindToLifecycle())
                        .subscribe(dataBeans1 -> {
                            //更新列表数据
                            dataBeans.remove(dataBean);
                            notifyDataSetChanged();
                        }, Throwable::printStackTrace);
            });
            return convertView;
        }

        class ViewHolder {
            TextView mTv1;
            TextView mTv2;
            TextView mTv3;
            TextView mTv4;
            Button mBtn;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mTv4 = (TextView) view.findViewById(R.id.tv4);
                mBtn = (Button) view.findViewById(R.id.btn);

            }
        }
    }
}
