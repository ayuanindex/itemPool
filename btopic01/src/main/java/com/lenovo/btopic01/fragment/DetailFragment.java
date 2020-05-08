package com.lenovo.btopic01.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic01.ApiService;
import com.lenovo.btopic01.MainActivity;
import com.lenovo.btopic01.R;
import com.lenovo.btopic01.bean.CarsBean;
import com.lenovo.btopic01.bean.LinePeopleBean;
import com.lenovo.btopic01.bean.PeopleBean;
import com.lenovo.btopic01.bean.ProductionLineBean;
import com.lenovo.btopic01.bean.StatusBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class DetailFragment extends BaseFragment {
    private final int position;
    private final MainActivity.ResultData resultData;
    private TextView tvPageStatus;
    private TextView tvLineName;
    private TextView tvIsAi;
    private TextView tvLineStatus;
    private TextView tvLineDescription;
    private GridView gvStaff;
    private ListView lvList;
    private ApiService remote;
    private LinearLayout llContent;
    private ProductionLineBean.DataBean dataBean;
    private List<PeopleBean.DataBean> allPeopleList;
    private List<PeopleBean.DataBean> peoples;
    private CustomerGridAdapter customerGridAdapter;
    private List<CarsBean.DataBean> cars;
    private CustomerListAdapter customerListAdapter;
    private View tvTip;

    public DetailFragment(int position, MainActivity.ResultData resultData) {
        this.position = position;
        this.resultData = resultData;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(View view) {
        tvPageStatus = view.findViewById(R.id.tv_pageStatus);
        tvLineName = view.findViewById(R.id.tv_lineName);
        tvIsAi = view.findViewById(R.id.tv_isAi);
        tvLineStatus = view.findViewById(R.id.tv_lineStatus);
        tvLineDescription = view.findViewById(R.id.tv_lineDescription);
        gvStaff = view.findViewById(R.id.gv_staff);
        lvList = view.findViewById(R.id.lv_list);
        llContent = view.findViewById(R.id.ll_content);
        tvTip = view.findViewById(R.id.tv_tip);

        initListener();
    }

    /**
     * 获取控件监听
     */
    private void initListener() {
        tvPageStatus.setOnClickListener((View v) -> showDialog());

        gvStaff.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            PeopleBean.DataBean item = customerGridAdapter.getItem(position);
            showPeopleDialog(item);
        });
    }

    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);
        // 从接口中获取所有人员信息
        allPeopleList = resultData.getResult();

        // 初始化gridview
        peoples = new ArrayList<>(0);
        customerGridAdapter = new CustomerGridAdapter();
        gvStaff.setAdapter(customerGridAdapter);

        cars = new ArrayList<>();
        customerListAdapter = new CustomerListAdapter();
        lvList.setAdapter(customerListAdapter);

        // 获取生产线的信息
        getProductionLine();
    }

    /**
     * 获取当前位置的生产线信息
     */
    @SuppressLint("CheckResult")
    private void getProductionLine() {
        remote.getAllProductionLine(position)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((ProductionLineBean productionLineBean) -> {
                    if (productionLineBean.getData().size() > 0) {
                        return productionLineBean.getData().get(0);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ProductionLineBean.DataBean dataBean) -> {
                    this.dataBean = dataBean;
                    Log.d(TAG, "getProductionLine: " + dataBean.toString());
                    // 将生产线信息展现到控件中
                    setInComponents(dataBean);
                    // 获取成品车辆
                    finishedVehicles(dataBean.getId());
                }, (Throwable throwable) -> {
                    tvPageStatus.setVisibility(View.VISIBLE);
                    llContent.setVisibility(View.GONE);
                    Log.d(TAG, "getProductionLine: 按位置获取生产线信息出现错误");
                    Log.d(TAG, "getProductionLine: " + throwable.getMessage());
                }).isDisposed();
    }

    /**
     * 展现生产新数据至组件中
     *
     * @param dataBean 需要展现的数据
     */
    private void setInComponents(ProductionLineBean.DataBean dataBean) {
        if (dataBean == null) {
            tvPageStatus.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
            return;
        }

        String lineName = "";
        String des = "";
        switch (dataBean.getProductionLineId()) {
            case 1:
                lineName = "轿车车型生产线";
                des = "生产轿车汽车";
                break;
            case 2:
                lineName = "MPV车型生产线";
                des = "生产MPV汽车";
                break;
            case 3:
                lineName = "SUV车型生产线";
                des = "生产SUV汽车";
                break;
            default:
                break;
        }
        tvLineName.setText(lineName);
        tvLineDescription.setText(des);

        tvIsAi.setText(dataBean.getIsAI() == 0 ? "" : "AI");

        String status = "";
        String textColor = "#000000";
        switch (dataBean.getType()) {
            case 0:
                status = "建设中";
                textColor = "#7481FD";
                break;
            case 1:
                status = "缺原材料";
                textColor = "#FA0019";
                break;
            case 2:
                status = "生产中";
                textColor = "#2BDB3A";
                break;
            case 3:
                status = "库存已满";
                textColor = "#D1CD73";
                break;
            default:
                break;
        }
        tvLineStatus.setText(status);
        tvLineStatus.setTextColor(Color.parseColor(textColor));

        // 获取生产线人员信息
        getProductionLinePeople();
    }

    /**
     * 成品车辆列表
     *
     * @param id 生产线ID
     */
    private void finishedVehicles(int id) {
        this.cars = resultData.getCars(id);
        if (cars.size() == 0) {
            lvList.setVisibility(View.GONE);
            tvTip.setVisibility(View.VISIBLE);
        } else {
            lvList.setVisibility(View.VISIBLE);
            tvTip.setVisibility(View.GONE);
            customerListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取生产线人员信息
     */
    @SuppressLint("CheckResult")
    private void getProductionLinePeople() {
        remote.getLinePeople(dataBean.getId())
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((LinePeopleBean linePeopleBean) -> {
                    // 获取当前生产线所有员工的JavaBean集合
                    List<LinePeopleBean.DataBean> data = linePeopleBean.getData();
                    for (LinePeopleBean.DataBean datum : data) {
                        for (PeopleBean.DataBean bean : allPeopleList) {
                            if (datum.getPeopleId() == bean.getId()) {
                                peoples.add(bean);
                            }
                        }
                    }
                    return peoples;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<PeopleBean.DataBean> peoples) -> {
                    this.peoples = peoples;
                    customerGridAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "getProductionLinePeople: 获取生产线人员信息出现问题----------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 弹出对话框创建生产线
     */
    private void showDialog() {
        AtomicInteger check = new AtomicInteger();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择类型");
        CharSequence[] items = {"轿车车型生产线", "MPV车型生产线", "SUV车型生产线"};
        builder.setSingleChoiceItems(items, 0, (DialogInterface dialog, int which) -> {
            check.set(which);
            Log.d(TAG, "onClick:" + which);
        });
        builder.setPositiveButton("确定", (DialogInterface dialog, int which) -> {
            addProductionLine(check.intValue() + 1);
            dialog.dismiss();
        });

        builder.setNegativeButton("取消", (DialogInterface dialog, int which) -> dialog.dismiss());
        builder.show();
    }

    /**
     * 显示人员信息
     *
     * @param item 选择的员工条目
     */
    @SuppressLint("SetTextI18n")
    private void showPeopleDialog(PeopleBean.DataBean item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        AlertDialog alertDialog = builder.create();
        View inflate = View.inflate(mActivity, R.layout.dialog_peopledetail, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.ivIcon.setImageResource(R.drawable.pic_icon);
        viewHolder.tvName.setText(item.getPeopleName());
        viewHolder.tvSalary.setText("薪资：" + item.getGold());
        viewHolder.tvStamina.setText("" + item.getHp());

        String type = "";
        switch (item.getStatus()) {
            case 0:
                type = "工程师";
                break;
            case 1:
                type = "工人";
                break;
            case 2:
                type = "技术人员";
                break;
            case 3:
                type = "检测人员";
                break;
            default:
                break;
        }
        viewHolder.tvType.setText(type);
        // 算出百分比
        float percentage = (item.getHp() / 100f);
        ViewGroup.LayoutParams cardViewLayoutParams = viewHolder.cardView.getLayoutParams();
        int width = viewHolder.rlParent.getLayoutParams().width;
        cardViewLayoutParams.width = (int) (width * percentage);
        viewHolder.cardView.setLayoutParams(cardViewLayoutParams);

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    /**
     * dialog的ViewHodler
     */
    public static
    class ViewHolder {
        public View rootView;
        public ImageView ivIcon;
        public TextView tvName;
        public TextView tvType;
        public TextView tvSalary;
        public TextView tvStamina;
        public CardView cardView;
        public RelativeLayout rlParent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivIcon = rootView.findViewById(R.id.iv_icon);
            this.tvName = rootView.findViewById(R.id.tv_name);
            this.tvType = rootView.findViewById(R.id.tv_type);
            this.tvSalary = rootView.findViewById(R.id.tv_salary);
            this.cardView = rootView.findViewById(R.id.card);
            this.tvStamina = rootView.findViewById(R.id.tv_stamina);
            this.rlParent = rootView.findViewById(R.id.rl_parent);
        }
    }

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型
     */
    @SuppressLint("CheckResult")
    private void addProductionLine(int lineId) {
        remote.createProduction(lineId, position)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((StatusBean statusBean) -> {
                    String msg = "该位置已存在生产线";
                    if (!msg.equals(statusBean.getMessage())) {
                        // 创建成功冲洗获取当前位置的生产线
                        getProductionLine();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "addProductionLine: 常见生产线出现问题---------" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerGridAdapter extends BaseAdapter {
        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvDes;

        @Override
        public int getCount() {
            return peoples.size();
        }

        @Override
        public PeopleBean.DataBean getItem(int position) {
            return peoples.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity, R.layout.item_personnel_grid, null);
            } else {
                view = convertView;
            }
            initView(view);
            ivIcon.setImageResource(R.drawable.pic_icon);
            tvName.setText(getItem(position).getPeopleName());
            tvDes.setText(getItem(position).getContent());
            return view;
        }

        private void initView(View view) {
            ivIcon = view.findViewById(R.id.iv_icon);
            tvName = view.findViewById(R.id.tv_name);
            tvDes = view.findViewById(R.id.tv_des);
        }
    }

    class CustomerListAdapter extends BaseAdapter {
        private TextView tvId;
        private TextView tvCarType;
        private TextView tvProductionLine;
        private TextView tvCount;
        private TextView tvDes;

        @Override
        public int getCount() {
            return cars.size();
        }

        @Override
        public CarsBean.DataBean getItem(int position) {
            return cars.get(position);
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
                view = View.inflate(mActivity, R.layout.item_cars_list, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvId.setText(getItem(position).getId() + "");
            tvCarType.setText(getItem(position).getCarType().getCarTypeName());
            tvProductionLine.setText(tvLineName.getText().toString().trim());
            tvCount.setText(getItem(position).getNum() + "");
            tvDes.setText(getItem(position).getCarType().getContent());
            return view;
        }

        private void initView(View view) {
            tvId = view.findViewById(R.id.tv_id);
            tvCarType = view.findViewById(R.id.tv_carType);
            tvProductionLine = view.findViewById(R.id.tv_productionLine);
            tvCount = view.findViewById(R.id.tv_count);
            tvDes = view.findViewById(R.id.tv_des);
        }
    }

}
