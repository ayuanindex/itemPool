package com.lenovo.atopic06;


import android.graphics.Matrix;
import android.graphics.PointF;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lenovo.basic.base.act.Base2Activity;
import com.lenovo.basic.utils.ImageUtils;


public class PictureActivity extends Base2Activity {
    private ImageView mIv;
    private Button mTvBack;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mTvBack = (Button) findViewById(R.id.tv_back);
    }

    @Override
    protected void initEvent() {
        //关闭当前页面
        mTvBack.setOnClickListener(v -> finish());
        //设置触摸
        mIv.setOnTouchListener(new MyTouchListener());
    }

    @Override
    protected void initData() {
        String icon = getIntent().getStringExtra("data");
        if (!TextUtils.isEmpty(icon)) {
            ImageUtils.setBitmapCenterCrop(this, getResources().getIdentifier(icon, "drawable", getPackageName()), mIv);
        }
    }

    private class MyTouchListener implements View.OnTouchListener {
        private int mode = 0; //默认状态
        private int MODE_DRAG = 1; // 拖拉状态
        private int MODE_ZOOM = 2;//缩放状态
        private PointF startPointF = new PointF();//图片开始位置记录
        private Matrix matrix = new Matrix();//拖拉移动后的位置
        private Matrix currentMatrix = new Matrix();//当前位置
        private float startDis; //记录两手指开始距离
        private PointF midPointF; //记录手指中间点


        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    //记录当前位置
                    currentMatrix.set(mIv.getImageMatrix());
                    startPointF.set(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    startDis = distance(event);//两手指间距离
                    if (startDis > 10f) {
                        midPointF = mid(event);
                        currentMatrix.set(mIv.getImageMatrix());
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPointF.x;
                        float dy = event.getY() - startPointF.y;
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }
                    if (mode == MODE_ZOOM) {
                        float endDis = distance(event);
                        if (endDis > 10f) {
                            float scale = endDis / startDis;
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPointF.x, midPointF.y);
                        }
                    }
                    break;
            }
            mIv.setImageMatrix(matrix);
            return true;
        }

        //计算两手指之间的点
        private PointF mid(MotionEvent event) {
            float dx = event.getX(1) + event.getX(0);
            float dy = event.getY(1) + event.getY(0);
            return new PointF(dx / 2, dy / 2);
        }

        //计算两手指之间的距离
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

    }
}
