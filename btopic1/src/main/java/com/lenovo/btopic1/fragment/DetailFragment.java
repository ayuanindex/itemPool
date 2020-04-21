package com.lenovo.btopic1.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic1.ApiService;
import com.lenovo.btopic1.R;
import com.lenovo.btopic1.bean.ProductionLineBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class DetailFragment extends BaseFragment {
    private final int position;
    private TextView tvPageStatus;
    private TextView tvLineName;
    private TextView tvIsAi;
    private TextView tvLineStatus;
    private TextView tvLineDescription;
    private GridView gvStaff;
    private ListView lvList;
    private ApiService remote;
    private LinearLayout llContent;

    public DetailFragment(int position) {
        this.position = position;
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
    }

    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);
        getProductionLine();
    }

    /**
     * 获取当前位置的生产线信息
     */
    @SuppressLint("CheckResult")
    private void getProductionLine() {
        Disposable subscribe = remote.getAllProductionLine(position)
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
                    Log.d(TAG, "getProductionLine: " + dataBean.toString());
                    // 将生产线信息展现到控件中
                    setInComponents(dataBean);
                }, (Throwable throwable) -> {
                    tvPageStatus.setVisibility(View.VISIBLE);
                    llContent.setVisibility(View.GONE);
                    Log.d(TAG, "getProductionLine: 按位置获取生产线信息出现错误");
                    Log.d(TAG, "getProductionLine: " + throwable.getMessage());
                });
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
    }
}
