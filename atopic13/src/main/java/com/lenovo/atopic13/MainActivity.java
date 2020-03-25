package com.lenovo.atopic13;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 实现人员招聘
 */
public class MainActivity extends BaseActivity {

    private Button mTvQuery;
    private ListView mLv;
    private ApiSevices apiSevices;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvQuery = (Button) findViewById(R.id.tv_query);
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {
        mTvQuery.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,LogActivity.class)));
    }

    @Override
    protected void initData() {
        apiSevices = Network.remote(ApiSevices.class);
        query();
    }

    @SuppressWarnings("CheckResult")
    private void query() {
        apiSevices.getAllPeople()
                .map(People::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);


    }

    private class MyAdapter extends BaseAdapter {
        private List<People.DataBean> dataBeans;

        public MyAdapter(List<People.DataBean> dataBeans) {
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
            People.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText("" + dataBean.getId());
            holder.mTv2.setText(dataBean.getPeopleName());
            holder.mTv3.setText(dataBean.getContent());
            holder.mTv4.setText(dataBean.getGold() + "");

            //招聘按钮
            holder.mBtn.setOnClickListener(v -> {
                //新增学生员工
                apiSevices.createUserPeople(1, 100, dataBean.getId(), 1, 1)
                        .map(CreatePeople::getData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(MainActivity.this.bindToLifecycle())
                        .subscribe(datas -> {
                            //从列表中移除，并更新界面
                            dataBeans.remove(dataBean);
                            notifyDataSetChanged();
                            //添加招募日志到服务器
                            apiSevices.createUserPeopleLog(1, dataBean.getId(), new Date().getTime() / 1000)
                                    .map(CreatePeopleLog::getData)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .compose(MainActivity.this.bindToLifecycle())
                                    .subscribe(dataBeans1 -> {
                                        Toast.makeText(MainActivity.this, "招募成功", Toast.LENGTH_SHORT).show();
                                    }, Throwable::printStackTrace);
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
