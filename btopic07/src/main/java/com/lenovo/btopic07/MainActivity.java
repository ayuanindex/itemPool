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
            // ??????????????????????????????
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
                    // ????????????????????????????????????????????????
                    startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                    tvSearch.setText("??????");

                    AllPeopleFragment.dataBeans.clear();

                    // ??????
                    String searchStr = etSearchContent.getText().toString().trim();
                    String regex = ".*" + searchStr + ".*";
                    // ??????AllPeopleFragment?????????????????????
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
     * ??????????????????
     *
     * @param status ?????????????????????
     * @return ??????????????????
     */
    private String getWorkType(int status) {
        switch (status) {
            case 0:
                return "?????????";
            case 1:
                return "??????";
            case 2:
                return "????????????";
            case 3:
                return "????????????";
            default:
                return "????????????";
        }
    }

    public interface ResultData {
        /**
         * ????????????????????????
         *
         * @param str ??????????????????
         */
        void input(String str);
    }
}
