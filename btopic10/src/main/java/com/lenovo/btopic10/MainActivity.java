package com.lenovo.btopic10;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.android.material.textview.MaterialTextView;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic10.bean.AllProductionLineBean;
import com.lenovo.btopic10.bean.AllStageBean;
import com.lenovo.btopic10.bean.CreateResultBean;
import com.lenovo.btopic10.bean.UpdateAiResultBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private ArrayList<Integer> positionList;
    private List<AllStageBean.DataBean> stageLists;
    private LinearLayout llLoading;
    private LinearLayout llContent;
    private ArrayList<String> lines;
    private int theMaximumLength = 4;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvList = (ListView) findViewById(R.id.lv_list);
        cardAdd = (CardView) findViewById(R.id.card_add);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
    }

    @Override
    protected void initEvent() {
        cardAdd.setOnClickListener((View v) -> alertDialog());
    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        productionLines = new ArrayList<>();
        customerAdapter = new CustomerAdapter();
        lvList.setAdapter(customerAdapter);

        // ?????????????????????
        initSpinnerData();

        // ????????????????????????
        getAllStage();
    }

    /**
     * ?????????Spinner?????????
     */
    private void initSpinnerData() {
        // ??????????????????????????????
        positionList = new ArrayList<>();
        for (int i = 0; i < theMaximumLength; i++) {
            positionList.add(i);
        }

        // ???????????????????????????????????????
        lines = new ArrayList<>();
        lines.add("?????????????????????");
        lines.add("MPV???????????????");
        lines.add("SUV???????????????");
    }

    /**
     * ??????????????????????????????
     */
    private void getAllStage() {
        remote.getAllStage().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllStageBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<AllStageBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    stageLists = dataBeans;
                    // ????????????????????????????????????
                    getAllProductionLine(dataBeans);
                }, (Throwable throwable) -> Log.d(TAG, "accept: ???????????????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param allStage ???????????????????????????
     */
    private void getAllProductionLine(List<AllStageBean.DataBean> allStage) {
        remote.getAllProductionLine().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((AllProductionLineBean allProductionLineBean) -> {
                    for (AllProductionLineBean.DataBean datum : allProductionLineBean.getData()) {
                        // ???????????????????????????
                        for (AllStageBean.DataBean dataBean : allStage) {
                            if (datum.getStageId() == dataBean.getId()) {
                                datum.setStageName(dataBean.getStageName());
                                break;
                            }
                        }

                        // ??????????????????
                        for (Integer integer : positionList) {
                            if (datum.getPosition() == integer) {
                                positionList.remove(integer);
                                break;
                            }
                        }
                    }
                    return allProductionLineBean.getData();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<AllProductionLineBean.DataBean> dataBeans) -> {
                    productionLines.clear();
                    productionLines = dataBeans;
                    customerAdapter.notifyDataSetChanged();
                    if (dataBeans.size() > theMaximumLength) {
                        cardAdd.setEnabled(false);
                    }

                    // ???????????????????????????
                    llLoading.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                }, (Throwable throwable) -> Log.d(TAG, "accept: ?????????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ??????????????????Ai??????
     *
     * @param id   ?????????ID
     * @param isAi 0->???Ai???1->Ai
     * @param item ?????????????????????
     */
    private void updateAiState(int id, int isAi, AllProductionLineBean.DataBean item) {
        remote.updateAiState(id, isAi).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(UpdateAiResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((UpdateAiResultBean.DataBean dataBean) -> {
                    item.setIsAI(Integer.parseInt(dataBean.getIsAI()));
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: ??????Ai??????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ??????????????????????????????
     */
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        View inflate = View.inflate(this, R.layout.dialog_create_production_line, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);

        // ?????????????????????????????????spinner
        viewHolder.spLine.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, lines));

        // ?????????????????????spinner
        viewHolder.spPosition.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, positionList));

        final int[] line = {-1};
        final int[] lienPosition = {-1};

        viewHolder.spLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                line[0] = (position + 1);
                Log.d(TAG, "onItemSelected:?????????" + line[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.spPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lienPosition[0] = positionList.get(position);
                Log.d(TAG, "onItemSelected:??????" + lienPosition[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.cardCreate.setOnClickListener((View v) -> {
            if (lienPosition[0] < 0) {
                return;
            }
            alertDialog.dismiss();
            createProductionLine(line[0], lienPosition[0]);
        });

        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        alertDialog.show();
    }

    /**
     * ???????????????
     *
     * @param lineId ???????????????ID
     * @param pos    ????????????????????????
     */
    private void createProductionLine(int lineId, int pos) {
        remote.createProductionLine(lineId, pos).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(CreateResultBean::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((String s) -> {
                    Log.d(TAG, "createProductionLine: " + s + pos + " " + lineId);
                    String str = "???????????????????????????";
                    if (str.equals(s)) {
                        initSpinnerData();
                        // ?????????????????????
                        getAllProductionLine(stageLists);
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: ???????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    public static class ViewHolder {
        public View rootView;
        public Spinner spLine;
        public Spinner spPosition;
        public CardView cardCreate;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.spLine = (Spinner) rootView.findViewById(R.id.sp_line);
            this.spPosition = (Spinner) rootView.findViewById(R.id.sp_position);
            this.cardCreate = (CardView) rootView.findViewById(R.id.card_create);
        }

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
            tvLineName.setText(productionLineId == 1 ? "?????????????????????" : (productionLineId == 2 ? "MPV???????????????" : "SUV???????????????"));

            int type = item.getType();
            tvState.setText(type == 0 ? "?????????" : (type == 1 ? "????????????" : (type == 2 ? "?????????" : "????????????")));

            tvPosition.setText(String.valueOf(item.getPosition()));

            tvProcess.setText(item.getStageName());

            swAi.setOnCheckedChangeListener(null);


            swAi.setChecked(item.getIsAI() == 1);

            swAi.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                Log.d(TAG, "onCheckedChanged: ");
                updateAiState(item.getId(), item.getIsAI() == 0 ? 1 : 0, item);
                // ????????????????????????
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
