package com.lenovo.btopic07;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.j256.ormlite.dao.Dao;
import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.lenovo.btopic07.bean.AllPeopleBean;
import com.lenovo.btopic07.bean.HistoryBean;
import com.lenovo.btopic07.databases.OrmHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ayuan
 */
public class MainActivity extends BaseFragmentActivity {
    private EditText etSearchContent;
    private CardView cardCancel;
    private TextView tvSearch;
    private AllPeopleFragment allPeopleFragment;
    private SearchHistoryFragment searchHistoryFragment;
    private Runnable r;
    private Handler uiHandler;
    private Dao<HistoryBean, ?> historyBeanDao;
    private boolean model = false;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        etSearchContent = findViewById(R.id.et_searchContent);
        cardCancel = findViewById(R.id.card_cancel);
        tvSearch = findViewById(R.id.tv_search);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEvent() {
        etSearchContent.setOnTouchListener((View v, MotionEvent event) -> {
            startFragmentWithReplace(R.id.ll_replace, searchHistoryFragment);
            return false;
        });

        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    AllPeopleFragment.dataBeans.clear();
                    AllPeopleFragment.dataBeans.addAll(AllPeopleFragment.dataBeanList);
                    startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                } else {
                    if (model) {
                        startFragmentWithReplace(R.id.ll_replace, searchHistoryFragment);
                        model = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cardCancel.setOnClickListener((View v) -> {
            // 点击搜索按钮进行搜索
            uiHandler.post(r);
            model = true;
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void initData() {
        try {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 10);

            historyBeanDao = OrmHelper.getInstance().getDao(HistoryBean.class);

            uiHandler = new Handler(getMainLooper());
            r = () -> {
                try {
                    // 执行延时操作，切换到人员显示界面
                    startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                    tvSearch.setText("搜索");

                    AllPeopleFragment.dataBeans.clear();

                    // 工人
                    String searchStr = etSearchContent.getText().toString().trim();
                    String regex = ".*" + searchStr + ".*";
                    // 操作AllPeopleFragment的静态变量集合
                    for (AllPeopleBean.DataBean dataBean : AllPeopleFragment.dataBeanList) {
                        if (dataBean.getPeopleName().matches(regex)) {
                            AllPeopleFragment.dataBeans.add(dataBean);
                        } else if (String.valueOf(dataBean.getHp()).matches(regex)) {
                            AllPeopleFragment.dataBeans.add(dataBean);
                        } else if (String.valueOf(dataBean.getGold()).matches(regex)) {
                            AllPeopleFragment.dataBeans.add(dataBean);
                        } else if (getWorkType(dataBean.getStatus()).matches(regex)) {
                            AllPeopleFragment.dataBeans.add(dataBean);
                        } else if (dataBean.getContent().matches(regex)) {
                            AllPeopleFragment.dataBeans.add(dataBean);
                        }
                    }

                    if (AllPeopleFragment.dataBeans.size() > 0) {
                        HistoryBean t = new HistoryBean(searchStr);
                        List<HistoryBean> historyBeans = historyBeanDao.queryForAll();
                        if (!historyBeans.contains(t)) {
                            historyBeanDao.create(t);
                        }
                    } else {
                        AllPeopleFragment.dataBeans.addAll(AllPeopleFragment.dataBeanList);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            };

            ResultData resultData = (String str) -> etSearchContent.setText(str);

            searchHistoryFragment = new SearchHistoryFragment(resultData);
            allPeopleFragment = new AllPeopleFragment();
            startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public interface ResultData {
        /**
         * 点击置入历史记录
         *
         * @param str 搜索记录文本
         */
        void input(String str);
    }
}
