package com.lenovo.atopic03;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 查询全部供货列表
 */
public class MainActivity extends BaseActivity {
    private TextView mTvMoney;
    private ListView mLv;
    private ApiService apiService;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mLv = (ListView) findViewById(R.id.lv);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        querySuppierList();
    }


    /**
     * 查询供货列表
     */
    @SuppressWarnings("CheckResult")
    private void querySuppierList() {
        //供货商数据集
        Map<Integer, String> datasSuppier = new HashMap<>();
        //原材料数据集
        Map<Integer, String> datasPart = new HashMap<>();
        Map<Integer, Integer> datasPartAire = new HashMap<>();
        //自定义javabean，列表数据集
        List<Bean> datas = new ArrayList<>();
        apiService.getAllSuppier()
                .map(Suppier::getData)
                .flatMap(dataBeans -> {
                    //将所有的供货商保存
                    for (int i = 0; i < dataBeans.size(); i++) {
                        Suppier.DataBean dataBean = dataBeans.get(i);
                        datasSuppier.put(dataBean.getId(), dataBean.getContent());
                    }
                    //请求原材料数据
                    return apiService.getAllPart().map(Part::getData);
                })
                .flatMap(dataBeans -> {
                    //将所有原材料保存
                    for (int i = 0; i < dataBeans.size(); i++) {
                        Part.DataBean dataBean = dataBeans.get(i);
                        datasPart.put(dataBean.getId(), dataBean.getPartName());
                        datasPartAire.put(dataBean.getId(), dataBean.getArea());
                    }
                    //请求供货列表数据
                    return apiService.getAllSuppierList().map(SuppierList::getData);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //绑定生命周期
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    int totalMoney = 0;
                    //处理供货列表数据
                    for (int i = 0; i < dataBeans.size(); i++) {
                        Bean bean = new Bean();
                        SuppierList.DataBean dataBean = dataBeans.get(i);
                        totalMoney += dataBean.getGold()*dataBean.getNum();
                        bean.setMoney(dataBean.getGold());
                        bean.setNum(dataBean.getNum());
                        bean.setId(dataBean.getId());
                        bean.setArea(datasPartAire.get(dataBean.getPartId()));
                        bean.setSuppierName(datasSuppier.get(dataBean.getSuppierId()));
                        bean.setName(datasPart.get(dataBean.getPartId()));
                        datas.add(bean);
                    }
                    //设置总金额
                    mTvMoney.setText("供货总额："+new DecimalFormat("###,###").format(totalMoney));
                    //设置适配器显示数据
                    mLv.setAdapter(new MyAdapter(datas));
                }, Throwable::printStackTrace);
    }

    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<Bean> dataBeans;

        public MyAdapter(List<Bean> dataBeans) {
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
            Bean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(dataBean.getName());
            holder.mTv3.setText(dataBean.getMoney() + "");
            holder.mTv4.setText(dataBean.getNum() + "");
            holder.mTv5.setText(dataBean.getSuppierName() + "");
            holder.mTv6.setText(dataBean.getArea() + "");
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
