package com.lenovo.btopic04;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.basic.base.frag.BaseFragment;

import java.util.ArrayList;

/**
 * @author ayuan
 */
public class MainActivity extends BaseFragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<BaseFragment> baseFragments;
    private MainPagerAdapter mainPagerAdapter;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        baseFragments = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            // 添加tab并设置文本
            tabLayout.addTab(tabLayout.newTab());
        }
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);

        baseFragments.add(new OrderFormFragment());
        baseFragments.add(new OrderFormFragment());
        baseFragments.add(new OrderFormFragment());
        baseFragments.add(new OrderFormFragment());

        mainPagerAdapter.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return baseFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "tab" + position;
        }

        @Override
        public int getCount() {
            return baseFragments.size();
        }
    }
}
