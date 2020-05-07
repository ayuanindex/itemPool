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
import com.lenovo.btopic07.bean.HistoryBean;
import com.lenovo.btopic07.databases.OrmHelper;

import java.sql.SQLException;

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
        etSearchContent.setOnTouchListener((View v, MotionEvent event) -> {
            startFragmentWithReplace(R.id.ll_replace, searchHistoryFragment);
            tvSearch.setText("取消");
            model = true;
            return true;
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
                Log.d(TAG, "afterTextChanged: " + s);
                uiHandler.removeCallbacks(r);
                uiHandler.postDelayed(r, 1000);
            }
        });

        cardCancel.setOnClickListener((View v) -> {
            if (model) {
                startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                etSearchContent.setFocusable(View.FOCUSABLE);
                tvSearch.setText("搜索");
                model = false;
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
            r = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 执行延时操作，切换到人员显示界面
                        startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
                        model = false;
                        tvSearch.setText("搜索");

                        historyBeanDao.create(new HistoryBean(etSearchContent.getText().toString().trim()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };

            searchHistoryFragment = new SearchHistoryFragment();
            allPeopleFragment = new AllPeopleFragment();
            startFragmentWithReplace(R.id.ll_replace, allPeopleFragment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
