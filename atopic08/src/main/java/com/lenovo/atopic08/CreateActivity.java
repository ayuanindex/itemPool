package com.lenovo.atopic08;


import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.lenovo.basic.base.act.Base2Activity;
import com.lenovo.basic.utils.Network;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建订单页面
 */
public class CreateActivity extends Base2Activity {
    private Button mBtnCommit;
    private Button mBtnBack;
    private ApiService apiService;
    private EditText mEtUserAppointmentName;
    private Spinner mSp1;
    private Spinner mSp2;
    private Spinner mSp3;
    private Spinner mSp4;
    private Spinner mSp5;
    private EditText mEtContent;
    private Spinner mSp6;
    private Spinner mSp7;
    private Spinner mSp8;
    private Spinner mSp9;
    private HashMap<Integer, Integer> carGold;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_create;
    }

    @Override
    protected void initView() {
        mEtUserAppointmentName = (EditText) findViewById(R.id.et_userAppointmentName);
        mSp1 = (Spinner) findViewById(R.id.sp_1);
        mSp2 = (Spinner) findViewById(R.id.sp_2);
        mSp3 = (Spinner) findViewById(R.id.sp_3);
        mSp4 = (Spinner) findViewById(R.id.sp_4);
        mSp5 = (Spinner) findViewById(R.id.sp_5);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mSp6 = (Spinner) findViewById(R.id.sp_6);
        mSp7 = (Spinner) findViewById(R.id.sp_7);
        mSp8 = (Spinner) findViewById(R.id.sp_8);
        mSp9 = (Spinner) findViewById(R.id.sp_9);
        mBtnCommit = (Button) findViewById(R.id.btn_commit);
        mBtnBack = (Button) findViewById(R.id.btn_back);
    }

    @Override
    protected void initEvent() {
        //提交订单
        mBtnCommit.setOnClickListener(v -> {
            commit();
        });

        //返回按钮
        mBtnBack.setOnClickListener(v -> finish());
    }

    private void commit() {


        apiService.createUserAppointment(
                1, mEtUserAppointmentName.getText().toString(), mEtContent.getText().toString(),
                0, mSp1.getSelectedItemPosition() + 1, new Date().getTime() / 1000, mSp5.getSelectedItemPosition() + 1,
                carGold.get(mSp1.getSelectedItemPosition() + 1), mSp2.getSelectedItemPosition(),
                mSp3.getSelectedItemPosition(), mSp6.getSelectedItemPosition(),
                mSp7.getSelectedItemPosition(), mSp8.getSelectedItemPosition(), mSp4.getSelectedItemPosition(),
                mSp9.getSelectedItemPosition())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    Log.e("TAG", "---------" + dataBeans.toString());
                    setResult(20);
                    CreateActivity.this.finish();
                }, Throwable::printStackTrace);
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        carGold = new HashMap<>();
        carGold.put(1, 2000);
        carGold.put(2, 3000);
        carGold.put(3, 4000);
    }
}
