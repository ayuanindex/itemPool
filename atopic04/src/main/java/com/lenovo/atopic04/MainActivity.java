package com.lenovo.atopic04;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    @SuppressWarnings("CheckResult")
    private void query() {
        //存储车辆信息
        Map<Integer, String> carTypeMap = new HashMap<>();
        //请求车辆信息
        apiService.getAllCarInfo()
                .map(CarInfo::getData)
                .flatMap(dataBeans -> {
                    //保存车辆信息
                    for (int i = 0; i < dataBeans.size(); i++) {
                        CarInfo.DataBean dataBean = dataBeans.get(i);
                        carTypeMap.put(dataBean.getId(), dataBean.getContent());
                    }
                    //请求全部卖出记录
                    return map(apiService.getAllUserSellOutLog(), mTvDate.getText().toString());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    int totalMoney = 0;
                    for (int i = 0; i < dataBeans.size(); i++) {
                        SaleLog.DataBean dataBean = dataBeans.get(i);
                        totalMoney += dataBean.getGold()*dataBean.getNum();
                        dataBean.setCarType(carTypeMap.get(dataBean.getCarId()));
                    }
                    //设置金额
                    mTvMoney.setText("卖出总额：" + new DecimalFormat("###,###").format(totalMoney));
                    //设置列表数据
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);
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
        datePickerDialog.updateDate(2019, 10, 23);
    }



    /**
     * 数据处理
     */
    private Observable<List<SaleLog.DataBean>> map(Observable<SaleLog> observable, String startDate) {
        //开始时间
        long startTime = 0;
        try {
            startTime = sdfParse2.parse(startDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long finalStartTime = startTime;
        return observable.map(SaleLog::getData)
                .map(dataBeans -> {
                    //过滤非当前时间数据
                    List<SaleLog.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        long tempTime = Long.valueOf(dataBeans.get(i).getTime() + "000");
                        if (tempTime >= finalStartTime && tempTime < finalStartTime + dayOfTimeMillin) {
                            SaleLog.DataBean dataBean = dataBeans.get(i);
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
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<SaleLog.DataBean> dataBeans;

        public MyAdapter(List<SaleLog.DataBean> dataBeans) {
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
            SaleLog.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(dataBean.getCarType());
            holder.mTv3.setText(dataBean.getGold() + "");
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
