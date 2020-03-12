package com.lenovo.atopic05;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 查询人员工作状态
 */
public class MainActivity extends BaseActivity {
    private ListView mLv;
    private ApiService apiService;
    private Map<Integer, String> productLines;
    private Map<Integer, Integer> userProductLines;

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


    /**
     * 查询员工信息
     */
    @SuppressWarnings("CheckResult")
    private void query() {
        //全部生产线集
        productLines = new HashMap<>();
        //学生生产线集
        userProductLines = new HashMap<>();
        //查询全部生产线
        apiService.getAllProductionLine()
                .map(ProductionLine::getData)
                .flatMap(dataBeans -> {
                    //保存全部生产线所需要的数据
                    for (int i = 0; i < dataBeans.size(); i++) {
                        ProductionLine.DataBean dataBean = dataBeans.get(i);
                        productLines.put(dataBean.getId(), dataBean.getProductionLineName());
                    }
                    //查询全部学生生产线
                    return apiService.getAllUserProductionLine().map(UserProductionLine::getData);
                })
                .flatMap(dataBeans -> {
                    //保存学生生产线数据
                    for (int i = 0; i < dataBeans.size(); i++) {
                        UserProductionLine.DataBean dataBean = dataBeans.get(i);
                        userProductLines.put(dataBean.getId(), dataBean.getProductionLineId());
                    }
                    //请求查询全部学生员工数据
                    return apiService.getAllUserPeople().map(UserPeople::getData);
                })
                //切换线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //绑定生命周期
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    //设置适配器
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);
    }


    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<UserPeople.DataBean> dataBeans;

        public MyAdapter(List<UserPeople.DataBean> dataBeans) {
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
            UserPeople.DataBean dataBean = dataBeans.get(position);
            UserPeople.DataBean.PersonBean person = dataBean.getPerson();
            holder.mTv1.setText(person.getPersonName() + "");
            holder.mTv2.setText(person.getPrice() + "");
            holder.mTv3.setText(dataBean.getHp() + "");
            holder.mTv4.setText(productLines.get(userProductLines.get(dataBean.getUserLineId())) + "");
            holder.mTv5.setText(dataBean.getPerson().getContent() + "");
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
