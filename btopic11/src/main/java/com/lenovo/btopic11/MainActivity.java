package com.lenovo.btopic11;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic11.bean.GoldCoinExpenditureBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {

    private LineChart lineChart;
    private Spinner spDataType;
    private SwitchMaterial swDataLabel;
    private ApiService remote;
    private List<GoldCoinExpenditureBean.DataBean> outLayList;
    private List<GoldCoinExpenditureBean.DataBean> allOutLayList;
    private LinearLayout llLoading;
    private LinearLayout llContent;
    private boolean isInit = true;
    private int type = 0;
    private LinearLayout llLegend;
    private TextView tvPageStatus;

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
        lineChart = (LineChart) findViewById(R.id.lineChart);
        spDataType = (Spinner) findViewById(R.id.sp_dataType);
        swDataLabel = (SwitchMaterial) findViewById(R.id.sw_dataLabel);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        llLegend = (LinearLayout) findViewById(R.id.ll_legend);
        tvPageStatus = (TextView) findViewById(R.id.tv_pageStatus);
    }

    @Override
    protected void initEvent() {
        spDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInit) {
                    String[] stringArray = getResources().getStringArray(R.array.types);
                    String s = stringArray[position];
                    type = position;
                    initLineChart();
                    if (outLayList.size() == 0) {
                        lineChart.setVisibility(View.GONE);
                        tvPageStatus.setVisibility(View.VISIBLE);
                    } else {
                        lineChart.setVisibility(View.VISIBLE);
                        tvPageStatus.setVisibility(View.GONE);
                    }
                    Log.d(TAG, "onItemSelected: " + position);
                }
                isInit = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        swDataLabel.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            llLegend.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        // 用于显示的集合
        outLayList = new ArrayList<>();
        allOutLayList = new ArrayList<>();

        // 获取所有金币支出日志
        getAllOutLayLog();
    }

    /**
     * 获取所有金币支出日志
     */
    private void getAllOutLayLog() {
        remote.outlayLog().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(GoldCoinExpenditureBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    llLoading.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                    // 切换界面状态
                    allOutLayList = dataBeans;
                    // 初始化图表数据
                    initLineChart();
                    if (outLayList.size() == 0) {
                        lineChart.setVisibility(View.GONE);
                        tvPageStatus.setVisibility(View.VISIBLE);
                    } else {
                        lineChart.setVisibility(View.VISIBLE);
                        tvPageStatus.setVisibility(View.GONE);
                    }
                    Log.d(TAG, "accept: " + dataBeans.toString());
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取全部金币支出日志出现问题----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 初始化图表数据
     */
    private void initLineChart() {
        outLayList.clear();

        // 将集合逆序输出
        Collections.reverse(allOutLayList);


        // 取出最近的20条指定type类型的数据
        for (int i = 0; i < allOutLayList.size(); i++) {
            if (allOutLayList.get(i).getType() == type) {
                outLayList.add(allOutLayList.get(i));
                if (outLayList.size() == 20) {
                    break;
                }
            }
        }

        // 恢复到正常排序
        Collections.reverse(allOutLayList);

        // 如果没有数据，则退出此方法
        if (outLayList.size() == 0) {
            return;
        }

        // 将取出的数据逆序
        Collections.reverse(outLayList);

        // 剩余金币
        ArrayList<Entry> remainingCoins = new ArrayList<>();

        // 金币
        ArrayList<Entry> golds = new ArrayList<>();

        // 设定显示在图表中数据的个数
        int maxLength = 0;

        // 根据对应type类型的消费数据的数量来决定需要显示数据的数量
        // 如果长度不足20则显示集合中所有的数据，如果长度大于20则只显示20条数据
        maxLength = Math.min(outLayList.size(), 20);

        // 填充条目
        for (int i = 0; i < outLayList.size(); i++) {
            remainingCoins.add(new Entry(i, outLayList.get(i).getEndPrice()));

            golds.add(new Entry(i, outLayList.get(i).getPrice()));
        }

        LineDataSet surplusLineDataSet = new LineDataSet(remainingCoins, "剩余金币");
        LineDataSet goldLineDataSet = new LineDataSet(golds, "金币");

        surplusLineDataSet.setCircleColor(Color.parseColor("#355BB6"));
        surplusLineDataSet.setColor(Color.parseColor("#355BB6"));
        surplusLineDataSet.setDrawCircleHole(false);
        surplusLineDataSet.setCircleRadius(4f);

        goldLineDataSet.setCircleColor(Color.parseColor("#E56726"));
        goldLineDataSet.setColor(Color.parseColor("#E56726"));
        goldLineDataSet.setDrawCircleHole(false);
        goldLineDataSet.setCircleRadius(4f);

        LineData lineData = new LineData();
        lineData.addDataSet(surplusLineDataSet);
        lineData.addDataSet(goldLineDataSet);

        // 设置Y轴
        lineChart.getAxisRight().setEnabled(false);
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setTextSize(14f);

        // 设置X轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);

        // 设置图表描述不可用
        lineChart.getDescription().setEnabled(false);

        // 设置图例不可用
        lineChart.getLegend().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
