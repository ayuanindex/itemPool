package com.lenovo.basic.base.frag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lenovo.basic.base.act.BaseFragmentActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

/**
 * 所有Fragment的基类，使用此Fragment必须依赖BaseFragmentActivity
 */
public abstract class BaseFragment extends RxFragment {
    /**
     * Log标记
     */
    public final String TAG = this.getClass().getSimpleName();

    public BaseFragmentActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseFragmentActivity) context;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layId = getLayout();
        // 初始化当前的跟布局，但是不在创建时就添加到container里边
        View rootView = inflater.inflate(layId, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 给Fragment设置布局文件
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayout();

    /**
     * 初始化布局
     *
     * @param view
     */
    protected abstract void initView(View view);


    /**
     * 初始化数据
     */
    protected abstract void init();

    /**
     * 打开一个Fragment,并将其添加到回退栈
     *
     * @param resId    被替换的FragmentLayout布局
     * @param fragment
     */
    public void startFragmentAddToBackStack(@IdRes int resId, @io.reactivex.annotations.NonNull BaseFragment fragment) {
        mActivity.startFragmentAddToBackStack(resId, fragment);
    }

    /**
     * 打开一个Fragment
     *
     * @param resId
     * @param fragment
     */
    public void startFragmentWithReplace(@IdRes int resId, @io.reactivex.annotations.NonNull BaseFragment fragment) {
        mActivity.startFragmentWithReplace(resId, fragment);
    }

    /**
     * 打开Activity
     *
     * @param activity 当前activity
     * @param cls      目标界面
     * @param b        是否结束当前页面
     */
    public void startActivity(Activity activity, Class<? extends Activity> cls, boolean b) {
        mActivity.startActivity(activity, cls, b);
    }

    public void startActivity(Intent intent, boolean b) {
        mActivity.startActivity(intent, b);
    }


    /**
     * 通过包名跳转到指定model下的Activity
     *
     * @param activity 要跳转的activity所在model的包名
     * @param cls      目标Activity的包路径
     * @param isFinish 是关闭当前Activity
     * @return 返回跳转时产生的Intent
     */
    public Intent startActivity(String activity, String cls, boolean isFinish) {
        return mActivity.startActivity(activity, cls, isFinish);
    }

    /**
     * 关闭当前页面
     *
     * @param activity 被关闭的页面
     */
    public void closeActivity(Activity activity) {
        mActivity.closeActivity(activity);
    }

    /**
     * Fragment回退
     */
    public void popBackStack() {
        mActivity.popBackStack();
    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
