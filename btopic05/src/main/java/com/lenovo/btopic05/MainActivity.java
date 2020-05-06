package com.lenovo.btopic05;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic05.bean.ProductionLineBean;
import com.lenovo.btopic05.bean.ProductionProcedureBean;
import com.lenovo.btopic05.bean.ProductionProcessesBean;
import com.lenovo.btopic05.bean.ResultStatusBean;
import com.lenovo.btopic05.bean.SimpleBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {

    private ListView lvList;
    private ImageView ivIcon;
    private ApiService remote;
    private List<ProductionProcessesBean.DataBean> processesBeans;
    private List<ProductionProcedureBean.DataBean> procedureBeans;
    private ArrayList<SimpleBean> simpleBeans;
    private CustomerAdapter customerAdapter;
    private int currentStep;
    private int productionLineId;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvList = findViewById(R.id.lv_list);
        ivIcon = findViewById(R.id.iv_icon);
    }

    @Override
    protected void initEvent() {
        lvList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            int resourceId = gerResourceId(position + 1);
            ivIcon.setImageResource(resourceId);
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

    private int gerResourceId(int position) {
        String name;
        int i = 10;
        if (position < i) {
            name = "line0" + productionLineId + "_0" + position;
        } else {
            name = "line0" + productionLineId + "_" + position;
        }
        return getResources().getIdentifier(name, "drawable", getPackageName());
    }

    /**
     * 获取4号位置的生产线信息
     */
    private void getProductionLine() {
        remote.getProductionLineByPosition(3).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ProductionLineBean productionLineBean) -> {
                    if (productionLineBean.getData().size() == 0) {
                        createProductionLine();
                    } else {
                        ProductionLineBean.DataBean dataBean = productionLineBean.getData().get(0);
                        currentStep = dataBean.getStageId();
                        productionLineId = dataBean.getProductionLineId();
                        if (dataBean.getIsAI() == 0) {
                            toggleAiState(dataBean.getId());
                        } else {
                            // 获取所有生产环节信息
                            getAllProductionProcesses(dataBean.getId());
                        }
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取生产线信息出现错误-----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 获取当前生产线的所有生产环节数据
     *
     * @param id 生产线ID
     */
    private void getAllProductionProcesses(int id) {
        remote.getAllProductionProcesses(id).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ProductionProcessesBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<ProductionProcessesBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    processesBeans = dataBeans;
                    getAllProductionProcedure();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取所有环节数据出现问题" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 获取所有的生产工序
     */
    private void getAllProductionProcedure() {
        remote.getAllProductionProcedure().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ProductionProcedureBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<ProductionProcedureBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    procedureBeans = dataBeans;
                    Collections.reverse(processesBeans);

                    for (ProductionProcessesBean.DataBean processesBean : processesBeans) {
                        for (ProductionProcedureBean.DataBean dataBean : procedureBeans) {
                            if (processesBean.getNextUserPlStepId() - 1 == dataBean.getPlStepId()) {
                                SimpleBean e = new SimpleBean();
                                e.setTitle(dataBean.getStageName());
                                e.setDes(dataBean.getContent());
                                e.setPlStepId(dataBean.getPlStepId());
                                simpleBeans.add(e);
                                break;
                            } else if (processesBean.getNextUserPlStepId() == -1) {
                                int plStepId = simpleBeans.get(simpleBeans.size() - 1).getPlStepId();
                                for (ProductionProcedureBean.DataBean procedureBean : procedureBeans) {
                                    if (procedureBean.getPlStepId() == plStepId + 1) {
                                        SimpleBean e = new SimpleBean();
                                        e.setTitle(procedureBean.getStageName());
                                        e.setDes(procedureBean.getContent());
                                        e.setPlStepId(procedureBean.getPlStepId());
                                        simpleBeans.add(e);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    Log.d(TAG, "accept: " + simpleBeans.toString());
                    int i = gerResourceId(1);
                    ivIcon.setImageResource(i);
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: -----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 切换沙盘是否AI的状态
     *
     * @param id 生产线ID
     */
    private void toggleAiState(int id) {
        remote.changeIsAi(id, 1).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ResultStatusBean resultStatusBean) -> {
                    Log.d(TAG, "accept: " + resultStatusBean.toString());
                    getAllProductionProcesses(id);
                }, (Throwable throwable) -> Log.d(TAG, "accept: 切换AI状态出现问题——----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 创建生产线
     */
    private void createProductionLine() {
        remote.createProductionLine(2, 3).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ProductionLineBean::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((String s) -> {
                    String msg = "该位置已存在生产线";
                    if (!msg.equals(s)) {
                        // 创建生产成功，重新获取生产线信息
                        getProductionLine();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 创建生产线出现问题-----" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvLabel;
        private TextView tvTitle;
        private TextView tvDes;

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
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvLabel.setText(String.valueOf((position + 1)));
            tvTitle.setText(getItem(position).getTitle());
            tvDes.setText(getItem(position).getDes());

            if (getItem(position).getPlStepId() == currentStep) {
                tvLabel.setTextColor(Color.WHITE);
                tvLabel.setBackgroundColor(Color.parseColor("#2A8BFF"));
            } else {
                tvLabel.setTextColor(Color.BLACK);
                tvLabel.setBackgroundColor(Color.WHITE);
            }
            return view;
        }

        private void initView(View view) {
            tvLabel = view.findViewById(R.id.tv_label);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDes = view.findViewById(R.id.tv_des);
        }
    }

}
