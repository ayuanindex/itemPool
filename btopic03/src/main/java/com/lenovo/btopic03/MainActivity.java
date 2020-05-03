package com.lenovo.btopic03;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic03.bean.EnvironmentBean;
import com.lenovo.btopic03.bean.ResultBean;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {

    private LineChart lineChart;
    private TextView tvLightOpenText;
    private CardView cardLightOpen;
    private TextView tvLightCloseText;
    private CardView cardLightClose;
    private TextView tvAirConditioningOpenText;
    private CardView cardAirConditioningOpen;
    private TextView tvAirConditioningCloseText;
    private CardView cardAirConditioningClose;
    private ApiService remote;
    private ArrayList<Entry> yVals;
    private LineDataSet lineDataSet;
    private LineData lineData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override

    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        // 图标
        lineChart = (LineChart) findViewById(R.id.line_chart);
        // 电灯开关文字以及按钮控件
        tvLightOpenText = (TextView) findViewById(R.id.tv_light_openText);
        cardLightOpen = (CardView) findViewById(R.id.card_light_open);
        tvLightCloseText = (TextView) findViewById(R.id.tv_light_closeText);
        cardLightClose = (CardView) findViewById(R.id.card_light_close);
        // 空调开关文字以及按钮控件
        tvAirConditioningOpenText = (TextView) findViewById(R.id.tv_airConditioning_openText);
        cardAirConditioningOpen = (CardView) findViewById(R.id.card_airConditioning_open);
        tvAirConditioningCloseText = (TextView) findViewById(R.id.tv_airConditioning_closeText);
        cardAirConditioningClose = (CardView) findViewById(R.id.card_airConditioning_close);
    }

    @Override
    public void initEvent() {
        cardLightOpen.setOnClickListener((View v) -> {
            changeStatus(tvLightOpenText, tvLightCloseText, true);
            changeLightStatus(true);
        });

        cardLightClose.setOnClickListener((View v) -> {
            changeStatus(tvLightOpenText, tvLightCloseText, false);
            changeLightStatus(false);
        });

        cardAirConditioningOpen.setOnClickListener((View v) -> {
            changeStatus(tvAirConditioningOpenText, tvAirConditioningCloseText, true);
            changeAirConditioningStatus(1);
        });

        cardAirConditioningClose.setOnClickListener((View v) -> {
            changeStatus(tvAirConditioningOpenText, tvAirConditioningCloseText, false);
            changeAirConditioningStatus(2);
        });
    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        initChart();
        // 获取工厂环境信息
        initEnvironment();
    }

    /**
     * 初始化图表
     */
    private void initChart() {
        yVals = new ArrayList<>();
        int i1 = 19;
        for (int i = 0; i < i1; i++) {
            yVals.add(new Entry(i, 0));
        }
        lineDataSet = new LineDataSet(yVals, "耗电量");
        lineDataSet.setCircleColor(Color.parseColor("#3459B6"));
        lineDataSet.setColor(Color.parseColor("#3459B6"));
        lineDataSet.setDrawCircleHole(false);
        lineData = new LineData(lineDataSet);

        lineChart.getDescription().setText("");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void initEnvironment() {
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe((Long aLong) -> remote.getEnvironment(1).compose(bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .map((EnvironmentBean environmentBean) -> environmentBean.getData().get(0))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((EnvironmentBean.DataBean dataBean) -> {
                            Log.d(TAG, "accept: " + dataBean.toString());
                            // 添加图表数据
                            addData(dataBean);
                            // 设置灯光和空调的状态
                            setLightOrAirConditioningStatus(dataBean.getLightSwitch(), dataBean.getAcOnOff());
                            // 根据环境温度设置空调状态
                            autoChangeAirConditioningStatus(dataBean.getWorkshopTemp());
                        }, (Throwable throwable) -> Log.d(TAG, "accept: 获取工厂环境出现问题" + throwable.getMessage()))
                        .isDisposed(), (Throwable throwable) -> Log.d(TAG, "accept: 循环出现问题" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 设置空调状态
     *
     * @param workshopTemp 车间温度
     */
    private void autoChangeAirConditioningStatus(String workshopTemp) {
        int workShopTempInt = Integer.parseInt(workshopTemp.replace("℃", ""));
        changeStatus(tvAirConditioningOpenText, tvAirConditioningCloseText, workShopTempInt == 1);
        int criticalValue = 20;
        if (workShopTempInt > criticalValue) {
            changeAirConditioningStatus(1);
        } else {
            changeAirConditioningStatus(2);
        }
    }

    /**
     * 添加图表数据
     *
     * @param dataBean 需要添加的数据
     */
    private void addData(EnvironmentBean.DataBean dataBean) {
        int maxIndex = 20;
        if (yVals.size() > maxIndex) {
            yVals.remove(0);
        }

        if (dataBean != null) {
            String powerConsume = dataBean.getPowerConsume();
            int powerConsumeInt = Integer.parseInt(powerConsume);
            for (int i = 0; i < yVals.size(); i++) {
                Entry entry = yVals.get(i);
                entry.setX(i);
                yVals.set(i, entry);
            }
            if (yVals.size() > maxIndex) {
                yVals.add(new Entry(19, powerConsumeInt));
            } else {
                yVals.add(new Entry(yVals.size() - 1, powerConsumeInt));
            }
        }

        lineDataSet.notifyDataSetChanged();
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    /**
     * 切换按钮的状态
     *
     * @param openText  开的文字
     * @param closeText 关的文字
     */
    private void changeStatus(TextView openText, TextView closeText, boolean status) {
        if (status) {
            openText.setTextColor(Color.parseColor("#FFFFFF"));
            closeText.setTextColor(Color.parseColor("#606060"));
            openText.setBackgroundColor(Color.parseColor("#298BFF"));
            closeText.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            openText.setTextColor(Color.parseColor("#606060"));
            closeText.setTextColor(Color.parseColor("#FFFFFF"));
            openText.setBackgroundColor(Color.parseColor("#FFFFFF"));
            closeText.setBackgroundColor(Color.parseColor("#298BFF"));
        }
    }

    /**
     * 设置灯光跟空调的状态
     *
     * @param aSwitch     空调状态：0表示关，1表示冷风，2表示暖风
     * @param lightSwitch 灯光状态：1表示开，0表示关
     */
    private void setLightOrAirConditioningStatus(int lightSwitch, int aSwitch) {
        changeStatus(tvLightOpenText, tvLightCloseText, lightSwitch == 1);

        switch (aSwitch) {
            case 0:
                tvAirConditioningOpenText.setTextColor(Color.parseColor("#606060"));
                tvAirConditioningCloseText.setTextColor(Color.parseColor("#606060"));
                tvAirConditioningOpenText.setBackgroundColor(Color.WHITE);
                tvAirConditioningCloseText.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                changeStatus(tvAirConditioningOpenText, tvAirConditioningCloseText, true);
                break;
            case 2:
                changeStatus(tvAirConditioningOpenText, tvAirConditioningCloseText, false);
                break;
            default:
        }
    }

    /**
     * 修改空调的状态
     *
     * @param isOpen true表示开启，false表示关闭
     */
    private void changeLightStatus(boolean isOpen) {
        remote.changeLightStatus(1, isOpen ? 1 : 0).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (ResultBean.DataBean dataBean) -> Log.d(TAG, "accept: " + dataBean.toString()),
                        (Throwable throwable) -> Log.d(TAG, "accept: 修改开关在状态出现问题-------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 修改空调的状态
     *
     * @param status 状态
     */
    private void changeAirConditioningStatus(int status) {
        remote.changeLightStatus(1, status).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (ResultBean.DataBean dataBean) -> Log.d(TAG, "accept: " + dataBean.toString()),
                        (Throwable throwable) -> Log.d(TAG, "accept: 修改空调状态出现错误----" + throwable.getMessage())
                )
                .isDisposed();

    }
}
