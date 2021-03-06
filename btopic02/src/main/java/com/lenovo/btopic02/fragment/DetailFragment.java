package com.lenovo.btopic02.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic02.ApiService;
import com.lenovo.btopic02.MainActivity;
import com.lenovo.btopic02.R;
import com.lenovo.btopic02.bean.AllPeopleBean;
import com.lenovo.btopic02.bean.ChangeResultBean;
import com.lenovo.btopic02.bean.JobBean;
import com.lenovo.btopic02.bean.ProductionLineBean;
import com.lenovo.btopic02.bean.RemoveStudentResult;
import com.lenovo.btopic02.bean.StudentStaffBean;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class DetailFragment extends BaseFragment {

    private final AllPeopleBean.DataBean allPeopleBean;
    private final MainActivity.Refresh refresh;
    private StudentStaffBean.DataBean child;
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvContent;
    private TextView tvJobType;
    private TextView tvCurrentHp;
    private Spinner spProductionLine;
    private Spinner spJobType;
    private CardView cardRemoveCurrent;
    private ProgressBar pbProgress;
    private TextView tvTextStatus;
    private static String currentLineType = "";
    private int lineType = 0;
    private CustomerTypeAdapter customerTypeAdapter;
    private boolean isInit = true;
    private boolean isSelect = false;
    private ApiService remote;
    private ArrayList<ProductionLineBean> productionLineBeans;
    private CustomerLineAdapter customerLineAdapter;
    private ArrayList<JobBean> jobBeans;

    public DetailFragment(StudentStaffBean.DataBean child, AllPeopleBean.DataBean allPeopleBeans, MainActivity.Refresh refresh) {
        this.child = child;
        this.allPeopleBean = allPeopleBeans;
        this.refresh = refresh;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        ivIcon = view.findViewById(R.id.iv_icon);
        spJobType = view.findViewById(R.id.sp_jobType);
        tvContent = view.findViewById(R.id.tv_content);
        tvJobType = view.findViewById(R.id.tv_jobType);
        pbProgress = view.findViewById(R.id.pb_progress);
        tvCurrentHp = view.findViewById(R.id.tv_currentHp);
        tvTextStatus = view.findViewById(R.id.tv_textStatus);
        spProductionLine = view.findViewById(R.id.sp_productionLine);
        cardRemoveCurrent = view.findViewById(R.id.card_removeCurrent);

        initListener();
    }

    private void initListener() {
        spProductionLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isSelect = false;
                if (!isInit) {
                    setProductionLine(position);
                }
                isInit = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spJobType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSelect) {
                    // ?????????????????????
                    changeWorkPost(child.getId(), customerTypeAdapter.getItem(position).getWorkPostId());
                }
                isSelect = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cardRemoveCurrent.setOnClickListener((View v) -> {
            tvTextStatus.setVisibility(View.GONE);
            pbProgress.setVisibility(View.VISIBLE);
            // ??????????????????
            deleteStudent();
        });
    }

    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);

        // ??????????????????
        productionLineBeans = new ArrayList<>();
        customerLineAdapter = new CustomerLineAdapter();
        spProductionLine.setAdapter(customerLineAdapter);

        // ?????????????????????
        jobBeans = new ArrayList<>();
        customerTypeAdapter = new CustomerTypeAdapter();
        spJobType.setAdapter(customerTypeAdapter);

        // ????????????
        echoData();

        // ?????????????????????????????????
        getProductionLine(0);
    }

    /**
     * ????????????
     */
    @SuppressLint("SetTextI18n")
    private void echoData() {
        ivIcon.setImageResource(R.drawable.pic_icon);
        tvName.setText(allPeopleBean.getPeopleName());
        tvContent.setText(allPeopleBean.getContent());
        tvCurrentHp.setText("???????????????" + child.getPower());
        // ??????getType????????????????????????
        tvJobType.setText("???????????????" + getType());
    }

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @return ?????? ????????????
     */
    private String getType() {
        int workPostId = Integer.parseInt(child.getWorkPostId());
        setCurrentLineType(workPostId);

        // ?????????????????????
        int type = workPostId - ((lineType - 1) * 4) - 1;
        switch (type) {
            case 0:
                return currentLineType + "?????????";
            case 1:
                return currentLineType + "??????";
            case 2:
                return currentLineType + "????????????";
            case 3:
                return currentLineType + "?????????";
            default:
                return "";
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param workPostId ????????????ID
     */
    private void setCurrentLineType(int workPostId) {
        // ???????????? ?????????????????????????????? ---???????????????
        if (workPostId >= 1 && workPostId <= 4) {
            // ????????????
            currentLineType = "????????????";
            lineType = 1;
        } else if (workPostId >= 5 && workPostId <= 8) {
            // MPV??????
            currentLineType = "MPV??????";
            lineType = 2;
        } else if (workPostId >= 9 && workPostId <= 12) {
            // SUV??????
            currentLineType = "SUV??????";
            lineType = 3;
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param position ?????????????????????
     */
    private void getProductionLine(int position) {
        remote.getProductionLineByPosition(position).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ProductionLineBean productionLineBean) -> {
                    productionLineBeans.add(productionLineBean);
                    keepGoing(position);
                }, (Throwable throwable) -> keepGoing(position))
                .isDisposed();
    }

    /**
     * ??????????????????
     *
     * @param position ????????????
     */
    private void keepGoing(int position) {
        if (position < 3) {
            getProductionLine(position + 1);
        } else if (position == 3) {
            // ????????????????????????????????????spinner
            customerLineAdapter.notifyDataSetChanged();

            // ?????????????????????????????????
            for (int i = 0; i < productionLineBeans.size(); i++) {
                // ???????????????ID
                if (productionLineBeans.get(i).getData().get(0).getId() == child.getUserProductionLineId()) {
                    // ???????????????spinner????????????
                    spProductionLine.setSelection(i, true);

                    // ???????????????????????????
                    /*setCurrentLineType(Integer.parseInt(child.getWorkPostId()));*/

                    // ???????????????????????????????????????
                    int lineId = productionLineBeans.get(i).getData().get(0).getLineId();

                    // ?????????????????????spinner
                    setWorkPostType(new JobBean(getType(), Integer.parseInt(child.getWorkPostId())), lineId);
                    break;
                }
            }
        }
    }

    /**
     * ??????????????????spinner
     *
     * @param item   ?????????????????????????????????
     * @param lineId ????????????????????????
     */
    private void setWorkPostType(JobBean item, int lineId) {
        switch (lineId) {
            case 1:
                currentLineType = "????????????";
                setJobBeans(item, 1, 2, 3, 4);
                break;
            case 2:
                currentLineType = "MPV??????";
                setJobBeans(item, 5, 6, 7, 8);
                break;
            case 3:
                currentLineType = "SUV4??????";
                setJobBeans(item, 9, 10, 11, 12);
                break;
            default:
        }
    }

    private void setProductionLine(int position) {
        // ???????????????????????????
        ProductionLineBean.DataBean dataBean = customerLineAdapter.getItem(position).getData().get(0);
        // ?????????????????????????????????
        changeLine(child.getId(), dataBean.getId(), (ChangeResultBean changeResultBean) -> {
            // ??????????????????????????????
            refresh.update();
            lineType = dataBean.getLineId();
            // ?????????????????????????????????
            int selectedItemPosition = spJobType.getSelectedItemPosition();
            JobBean item = customerTypeAdapter.getItem(selectedItemPosition);
            Log.d(TAG, "setProductionLine: " + item);
            // ???????????????????????????
            jobBeans.clear();
            setWorkPostType(item, lineType);
        });
    }

    /**
     * ???????????????????????????
     *
     * @param id                   ?????????????????????ID
     * @param userProductionLineId ???????????????ID
     * @param onNext               ?????????????????????
     */
    private void changeLine(int id, int userProductionLineId, Consumer<ChangeResultBean> onNext) {
        remote.changeLine(id, userProductionLineId).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, throwable -> Log.d(TAG, "accept: ???????????????????????????????????????-----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ?????????????????????
     *
     * @param id         ??????ID
     * @param workPostId ????????????
     */
    private void changeWorkPost(int id, int workPostId) {
        remote.changeWork(id, workPostId).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ChangeResultBean changeResultBean) -> {
                    refresh.update();
                    if (jobBeans.size() > 4) {
                        for (JobBean jobBean : jobBeans) {
                            if (jobBean.getWorkPostId() == workPostId) {
                                jobBeans.remove(jobBean);
                                break;
                            }
                        }
                    }
                    customerTypeAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> {
                    // ??????????????????????????????
                    checkCurrentWork();
                    Log.d(TAG, "accept: ????????????????????????" + throwable.getMessage());
                })
                .isDisposed();
    }

    /**
     * ???????????????JavaBean???????????????????????????????????????????????????
     *
     * @param item ?????????????????????
     * @param type ????????????1???12???1???2???3???4???5???6???7???8???9???10???11???12???
     */
    private void setJobBeans(JobBean item, int... type) {
        jobBeans.add(new JobBean(currentLineType + "?????????", type[0]));
        jobBeans.add(new JobBean(currentLineType + "??????", type[1]));
        jobBeans.add(new JobBean(currentLineType + "????????????", type[2]));
        jobBeans.add(new JobBean(currentLineType + "????????????", type[3]));
        if (item != null) {
            if (!jobBeans.contains(item)) {
                jobBeans.add(item);
            }
        }

        customerTypeAdapter.notifyDataSetChanged();
        checkCurrentWork();
    }

    private void checkCurrentWork() {
        // ??????????????????????????????
        int postId = Integer.parseInt(child.getWorkPostId());
        // ????????????????????????????????????
        for (int i = 0; i < jobBeans.size(); i++) {
            if (jobBeans.get(i).getWorkPostId() == postId) {
                spJobType.setSelection(i, true);
                break;
            }
        }
    }

    /**
     * ??????????????????
     */
    private void deleteStudent() {
        remote.removeStudent(child.getId()).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((RemoveStudentResult removeStudentResult) -> {
                    Log.d(TAG, "accept: " + removeStudentResult.toString());

                    pbProgress.setVisibility(View.GONE);
                    tvTextStatus.setText("????????????");
                    tvTextStatus.setVisibility(View.VISIBLE);

                    refresh.update();

                    new Handler().postDelayed(() -> startFragmentWithReplace(R.id.ll_replace, new AdFragment()), 500);

                }, (Throwable throwable) -> Log.d(TAG, "accept: ?????????????????????????????????" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerLineAdapter extends BaseAdapter {
        private TextView tvText;

        @Override
        public int getCount() {
            return productionLineBeans.size();
        }

        @Override
        public ProductionLineBean getItem(int position) {
            return productionLineBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity, R.layout.item_spinner, null);
            } else {
                view = convertView;
            }
            initView(view);
            ProductionLineBean.DataBean dataBean = getItem(position).getData().get(0);
            int lineId = dataBean.getLineId();
            String line = "";
            switch (lineId) {
                case 1:
                    line = "?????????????????????";
                    break;
                case 2:
                    line = "MPV???????????????";
                    break;
                case 3:
                    line = "SUV???????????????";
                    break;
                default:
            }
            tvText.setText(line + "-??????" + (dataBean.getPos() + 1));
            return view;
        }

        private void initView(View view) {
            tvText = view.findViewById(R.id.tv_text);
        }
    }


    class CustomerTypeAdapter extends BaseAdapter {
        private TextView tvText;

        @Override
        public int getCount() {
            return jobBeans.size();
        }

        @Override
        public JobBean getItem(int position) {
            return jobBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity, R.layout.item_spinner, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvText.setText(getItem(position).getJobName());
            return view;
        }

        private void initView(View view) {
            tvText = view.findViewById(R.id.tv_text);
        }
    }
}
