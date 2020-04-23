package com.lenovo.btopic02.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.lenovo.btopic02.bean.AddStudentStaffResult;
import com.lenovo.btopic02.bean.AllPeopleBean;
import com.lenovo.btopic02.bean.ProductionLineBean;
import com.lenovo.btopic02.bean.SimpleBean;
import com.lenovo.btopic02.bean.StudentStaffBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
    private static ArrayList<ProductionLineBean> productionLineBeans;
    private CustomerAdapter customerAdapter;

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

        productionLineBeans = new ArrayList<>(4);

        allPeople = resultData.getResultData();
        ArrayList<SimpleBean> allStudent = resultData.getAllStudent();

        for (SimpleBean simpleBean : allStudent) {
            for (StudentStaffBean.DataBean dataBean : simpleBean.getDataBeans()) {
                for (AllPeopleBean.DataBean allPerson : allPeople) {
                    if (allPerson.getId() == dataBean.getPeopleId()) {
                        allPerson.setRecruitment(true);
                    }
                }
            }
        }

        customerAdapter = new CustomerAdapter();
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
     *
     * @param item 人员
     */
    @SuppressLint("SetTextI18n")
    private void showDialog(AllPeopleBean.DataBean item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        View inflate = View.inflate(mActivity, R.layout.dialog_select_line, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);

        // 给radioGroup添加子控件
        for (int i = 0; i < productionLineBeans.size(); i++) {
            ProductionLineBean.DataBean dataBean = productionLineBeans.get(i).getData().get(0);
            // 穿件RadioButton
            RadioButton child = new RadioButton(mActivity);
            child.setId(i);
            // 设置控件显示的名称
            child.setText(viewHolder.getLineName(dataBean.getLineId()) + "——位置" + (dataBean.getPos() + 1));
            // 将控件设置进入RadioGroup，-1标示每次添加都是在最下面
            viewHolder.radioGroup.addView(child, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        }

        viewHolder.cardOk.setOnClickListener((View v) -> {
            if (productionLineBeans.size() == 0) {
                alertDialog.dismiss();
                return;
            }
            // 显示加载中的状态
            viewHolder.loading();
            // 获取当前选择的生产线类型
            int checkedRadioButtonId = viewHolder.radioGroup.getCheckedRadioButtonId();
            ProductionLineBean current = viewHolder.getCurrent(checkedRadioButtonId);
            ProductionLineBean.DataBean dataBean = current.getData().get(0);
            addStudentStaff(item, dataBean.getId(), dataBean.getLineId(), addStudentStaffResult -> {
                Log.d(TAG, "accept: " + addStudentStaffResult.toString());
                item.setRecruitment(true);
                // 显示成功的状态
                viewHolder.success();
                // 延时销毁对话框
                new Handler().postDelayed(() -> {
                    customerAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }, 1000);
            }, (Throwable throwable) -> {
                // 显示失败的状态
                viewHolder.error();
                // 延时销毁对话框
                new Handler().postDelayed(alertDialog::dismiss, 1000);
                Log.d(TAG, "accept: 添加学生员工出现错误-----" + throwable.getMessage());
            });
        });

        viewHolder.cardCancel.setOnClickListener((View v) -> alertDialog.dismiss());
        // 设置AlertDialog周围不可以通过点击取消
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static class ViewHolder {

        public View rootView;
        public RadioGroup radioGroup;
        public CardView cardOk;
        public CardView cardCancel;
        public ProgressBar pbProgress;
        public ImageView ivSuccess;
        public ImageView ivError;
        public LinearLayout llContent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
            this.cardOk = (CardView) rootView.findViewById(R.id.card_ok);
            this.cardCancel = (CardView) rootView.findViewById(R.id.card_cancel);
            this.pbProgress = (ProgressBar) rootView.findViewById(R.id.pb_progress);
            this.ivSuccess = (ImageView) rootView.findViewById(R.id.iv_success);
            this.ivError = (ImageView) rootView.findViewById(R.id.iv_error);
            this.llContent = (LinearLayout) rootView.findViewById(R.id.ll_content);
        }

        private void success() {
            this.llContent.setVisibility(View.GONE);
            this.pbProgress.setVisibility(View.GONE);
            this.ivSuccess.setVisibility(View.VISIBLE);
            this.ivError.setVisibility(View.GONE);
        }

        private void loading() {
            this.llContent.setVisibility(View.GONE);
            this.pbProgress.setVisibility(View.VISIBLE);
            this.ivSuccess.setVisibility(View.GONE);
            this.ivError.setVisibility(View.GONE);
        }

        private void error() {
            this.llContent.setVisibility(View.GONE);
            this.pbProgress.setVisibility(View.GONE);
            this.ivSuccess.setVisibility(View.GONE);
            this.ivError.setVisibility(View.GONE);
        }

        private ProductionLineBean getCurrent(int index) {
            return productionLineBeans.get(index);
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

    /**
     * 新增学生员工
     *
     * @param item              需要添加的学生员工对象
     * @param productionLineId  生产线ID
     * @param lineId            生产线类型ID
     * @param onNext            订阅成功的回调
     * @param throwableConsumer 异常信息
     */
    private void addStudentStaff(AllPeopleBean.DataBean item, int productionLineId, int lineId, Consumer<AddStudentStaffResult> onNext, Consumer<Throwable> throwableConsumer) {
        // 当前人员的工作类型
        int status = item.getStatus();
        HashMap<String, Object> hashMap = new HashMap<>(5);
        hashMap.put("userWorkId", 1);
        hashMap.put("power", item.getHp());
        hashMap.put("peopleId", item.getId());
        hashMap.put("userProductionLineId", productionLineId);
        hashMap.put("workPostId", ((status + 1) + ((lineId - 1) * 4)));
        remote.addStudentStaff(hashMap).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, throwableConsumer)
                .isDisposed();
    }

    class CustomerAdapter extends BaseAdapter {

        private TextView tvName;
        private TextView tvSalary;
        private TextView tvPower;
        private TextView tvDes;
        private TextView tvStatus;
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

            if (getItem(position).isRecruitment()) {
                cardRecruitment.setCardBackgroundColor(Color.LTGRAY);
                cardRecruitment.setEnabled(false);
                tvStatus.setText("已招聘");
            } else {
                cardRecruitment.setCardBackgroundColor(Color.WHITE);
                cardRecruitment.setEnabled(true);
                tvStatus.setText("招聘");
            }

            // 通过体力值设置进度条的进度
            float v = getItem(position).getHp() / 100f;
            ViewGroup.LayoutParams layoutParams = cardChild.getLayoutParams();
            layoutParams.width = (int) (v * width);
            cardChild.setLayoutParams(layoutParams);

            cardRecruitment.setOnClickListener((View v1) -> {
                productionLineBeans.clear();
                getProductionLine(() -> showDialog(CustomerAdapter.this.getItem(position)), 0);
            });
            return view;
        }

        private void initView(View view) {
            tvName = view.findViewById(R.id.tv_name);
            tvSalary = view.findViewById(R.id.tv_salary);
            tvPower = view.findViewById(R.id.tv_power);
            tvDes = view.findViewById(R.id.tv_des);
            tvStatus = view.findViewById(R.id.tv_status);
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
