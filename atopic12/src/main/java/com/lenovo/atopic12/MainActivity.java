package com.lenovo.atopic12;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 编码实现工厂环境管理
 */
public class MainActivity extends BaseActivity {
    private TextView mTvTime;
    private TextView mTv1Status;
    private TextView mTv1Wind;
    private Switch mSw1;
    private Button mBtn1Wind;
    private TextView mTv2Status;
    private Switch mSw2;
    private TextView mTv3Wendu;
    private Spinner mSp3;
    private Button mBtn3Wendu;
    private TextView mTv4Status;
    private TextView mTv4Wendu;
    private TextView mTv5Num;
    private EditText mEt5Guagnzhao;
    private Button mBtn5Guangzhao;
    private TextView mTv6Num;
    private EditText mEt6Dianli;
    private Button mBtn6Dianli;
    private ApiService apiService;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTv1Status = (TextView) findViewById(R.id.tv_1_status);
        mTv1Wind = (TextView) findViewById(R.id.tv_1_wind);
        mSw1 = (Switch) findViewById(R.id.sw_1);
        mBtn1Wind = (Button) findViewById(R.id.btn_1_wind);
        mTv2Status = (TextView) findViewById(R.id.tv_2_status);
        mSw2 = (Switch) findViewById(R.id.sw_2);
        mTv3Wendu = (TextView) findViewById(R.id.tv_3_wendu);
        mSp3 = (Spinner) findViewById(R.id.sp_3);
        mBtn3Wendu = (Button) findViewById(R.id.btn_3_wendu);
        mTv4Status = (TextView) findViewById(R.id.tv_4_status);
        mTv4Wendu = (TextView) findViewById(R.id.tv_4_wendu);
        mTv5Num = (TextView) findViewById(R.id.tv_5_num);
        mEt5Guagnzhao = (EditText) findViewById(R.id.et_5_guagnzhao);
        mBtn5Guangzhao = (Button) findViewById(R.id.btn_5_guangzhao);
        mTv6Num = (TextView) findViewById(R.id.tv_6_num);
        mEt6Dianli = (EditText) findViewById(R.id.et_6_dianli);
        mBtn6Dianli = (Button) findViewById(R.id.btn_6_dianli);
    }

    @Override
    protected void initEvent() {
        //切换冷暖风
        mBtn1Wind.setOnClickListener(v -> {
            if (!mSw1.isChecked()) {
                Toast.makeText(this, "请先打开空调", Toast.LENGTH_SHORT).show();
            } else {
                boolean b = mTv1Wind.getText().toString().trim().equals("冷风");
                //进行冷暖风切换
                acOffOnChange(b, 2, 1);
            }
        });

        //打开关闭灯光
        mSw2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mSw2.isPressed())
                apiService.updateLightSwitch(1, isChecked ? 1 : 0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(this.bindToLifecycle())
                        .subscribe(ligthSwitch -> {
                            //更新界面数据
                            query();
                        }, Throwable::printStackTrace);
        });

        //打开关闭空调
        mSw1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mSw1.isPressed()) {
                acOffOnChange(isChecked, 1, 0);
            }
        });

        //设置温度
        mBtn3Wendu.setOnClickListener(v -> {
            //获取温度
            int wenduInt = mSp3.getSelectedItemPosition() + 16;
            apiService.updateWorkshopTemp(1, wenduInt + "℃")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindToLifecycle())
                    .subscribe(lightSwitch -> query(), Throwable::printStackTrace);
        });

        //设置光照
        mBtn5Guangzhao.setOnClickListener(v -> {
            //如果没有打开灯，不能设置光照
            if (mSw2.isChecked()) {
                //获取光照值
                String guangzhaoStr = mEt5Guagnzhao.getText().toString().trim();
                if (!TextUtils.isEmpty(guangzhaoStr)) {
                    int i = Integer.parseInt(guangzhaoStr);
                    if (i > 3000 || i < 0) {
                        Toast.makeText(this, "请注意光照值的范围", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    apiService.updateBeam(1, i)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(this.bindToLifecycle())
                            .subscribe(lightSwitch -> {

                                Log.e(TAG, "------------" + lightSwitch.toString());
                                query();
                            }, Throwable::printStackTrace);
                } else {
                    Toast.makeText(this, "光照值不能为空", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请先开灯", Toast.LENGTH_SHORT).show();
            }


        });

        //设置电力供应
        mBtn6Dianli.setOnClickListener(v -> {
            //获取电力
            String dianliStr = mEt6Dianli.getText().toString();
            if (!TextUtils.isEmpty(dianliStr)) {
                int i = Integer.parseInt(dianliStr);
                if (i > 1000 || i < 0) {
                    Toast.makeText(this, "请注意电力值的范围", Toast.LENGTH_SHORT).show();
                    return;
                }
                apiService.updatePower(1, i)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(this.bindToLifecycle())
                        .subscribe(userWorkEnvironmental -> query(), Throwable::printStackTrace);
            } else {
                Toast.makeText(this, "请输入电力值", Toast.LENGTH_SHORT).show();
            }

        });


    }

    //设置空调状态
    @SuppressWarnings("CheckResult")
    private void acOffOnChange(boolean isChecked, int i, int i2) {
        apiService.updateAcOnOff(1, isChecked ? i : i2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(userWorkEnvironmental -> query(), Throwable::printStackTrace);
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        query();
    }

    //查询工厂环境信息
    @SuppressWarnings("CheckResult")
    private void query() {
        apiService.getInfoUserWorkEnvironmental(1)
                .map(UserWorkEnvironmental::getData)
                .map(dataBeans -> dataBeans.get(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBean -> {
                    //设置左上角时间
                    mTvTime.setText(dataBean.getTime());
                    //空调状态有关信息
                    if (dataBean.getAcOnOff() == 0) {
                        mTv1Status.setText("关");
                        mTv1Wind.setText("关");
                        mSw1.setChecked(false);
                    } else {
                        mTv1Status.setText("开");
                        mTv1Wind.setText(dataBean.getAcOnOff() == 1 ? "冷风" : "暖风");
                        mSw1.setChecked(true);
                    }
                    //设置灯光有关信息
                    mTv2Status.setText(dataBean.getLightSwitch() == 0 ? "关" : "开");
                    mSw2.setChecked(dataBean.getLightSwitch() == 1);
                    //车间温度有关信息
                    mTv3Wendu.setText(dataBean.getWorkshopTemp());
                    //室外温度有关信息
                    mTv4Status.setText(dataBean.getOutTemp());
                    //设置光照有关信息
                    mTv5Num.setText(dataBean.getBeam() + "");
                    //设置电力供应有关信息
                    mTv6Num.setText(dataBean.getPower() + "");
                }, Throwable::printStackTrace);
    }
}
