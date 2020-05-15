package com.lenovo.btopic12;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textview.MaterialTextView;
import com.lenovo.basic.base.act.BaseActivity;

/**
 * @author ayuan
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ListView lvList;
    private AppCompatImageView ivImage;
    private MaterialTextView tvLink;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvList = findViewById(R.id.lv_list);
        ivImage = findViewById(R.id.iv_image);
        tvLink = findViewById(R.id.tv_link);
    }

    @Override
    protected void initEvent() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    class CustomerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}
