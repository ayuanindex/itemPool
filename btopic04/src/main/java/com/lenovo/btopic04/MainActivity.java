package com.lenovo.btopic04;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic04.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseFragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<BaseFragment> baseFragments;
    private MainPagerAdapter mainPagerAdapter;
    private ArrayList<String> labels;
    private ApiService remote;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        remote = Network.remote(ApiService.class);

        // 获取所有的订单
        getAllOrder();

        baseFragments = new ArrayList<>();

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        labels = new ArrayList<>();
        labels.add("全部订单");
        labels.add("已下单");
        labels.add("生产中");
        labels.add("完成");

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(mainPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void getAllOrder() {
        remote.getAllOrder().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(OrderBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<OrderBean.DataBean> dataBeans) -> {
                    ResultListener resultListener = index -> {
                        if (index == -1) {
                            return dataBeans;
                        }

                        ArrayList<OrderBean.DataBean> orderPlaced = new ArrayList<>();
                        for (OrderBean.DataBean dataBean : dataBeans) {
                            if (dataBean.getType() == index) {
                                orderPlaced.add(dataBean);
                            }
                        }
                        return orderPlaced;
                    };
                    Log.d(TAG, "getAllOrder: 哈哈哈" + dataBeans.size());
                    // 全部订单
                    baseFragments.add(new OrderFormFragment(resultListener, -1));
                    // 已下单
                    baseFragments.add(new OrderFormFragment(resultListener, 0));
                    // 生产中
                    baseFragments.add(new OrderFormFragment(resultListener, 1));
                    // 完成
                    baseFragments.add(new OrderFormFragment(resultListener, 2));

                    mainPagerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取所有订单出现错误---------" + throwable.getMessage()))
                .isDisposed();
    }

    public interface ResultListener {
        /**
         * 获取到对应类型的订单
         *
         * @param index 订单类型
         * @return 返回筛选后的订单
         */
        List<OrderBean.DataBean> getOrderData(int index);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return baseFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return labels.get(position);
        }

        @Override
        public int getCount() {
            return baseFragments.size();
        }
    }
}
