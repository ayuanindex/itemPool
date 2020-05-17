package com.lenovo.btopic12;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.lenovo.btopic12.bean.SimpleBean;

import java.util.ArrayList;

/**
 * @author ayuan
 */
public class DesActivity extends AppCompatActivity {

    private ArrayList<SimpleBean> simpleBeans;
    private TextView tvHp;
    private CardView cardChild;
    private CardView cardParent;
    private ImageView ivLeft;
    private ImageView ivImage;
    private TextView tvName;
    private TextView tvContent;
    private TextView tvConsume;
    private ImageView ivRight;
    private int current;
    private TextView tvPrevious;
    private TextView tvNextStep;
    int start = 0, end = 19;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        tvHp = findViewById(R.id.tv_hp);
        cardChild = findViewById(R.id.cardChild);
        cardParent = findViewById(R.id.cardParent);
        ivLeft = findViewById(R.id.iv_left);
        ivImage = findViewById(R.id.iv_image);
        tvName = findViewById(R.id.tv_name);
        tvContent = findViewById(R.id.tv_content);
        tvConsume = findViewById(R.id.tv_consume);
        ivRight = findViewById(R.id.iv_right);
        tvPrevious = findViewById(R.id.tv_previous);
        tvNextStep = findViewById(R.id.tv_nextStep);
    }

    private void initListener() {
        tvPrevious.setOnClickListener((View v) -> {
            if (current > start) {
                current--;
            }
            updateView();
        });


        tvNextStep.setOnClickListener((View v) -> {
            if (current < end) {
                current++;
            }
            updateView();
        });
    }

    private void initData() {
        int resourceId = getIntent().getIntExtra("resourceId", 1);
        current = getIntent().getIntExtra("current", 1);

        ivImage.setImageResource(resourceId);

        simpleBeans = MainActivity.simpleBeans;

        updateView();
    }

    @SuppressLint("SetTextI18n")
    private void updateView() {
        // 根据但前位置判断左右两边是否有生产工序，有则显示，没有则隐藏
        if (current == start) {
            ivLeft.setVisibility(View.GONE);
            tvPrevious.setVisibility(View.GONE);

            ivRight.setVisibility(View.VISIBLE);
            tvNextStep.setVisibility(View.VISIBLE);

            ivRight.setImageResource(simpleBeans.get(current + 1).getIcon());
        } else if (current == end) {
            ivLeft.setVisibility(View.VISIBLE);
            tvPrevious.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.GONE);
            tvNextStep.setVisibility(View.GONE);

            ivLeft.setImageResource(simpleBeans.get(current - 1).getIcon());
        } else {
            ivLeft.setVisibility(View.VISIBLE);
            tvPrevious.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.VISIBLE);
            tvNextStep.setVisibility(View.VISIBLE);

            ivRight.setImageResource(simpleBeans.get(current + 1).getIcon());
            ivLeft.setImageResource(simpleBeans.get(current - 1).getIcon());
        }

        SimpleBean simpleBean = simpleBeans.get(current);
        // 填充中间主体部分
        tvHp.setText("HP:" + simpleBean.getHp());
        ivImage.setImageResource(simpleBean.getIcon());
        tvName.setText(simpleBean.getLinkName());
        tvContent.setText(simpleBean.getDes());
        tvConsume.setText("损耗：" + simpleBean.getConsume());

        // 设置进度条
        int width = cardParent.getLayoutParams().width;
        float v = simpleBean.getHp() / 100f;
        float progress = width * v;

        ValueAnimator valueAnimator;
        valueAnimator = ValueAnimator.ofFloat(0, progress);

        valueAnimator.addUpdateListener((ValueAnimator animation) -> {
            float animatedValue = (float) animation.getAnimatedValue();
            cardChild.getLayoutParams().width = (int) animatedValue;
            cardChild.requestLayout();
        });

        valueAnimator.setDuration(500);
        valueAnimator.start();
    }
}
