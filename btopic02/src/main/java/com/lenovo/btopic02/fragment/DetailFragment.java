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
                    // 修改员工的岗位
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
            // 在线删除员工
            deleteStudent();
        });
    }

    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);

        // 初始化生产线
        productionLineBeans = new ArrayList<>();
        customerLineAdapter = new CustomerLineAdapter();
        spProductionLine.setAdapter(customerLineAdapter);

        // 初始化工作岗位
        jobBeans = new ArrayList<>();
        customerTypeAdapter = new CustomerTypeAdapter();
        spJobType.setAdapter(customerTypeAdapter);

        // 回显数据
        echoData();

        // 找到当前已存在的生产线
        getProductionLine(0);
    }

    /**
     * 回显数据
     */
    @SuppressLint("SetTextI18n")
    private void echoData() {
        ivIcon.setImageResource(R.drawable.pic_icon);
        tvName.setText(allPeopleBean.getPeopleName());
        tvContent.setText(allPeopleBean.getContent());
        tvCurrentHp.setText("当前体力：" + child.getPower());
        // 通过getType方法获取工作岗位
        tvJobType.setText("工作岗位：" + getType());
    }

    /**
     * 根据当前员工的岗位获取到对应具体岗位的名称
     *
     * @return 返回 岗位类型
     */
    private String getType() {
        int workPostId = Integer.parseInt(child.getWorkPostId());
        setCurrentLineType(workPostId);

        // 计算出岗位类别
        int type = workPostId - ((lineType - 1) * 4) - 1;
        switch (type) {
            case 0:
                return currentLineType + "工程师";
            case 1:
                return currentLineType + "工人";
            case 2:
                return currentLineType + "技术人员";
            case 3:
                return currentLineType + "质检员";
            default:
                return "";
        }
    }

    /**
     * 设置当前生产线的所属类别
     *
     * @param workPostId 工作岗位ID
     */
    private void setCurrentLineType(int workPostId) {
        // 已有数据 岗位类型，生产线类型 ---》工作类型
        if (workPostId >= 1 && workPostId <= 4) {
            // 轿车汽车
            currentLineType = "轿车汽车";
            lineType = 1;
        } else if (workPostId >= 5 && workPostId <= 8) {
            // MPV汽车
            currentLineType = "MPV汽车";
            lineType = 2;
        } else if (workPostId >= 9 && workPostId <= 12) {
            // SUV汽车
            currentLineType = "SUV汽车";
            lineType = 3;
        }
    }

    /**
     * 获取所有已经存在的生产线
     *
     * @param position 开始查找的位置
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
     * 继续获取数据
     *
     * @param position 当前位置
     */
    private void keepGoing(int position) {
        if (position < 3) {
            getProductionLine(position + 1);
        } else if (position == 3) {
            // 请求结束，刷新生产喜爱呢spinner
            customerLineAdapter.notifyDataSetChanged();

            // 找出当前员工所属生产线
            for (int i = 0; i < productionLineBeans.size(); i++) {
                // 匹配生产线ID
                if (productionLineBeans.get(i).getData().get(0).getId() == child.getUserProductionLineId()) {
                    // 选择生产线spinner默认选中
                    spProductionLine.setSelection(i, true);

                    // 设置所属生产线类别
                    /*setCurrentLineType(Integer.parseInt(child.getWorkPostId()));*/

                    // 获取当前员工所属生产线类型
                    int lineId = productionLineBeans.get(i).getData().get(0).getLineId();

                    // 设置工作岗位的spinner
                    setWorkPostType(new JobBean(getType(), Integer.parseInt(child.getWorkPostId())), lineId);
                    break;
                }
            }
        }
    }

    /**
     * 设置工作岗位spinner
     *
     * @param item   当前选择的工作岗位类型
     * @param lineId 当前生产线的类型
     */
    private void setWorkPostType(JobBean item, int lineId) {
        switch (lineId) {
            case 1:
                currentLineType = "轿车汽车";
                setJobBeans(item, 1, 2, 3, 4);
                break;
            case 2:
                currentLineType = "MPV汽车";
                setJobBeans(item, 5, 6, 7, 8);
                break;
            case 3:
                currentLineType = "SUV4汽车";
                setJobBeans(item, 9, 10, 11, 12);
                break;
            default:
        }
    }

    private void setProductionLine(int position) {
        // 获取选择后的生产线
        ProductionLineBean.DataBean dataBean = customerLineAdapter.getItem(position).getData().get(0);
        // 请求修改员工所处生产线
        changeLine(child.getId(), dataBean.getId(), (ChangeResultBean changeResultBean) -> {
            // 更新左侧已有员工列表
            refresh.update();
            lineType = dataBean.getLineId();
            // 获取当前选择的工作类型
            int selectedItemPosition = spJobType.getSelectedItemPosition();
            JobBean item = customerTypeAdapter.getItem(selectedItemPosition);
            Log.d(TAG, "setProductionLine: " + item);
            // 清空之前的工作类型
            jobBeans.clear();
            setWorkPostType(item, lineType);
        });
    }

    /**
     * 修改员工所属生产线
     *
     * @param id                   需要修改的员工ID
     * @param userProductionLineId 目标生产线ID
     * @param onNext               请求成功的回调
     */
    private void changeLine(int id, int userProductionLineId, Consumer<ChangeResultBean> onNext) {
        remote.changeLine(id, userProductionLineId).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, throwable -> Log.d(TAG, "accept: 修改员工所属生产线出现问题-----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 修改员工的岗位
     *
     * @param id         员工ID
     * @param workPostId 目标岗位
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
                    // 重新返回所属工作岗位
                    checkCurrentWork();
                    Log.d(TAG, "accept: 修改岗位出现问题" + throwable.getMessage());
                })
                .isDisposed();
    }

    /**
     * 设置岗位的JavaBean到集合中，每段代表一种类型的生产线
     *
     * @param item 员工当前的岗位
     * @param type 岗位类型1～12（1，2，3，4｜5，6，7，8｜9，10，11，12）
     */
    private void setJobBeans(JobBean item, int... type) {
        jobBeans.add(new JobBean(currentLineType + "工程师", type[0]));
        jobBeans.add(new JobBean(currentLineType + "工人", type[1]));
        jobBeans.add(new JobBean(currentLineType + "技术人员", type[2]));
        jobBeans.add(new JobBean(currentLineType + "检测人员", type[3]));
        if (item != null) {
            if (!jobBeans.contains(item)) {
                jobBeans.add(item);
            }
        }

        customerTypeAdapter.notifyDataSetChanged();
        checkCurrentWork();
    }

    private void checkCurrentWork() {
        // 获取当前员工所属岗位
        int postId = Integer.parseInt(child.getWorkPostId());
        // 设置工作岗位类型默认选中
        for (int i = 0; i < jobBeans.size(); i++) {
            if (jobBeans.get(i).getWorkPostId() == postId) {
                spJobType.setSelection(i, true);
                break;
            }
        }
    }

    /**
     * 删除学生员工
     */
    private void deleteStudent() {
        remote.removeStudent(child.getId()).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((RemoveStudentResult removeStudentResult) -> {
                    Log.d(TAG, "accept: " + removeStudentResult.toString());

                    pbProgress.setVisibility(View.GONE);
                    tvTextStatus.setText("删除成功");
                    tvTextStatus.setVisibility(View.VISIBLE);

                    refresh.update();

                    new Handler().postDelayed(() -> startFragmentWithReplace(R.id.ll_replace, new AdFragment()), 500);

                }, (Throwable throwable) -> Log.d(TAG, "accept: 删除学生呢员工出现问题" + throwable.getMessage()))
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
                    line = "轿车车型生产线";
                    break;
                case 2:
                    line = "MPV车型生产线";
                    break;
                case 3:
                    line = "SUV车型生产线";
                    break;
                default:
            }
            tvText.setText(line + "-位置" + (dataBean.getPos() + 1));
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
