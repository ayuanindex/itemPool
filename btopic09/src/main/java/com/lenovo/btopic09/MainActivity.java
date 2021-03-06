package com.lenovo.btopic09;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic09.bean.AllPeopleBean;
import com.lenovo.btopic09.bean.EnlistLogBean;
import com.lenovo.btopic09.bean.LeftLogBean;
import com.lenovo.btopic09.bean.MakingsBean;
import com.lenovo.btopic09.bean.MaterialBean;
import com.lenovo.btopic09.bean.RightLogBean;
import com.lenovo.btopic09.bean.UserWorkBean;
import com.lenovo.btopic09.bean.UserWorkResultBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private TextView tvLeftState;
    private TextView tvRightState;
    private ArrayList<LeftLogBean> leftLogs;
    private LeftCustomerAdapter leftAdapter;
    private SimpleDateFormat simpleDateFormat;
    private ArrayList<RightLogBean> rightLogs;
    private RightCustomerAdapter rightAdapter;

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
        tvLeftState = (TextView) findViewById(R.id.tv_leftState);
        tvRightState = (TextView) findViewById(R.id.tv_rightState);
    }

    @Override
    protected void initEvent() {
        tvFactoryFunds.setOnClickListener((View v) -> {
            // ???????????????????????????
            alertDialog();
        });
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void initData() {
        uiHandler = new Handler(getMainLooper());

        remote = Network.remote(ApiService.class);

        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        // ?????????ListView
        leftLogs = new ArrayList<>();
        leftAdapter = new LeftCustomerAdapter();
        lvLeftList.setAdapter(leftAdapter);

        rightLogs = new ArrayList<>();
        rightAdapter = new RightCustomerAdapter();
        lvRightList.setAdapter(rightAdapter);


        // ???????????????????????????
        getUserWorkInfo((UserWorkBean.DataBean dataBean) -> {
            Log.d(TAG, "accept: " + dataBean.toString());
            int price = dataBean.getPrice();

            if (price < 5000) {
                tvFactoryFunds.setTextColor(Color.parseColor("#FD4848"));
            } else {
                tvFactoryFunds.setTextColor(Color.parseColor("#606060"));
            }

            tvFactoryFunds.setText(String.valueOf(price));
            tvMaterialInventory.setText(String.valueOf(dataBean.getPartCapacity()));
            tvCarInventory.setText(String.valueOf(dataBean.getCarCapacity()));
        });

        // ????????????????????????
        getAllPeople();

        // ?????????????????????
        getRawMaterials();
    }

    /**
     * ???????????????????????????
     *
     * @param dataBeanConsumer ???????????????
     */
    private void getUserWorkInfo(Consumer<UserWorkBean.DataBean> dataBeanConsumer) {
        remote.getUserWork(1).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((UserWorkBean userWorkBean) -> userWorkBean.getData().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeanConsumer, (Throwable throwable) -> Log.d(TAG, "accept: ??????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ????????????????????????
     */
    private void getAllPeople() {
        remote.getAllPeople().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllPeopleBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<AllPeopleBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    // ??????????????????????????????
                    getAllEnlistLog(dataBeans);
                }, (Throwable throwable) -> Log.d(TAG, "accept: ????????????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ???????????????????????????
     */
    private void getRawMaterials() {
        remote.getAllMaterial().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(MaterialBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<MaterialBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    getAllMaking(dataBeans);
                }, (Throwable throwable) -> Log.d(TAG, "accept: ????????????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ??????????????????????????????
     *
     * @param allMaterial ?????????????????????
     */
    private void getAllMaking(List<MaterialBean.DataBean> allMaterial) {
        remote.getAllMaking().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(MakingsBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<MakingsBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    if (dataBeans.size() == 0) {
                        lvRightList.setVisibility(View.GONE);
                        tvRightState.setVisibility(View.VISIBLE);
                    } else {
                        lvRightList.setVisibility(View.VISIBLE);
                        tvRightState.setVisibility(View.GONE);

                        for (MakingsBean.DataBean dataBean : dataBeans) {
                            for (MaterialBean.DataBean bean : allMaterial) {
                                if (dataBean.getPartId() == bean.getId()) {
                                    Date date = new Date(dataBean.getTime() * 1000L);
                                    String formatTime = simpleDateFormat.format(date);
                                    rightLogs.add(new RightLogBean(
                                            bean.getMaterialName(),
                                            bean.getContent(),
                                            String.valueOf(dataBean.getNum()),
                                            String.valueOf(bean.getPrice()),
                                            formatTime
                                    ));
                                    break;
                                }
                            }
                        }
                    }

                    rightAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "accept: ???????????????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ????????????????????????
     *
     * @param allPeople ??????????????????
     */
    private void getAllEnlistLog(List<AllPeopleBean.DataBean> allPeople) {
        remote.getEnlistLog().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(EnlistLogBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<EnlistLogBean.DataBean> dataBeans) -> {
                    Log.d(TAG, "accept: " + dataBeans.toString());
                    if (dataBeans.size() == 0) {
                        lvLeftList.setVisibility(View.GONE);
                        tvLeftState.setVisibility(View.VISIBLE);
                    } else {
                        lvLeftList.setVisibility(View.VISIBLE);
                        tvLeftState.setVisibility(View.GONE);

                        for (EnlistLogBean.DataBean dataBean : dataBeans) {
                            for (AllPeopleBean.DataBean allPerson : allPeople) {
                                if (dataBean.getUserPeopleId() == allPerson.getId()) {
                                    int time = dataBean.getTime();
                                    String timeFormat = simpleDateFormat.format(new Date(time * 1000L));
                                    leftLogs.add(new LeftLogBean(allPerson.getPeopleName(), String.valueOf(allPerson.getGold()), allPerson.getContent(), timeFormat));
                                    break;
                                }
                            }
                        }
                        leftAdapter.notifyDataSetChanged();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: ??????????????????????????????----" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ???????????????
     */
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        View inflate = View.inflate(this, R.layout.dialog_investment, null);
        alertDialog.setView(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);

        viewHolder.cardOk.setOnClickListener((View v) -> {
            String moneyStr = viewHolder.etMoney.getText().toString().trim();
            if (TextUtils.isEmpty(moneyStr)) {
                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                return;
            }

            int moneyInt = Integer.parseInt(moneyStr);

            uiHandler.postDelayed(() -> {
                // ???????????????????????????
                getUserWorkInfo((UserWorkBean.DataBean dataBean) -> {
                    int price = dataBean.getPrice();
                    price += moneyInt;
                    modifyFactoryMoney(price);
                });
            }, 10000);

            alertDialog.dismiss();
        });

        viewHolder.cardCancel.setOnClickListener((View v) -> alertDialog.dismiss());

        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        alertDialog.show();
    }

    /**
     * ?????????????????????
     *
     * @param price ????????????
     */
    private void modifyFactoryMoney(int price) {
        remote.modifyFactoryMoney(1, price).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(UserWorkResultBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (UserWorkResultBean.DataBean dataBean) -> tvFactoryFunds.setText(String.valueOf(dataBean.getPrice())),
                        (Throwable throwable) -> Log.d(TAG, "accept: ????????????????????????----" + throwable.getMessage())
                )
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

    class LeftCustomerAdapter extends BaseAdapter {
        private TextView tvOne;
        private TextView tvTwo;
        private TextView tvThree;
        private TextView tvFour;

        @Override
        public int getCount() {
            return leftLogs.size();
        }

        @Override
        public LeftLogBean getItem(int position) {
            return leftLogs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_left_log, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvOne.setText(getItem(position).getName());
            tvTwo.setText(getItem(position).getGold());
            tvThree.setText(getItem(position).getContent());
            tvFour.setText(getItem(position).getTime());
            return view;
        }

        private void initView(View view) {
            tvOne = (TextView) view.findViewById(R.id.tv_one);
            tvTwo = (TextView) view.findViewById(R.id.tv_two);
            tvThree = (TextView) view.findViewById(R.id.tv_three);
            tvFour = (TextView) view.findViewById(R.id.tv_four);
        }
    }

    class RightCustomerAdapter extends BaseAdapter {

        private TextView tvOne;
        private TextView tvTwo;
        private TextView tvThree;
        private TextView tvFour;
        private TextView tvFive;

        @Override
        public int getCount() {
            return rightLogs.size();
        }

        @Override
        public RightLogBean getItem(int position) {
            return rightLogs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_right_log, null);
            } else {
                view = convertView;
            }
            initView(view);

            tvOne.setText(getItem(position).getName());
            tvTwo.setText(getItem(position).getSupply());
            tvThree.setText(getItem(position).getNum());
            tvFour.setText(getItem(position).getPrice());
            tvFive.setText(getItem(position).getTime());
            return view;
        }

        private void initView(View view) {
            tvOne = (TextView) view.findViewById(R.id.tv_one);
            tvTwo = (TextView) view.findViewById(R.id.tv_two);
            tvThree = (TextView) view.findViewById(R.id.tv_three);
            tvFour = (TextView) view.findViewById(R.id.tv_four);
            tvFive = (TextView) view.findViewById(R.id.tv_five);
        }
    }
}