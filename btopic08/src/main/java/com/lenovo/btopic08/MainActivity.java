package com.lenovo.btopic08;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {

    private Intent service;

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

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        service = new Intent(this, NotificationService.class);
        startService(service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(service);
    }
}
