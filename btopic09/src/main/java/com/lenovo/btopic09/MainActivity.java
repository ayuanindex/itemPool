package com.lenovo.btopic09;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic09.bean.UserWorkBean;
import com.lenovo.btopic09.bean.UserWorkResultBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {
    private TextView tvFactoryFunds;
    private TextView tvMaterialInventory;
    private TextView tvCarInventory;
    private ListView lvLeftList;
    private ListView lvRightList;
    private Handler uiHandler;
    private ApiService remote;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tvFactoryFunds = (TextView) findViewById(R.id.tv_factoryFunds);
        tvMaterialInventory = (TextView) findViewById(R.id.tv_materialInventory);
        tvCarInventory = (TextView) findViewById(R.id.tv_carInventory);
        lvLeftList = (ListView) findViewById(R.id.lvLeftList);
        lvRightList = (ListView) findViewById(R.id.lvRightList);
    }

    @Override
    protected void initEvent() {
        tvFactoryFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出拉投资的对话框
                alertDialog();
            }
        });
    }

    @Override
    protected void initData() {
        uiHandler = new Handler(getMainLooper());

        remote = Network.remote(ApiService.class);

        // 获取当前工厂的信息
        getUserWorkInfo((UserWorkBean.DataBean dataBean) -> {
            Log.d(TAG, "accept: " + dataBean.toString());
            tvFactoryFunds.setText(String.valueOf(dataBean.getPrice()));
            tvMaterialInventory.setText(String.valueOf(dataBean.getPartCapacity()));
            tvCarInventory.setText(String.valueOf(dataBean.getCarCapacity()));
        });
    }

    /**
     * 获取当前工厂的信息
     *
     * @param dataBeanConsumer 成功的回调
     */
    private void getUserWorkInfo(Consumer<UserWorkBean.DataBean> dataBeanConsumer) {
        remote.getUserWork(1).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((UserWorkBean userWorkBean) -> userWorkBean.getData().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeanConsumer, (Throwable throwable) -> Log.d(TAG, "accept: 获取工厂信息数据失败----" + throwable.getMessage()))
                .isDisposed();
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        View inflate = View.inflate(this, R.layout.dialog_investment, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);

        viewHolder.cardOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moneyStr = viewHolder.etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(moneyStr)) {
                    Toast.makeText(MainActivity.this, "请输入正确金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                int moneyInt = Integer.parseInt(moneyStr);

                uiHandler.postDelayed(() -> {
                    // 获取当前工厂的资金
                    getUserWorkInfo((UserWorkBean.DataBean dataBean) -> {
                        int price = dataBean.getPrice();
                        price += moneyInt;
                        modifyFactoryMoney(price);
                    });
                }, 10000);

                alertDialog.dismiss();
            }
        });

        viewHolder.cardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        alertDialog.show();
    }

    /**
     * 修改工厂的金币
     *
     * @param price 目标金币
     */
    private void modifyFactoryMoney(int price) {
        remote.modifyFactoryMoney(1, price).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(UserWorkResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserWorkResultBean.DataBean>() {
                    @Override
                    public void accept(UserWorkResultBean.DataBean dataBean) throws Exception {
                        tvFactoryFunds.setText(String.valueOf(dataBean.getPrice()));
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 修改金币出现问题----" + throwable.getMessage()))
                .isDisposed();
    }

    public static class ViewHolder {

        public View rootView;
        public EditText etMoney;
        public CardView cardCancel;
        public CardView cardOk;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.etMoney = (EditText) rootView.findViewById(R.id.et_money);
            this.cardCancel = (CardView) rootView.findViewById(R.id.card_cancel);
            this.cardOk = (CardView) rootView.findViewById(R.id.card_Ok);
        }

    }
}
