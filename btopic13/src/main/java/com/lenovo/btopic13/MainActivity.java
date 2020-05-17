package com.lenovo.btopic13;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic13.bean.ProductionLineBean;
import com.lenovo.btopic13.bean.SwitchAiResultBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {
    private GridView gvList;
    private ListView lvList;
    private ApiService remote;
    private ArrayList<ProductionLineBean.DataBean> productionLines;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<String> labels;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        gvList = findViewById(R.id.gv_list);
        lvList = findViewById(R.id.lv_list);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        // 初始化待显示的生产线集合
        productionLines = new ArrayList<>(4);
        int j = 4;
        for (int i = 0; i < j; i++) {
            ProductionLineBean.DataBean e = new ProductionLineBean.DataBean();
            e.setPosition(i);
            productionLines.add(e);
        }

        gridViewAdapter = new GridViewAdapter();
        gvList.setAdapter(gridViewAdapter);

        labels = new ArrayList<>();
        int length = 10;
        for (int i = 0; i < length; i++) {
            labels.add("其他题目的跳转");
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter();
        lvList.setAdapter(listViewAdapter);

        getAllProductionLine();
    }

    private void getAllProductionLine() {
        remote.getAllProductionLine().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((Function<ProductionLineBean, List<ProductionLineBean.DataBean>>) (ProductionLineBean productionLineBean) -> {
                    for (int i = 0; i < productionLines.size(); i++) {
                        for (ProductionLineBean.DataBean datum : productionLineBean.getData()) {
                            if (datum.getPosition() == productionLines.get(i).getPosition()) {
                                productionLines.set(i, datum);
                            }
                        }
                    }
                    return productionLines;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<ProductionLineBean.DataBean> dataBeans) -> {
                    for (ProductionLineBean.DataBean productionLine : productionLines) {
                        if (productionLine.getUserWorkId() == 0) {
                            productionLine.setPageStatus(0);
                        } else {
                            productionLine.setPageStatus(2);
                        }
                    }
                    gridViewAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 查询所有的生产线出现错误------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 切换生产线的Ai状态
     *
     * @param item 需要进行操作的对象
     */
    private void switchAi(ProductionLineBean.DataBean item) {
        remote.changeAiStatus(item.getId(), "1".equals(item.getIsAI()) ? 0 : 1).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(SwitchAiResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((SwitchAiResultBean.DataBean dataBean) -> {
                    item.setIsAI(dataBean.getIsAI());
                    item.setPageStatus(2);
                    gridViewAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 修改Ai状态发生问题-----------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 创建生产线
     *
     * @param item 需要去填充显示的对象
     * @param type 生产线类型
     */
    private void createProductionLine(ProductionLineBean.DataBean item, int type) {
        remote.createProductionLine(type, item.getPosition()).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(SwitchAiResultBean::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((String s) -> {
                    String message = "创建学生生产线成功";
                    if (message.equals(s)) {
                        getAllProductionLine();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 创建生产线出现问题------------- " + throwable.getMessage()))
                .isDisposed();
    }

    class ListViewAdapter extends BaseAdapter {
        private TextView tvLabelName;

        @Override
        public int getCount() {
            return labels.size();
        }

        @Override
        public String getItem(int position) {
            return labels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_listview, null);
            } else {
                view = convertView;
            }

            initView(view);
            tvLabelName.setText(getItem(position));
            return view;
        }

        private void initView(View view) {
            tvLabelName = view.findViewById(R.id.tv_labelName);
        }
    }


    class GridViewAdapter extends BaseAdapter {
        private RelativeLayout rlContent;
        private ImageView ivCarIcon;
        private TextView tvCycle;
        private TextView tvLineName;
        private CardView cardParent;
        private CardView cardChild;
        private TextView tvNumber;
        private LinearLayout llChangeAi;
        private Switch swChange;
        private LinearLayout llNull;
        private CardView cardMpv;

        private CardView cardSuv;

        private CardView cardCar;

        @Override
        public int getCount() {
            return productionLines.size();
        }

        @Override
        public ProductionLineBean.DataBean getItem(int position) {
            return productionLines.get(position);
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
                view = View.inflate(MainActivity.this, R.layout.item_gridview, null);
            } else {
                view = convertView;
            }
            initView(view);
            ProductionLineBean.DataBean item = getItem(position);
            int pageStatus = item.getPageStatus();

            // 设置页面状态
            setPageState(pageStatus);

            // 设置图片和生产线名称
            setIconAndName(item);

            // 设置圆点颜色
            setCycleColor(item);

            // 设置当前生产线的进度
            int stageId = item.getStageId();
            tvNumber.setText(stageId + "");
            float progress = 0f;

            if (stageId >= 5 && stageId <= 24) {
                progress = ((20 - (24 - stageId)) / 20f);
            } else if (stageId >= 25 && stageId <= 44) {
                progress = ((20 - (44 - stageId)) / 20f);
            } else if (stageId >= 45 && stageId <= 64) {
                progress = ((20 - (64 - stageId)) / 20f);
            }
            int width = (int) (cardParent.getLayoutParams().width * progress);
            ViewGroup.LayoutParams layoutParams = cardChild.getLayoutParams();
            layoutParams.width = width;
            cardChild.setLayoutParams(layoutParams);

            // 设置Ai切换状态界面的开关状态
            swChange.setChecked("1".equals(item.getIsAI()));

            rlContent.setOnClickListener((View v) -> {
                item.setPageStatus(1);
                notifyDataSetChanged();
            });

            llChangeAi.setOnClickListener((View v) -> {
                item.setPageStatus(2);
                notifyDataSetChanged();
            });

            swChange.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                // 切换AI
                switchAi(item);
            });

            int visibility = llNull.getVisibility();
            if (visibility == View.VISIBLE) {
                cardCar.setOnClickListener((View v) -> createProductionLine(item, 1));

                cardMpv.setOnClickListener((View v) -> createProductionLine(item, 2));

                cardSuv.setOnClickListener((View v) -> createProductionLine(item, 3));
            }
            return view;
        }

        private void setCycleColor(ProductionLineBean.DataBean item) {
            String colorStr = "#000000";
            switch (item.getType()) {
                case 0:
                    colorStr = "#FFB326";
                    break;
                case 1:
                    colorStr = "#8B191D";
                    break;
                case 2:
                    colorStr = "#38D971";
                    break;
                case 3:
                    colorStr = "#9B23F1";
                    break;
                default:
            }
            tvCycle.setBackgroundColor(Color.parseColor(colorStr));
        }

        private void setIconAndName(ProductionLineBean.DataBean item) {
            String lineName = "";
            switch (item.getProductionLineId()) {
                case 1:
                    lineName = "轿车汽车生产线";
                    ivCarIcon.setImageResource(R.drawable.pic_01);
                    break;
                case 2:
                    lineName = "MPV汽车生产线";
                    ivCarIcon.setImageResource(R.drawable.pic_02);
                    break;
                case 3:
                    lineName = "SUV汽车生产线";
                    ivCarIcon.setImageResource(R.drawable.pic_03);
                    break;
                default:
            }
            tvLineName.setText(lineName);
        }

        private void setPageState(int pageStatus) {
            switch (pageStatus) {
                case 0:
                    llNull.setVisibility(View.VISIBLE);
                    llChangeAi.setVisibility(View.INVISIBLE);
                    rlContent.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    llNull.setVisibility(View.INVISIBLE);
                    llChangeAi.setVisibility(View.VISIBLE);
                    rlContent.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    llNull.setVisibility(View.INVISIBLE);
                    llChangeAi.setVisibility(View.INVISIBLE);
                    rlContent.setVisibility(View.VISIBLE);
                    break;
                default:
            }
        }

        private void initView(View view) {
            rlContent = view.findViewById(R.id.rl_content);
            llChangeAi = view.findViewById(R.id.ll_changeAi);
            llNull = view.findViewById(R.id.ll_null);
            ivCarIcon = view.findViewById(R.id.iv_carIcon);
            tvCycle = view.findViewById(R.id.tv_cycle);
            tvLineName = view.findViewById(R.id.tv_lineName);
            cardParent = view.findViewById(R.id.cardParent);
            cardChild = view.findViewById(R.id.cardChild);
            tvNumber = view.findViewById(R.id.tv_number);
            swChange = view.findViewById(R.id.sw_change);
            cardMpv = view.findViewById(R.id.cardMpv);
            cardSuv = view.findViewById(R.id.cardSuv);
            cardCar = view.findViewById(R.id.cardCar);
        }

    }

}
