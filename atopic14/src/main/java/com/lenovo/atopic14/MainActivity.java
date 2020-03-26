package com.lenovo.atopic14;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ListView mLv;
    private TextView mTvMoney;
    private Button mBtnSaleLog;
    private Button mBtnSale;
    private ApiSevices apiSevices;
    private MyAdapter adapter;
    //记录工厂资金
    private int workInfoMoney = 0;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mBtnSaleLog = (Button) findViewById(R.id.btn_sale_log);
        mBtnSale = (Button) findViewById(R.id.btn_sale);
    }

    @Override
    protected void initEvent() {
        //跳转到卖出日志界面
        mBtnSaleLog.setOnClickListener(v -> startActivity(new Intent(this, LogActivity.class)));

        //卖出按钮
        mBtnSale.setOnClickListener(v -> {
            List<CarStore.DataBean> dataBeans = adapter.getDataBeans();
            dataBeans.forEach(dataBean -> {
                if (dataBean.isChecked()) {
                    Observable<CarStore> carStoreObservable = null;
                    if (dataBean.getTypeDes().equals("成品")) {
                        //删除成品车辆
                        carStoreObservable = apiSevices.deleteUserNormalCarStore(dataBean.getId());
                    } else if (dataBean.getTypeDes().equals("维修")) {
                        carStoreObservable = apiSevices.deleteUserRepairCarStore(dataBean.getId());
                    }
                    carStoreObservable.map(CarStore::getData)
                            .flatMap(dataBeans1 -> {
                                workInfoMoney += dataBean.getPrice() * dataBean.getNum();
                                return apiSevices.updatePriceUserWorkInfo(1, workInfoMoney);
                            })
                            //线程切换
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            //绑定生命周期
                            .compose(MainActivity.this.bindToLifecycle())
                            .subscribe(dataBeans1 -> {
                                //重新查询工厂金币，进行显示
                                queryUserWorkInfo();
                                //删除列表对应数据
                                adapter.getDataBeans().remove(dataBean);
                                //更新列表
                                adapter.notifyDataSetChanged();
                                //新增学生卖出记录
                                apiSevices.createUserSellOutLog(1, dataBean.getCarId(), dataBean.getPrice(),
                                        new Date().getTime() / 1000L, dataBean.getNum())
                                        //线程切换
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        //绑定生命周期
                                        .compose(MainActivity.this.bindToLifecycle())
                                        .subscribe(carStore -> {
                                            Log.e(TAG, "----------------1");
                                        }, Throwable::printStackTrace);
                            }, Throwable::printStackTrace);
                }
            });
        });
    }

    @Override
    protected void initData() {
        apiSevices = Network.remote(ApiSevices.class);
        //查询工厂金币数据
        queryUserWorkInfo();
        //查询正常/问题车辆
        queryCar();
    }

    /**
     * 查询正常和问题车辆
     */
    @SuppressWarnings("CheckResult")
    private void queryCar() {
        //请求正常车辆数据
        Observable<List<CarStore.DataBean>> normalObservable = apiSevices.getAllUserNormalCarStore()
                .map(CarStore::getData)
                .map(dataBeans -> {//标记为成品车辆
                    dataBeans.forEach(dataBean -> dataBean.setTypeDes("成品"));
                    return dataBeans;
                });
        //请求维修车辆数据
        Observable<List<CarStore.DataBean>> questionObservable = apiSevices.getAllUserQuestion()
                .map(CarStore::getData)
                .map(dataBeans -> {//标记为维修车辆
                    dataBeans.forEach(dataBean -> dataBean.setTypeDes("维修"));
                    return dataBeans;
                });
        //保存车辆数据和车辆信息数据
        Map<Integer, Car.DataBean> carMap = new HashMap<>();
        Map<Integer, CarInfo.DataBean> carInfoMap = new HashMap<>();
        //查询全部车辆信息
        apiSevices.getAllCarInfo().map(CarInfo::getData)
                .flatMap(dataBeans -> {
                    //保存车辆信息数据
                    dataBeans.forEach(dataBean -> carInfoMap.put(dataBean.getId(), dataBean));
                    //查询全部车辆
                    return apiSevices.getAllCar().map(Car::getData);
                })
                .flatMap(dataBeans -> {
                    //保存车辆信息
                    dataBeans.forEach(dataBean -> carMap.put(dataBean.getId(), dataBean));
                    //合并正常车辆和问题车辆数据
                    return Observable.zip(normalObservable, questionObservable, (dataBeans2, dataBeans3) -> {
                        dataBeans2.addAll(dataBeans3);
                        return dataBeans2;
                    });
                })
                .map(dataBeans -> {//格式化数据，补充carName和price属性
                    dataBeans.forEach(dataBean -> {
                        dataBean.setCarName(carMap.get(dataBean.getCarId()).getCarName());
                        dataBean.setPrice(carInfoMap.get(dataBean.getCarId()).getGold());
                    });
                    return dataBeans;
                })
                //线程切换
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //绑定生命周期
                .compose(MainActivity.this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    //设置适配器，显示数据
                    mLv.setAdapter(adapter = new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);
    }

    /**
     * 查询工厂金币数据
     */
    @SuppressWarnings("CheckResult")
    private void queryUserWorkInfo() {
        apiSevices.getInfoUserWorkInfo(1)
                .map(UserWorkInfo::getData)
                .map(dataBeans -> dataBeans.get(0).getPrice())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(MainActivity.this.bindToLifecycle())
                .subscribe(integer -> {
                    workInfoMoney = integer;
                    mTvMoney.setText("工厂资金：" + new DecimalFormat("###,###").format(integer));
                }, Throwable::printStackTrace);
    }


    private class MyAdapter extends BaseAdapter {
        private List<CarStore.DataBean> dataBeans;

        public MyAdapter(List<CarStore.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<CarStore.DataBean> getDataBeans() {
            return dataBeans;
        }

        public void setDataBeans(List<CarStore.DataBean> dataBeans) {
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
            CarStore.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText("" + dataBean.getCarName());
            holder.mTv2.setText(dataBean.getPrice() + "");
            holder.mTv3.setText(dataBean.getNum() + "");
            holder.mTv4.setText(dataBean.getTypeDes());
            holder.mCb.setChecked(dataBean.isChecked());
            holder.mCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {
                    dataBean.setChecked(isChecked);
                }
            });
            return convertView;
        }

        class ViewHolder {

            TextView mTv1;
            TextView mTv2;
            TextView mTv3;
            TextView mTv4;
            CheckBox mCb;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mTv4 = (TextView) view.findViewById(R.id.tv4);
                mCb = (CheckBox) view.findViewById(R.id.cb);
            }
        }
    }
}
