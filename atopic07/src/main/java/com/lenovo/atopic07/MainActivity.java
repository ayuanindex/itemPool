package com.lenovo.atopic07;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 查询全部生产环节原材料消耗
 */
public class MainActivity extends BaseActivity {
    private ListView mLv;
    private ApiService apiService;
    private HashMap<Integer, Part.DataBean> partMap;
    private HashMap<Integer, PLStep.DataBean> pLStepMap;
    private MyAdapter adapter;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        query();
    }

    @SuppressWarnings("CheckResult")
    private void query() {
        //原材料集
        partMap = new HashMap<>();
        //生产环节集
        pLStepMap = new HashMap<>();
        //查询全部原材料
        apiService.getAllPart()
                .map(Part::getData)
                .flatMap(dataBeans -> {
                    //保存原材料
                    dataBeans.forEach(dataBean -> partMap.put(dataBean.getId(), dataBean));
                    //查询全部生产环节
                    return apiService.getAllPLStep().map(PLStep::getData);
                })
                .flatMap(dataBeans -> {
                    //保存全部生产环节
                    dataBeans.forEach(dataBean -> pLStepMap.put(dataBean.getId(), dataBean));
                    //查询全部生产环节原材料消耗
                    return apiService.getAllPlStepCost().map(plStepCost::getData);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> mLv.setAdapter(adapter = new MyAdapter(dataBeans)), Throwable::printStackTrace);


    }


    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<plStepCost.DataBean> dataBeans;

        public MyAdapter(List<plStepCost.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<plStepCost.DataBean> getDataBeans() {
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
            plStepCost.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(pLStepMap.get(dataBean.getPlStepId()).getPlStepName());
            holder.mTv3.setText(pLStepMap.get(dataBean.getPlStepId()).getConsume()+"");
            holder.mTv4.setText(partMap.get(dataBean.getPartId()).getPartName());
            holder.mTv5.setText(dataBean.getNum() + "");
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
