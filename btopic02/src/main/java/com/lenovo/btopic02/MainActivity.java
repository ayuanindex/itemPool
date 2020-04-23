package com.lenovo.btopic02;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.btopic02.bean.AllPeopleBean;
import com.lenovo.btopic02.bean.SimpleBean;
import com.lenovo.btopic02.bean.StudentStaffBean;
import com.lenovo.btopic02.fragment.AdFragment;
import com.lenovo.btopic02.fragment.AllPeopleFragment;
import com.lenovo.btopic02.fragment.DetailFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class MainActivity extends BaseFragmentActivity {
    private CardView cardAddStudentStaff;
    private ExpandableListView elList;
    private ApiService remote;
    private ArrayList<SimpleBean> simpleBeans;
    private CustomerAdapter customerAdapter;
    private List<AllPeopleBean.DataBean> allPeopleBeans;
    private AllPeopleFragment allPeopleFragment;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        cardAddStudentStaff = findViewById(R.id.card_addStudentStaff);
        elList = findViewById(R.id.el_list);
    }

    @Override
    protected void initEvent() {
        cardAddStudentStaff.setOnClickListener(v -> {
            if (allPeopleBeans != null) {
                startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
            }
        });

        elList.setOnChildClickListener((ExpandableListView parent, View v, int groupPosition, int childPosition, long id) -> {
            if (allPeopleBeans.size() > 0) {
                StudentStaffBean.DataBean child = customerAdapter.getChild(groupPosition, childPosition);
                for (AllPeopleBean.DataBean allPeopleBean : allPeopleBeans) {
                    if (allPeopleBean.getId() == child.getPeopleId()) {
                        startFragmentWithReplace(R.id.ll_replace, new DetailFragment(child, allPeopleBean, new Refresh() {
                            @Override
                            public void update() {
                                customerAdapter.notifyDataSetChanged();
                            }
                        }));
                        break;
                    }
                }
            }
            return true;
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
        elList.setAdapter(customerAdapter);

        // 获取所有人员信息
        getAllPeople();
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
                    allPeopleBeans = dataBeans;
                    // 获取所有学生员工信息
                    getAllStudentStaff();
                }, (Throwable throwable) -> Log.d(TAG, "getAllPeople: 获取所有人员信息出现问题----------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 获取全部学生员工
     */
    private void getAllStudentStaff() {
        remote.getLineStudentStaff()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((Function<StudentStaffBean, List<SimpleBean>>) studentStaffBean -> {
                    List<StudentStaffBean.DataBean> data = studentStaffBean.getData();
                    for (StudentStaffBean.DataBean datum : data) {
                        if (TextUtils.isEmpty(datum.getWorkPostId())) {
                            continue;
                        }
                        getType(datum);
                    }

                    allPeopleFragment = new AllPeopleFragment(new ResultData() {
                        @Override
                        public List<AllPeopleBean.DataBean> getResultData() {
                            return allPeopleBeans;
                        }

                        @Override
                        public ArrayList<SimpleBean> getAllStudent() {
                            return simpleBeans;
                        }
                    });
                    return simpleBeans;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<SimpleBean> simpleBeans) -> {
                    // 刷新集合
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "getAllStudentStaff: 获取所有生产线中的学生员工出现问题----------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * 获取同一的工作岗位数据并添加到simple数据模型集合中对应的集合
     *
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

        @SuppressLint("SetTextI18n")
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_child_list, null);
            } else {
                view = convertView;
            }
            initChildView(view);
            AllPeopleBean.DataBean currentBean = getCurrentBean(getChild(groupPosition, childPosition).getPeopleId());
            if (currentBean != null) {
                ivIcon.setImageResource(R.drawable.pic_icon);
                tvName.setText(currentBean.getPeopleName());
                tvContent.setText(currentBean.getContent());
            }
            return view;
        }

        /**
         * 获取当前的对象
         *
         * @param peopleId 需要查找的学生人员ID
         * @return 返回指定人员的对象
         */
        private AllPeopleBean.DataBean getCurrentBean(int peopleId) {
            for (AllPeopleBean.DataBean allPeopleBean : allPeopleBeans) {
                if (allPeopleBean.getId() == peopleId) {
                    return allPeopleBean;
                }
            }
            return null;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
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

    public interface ResultData {
        /**
         * 获取全部人员
         *
         * @return 返回全部人员对象集合
         */
        List<AllPeopleBean.DataBean> getResultData();

        /**
         * 获取已招聘人员信息
         *
         * @return 返回全部学生员工
         */
        ArrayList<SimpleBean> getAllStudent();
    }

    public interface Refresh {
        void update();
    }
}
