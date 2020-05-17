package com.lenovo.btopic14;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic14.bean.SurroundingsBean;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {

    private LineChart chart;
    private TextView tvMax;
    private TextView tvConsume;
    private TextView tvSupply;
    private ApiService remote;
    private ArrayList<SurroundingsBean.DataBean> dataBeans;
    private float coefficient = 1.2f;
    private int peak = 0;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        chart = findViewById(R.id.chart);
        tvMax = findViewById(R.id.tv_max);
        tvConsume = findViewById(R.id.tv_consume);
        tvSupply = findViewById(R.id.tv_supply);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        dataBeans = new ArrayList<>();

        getSurroundings();
    }

    @SuppressLint("SetTextI18n")
    private void getSurroundings() {
        Observable.interval(2, TimeUnit.SECONDS).compose(bindToLifecycle())
                .subscribe((Long aLong) -> {
                    Log.d(TAG, "accept: ------------");
                    remote.getSurroundings(1).compose(bindToLifecycle())
                            .subscribeOn(Schedulers.io())
                            .map((SurroundingsBean surroundingsBean) -> surroundingsBean.getData().get(0))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(dataBean -> {
                                int maxLen = 20;
                                if (dataBeans.size() < maxLen) {
                                    dataBeans.add(dataBean);
                                } else {
                                    dataBeans.remove(0);
                                    dataBeans.add(dataBean);
                                }

                                // 计算系数
                                coefficient = (dataBean.getLightSwitch() == 1 ? 0 : 2.1f) + (dataBean.getAcOnOff() == 0 ? 0 : dataBean.getAcOnOff() == 1 ? 3.2f : 4.3f);

                                tvConsume.setText(dataBean.getPowerConsume() + "kw/h");
                                tvSupply.setText((Integer.parseInt(dataBean.getPowerConsume()) * coefficient) + "kw/h");
                                int max = Math.max(peak, Integer.parseInt(dataBean.getPowerConsume()));
                                tvMax.setText(max + "kw/h");

                                initChart();
                            }, (Throwable throwable) -> Log.d(TAG, "accept: 获取工厂环境出现问题------------" + throwable.getMessage()))
                            .isDisposed();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 循环出现错误-----" + throwable.getMessage()))
                .isDisposed();
    }

    private void initChart() {
        ArrayList<Entry> consume = new ArrayList<>();
        ArrayList<Entry> supply = new ArrayList<>();

        for (int i = 0; i < dataBeans.size(); i++) {
            int consumePower = Integer.parseInt(dataBeans.get(i).getPowerConsume());
            int supplyPower = (int) (consumePower * coefficient);

            consume.add(new Entry(i, consumePower));
            supply.add(new Entry(i, supplyPower));
        }

        LineDataSet consumeLineDataSet = new LineDataSet(consume, "电力消耗");
        consumeLineDataSet.setColor(Color.parseColor("#D57B1A"));
        consumeLineDataSet.setCircleColor(Color.parseColor("#D57B1A"));
        consumeLineDataSet.setCircleRadius(6);
        consumeLineDataSet.setDrawCircleHole(false);

        LineDataSet supplyLineDataSet = new LineDataSet(supply, "电力供应");
        supplyLineDataSet.setColor(Color.parseColor("#1590C1"));
        supplyLineDataSet.setCircleColor(Color.parseColor("#1590C1"));
        supplyLineDataSet.setCircleRadius(6);
        supplyLineDataSet.setDrawCircleHole(false);

        LineData lineData = new LineData();
        lineData.addDataSet(consumeLineDataSet);
        lineData.addDataSet(supplyLineDataSet);

        chart.getAxisRight().setEnabled(false);

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setTextSize(16f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(16f);

        chart.getDescription().setEnabled(false);

        chart.setData(lineData);
        chart.invalidate();
    }
}
