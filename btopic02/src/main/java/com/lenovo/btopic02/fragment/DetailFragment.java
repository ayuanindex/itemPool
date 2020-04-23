package com.lenovo.btopic02.fragment;

import android.view.View;
import android.widget.TextView;

import com.lenovo.basic.base.frag.BaseFragment;
import com.lenovo.btopic02.R;
import com.lenovo.btopic02.bean.StudentStaffBean;

/**
 * @author ayuan
 */
public class DetailFragment extends BaseFragment {

    private final StudentStaffBean.DataBean child;
    private TextView tvHaha;

    public DetailFragment(StudentStaffBean.DataBean child) {
        this.child = child;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(View view) {

        tvHaha = (TextView) view.findViewById(R.id.tv_haha);
    }

    @Override
    protected void init() {
        tvHaha.setText(child.toString());
    }
}
