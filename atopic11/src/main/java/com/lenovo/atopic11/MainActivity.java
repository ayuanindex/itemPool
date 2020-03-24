package com.lenovo.atopic11;


import android.content.Intent;
import android.icu.text.PluralRules;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.ImageUtils;
import com.lenovo.basic.utils.Network;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 编码查询生产环节
 */
public class MainActivity extends BaseActivity {
    private GridView mGv;
    private MyAdapter myAdapter;
    private ApiService apiService;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        mGv = (GridView) findViewById(R.id.gv);

    }

    @Override
    protected void initEvent() {
        mGv.setOnItemClickListener((parent, view, position, id) -> {
            String icon = myAdapter.getDataBeans().get(position).getIcon();
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

    /**
     * 查询全部生产环节
     */
    private void query() {
        apiService.getAllPLStep()
                .map(PLStep::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    //设置适配器
                    mGv.setAdapter(myAdapter = new MyAdapter(dataBeans));
                }, Throwable::printStackTrace);
    }


    private class MyAdapter extends BaseAdapter {

        private List<PLStep.DataBean> dataBeans;

        public MyAdapter(List<PLStep.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<PLStep.DataBean> getDataBeans() {
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
            //设置图片和名称
            PLStep.DataBean dataBean = dataBeans.get(position);
            int drawable = getResources().getIdentifier(dataBean.getIcon(), "drawable", getPackageName());
            ImageUtils.setBitmapCenterCrop(MainActivity.this, drawable, holder.mIv);
            holder.mTvContent.setText(dataBean.getPlStepName());
            return convertView;
        }

        class ViewHolder {
            ImageView mIv;
            TextView mTvContent;

            public ViewHolder(View view) {
                mIv = (ImageView) view.findViewById(R.id.iv);
                mTvContent = (TextView) view.findViewById(R.id.tv_content);
            }
        }
    }

}
