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

        // ?????????????????????
        outLayList = new ArrayList<>();
        allOutLayList = new ArrayList<>();

        // ??????????????????????????????
        getAllOutLayLog();
    }

    /**
     * ??????????????????????????????
     */
    private void getAllOutLayLog() {
        remote.outlayLog().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(GoldCoinExpenditureBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    llLoading.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                    // ??????????????????
                    allOutLayList = dataBeans;
                    // ?????????????????????
                    initLineChart();
                    if (outLayList.size() == 0) {
                        lineChart.setVisibility(View.GONE);
                        tvPageStatus.setVisibility(View.VISIBLE);
                    } else {
                        lineChart.setVisibility(View.VISIBLE);
                        tvPageStatus.setVisibility(View.GONE);
                    }
                    Log.d(TAG, "accept: " + dataBeans.toString());
                }, (Throwable throwable) -> Log.d(TAG, "accept: ??????????????????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ?????????????????????
     */
    private void initLineChart() {
        outLayList.clear();

        // ?????????????????????
        Collections.reverse(allOutLayList);


        // ???????????????20?????????type???????????????
        for (int i = 0; i < allOutLayList.size(); i++) {
            if (allOutLayList.get(i).getType() == type) {
                outLayList.add(allOutLayList.get(i));
                if (outLayList.size() == 20) {
                    break;
                }
            }
        }

        // ?????????????????????
        Collections.reverse(allOutLayList);

        // ???????????????????????????????????????
        if (outLayList.size() == 0) {
            return;
        }

        // ????????????????????????
        Collections.reverse(outLayList);

        // ????????????
        ArrayList<Entry> remainingCoins = new ArrayList<>();

        // ??????
        ArrayList<Entry> golds = new ArrayList<>();

        // ???????????????????????????????????????
        int maxLength = 0;

        // ????????????type??????????????????????????????????????????????????????????????????
        // ??????????????????20??????????????????????????????????????????????????????20????????????20?????????
        maxLength = Math.min(outLayList.size(), 20);

        // ????????????
        for (int i = 0; i < outLayList.size(); i++) {
            remainingCoins.add(new Entry(i, outLayList.get(i).getEndPrice()));

            golds.add(new Entry(i, outLayList.get(i).getPrice()));
        }

        LineDataSet surplusLineDataSet = new LineDataSet(remainingCoins, "????????????");
        LineDataSet goldLineDataSet = new LineDataSet(golds, "??????");

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

        // ??????Y???
        lineChart.getAxisRight().setEnabled(false);
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setTextSize(14f);

        // ??????X???
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);

        // ???????????????????????????
        lineChart.getDescription().setEnabled(false);

        // ?????????????????????
        lineChart.getLegend().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
