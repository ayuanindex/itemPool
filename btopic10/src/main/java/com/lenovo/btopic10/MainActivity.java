package com.lenovo.btopic10;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.cardview.widget.CardView;

import com.google.android.material.textview.MaterialTextView;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic10.bean.AllProductionLineBean;
import com.lenovo.btopic10.bean.AllStageBean;
import com.lenovo.btopic10.bean.UpdateAiResultBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {
    private ListView lvList;
    private CardView cardAdd;
    private ApiService remote;
    private List<AllProductionLineBean.DataBean> productionLines;
    private CustomerAdapter customerAdapter;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvList = (ListView) findViewById(R.id.lv_list);
        cardAdd = (CardView) findViewById(R.id.card_add);
    }

    @Override
    protected void initEvent() {
        cardAdd.setOnClickListener((View v) -> {

        });
    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        productionLines = new ArrayList<>();
        customerAdapter = new CustomerAdapter();
        lvList.setAdapter(customerAdapter);

        // 获取所有生产环节
        getAllStage();
    }

    /**
     * 获取所有生产环节信息
     */
    private void getAllStage() {
        remote.getAllStage().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllStageBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<AllStageBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    // 获取所有已经创建的生产线
                    getAllProductionLine(dataBeans);
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取所有的生产工序出现问题----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 获取当前所有已经创建的生产线
     *
     * @param allStage 所有生产工序的集合
     */
    private void getAllProductionLine(List<AllStageBean.DataBean> allStage) {
        remote.getAllProductionLine().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<AllProductionLineBean, List<AllProductionLineBean.DataBean>>() {
                    @Override
                    public List<AllProductionLineBean.DataBean> apply(AllProductionLineBean allProductionLineBean) throws Exception {
                        for (AllProductionLineBean.DataBean datum : allProductionLineBean.getData()) {
                            for (AllStageBean.DataBean dataBean : allStage) {
                                if (datum.getStageId() == dataBean.getId()) {
                                    datum.setStageName(dataBean.getStageName());
                                    break;
                                }
                            }
                        }
                        return allProductionLineBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<AllProductionLineBean.DataBean> dataBeans) -> {
                    productionLines.clear();
                    productionLines = dataBeans;
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取所有生产线出现问题----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 修改生产线的Ai状态
     *
     * @param id   生产线ID
     * @param isAi 0->非Ai；1->Ai
     * @param item 需要修改的对象
     */
    private void updateAiState(int id, int isAi, AllProductionLineBean.DataBean item) {
        remote.updateAiState(id, isAi).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(UpdateAiResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((UpdateAiResultBean.DataBean dataBean) -> {
                    item.setIsAI(Integer.parseInt(dataBean.getIsAI()));
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 修改Ai状态出现问题----" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerAdapter extends BaseAdapter {
        private MaterialTextView tvLineName;
        private MaterialTextView tvState;
        private MaterialTextView tvPosition;
        private MaterialTextView tvProcess;
        private Switch swAi;

        @Override
        public int getCount() {
            return productionLines.size();
        }

        @Override
        public AllProductionLineBean.DataBean getItem(int position) {
            return productionLines.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_productionline, null);
            } else {
                view = convertView;
            }
            initView(view);
            AllProductionLineBean.DataBean item = getItem(position);

            int productionLineId = item.getProductionLineId();
            tvLineName.setText(productionLineId == 1 ? "轿车汽车生产线" : (productionLineId == 2 ? "MPV汽车生产线" : "SUV汽车生产线"));

            int type = item.getType();
            tvState.setText(type == 0 ? "建设中" : (type == 1 ? "缺原材料" : (type == 2 ? "生产中" : "库存已满")));

            tvPosition.setText(String.valueOf(item.getPosition()));

            tvProcess.setText(item.getStageName());

            swAi.setOnCheckedChangeListener(null);


            swAi.setChecked(item.getIsAI() == 1);

            swAi.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                Log.d(TAG, "onCheckedChanged: ");
                updateAiState(item.getId(), item.getIsAI() == 0 ? 1 : 0, item);
                // 禁止切换按钮状态
                buttonView.toggle();
            });
            return view;
        }

        private void initView(View view) {
            tvLineName = (MaterialTextView) view.findViewById(R.id.tv_lineName);
            tvState = (MaterialTextView) view.findViewById(R.id.tv_state);
            tvPosition = (MaterialTextView) view.findViewById(R.id.tv_position);
            tvProcess = (MaterialTextView) view.findViewById(R.id.tv_process);
            swAi = (Switch) view.findViewById(R.id.sw_ai);
        }
    }

}
