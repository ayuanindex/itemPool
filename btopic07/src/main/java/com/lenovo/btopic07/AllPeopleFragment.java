package com.lenovo.btopic07;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic07.bean.AllPeopleBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class AllPeopleFragment extends BaseFragment {

    private ProgressBar progress;
    private RecyclerView rcList;
    private ApiService remote;
    private static List<AllPeopleBean.DataBean> dataBeanList = new ArrayList<>();
    private CustomerAdapter customerAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_allpeople;
    }

    @Override
    protected void initView(View view) {
        progress = view.findViewById(R.id.progress);
        rcList = view.findViewById(R.id.rcList);
    }

    @Override
    protected void init() {
        remote = Network.remote(ApiService.class);

        rcList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        customerAdapter = new CustomerAdapter();
        rcList.setAdapter(customerAdapter);

        // 选择显示加载对话框
        isLoading(true);

        getAllPeople();
    }

    /**
     * 是否显示加载对话框
     *
     * @param isShow true表示显示加载，
     */
    private void isLoading(boolean isShow) {
        // 设置显示隐藏属性
        progress.setVisibility(isShow ? View.VISIBLE : View.GONE);
        rcList.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    /**
     * 获取所有人员信息
     */
    private void getAllPeople() {
        remote.getAllPeople().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllPeopleBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<AllPeopleBean.DataBean> dataBeans) -> {
                    if (dataBeanList.size() != dataBeans.size()) {
                        dataBeanList.clear();
                        dataBeanList = dataBeans;
                    }
                    customerAdapter.notifyDataSetChanged();

                    new Handler().postDelayed(() -> isLoading(false), 1000);

                    Log.d(TAG, "accept: " + dataBeans.toString());
                }, (Throwable throwable) -> Log.d(TAG, "accept: 获取所有人员信息出现问题-----" + throwable.getMessage()))
                .isDisposed();
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

        @NonNull
        @Override
        public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = View.inflate(mActivity, R.layout.item_people, null);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
            AllPeopleBean.DataBean dataBean = dataBeanList.get(position);
            holder.ivIcon.setImageResource(R.drawable.pic_icon);
            holder.tvPeopleName.setText(dataBean.getPeopleName());
            holder.tvWorkType.setText(getWorkType(dataBean.getStatus()));
            holder.tvMoney.setText(String.valueOf(dataBean.getGold()));
            holder.tvPower.setText(String.valueOf(dataBean.getHp()));
            holder.tvContent.setText(dataBean.getContent());
        }

        /**
         * 匹配员工职位
         *
         * @param status 当前职位标示符
         * @return 返沪职位信息
         */
        private String getWorkType(int status) {
            switch (status) {
                case 0:
                    return "工程师";
                case 1:
                    return "工人";
                case 2:
                    return "技术人员";
                case 3:
                    return "检测人员";
                default:
                    return "暂无职位";
            }
        }

        @Override
        public int getItemCount() {
            return dataBeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public ImageView ivIcon;
            public TextView tvPeopleName;
            public TextView tvWorkType;
            public TextView tvMoney;
            public TextView tvPower;
            public TextView tvContent;

            public ViewHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.ivIcon = rootView.findViewById(R.id.iv_icon);
                this.tvPeopleName = rootView.findViewById(R.id.tv_peopleName);
                this.tvWorkType = rootView.findViewById(R.id.tv_workType);
                this.tvMoney = rootView.findViewById(R.id.tv_money);
                this.tvPower = rootView.findViewById(R.id.tv_power);
                this.tvContent = rootView.findViewById(R.id.tv_content);
            }
        }
    }
}
