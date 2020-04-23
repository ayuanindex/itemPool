package com.lenovo.btopic02.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic02.ApiService;
import com.lenovo.btopic02.MainActivity;
import com.lenovo.btopic02.R;
import com.lenovo.btopic02.bean.AllPeopleBean;
import com.lenovo.btopic02.bean.ChangeLineResultBean;
import com.lenovo.btopic02.bean.JobBean;
import com.lenovo.btopic02.bean.ProductionLineBean;
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
    private static String currentLineType = "";
    private int lineId = 0;
    private ArrayList<String> jobs;
    private CustomerTypeAdapter customerTypeAdapter;
    private boolean isInit = true;
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
        tvName = (TextView) view.findViewById(R.id.tv_name);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        spJobType = (Spinner) view.findViewById(R.id.sp_jobType);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvJobType = (TextView) view.findViewById(R.id.tv_jobType);
        tvCurrentHp = (TextView) view.findViewById(R.id.tv_currentHp);
        spProductionLine = (Spinner) view.findViewById(R.id.sp_productionLine);
        cardRemoveCurrent = (CardView) view.findViewById(R.id.card_removeCurrent);

        initListener();
    }

    private void initListener() {
        spProductionLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInit) {
                    ProductionLineBean.DataBean dataBean = customerLineAdapter.getItem(position).getData().get(0);
                    // 请求修改员工所处生产线
                    changeLine(child.getId(), dataBean.getId(), (ChangeLineResultBean changeLineResultBean) -> {
                        lineId = dataBean.getLineId();
                        switch (lineId) {
                            case 1:
                                currentLineType = "轿车汽车";
                                setJobBeans(1, 2, 3, 4);
                                break;
                            case 2:
                                currentLineType = "MPV汽车";
                                setJobBeans(5, 6, 7, 8);
                                break;
                            case 3:
                                currentLineType = "SUV汽车";
                                setJobBeans(9, 10, 11, 12);
                                break;
                            default:
                                break;
                        }
                    });
                }
                isInit = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cardRemoveCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);
        productionLineBeans = new ArrayList<>();

        customerLineAdapter = new CustomerLineAdapter();
        spProductionLine.setAdapter(customerLineAdapter);

        jobBeans = new ArrayList<>();
        customerTypeAdapter = new CustomerTypeAdapter();
        spJobType.setAdapter(customerTypeAdapter);

        // 回显数据
        echoData();

        // 找到当前已存在的生产线
        getProductionLine(0);
    }

    /**
     * 修改员工所属生产线
     *
     * @param id                   需要修改的员工ID
     * @param userProductionLineId 目标生产线ID
     * @param onNext               请求成功的回调
     */
    private void changeLine(int id, int userProductionLineId, Consumer<ChangeLineResultBean> onNext) {
        remote.changeLine(id, userProductionLineId).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, throwable -> Log.d(TAG, "accept: 修改员工所属生产线出现问题-----" + throwable.getMessage()))
                .isDisposed();
    }

    private void getProductionLine(int position) {
        remote.getProductionLineByPosition(position).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ProductionLineBean productionLineBean) -> {
                    productionLineBeans.add(productionLineBean);
                    keepGoing(position);
                }, (Throwable throwable) -> {
                    keepGoing(position);
                })
                .isDisposed();
    }

    private void setJobBeans(int... type) {
        jobBeans.clear();
        jobBeans.add(new JobBean(currentLineType + "工程师", type[0]));
        jobBeans.add(new JobBean(currentLineType + "工人", type[1]));
        jobBeans.add(new JobBean(currentLineType + "技术人员", type[2]));
        jobBeans.add(new JobBean(currentLineType + "检测人员", type[3]));
        customerTypeAdapter.notifyDataSetChanged();
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
            // 执行完毕
            customerLineAdapter.notifyDataSetChanged();
            for (int i = 0; i < productionLineBeans.size(); i++) {
                if (productionLineBeans.get(i).getData().get(0).getId() == child.getUserProductionLineId()) {
                    spProductionLine.setSelection(i, true);
                    break;
                }
            }
        }
    }

    /**
     * 回显数据
     */
    @SuppressLint("SetTextI18n")
    private void echoData() {
        ivIcon.setImageResource(R.drawable.pic_icon);
        tvName.setText(allPeopleBean.getPeopleName());
        tvContent.setText(allPeopleBean.getContent());
        tvJobType.setText("工作类型：" + getType(child));
        tvCurrentHp.setText("当前体力：" + child.getPower());

        Log.d(TAG, "echoData: " + currentLineType);

        // 选择显示当前所属生产线
        spProductionLine.setSelection(lineId - 1, true);
    }

    /**
     * 获取同一的工作岗位数据并添加到simple数据模型集合中对应的集合
     *
     * @param datum 需要分类的员工对象
     * @return 返回 岗位类型
     */
    private String getType(StudentStaffBean.DataBean datum) {
        int workPostId = Integer.parseInt(datum.getWorkPostId());
        int type = 0;
        if (workPostId >= 1 && workPostId <= 4) {
            // 轿车汽车
            currentLineType = "轿车汽车";
            lineId = 1;
            setJobBeans(1, 2, 3, 4);
            type = workPostId - 1;
        } else if (workPostId >= 5 && workPostId <= 8) {
            // MPV汽车
            currentLineType = "MPV汽车";
            lineId = 2;
            setJobBeans(5, 6, 7, 8);
            type = workPostId - 5;
        } else if (workPostId >= 9 && workPostId <= 12) {
            // SUV汽车
            currentLineType = "SUV汽车";
            lineId = 3;
            setJobBeans(9, 10, 11, 12);
            type = workPostId - 9;
        }

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
            tvText = (TextView) view.findViewById(R.id.tv_text);
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
            tvText = (TextView) view.findViewById(R.id.tv_text);
        }
    }
}
