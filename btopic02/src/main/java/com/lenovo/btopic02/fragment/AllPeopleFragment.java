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
        // ?????????????????????
        getProgressWidth();

        remote = Network.remote(ApiService.class);

        productionLineBeans = new ArrayList<>(4);

        allPeople = resultData.getResultData();
        ArrayList<SimpleBean> allStudent = resultData.getAllStudent();

        // ???????????????????????????
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
        // ?????????????????????
        WindowManager windowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point outSize = new Point();
        defaultDisplay.getSize(outSize);
        int listViewWidth = (int) ((outSize.x * (3f / 5f)) - 20f);
        width = listViewWidth * (3f / 12f);
    }

    /**
     * ????????????????????????
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
                    Log.d(TAG, "getProductionLine: ???????????????????????????????????????" + throwable.getMessage());
                })
                .isDisposed();
    }

    /**
     * ????????????????????????
     *
     * @param item ??????
     */
    @SuppressLint("SetTextI18n")
    private void showDialog(AllPeopleBean.DataBean item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        View inflate = View.inflate(mActivity, R.layout.dialog_select_line, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);

        // ???radioGroup???????????????
        for (int i = 0; i < productionLineBeans.size(); i++) {
            ProductionLineBean.DataBean dataBean = productionLineBeans.get(i).getData().get(0);
            // ??????RadioButton
            RadioButton child = new RadioButton(mActivity);
            child.setId(i);
            // ???????????????????????????
            child.setText(viewHolder.getLineName(dataBean.getLineId()) + "????????????" + (dataBean.getPos() + 1));
            // ?????????????????????RadioGroup???-1????????????????????????????????????
            viewHolder.radioGroup.addView(child, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            // ????????????????????????
            if (i == 0) {
                child.setChecked(true);
            }
        }


        viewHolder.cardOk.setOnClickListener((View v) -> {
            if (productionLineBeans.size() == 0) {
                alertDialog.dismiss();
                return;
            }
            // ????????????????????????
            viewHolder.loading();
            // ????????????????????????????????????
            int checkedRadioButtonId = viewHolder.radioGroup.getCheckedRadioButtonId();
            Log.d(TAG, "showDialog: " + checkedRadioButtonId);
            ProductionLineBean current = viewHolder.getCurrent(checkedRadioButtonId);
            ProductionLineBean.DataBean dataBean = current.getData().get(0);
            addStudentStaff(item, dataBean.getId(), dataBean.getLineId(), addStudentStaffResult -> {
                Log.d(TAG, "accept: " + addStudentStaffResult.toString());
                item.setRecruitment(true);
                // ?????????????????????
                viewHolder.success();
                // ?????????????????????
                new Handler().postDelayed(() -> {
                    resultData.update();
                    customerAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }, 1000);
            }, (Throwable throwable) -> {
                // ?????????????????????
                viewHolder.error();
                // ?????????????????????
                new Handler().postDelayed(alertDialog::dismiss, 1000);
                Log.d(TAG, "accept: ??????????????????????????????-----" + throwable.getMessage());
            });
        });

        viewHolder.cardCancel.setOnClickListener((View v) -> alertDialog.dismiss());
        // ??????AlertDialog?????????????????????????????????
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
            this.radioGroup = rootView.findViewById(R.id.radio_group);
            this.cardOk = rootView.findViewById(R.id.card_ok);
            this.cardCancel = rootView.findViewById(R.id.card_cancel);
            this.pbProgress = rootView.findViewById(R.id.pb_progress);
            this.ivSuccess = rootView.findViewById(R.id.iv_success);
            this.ivError = rootView.findViewById(R.id.iv_error);
            this.llContent = rootView.findViewById(R.id.ll_content);
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
         * ???????????????????????????
         *
         * @param lineId ???????????????ID
         * @return ??????????????????
         */
        private String getLineName(int lineId) {
            return lineId == 1 ? "?????????????????????" : lineId == 2 ? "MPV???????????????" : "SUV???????????????";
        }

    }

    /**
     * ??????????????????
     *
     * @param item              ?????????????????????????????????
     * @param productionLineId  ?????????ID
     * @param lineId            ???????????????ID
     * @param onNext            ?????????????????????
     * @param throwableConsumer ????????????
     */
    private void addStudentStaff(AllPeopleBean.DataBean item, int productionLineId, int lineId, Consumer<AddStudentStaffResult> onNext, Consumer<Throwable> throwableConsumer) {
        // ???????????????????????????
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
                tvStatus.setText("?????????");
            } else {
                cardRecruitment.setCardBackgroundColor(Color.WHITE);
                cardRecruitment.setEnabled(true);
                tvStatus.setText("??????");
            }

            // ???????????????????????????????????????
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
         * ????????????????????????Dialog
         */
        void update();
    }
}
