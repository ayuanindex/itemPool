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

        // ???????????????
        textViews = new ArrayList<>(4);
        textViews.add(tvOne);
        textViews.add(tvTwo);
        textViews.add(tvThree);
        textViews.add(tvFour);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_progress);
        builder.setTitle("??????????????????");
        show = builder.show();

        // ????????????????????????????????????
        remote.getAllPeople()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(PeopleBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getCars, (Throwable throwable) -> {
                    Log.d(TAG, "getCars: ??????????????????????????????????????????????????????????????????-----" + throwable.getMessage());
                    show.dismiss();
                }).isDisposed();
    }

    /**
     * ????????????????????????????????????
     *
     * @param dataBeans ??????????????????
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
                    Log.d(TAG, "getCars: ??????????????????????????????-----" + throwable.getMessage());
                    show.dismiss();
                }).isDisposed();
    }

    private void setViewPager(ResultData resultData) {
        // ?????????fragment??????
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
     * ???????????????????????????????????????????????????????????????????????????????????????
     *
     * @author ayuan
     */
    public interface ResultData {
        /**
         * ????????????????????????
         *
         * @return ??????????????????????????????
         */
        List<PeopleBean.DataBean> getResult();

        /**
         * ????????????????????????????????????
         *
         * @param lineId ?????????ID
         * @return ????????????????????????????????????????????????
         */
        List<CarsBean.DataBean> getCars(int lineId);
    }
}
