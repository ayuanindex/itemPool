package com.lenovo.btopic1;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.btopic1.fragment.DetailFragment;

import java.util.ArrayList;

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

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        // 初始化圆点
        textViews = new ArrayList<>(4);
        textViews.add(tvOne);
        textViews.add(tvTwo);
        textViews.add(tvThree);
        textViews.add(tvFour);

        // 初始化fragment集合
        detailFragments = new ArrayList<>(4);
        detailFragments.add(new DetailFragment(0));
        detailFragments.add(new DetailFragment(1));
        detailFragments.add(new DetailFragment(2));
        detailFragments.add(new DetailFragment(3));

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
}
