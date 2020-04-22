package com.lenovo.btopic02;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic02.bean.SimpleBean;
import com.lenovo.btopic02.bean.StudentStaffBean;
import com.lenovo.btopic02.fragment.AdFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseFragmentActivity {
    private CardView card_addStudentStaff;
    private ExpandableListView el_list;
    private LinearLayout ll_replace;
    private ApiService remote;
    private ArrayList<SimpleBean> simpleBeans;
    private CustomerAdapter customerAdapter;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        card_addStudentStaff = findViewById(R.id.card_addStudentStaff);
        el_list = findViewById(R.id.el_list);
        ll_replace = findViewById(R.id.ll_replace);
    }

    @Override
    protected void initEvent() {
        card_addStudentStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 4/22/20 添加学生
            }
        });
    }

    @Override
    protected void initData() {
        // 开启默认的fragment
        AdFragment adFragment = new AdFragment();
        startFragmentWithReplace(R.id.ll_replace, adFragment);

        remote = Network.remote(ApiService.class);

        // 初始化需要展示的数据模型
        simpleBeans = new ArrayList<>();
        simpleBeans.add(new SimpleBean("工程师", new ArrayList<>()));
        simpleBeans.add(new SimpleBean("工人", new ArrayList<>()));
        simpleBeans.add(new SimpleBean("技术人员", new ArrayList<>()));
        simpleBeans.add(new SimpleBean("质检员", new ArrayList<>()));
        // 填充集合数据
        customerAdapter = new CustomerAdapter();
        el_list.setAdapter(customerAdapter);

        // 获取学生员工
        getAllStudentStaff();
    }

    /**
     * 获取全部学生员工
     */
    private void getAllStudentStaff() {
        remote.getLineStudentStaff()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<StudentStaffBean, List<SimpleBean>>() {
                    @Override
                    public List<SimpleBean> apply(StudentStaffBean studentStaffBean) throws Exception {
                        List<StudentStaffBean.DataBean> data = studentStaffBean.getData();
                        for (StudentStaffBean.DataBean datum : data) {
                            if (TextUtils.isEmpty(datum.getWorkPostId())) {
                                continue;
                            }

                            getType(datum);
                        }
                        return simpleBeans;
                    }

                    /**
                     * 获取同一的工作岗位数据并添加到simple数据模型集合中对应的集合
                     * @param datum 需要分类的员工对象
                     */
                    private void getType(StudentStaffBean.DataBean datum) {
                        int workPostId = Integer.parseInt(datum.getWorkPostId());
                        int type = 0;
                        if (workPostId >= 1 && workPostId <= 4) {
                            type = workPostId - 1;
                        } else if (workPostId >= 5 && workPostId <= 8) {
                            type = workPostId - 5;
                        } else if (workPostId >= 9 && workPostId <= 12) {
                            type = workPostId - 9;
                        }

                        // 添加至集合
                        simpleBeans.get(type).addStudentStaff(datum);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<SimpleBean> simpleBeans) -> {
                    // TODO: 4/22/20 填充集合
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> {
                    Log.d(TAG, "getAllStudentStaff: 获取所有生产线中的学生员工出现问题----------" + throwable.getMessage());
                })
                .isDisposed();
    }

    /**
     * @author ayuan
     */
    class CustomerAdapter extends BaseExpandableListAdapter {

        private ImageView ivWhetherToExpand;
        private TextView tvType;
        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvContent;

        @Override
        public int getGroupCount() {
            return simpleBeans.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return simpleBeans.get(groupPosition).getDataBeans().size();
        }

        @Override
        public SimpleBean getGroup(int groupPosition) {
            return simpleBeans.get(groupPosition);
        }

        @Override
        public StudentStaffBean.DataBean getChild(int groupPosition, int childPosition) {
            return simpleBeans.get(groupPosition).getDataBeans().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_group_list, null);
            } else {
                view = convertView;
            }
            initGroupView(view);
            ivWhetherToExpand.setImageResource(isExpanded ? R.drawable.pic_icon_down : R.drawable.pic_icon_right);
            tvType.setText(getGroup(groupPosition).getLabelName());
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_child_list, null);
            } else {
                view = convertView;
            }
            initChildView(view);
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        private void initGroupView(View view) {
            ivWhetherToExpand = view.findViewById(R.id.iv_whetherToExpand);
            tvType = view.findViewById(R.id.tv_type);
        }

        private void initChildView(View view) {
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
