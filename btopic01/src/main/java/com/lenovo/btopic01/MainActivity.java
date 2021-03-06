package com.lenovo.btopic01;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic01.bean.CarsBean;
import com.lenovo.btopic01.bean.PeopleBean;
import com.lenovo.btopic01.fragment.DetailFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseFragmentActivity {

    private ViewPager vpPager;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;
    private TextView tvFour;
    private ArrayList<TextView> textViews;
    private ArrayList<DetailFragment> detailFragments;
    private ApiService remote;
    private AlertDialog show;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        vpPager = findViewById(R.id.vp_pager);
        tvOne = findViewById(R.id.tv_one);
        tvTwo = findViewById(R.id.tv_two);
        tvThree = findViewById(R.id.tv_three);
        tvFour = findViewById(R.id.tv_four);
    }

    @Override
    protected void initEvent() {
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (TextView textView : textViews) {
                    if (textView.equals(textViews.get(position))) {
                        textView.setBackgroundResource(R.drawable.xml_cycle_white);
                    } else {
                        textView.setBackgroundResource(R.drawable.xml_cycle_black);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint({"CheckResult", "NewApi"})
    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        // 初始化圆点
        textViews = new ArrayList<>(4);
        textViews.add(tvOne);
        textViews.add(tvTwo);
        textViews.add(tvThree);
        textViews.add(tvFour);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_progress);
        builder.setTitle("初始化请稍后");
        show = builder.show();

        // 获取所有车辆成品仓库信息
        remote.getAllPeople()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(PeopleBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getCars, (Throwable throwable) -> {
                    Log.d(TAG, "getCars: 获取成品长获取所有人员信息出现错误裤出现问题-----" + throwable.getMessage());
                    show.dismiss();
                }).isDisposed();
    }

    /**
     * 获取所有车辆成品仓库信息
     *
     * @param dataBeans 待发送的数据
     */
    @SuppressLint("CheckResult")
    private void getCars(List<PeopleBean.DataBean> dataBeans) {
        remote.getCars()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((CarsBean carsBean) -> {
                    setViewPager(new ResultData() {
                        @Override
                        public List<PeopleBean.DataBean> getResult() {
                            return dataBeans;
                        }

                        @Override
                        public List<CarsBean.DataBean> getCars(int lineId) {
                            List<CarsBean.DataBean> beans = new ArrayList<>(0);
                            for (CarsBean.DataBean datum : carsBean.getData()) {
                                if (datum.getUserLineId() == lineId) {
                                    beans.add(datum);
                                }
                            }
                            return beans;
                        }
                    });
                    show.dismiss();
                }, (Throwable throwable) -> {
                    Log.d(TAG, "getCars: 获取成品长裤出现问题-----" + throwable.getMessage());
                    show.dismiss();
                }).isDisposed();
    }

    private void setViewPager(ResultData resultData) {
        // 初始化fragment集合
        detailFragments = new ArrayList<>(4);
        detailFragments.add(new DetailFragment(0, resultData));
        detailFragments.add(new DetailFragment(1, resultData));
        detailFragments.add(new DetailFragment(2, resultData));
        detailFragments.add(new DetailFragment(3, resultData));

        vpPager.setOffscreenPageLimit(4);

        vpPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return detailFragments.get(position);
            }

            @Override
            public int getCount() {
                return detailFragments.size();
            }
        });
    }

    /**
     * 通过调用此接口的方法可以获取到所有人员信息以及成平车辆信息
     *
     * @author ayuan
     */
    public interface ResultData {
        /**
         * 获取所有人员信息
         *
         * @return 返回所有人员信息集合
         */
        List<PeopleBean.DataBean> getResult();

        /**
         * 获取指定生产线的成品车辆
         *
         * @param lineId 生产线ID
         * @return 返回指定指定生产线的成品车辆集合
         */
        List<CarsBean.DataBean> getCars(int lineId);
    }
}
