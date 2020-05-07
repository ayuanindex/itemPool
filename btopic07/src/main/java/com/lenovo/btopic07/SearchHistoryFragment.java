package com.lenovo.btopic07;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.btopic07.bean.HistoryBean;
import com.lenovo.btopic07.databases.OrmHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.os.Looper.getMainLooper;

/**
 * @author ayuan
 */
public class SearchHistoryFragment extends BaseFragment {

    private final MainActivity.ResultData resultData;
    private GridView gvList;
    private List<HistoryBean> historyBeans = new ArrayList<>();
    private Dao<HistoryBean, ?> historyBeanDao;
    private CustomerAdapter customerAdapter;
    private Handler uiHandler;

    public SearchHistoryFragment(MainActivity.ResultData resultData) {
        this.resultData = resultData;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {
        gvList = view.findViewById(R.id.gvList);

        gvList.setOnItemClickListener((AdapterView<?> parent, View view1, int position, long id) -> resultData.input(historyBeans.get(position).getLabel()));
    }

    @Override
    protected void init() {
        try {
            uiHandler = new Handler(getMainLooper());

            historyBeans = new ArrayList<>();

            historyBeanDao = OrmHelper.getInstance().getDao(HistoryBean.class);
            historyBeans.addAll(historyBeanDao.queryForAll());

            customerAdapter = new CustomerAdapter();
            gvList.setAdapter(customerAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvLabel;
        private ImageView ivRemove;

        @Override
        public int getCount() {
            return historyBeans.size();
        }

        @Override
        public HistoryBean getItem(int position) {
            return historyBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(mActivity, R.layout.item_history, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvLabel.setText(getItem(position).getLabel());
            ivRemove.setOnClickListener((View v) -> new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        List<HistoryBean> label = historyBeanDao.queryBuilder().where().eq("label", getItem(position).getLabel()).query();
                        if (label.size() > 0) {
                            historyBeans.remove(getItem(position));
                            uiHandler.post(() -> customerAdapter.notifyDataSetChanged());
                            historyBeanDao.delete(label);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.start());
            return view;
        }

        private void initView(View view) {
            tvLabel = view.findViewById(R.id.tv_label);
            ivRemove = view.findViewById(R.id.iv_remove);
        }
    }

}
