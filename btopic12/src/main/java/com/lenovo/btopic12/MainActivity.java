package com.lenovo.btopic12;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.material.textview.MaterialTextView;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic12.bean.AllProductionProcessesBean;
import com.lenovo.btopic12.bean.AllStageBean;
import com.lenovo.btopic12.bean.AllStepInfoBean;
import com.lenovo.btopic12.bean.ProductionLineBean;
import com.lenovo.btopic12.bean.SimpleBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ListView lvList;
    private AppCompatImageView ivImage;
    private MaterialTextView tvLink;
    private CustomerAdapter customerAdapter;
    private ApiService remote;
    public static ArrayList<SimpleBean> simpleBeans;
    private ProductionLineBean.DataBean dataBean;
    private int current = 0;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvList = findViewById(R.id.lv_list);
        ivImage = findViewById(R.id.iv_image);
        tvLink = findViewById(R.id.tv_link);
    }

    @Override
    protected void initEvent() {
        lvList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            current = position;
            int resourceId = getResourceId(position + 1);
            ivImage.setImageResource(resourceId);
            tvLink.setText(customerAdapter.getItem(position).getDes());
        });

        ivImage.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, DesActivity.class);
            intent.putExtra("resourceId", getResourceId(current + 1));
            intent.putExtra("current", current);
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, ivImage, ivImage.getTransitionName()).toBundle());
        });
    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        simpleBeans = new ArrayList<>();

        customerAdapter = new CustomerAdapter();
        lvList.setAdapter(customerAdapter);

        getProductionLine();
    }

    private int getResourceId(int position) {
        String name;
        int i = 10;
        if (position < i) {
            name = "pic_line0" + dataBean.getProductionLineId() + "_0" + position;
        } else {
            name = "pic_line0" + dataBean.getProductionLineId() + "_" + position;
        }
        return getResources().getIdentifier(name, "drawable", getPackageName());
    }

    /**
     * 获取生产线信息
     */
    private void getProductionLine() {
        remote.getProductionByPosition(3).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((ProductionLineBean productionLineBean) -> {
                    if (productionLineBean.getData().size() > 0) {
                        return productionLineBean.getData().get(0);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ProductionLineBean.DataBean dataBean) -> {
                    if (dataBean == null) {
                        // 当前位置不存在生产线则创建生产线
                        createProduction();
                    } else {
                        // 生产线已经创建
                        Log.d(TAG, "getProductionLine: -----" + dataBean.toString());
                        this.dataBean = dataBean;
                        // 查询全部用户生产环节信息
                        getAllProductionProcesses();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取指定位置的生产线出现问题------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 查询全部用户生产环节信息
     */
    private void getAllProductionProcesses() {
        // 查询全部用户生产环节的详细信息数据
        remote.getAllProductionProcesses().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllProductionProcessesBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getAllStepInfo, (Throwable throwable) -> Log.d(TAG, "accept: 获取全部用户生产环节出错误----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 获取所有用户生产环节的详细信息数据
     *
     * @param allProductionProcesses 全部用户的生产环节信息
     */
    private void getAllStepInfo(List<AllProductionProcessesBean.DataBean> allProductionProcesses) {
        // 查询全部生产工序数据
        remote.getAllStepInfo().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((Function<AllStepInfoBean, List<SimpleBean>>) (AllStepInfoBean allStepInfoBean) -> {
                    for (AllProductionProcessesBean.DataBean allProductionProcess : allProductionProcesses) {
                        if (allProductionProcess.getUserProductionLineId() == dataBean.getId()) {
                            for (AllStepInfoBean.DataBean datum : allStepInfoBean.getData()) {
                                if (datum.getId() == allProductionProcess.getId()) {
                                    SimpleBean e = new SimpleBean();
                                    e.setHp(datum.getPower());
                                    e.setConsume(datum.getConsume());
                                    e.setPlStepId(datum.getStageId());
                                    simpleBeans.add(e);
                                    break;
                                }
                            }
                        }
                    }
                    return simpleBeans;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getAllStage, (Throwable throwable) -> Log.d(TAG, "accept: 获取生产环节详细信息数据出现问题------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 查询全部生产工序数据
     *
     * @param simpleBeans 带填充的集合
     */
    private void getAllStage(List<SimpleBean> simpleBeans) {
        remote.getAllStage().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((AllStageBean allStageBean) -> {
                    for (SimpleBean simpleBean : simpleBeans) {
                        for (AllStageBean.DataBean datum : allStageBean.getData()) {
                            if (datum.getPlStepId() == simpleBean.getPlStepId()) {
                                simpleBean.setLinkName(datum.getStageName());
                                simpleBean.setDes(datum.getContent());
                                break;
                            }
                        }
                    }
                    return simpleBeans;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<SimpleBean> beans) -> {
                    Collections.reverse(simpleBeans);

                    int resourceId = getResourceId(1);
                    ivImage.setImageResource(resourceId);

                    tvLink.setText(simpleBeans.get(0).getDes());

                    for (int i = 0; i < simpleBeans.size(); i++) {
                        simpleBeans.get(i).setIcon(getResourceId(i + 1));
                    }
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取全部生产工序信息数据发生错误-----" + throwable.getMessage()))
                .isDisposed();
    }

    private void createProduction() {
        remote.createProductionLine(3, 3).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ProductionLineBean::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((String s) -> {
                    Log.d(TAG, "createProduction: ------------" + s);
                    String message = "创建学生生产线成功";
                    if (message.equals(s)) {
                        // 重新查询指定位置的生产线的信息
                        getProductionLine();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 创建生产线出现问题------" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerAdapter extends BaseAdapter {
        private AppCompatImageView ivLinkIcon;
        private MaterialTextView tvLinkName;

        @Override
        public int getCount() {
            return simpleBeans.size();
        }

        @Override
        public SimpleBean getItem(int position) {
            return simpleBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_link, null);
            } else {
                view = convertView;
            }
            initView(view);
            int resourceId = getResourceId(position + 1);
            ivLinkIcon.setImageResource(resourceId);
            tvLinkName.setText(getItem(position).getDes());
            return view;
        }

        private void initView(View view) {
            ivLinkIcon = view.findViewById(R.id.iv_linkIcon);
            tvLinkName = view.findViewById(R.id.tv_linkName);
        }
    }

}
