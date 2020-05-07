package com.lenovo.btopic07;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private LinearLayout llReplace;
    private TextView tvSearch;
    private boolean model = false;
    private AllPeopleFragment allPeopleFragment;
    private SearchHistoryFragment searchHistoryFragment;
    private Runnable r;
    private Handler uiHandler;
    private Dao<HistoryBean, ?> historyBeanDao;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        etSearchContent = (EditText) findViewById(R.id.et_searchContent);
        cardCancel = (CardView) findViewById(R.id.card_cancel);
        llReplace = (LinearLayout) findViewById(R.id.ll_replace);
        tvSearch = (TextView) findViewById(R.id.tv_search);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEvent() {
        etSearchContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    startFragmentWithReplace(R.id.ll_replace, searchHistoryFragment);
                    tvSearch.setText("取消");
                    model = true;
                }
            }
        });

        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    uiHandler.removeCallbacks(r);
                    AllPeopleFragment.dataBeans.addAll(AllPeopleFragment.dataBeanList);
                    startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                } else {
                    Log.d(TAG, "afterTextChanged: " + s);
                    uiHandler.removeCallbacks(r);
                    uiHandler.postDelayed(r, 1000);
                }
            }
        });

        cardCancel.setOnClickListener((View v) -> {
            if (model) {
                startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                etSearchContent.setFocusable(View.FOCUSABLE);
                tvSearch.setText("搜索");
                model = false;
                AllPeopleFragment.dataBeans.clear();
                AllPeopleFragment.dataBeans.addAll(AllPeopleFragment.dataBeanList);
            }
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
                    model = false;
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
                        Log.d(TAG, "initData: " + AllPeopleFragment.dataBeans.toString());
                    } else {
                        AllPeopleFragment.dataBeans.addAll(AllPeopleFragment.dataBeanList);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            };

            ResultData resultData = (String str) -> {
                etSearchContent.setText(str);
            };

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
        void input(String str);
    }
}
