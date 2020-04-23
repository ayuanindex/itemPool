package com.lenovo.btopic02.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic02.ApiService;
import com.lenovo.btopic02.MainActivity;
import com.lenovo.btopic02.R;
import com.lenovo.btopic02.bean.AllPeopleBean;
import com.lenovo.btopic02.bean.ProductionLineBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class AllPeopleFragment extends BaseFragment {

    private final MainActivity.ResultData resultData;
    private ListView lvList;
    private List<AllPeopleBean.DataBean> allPeople;
    private float width;
    private ApiService remote;
    private ArrayList<ProductionLineBean> productionLineBeans;

    public AllPeopleFragment(MainActivity.ResultData resultData) {
        this.resultData = resultData;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_allpeople;
    }

    @Override
    protected void initView(View view) {
        lvList = view.findViewById(R.id.lv_list);
    }

    @Override
    protected void init() {
        // 获取进度条宽度
        getProgressWidth();

        remote = Network.remote(ApiService.class);

        productionLineBeans = new ArrayList<>();

        Log.d(TAG, "init: " + productionLineBeans.toString());

        allPeople = resultData.getResultData();

        CustomerAdapter customerAdapter = new CustomerAdapter();
        lvList.setAdapter(customerAdapter);
    }

    private void getProgressWidth() {
        // 计算进度条宽度
        WindowManager windowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point outSize = new Point();
        defaultDisplay.getSize(outSize);
        int listViewWidth = (int) ((outSize.x * (3f / 5f)) - 20f);
        width = listViewWidth * (3f / 12f);
    }

    /**
     * 获取所有的生产线
     */
    private void getProductionLine(Result result, int position) {
        int maxCount = 3;
        remote.getProductionLineByPosition(position).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ProductionLineBean productionLineBean) -> {
                    productionLineBeans.add(productionLineBean);
                    if (position < maxCount) {
                        getProductionLine(result, position + 1);
                    } else if (position == maxCount) {
                        result.update();
                    }
                }, (Throwable throwable) -> {
                    if (position < maxCount) {
                        getProductionLine(result, position + 1);
                    } else if (position == maxCount) {
                        result.update();
                    }
                    Log.d(TAG, "getProductionLine: 获取指定位置生产线出现错误" + throwable.getMessage());
                })
                .isDisposed();
    }

    /**
     * 选择生产线对话框
     */
    @SuppressLint("SetTextI18n")
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        View inflate = View.inflate(mActivity, R.layout.dialog_select_line, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);

        // 给radioGroup添加子控件
        for (ProductionLineBean productionLineBean : productionLineBeans) {
            ProductionLineBean.DataBean dataBean = productionLineBean.getData().get(0);
            // 穿件RadioButton
            RadioButton child = new RadioButton(mActivity);
            // 设置控件显示的名称
            child.setText(viewHolder.getLineName(dataBean.getLineId()) + "——位置" + (dataBean.getPos() + 1));
            // 将控件设置进入RadioGroup，-1标示每次添加都是在最下面
            viewHolder.radioGroup.addView(child, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        viewHolder.cardOk.setOnClickListener((View v) -> {
            // 获取当前选择的生产线类型
            int checkedRadioButtonId = viewHolder.radioGroup.getCheckedRadioButtonId();
            ProductionLineBean current = viewHolder.getCurrent(checkedRadioButtonId);
            Log.d(TAG, "onClick: " + current.getData().get(0).getId());
        });

        viewHolder.cardCancel.setOnClickListener((View v) -> alertDialog.dismiss());
        // 设置AlertDialog周围不可以通过点击取消
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public class ViewHolder {
        public View rootView;
        public RadioGroup radioGroup;
        public CardView cardOk;
        public CardView cardCancel;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
            this.cardOk = (CardView) rootView.findViewById(R.id.card_ok);
            this.cardCancel = (CardView) rootView.findViewById(R.id.card_cancel);
        }

        private ProductionLineBean getCurrent(int index) {
            return productionLineBeans.get(index - 1);
        }

        /**
         * 获取生产线类型名称
         *
         * @param lineId 生产线类型ID
         * @return 返回类型名称
         */
        private String getLineName(int lineId) {
            return lineId == 1 ? "轿车车型生产线" : lineId == 2 ? "MPV车型生产线" : "SUV车型生产线";
        }
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvName;
        private TextView tvSalary;
        private TextView tvPower;
        private TextView tvDes;
        private CardView cardRecruitment;

        private CardView cardChild;

        @Override
        public int getCount() {
            return allPeople.size();
        }

        @Override
        public AllPeopleBean.DataBean getItem(int position) {
            return allPeople.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity, R.layout.item_list, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvName.setText(getItem(position).getPeopleName());
            tvSalary.setText(String.valueOf(getItem(position).getGold()));
            tvPower.setText(String.valueOf(getItem(position).getHp()));
            tvDes.setText(getItem(position).getContent());

            // 通过体力值设置进度条的进度
            float v = getItem(position).getHp() / 100f;
            ViewGroup.LayoutParams layoutParams = cardChild.getLayoutParams();
            layoutParams.width = (int) (v * width);
            cardChild.setLayoutParams(layoutParams);

            cardRecruitment.setOnClickListener((View v1) -> getProductionLine(AllPeopleFragment.this::showDialog, 0));
            return view;
        }

        private void initView(View view) {
            tvName = view.findViewById(R.id.tv_name);
            tvSalary = view.findViewById(R.id.tv_salary);
            tvPower = view.findViewById(R.id.tv_power);
            tvDes = view.findViewById(R.id.tv_des);
            cardRecruitment = view.findViewById(R.id.card_recruitment);
            cardChild = view.findViewById(R.id.card_child);
        }
    }

    interface Result {
        /**
         * 开始更新操作显示Dialog
         */
        void update();
    }
}
