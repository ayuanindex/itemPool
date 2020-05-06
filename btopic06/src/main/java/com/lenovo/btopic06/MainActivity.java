package com.lenovo.btopic06;

import android.content.Intent;

import com.lenovo.basic.base.act.BaseActivity;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutIdRes() {
        startActivity(new Intent(this, BannerActivity.class), false);
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
