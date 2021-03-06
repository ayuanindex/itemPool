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
                        startFragmentWithReplace(R.id.ll_replace, new DetailFragment(child, allPeopleBean, this::getAllStudentStaff));
                        break;
                    }
                }
            }
            return true;
        });
    }

    @Override
    protected void initData() {
        // ???????????????fragment
        AdFragment adFragment = new AdFragment();
        startFragmentWithReplace(R.id.ll_replace, adFragment);

        remote = Network.remote(ApiService.class);

        // ????????????????????????????????????
        simpleBeans = new ArrayList<>();
        simpleBeans.add(new SimpleBean("?????????", new ArrayList<>()));
        simpleBeans.add(new SimpleBean("??????", new ArrayList<>()));
        simpleBeans.add(new SimpleBean("????????????", new ArrayList<>()));
        simpleBeans.add(new SimpleBean("?????????", new ArrayList<>()));

        // ??????????????????
        customerAdapter = new CustomerAdapter();
        elList.setAdapter(customerAdapter);

        // ????????????????????????
        getAllPeople();
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
                    allPeopleBeans = dataBeans;
                    // ??????????????????????????????
                    getAllStudentStaff();
                }, (Throwable throwable) -> Log.d(TAG, "getAllPeople: ????????????????????????????????????----------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ????????????????????????
     */
    private void getAllStudentStaff() {
        remote.getLineStudentStaff()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map((Function<StudentStaffBean, List<SimpleBean>>) studentStaffBean -> {
                    for (SimpleBean simpleBean : simpleBeans) {
                        simpleBean.getDataBeans().clear();
                    }

                    List<StudentStaffBean.DataBean> data = studentStaffBean.getData();
                    for (StudentStaffBean.DataBean datum : data) {
                        if (TextUtils.isEmpty(datum.getWorkPostId())) {
                            continue;
                        }
                        getType(datum);
                    }

                    allPeopleFragment = null;
                    allPeopleFragment = new AllPeopleFragment(new ResultData() {
                        @Override
                        public List<AllPeopleBean.DataBean> getResultData() {
                            // ????????????????????????????????????false
                            for (AllPeopleBean.DataBean allPeopleBean : allPeopleBeans) {
                                allPeopleBean.setRecruitment(false);
                            }
                            return allPeopleBeans;
                        }

                        @Override
                        public ArrayList<SimpleBean> getAllStudent() {
                            return simpleBeans;
                        }

                        @Override
                        public void update() {
                            getAllStudentStaff();
                        }
                    });
                    return simpleBeans;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<SimpleBean> simpleBeans) -> {
                    // ????????????
                    customerAdapter.notifyDataSetChanged();
                }, (Throwable throwable) -> Log.d(TAG, "getAllStudentStaff: ???????????????????????????????????????????????????----------" + throwable.getMessage()))
                .isDisposed();
    }

    /**
     * ?????????????????????????????????????????????simple????????????????????????????????????
     *
     * @param datum ???????????????????????????
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

        // ???????????????
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
         * ?????????????????????
         *
         * @param peopleId ???????????????????????????ID
         * @return ???????????????????????????
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
            ivIcon = view.findViewById(R.id.iv_icon);
            tvName = view.findViewById(R.id.tv_name);
            tvContent = view.findViewById(R.id.tv_content);
        }
    }

    public interface ResultData {
        /**
         * ??????????????????
         *
         * @return ??????????????????????????????
         */
        List<AllPeopleBean.DataBean> getResultData();

        /**
         * ???????????????????????????
         *
         * @return ????????????????????????
         */
        ArrayList<SimpleBean> getAllStudent();

        /**
         * ????????????????????????
         */
        void update();
    }

    public interface Refresh {
        /**
         * ??????????????????????????????
         */
        void update();
    }
}
