package com.lenovo.atopic02;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 查询合格率
 */
public class MainActivity extends BaseActivity {

    private PieChart mPieChart;
    private BarChart mBarChart;
    private ApiService apiService;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        mBarChart = (BarChart) findViewById(R.id.bar_chart);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);

        apiService.getAll().map(Bean::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    initBarChart(dataBeans);
                    initPieChart(dataBeans);
                }, throwable -> throwable.printStackTrace());
    }

    private void initPieChart(List<Bean.DataBean> dataBeans) {
        List<PieEntry> yValues = new ArrayList<>();
        for (int i = 0; i < dataBeans.size(); i++) {
            yValues.add(new PieEntry(dataBeans.get(i).getRate()));
        }
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        //格式化数据显示
        pieDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> value + "%");
        //设置值字体大小
        pieDataSet.setValueTextSize(20);
        //设置饼图颜色
        pieDataSet.setColors(Color.parseColor("#6EC840"), Color.parseColor("#DA804B"), Color.parseColor("#E9C14D"));
        PieData pieData = new PieData(pieDataSet);
        //不画空心
        mPieChart.setDrawHoleEnabled(false);
        //图例
        mPieChart.getLegend().setEnabled(false);
        //隐藏饼图描述
        mPieChart.getDescription().setText("");
        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }

    private void initBarChart(List<Bean.DataBean> dataBeans) {
        //创建Y轴数据
        List<String> xValues = new ArrayList<>();
        //设置y轴数据
        YAxis axisLeft = mBarChart.getAxisLeft();
        mBarChart.getAxisRight().setDrawGridLines(false);
        axisLeft.setValueFormatter((value, axis) -> (int) value + "%");
        //设置值间隔
        axisLeft.setGranularity(20);
        //设置轴值数量
        axisLeft.setLabelCount(5);
        axisLeft.setAxisMinimum(0);
        axisLeft.setAxisMaximum(100);
        axisLeft.setTextSize(20);
        LimitLine limitLine = new LimitLine(70, "警戒线");
        limitLine.setTextColor(Color.RED);
        limitLine.enableDashedLine(10f, 5f, 0f);//线宽长度，线间隔，一般为0，为补偿
        limitLine.setTextSize(20);
        axisLeft.addLimitLine(limitLine);


        //设置x轴数据
        XAxis xAxis = mBarChart.getXAxis();
        //设置x轴在图标底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置字体大小
        xAxis.setTextSize(20);
        //设置x轴值间隔为1
        xAxis.setGranularity(1);
        //设置值标签数量为3
        xAxis.setLabelCount(3);
        //设置x轴数据格式
        xAxis.setValueFormatter((value, axis) -> xValues.get((int) value));

        //创建Y轴数据
        List<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < dataBeans.size(); i++) {
            yValues.add(new BarEntry(i, dataBeans.get(i).getRate()));
            xValues.add(dataBeans.get(i).getPassRateName());
        }
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        //不显示值
        barDataSet.setDrawValues(false);
        //柱状图颜色
        barDataSet.setColors(Color.parseColor("#6EC840"), Color.parseColor("#DA804B"), Color.parseColor("#E9C14D"));
        //创建图数据
        BarData barData = new BarData(barDataSet);
        //双击不放大
        mBarChart.setDoubleTapToZoomEnabled(false);
        //不显示图例
        mBarChart.getLegend().setEnabled(false);
        mBarChart.setData(barData);
        mBarChart.invalidate();
    }
}
