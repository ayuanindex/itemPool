package com.lenovo.atopic09;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lenovo.basic.base.act.Base2Activity;
import com.lenovo.basic.utils.Network;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 新增日志页面
 */
public class CreateActivity extends Base2Activity {
    private EditText mEtPrice;
    private EditText mEtEndPrice;
    private Spinner mSp1;
    private TextView mTvTime;
    private Button mBtnBack;
    private Button mBtnCommit;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Disposable disposable;
    private ApiService apiService;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_create;
    }

    @Override
    protected void initView() {
        mEtPrice = (EditText) findViewById(R.id.et_price);
        mEtEndPrice = (EditText) findViewById(R.id.et_end_price);
        mSp1 = (Spinner) findViewById(R.id.sp_1);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBtnCommit = (Button) findViewById(R.id.btn_commit);
    }

    @SuppressWarnings("CheckResult")
    @Override
    protected void initEvent() {
        if (disposable != null && disposable.isDisposed())//避免多次订阅
            disposable.dispose();
        //实现时间的实时更新
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(aLong -> {
                    mTvTime.setText(getCurrentTime());
                }, Throwable::printStackTrace);
        //关闭当前页面
        mBtnBack.setOnClickListener(v -> finish());
        //提交日志
        mBtnCommit.setOnClickListener(v -> commit());
    }

    //提交日志数据
    @SuppressWarnings("CheckResult")
    private void commit() {
        Observable<Bean> ob;
        if (mSp1.getSelectedItemPosition() == 5) {
            //收入日志
            ob = apiService.createUserInPriceLog(1,
                    Integer.parseInt(mEtPrice.getText().toString().trim()),
                    Integer.parseInt(mEtEndPrice.getText().toString().trim()), new Date().getTime() / 1000L,
                    5);
        } else {
            //支出日志
            ob = apiService.createUserOutPriceLog(1,
                    Integer.parseInt(mEtPrice.getText().toString().trim()),
                    Integer.parseInt(mEtEndPrice.getText().toString().trim()), new Date().getTime() / 1000L,
                    mSp1.getSelectedItemPosition());
        }
        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(bean -> {
                    setResult(20);
                    finish();
                }, Throwable::printStackTrace);
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
    }

    //获取当前时间
    private String getCurrentTime() {
        return format.format(new Date());
    }
}
