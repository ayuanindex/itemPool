package com.lenovo.btopic04;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic04.bean.OrderBean;
import com.lenovo.btopic04.bean.ResultDataBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class OrderFormFragment extends BaseFragment {
    private final MainActivity.ResultListener resultListener;
    private final int index;
    private TextView tvPageStatus;
    private ListView lvList;
    private List<OrderBean.DataBean> orderData;
    private CustomerAdapter customerAdapter;
    private SimpleDateFormat simpleDateFormat;
    private ApiService remote;

    public OrderFormFragment(MainActivity.ResultListener resultListener, int index) {
        this.resultListener = resultListener;
        this.index = index;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_orderform;
    }

    @Override
    protected void initView(View view) {
        tvPageStatus = view.findViewById(R.id.tv_pageStatus);
        lvList = view.findViewById(R.id.lv_list);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);

        orderData = resultListener.getOrderData(index);

        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        customerAdapter = new CustomerAdapter();
        lvList.setAdapter(customerAdapter);
    }

    /**
     * 添加订单
     *
     * @param item 需要添加的对象
     */
    private void addOrder(OrderBean.DataBean item) {
        remote.addOrder(item.getParameter()).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((OrderBean resultDataBean) -> {
                    if (resultDataBean.getData().size() > 0) {
                        return resultDataBean.getData().get(0);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((OrderBean.DataBean dataBean) -> {
                    if (dataBean != null) {
                        lvList.setVisibility(View.VISIBLE);
                        tvPageStatus.setVisibility(View.GONE);

                        orderData.add(dataBean);
                        customerAdapter.notifyDataSetChanged();
                    } else {
                        lvList.setVisibility(View.GONE);
                        tvPageStatus.setVisibility(View.VISIBLE);
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 添加订单出现错误" + throwable.getMessage()))
                .isDisposed();

    }

    /**
     * 删除订单
     *
     * @param id 订单的ID
     */
    private void removeOrder(int id) {
        remote.removeOrder(id).compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((ResultDataBean resultDataBean) -> {
                    if (resultDataBean.getData().size() > 0) {
                        return resultDataBean.getData().get(0);
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ResultDataBean.DataBean dataBean) -> {
                    if (dataBean != null) {
                        Log.d(TAG, "accept: " + dataBean.toString());
                        for (OrderBean.DataBean orderDatum : orderData) {
                            if (orderDatum.getId() == id) {
                                orderData.remove(orderDatum);
                                break;
                            }
                        }
                        customerAdapter.notifyDataSetChanged();
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 移除订单发生错误----" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvOrderName;
        private TextView tvOrderStatus;
        private ImageView ivOrderIcon;
        private TextView tvOrderContent;
        private ImageView ivOption;
        private CardView cardPlaceAnOrder;
        private CardView cardCancelOrder;

        private TextView tvDueTime;

        @Override
        public int getCount() {
            return orderData.size();
        }

        @Override
        public OrderBean.DataBean getItem(int position) {
            return orderData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity, R.layout.item_order, null);
            } else {
                view = convertView;
            }
            initView(view);
            // 订单名称
            tvOrderName.setText(getItem(position).getUserAppointmentName());
            // 订单状态
            tvOrderStatus.setText(getItem(position).getOrderStatus());
            // 订单图片
            ivOrderIcon.setImageResource(R.drawable.pic_icon);
            // 订单描述
            tvOrderContent.setText(getItem(position).getOrderContent());
            // 交付时间(因为订单时间是10位数的时间戳，所有显示正常时间时需要乘1000L)
            tvDueTime.setText("交付时间" + simpleDateFormat.format(new Date(getItem(position).getTime() * 1000L)));

            cardCancelOrder.setVisibility(getItem(position).isChecked() ? View.VISIBLE : View.INVISIBLE);
            cardPlaceAnOrder.setVisibility(getItem(position).isChecked() ? View.VISIBLE : View.INVISIBLE);

            ivOption.setOnClickListener(null);
            ivOption.setOnClickListener((View v) -> {
                boolean checked = getItem(position).isChecked();
                for (OrderBean.DataBean orderDatum : orderData) {
                    if (!orderDatum.equals(getItem(position))) {
                        orderDatum.setChecked(false);
                    }
                }
                getItem(position).setChecked(!checked);
                notifyDataSetChanged();
            });

            // 添加订单
            cardPlaceAnOrder.setOnClickListener((View v) -> addOrder(getItem(position)));

            // 取消订单
            cardCancelOrder.setOnClickListener((View v) -> removeOrder(getItem(position).getId()));
            return view;
        }

        private void initView(View view) {
            tvOrderName = view.findViewById(R.id.tv_orderName);
            tvOrderStatus = view.findViewById(R.id.tv_orderStatus);
            ivOrderIcon = view.findViewById(R.id.iv_orderIcon);
            tvOrderContent = view.findViewById(R.id.tv_orderContent);
            ivOption = view.findViewById(R.id.iv_option);
            cardPlaceAnOrder = view.findViewById(R.id.card_placeAnOrder);
            cardCancelOrder = view.findViewById(R.id.card_cancelOrder);
            tvDueTime = view.findViewById(R.id.tv_dueTime);
        }

    }

}
