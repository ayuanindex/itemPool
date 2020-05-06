package com.lenovo.btopic06;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author ayuan
 */
public class BannerActivity extends AppCompatActivity {
    private WebView webView;
    private TextView tvJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
        initEvent();
        initData();
    }


    protected void initView() {
        webView = findViewById(R.id.webView);
        tvJump = findViewById(R.id.tv_jump);
    }

    protected void initEvent() {
        tvJump.setOnClickListener((View v) -> finish());
    }

    protected void initData() {
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        //载入网址
        webView.loadUrl("file:///android_asset/banner/banner.html");

        //设置启用Java脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置多媒体播放呢
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
    }
}
