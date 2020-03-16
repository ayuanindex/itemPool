package com.lenovo.atopic08;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 编码实现查询全部订单
 */
public class MainActivity extends BaseActivity {

    private TextView mTvMoney;
    private Button mTvOrder;
    private ListView mLv;
    private ApiService apiService;
    private Map<Integer, CarInfo.DataBean> carInfoMap;
    private Map<Integer, String> typeMap;
    private Map<String, Integer> colorMap;
    private int totalMoney = 0;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvOrder = (Button) findViewById(R.id.tv_order);
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {
        //跳转到下单页面
        mTvOrder.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, CreateActivity.class), 20));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode ==20) {
            query();//刷新界面
            Log.e("TAG","----------1111111112111111");
        }
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        //订单类型
        typeMap = new HashMap<>();
        typeMap.put(0, "已下单");
        typeMap.put(1, "生产中");
        typeMap.put(2, "完成");
        //车辆颜色（外观装饰）
        colorMap = new HashMap<>();
        colorMap.put("0", Color.RED);
        colorMap.put("1", Color.BLUE);
        colorMap.put("2", Color.GREEN);
        query();
    }

    @SuppressWarnings("CheckResult")
    private void query() {
        //存放车辆信息
        carInfoMap = new HashMap<>();
        //请求车辆信息
        apiService.getAllCar().map(CarInfo::getData)
                .flatMap(dataBeans -> {
                    //保存车辆信息
                    dataBeans.forEach(dataBean -> carInfoMap.put(dataBean.getId(), dataBean));
                    //获取订单数据
                    return apiService.getAllUserAppointment().map(UserAppointment::getData);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    totalMoney = 0;
                    //计算总金额
                    dataBeans.forEach(dataBean -> totalMoney += dataBean.getGold() * dataBean.getNum());
                    mTvMoney.setText("订单总收益：" + new DecimalFormat("###,###").format(totalMoney));
                    //显示数据
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);
    }


    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<UserAppointment.DataBean> dataBeans;

        public MyAdapter(List<UserAppointment.DataBean> dataBeans) {
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

            try {
                UserAppointment.DataBean dataBean = dataBeans.get(position);
                holder.mTv1.setText(dataBean.getId() + "");
                holder.mTv2.setText(dataBean.getUserAppointmentName());
                holder.mTv3.setText(dataBean.getGold() + "");
                holder.mTv4.setText(dataBean.getNum() + "");
                holder.mTv5.setText(carInfoMap.get(dataBean.getCarId()).getContent() + "");
                holder.mTv6.setText(typeMap.get(dataBean.getType()) + "");
                convertView.setBackgroundColor(colorMap.get(dataBean.getColor()));
            }catch (Exception e){
                //e.printStackTrace();
            }
            return convertView;
        }

        class ViewHolder {
            TextView mTv1;
            TextView mTv2;
            TextView mTv3;
            TextView mTv4;
            TextView mTv5;
            TextView mTv6;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mTv4 = (TextView) view.findViewById(R.id.tv4);
                mTv5 = (TextView) view.findViewById(R.id.tv5);
                mTv6 = (TextView) view.findViewById(R.id.tv6);
            }
        }
    }
}
