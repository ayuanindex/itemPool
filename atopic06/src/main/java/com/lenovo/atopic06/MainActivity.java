package com.lenovo.atopic06;


import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 查询全部生产工序
 */
public class MainActivity extends BaseActivity {
    private ListView mLv;
    private ApiService apiService;
    private Map<Integer, PLStep.DataBean> plStageMap;
    private List<Integer> dataLineStage;
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
//        列表的点击事件
        mLv.setOnItemClickListener((parent, view, position, id) -> {
            Stage.DataBean dataBean = adapter.getDataBeans().get(position);
            String icon = plStageMap.get(dataBean.getPlStepId()).getIcon();
            Intent intent = new Intent(MainActivity.this, PictureActivity.class);
            intent.putExtra("data", icon);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        query();
    }

    @SuppressWarnings("CheckResult")
    private void query() {
        //保存生产线出现的生产工序
        dataLineStage = new ArrayList<>();
        //保存全部生产环节
        plStageMap = new HashMap<>();
        //查询全部生产线
        apiService.getAllUserProductionLine()
                .map(UserProductionLine::getData)
                .flatMap(dataBeans -> {
                    //将生产线出现的生产工序保存
                    dataBeans.forEach(dataBean -> dataLineStage.add(dataBean.getStageId()));
                    //查询全部生产环节
                    return apiService.getAllPLStep().map(PLStep::getData);
                })
                .flatMap(dataBeans -> {
                    //保存全部生产环节
                    dataBeans.forEach(dataBean -> plStageMap.put(dataBean.getId(), dataBean));
                    //查询全部生产工序
                    return apiService.getAllStage().map(Stage::getData);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                //设置适配器
                .subscribe(dataBeans -> mLv.setAdapter(adapter = new MyAdapter(dataBeans)), Throwable::printStackTrace);
    }

    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<Stage.DataBean> dataBeans;

        public MyAdapter(List<Stage.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<Stage.DataBean> getDataBeans() {
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
            Stage.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(dataBean.getStageName() + "");
            holder.mTv3.setText(dataBean.getContent() + "");
            holder.mTv4.setText(plStageMap.get(dataBean.getPlStepId()).getPlStepName());
            if (dataBeans.size() > position + 1) {
                holder.mTv5.setText(dataBeans.get(position + 1).getStageName());
            } else {
                holder.mTv5.setText("");
            }
            if (dataLineStage.contains(dataBean.getPlStepId())) {
                convertView.setBackgroundColor(Color.RED);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }
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
