package com.lenovo.itempool;

import android.app.DatePickerDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 编码实现查询售出记录
 */
public class MainActivity extends BaseActivity {
    private TextView mTvMoney;
    private TextView mTvDate;
    private Button mTvQuery;
    private ListView mLv;
    private ApiService apiService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfParse2 = new SimpleDateFormat("yyyy-MM-dd");
    //一天的毫秒数
    private static Long dayOfTimeMillin = 24 * 60 * 60 * 1000L;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvQuery = (Button) findViewById(R.id.tv_query);
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {
        mTvDate.setOnClickListener(v -> selectDate(mTvDate));
        mTvQuery.setOnClickListener(v -> query());
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        query();
    }

    //查询售出记录
    private void query() {
        map(apiService.getUserSellInfoTEditer(), mTvDate.getText().toString())
                .subscribe(dataBeans -> {
                    int totalMoney = 0;
                    // 计算总价格
                    for (int i = 0; i < dataBeans.size(); i++) {
                        totalMoney += dataBeans.get(i).getPrice();
                    }
                    mTvMoney.setText("售出总额：" +new DecimalFormat("###,###,###").format(totalMoney));
                    //显示列表数据
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, throwable -> throwable.printStackTrace());
    }


    /**
     * 数据处理
     */
    private Observable<List<Bean.DataBean>> map(Observable<Bean> observable, String startDate) {
        //开始时间
        long startTime = 0;
        try {
            startTime = sdfParse2.parse(startDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long finalStartTime = startTime;
        return observable.map(Bean::getData)
                .map(dataBeans -> {
                    //过滤非当前时间数据
                    List<Bean.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        long tempTime = Long.valueOf(dataBeans.get(i).getTime() + "000");
                        if (tempTime >= finalStartTime && tempTime < finalStartTime + dayOfTimeMillin) {
                            Bean.DataBean dataBean = dataBeans.get(i);
                            //添加数据，即格式化好的时间字符串
                            dataBean.setTimeStr(sdf.format(new Date(tempTime)));
                            data.add(dataBean);
                        }
                    }
                    return data;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 弹出日期选择框
     */
    private void selectDate(TextView mTvDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTvDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                datePickerDialog.dismiss();
            }
        });
        datePickerDialog.show();
        datePickerDialog.updateDate(2019, 11, 20);
    }


    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<Bean.DataBean> dataBeans;

        public MyAdapter(List<Bean.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
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
            Bean.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(dataBean.getCarTypeName());
            holder.mTv3.setText(dataBean.getPrice() + "");
            holder.mTv4.setText(dataBean.getNum() + "");
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
