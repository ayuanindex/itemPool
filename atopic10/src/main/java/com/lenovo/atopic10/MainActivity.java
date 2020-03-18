package com.lenovo.atopic10;

import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.ImageUtils;
import com.lenovo.basic.utils.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 编码实现备料管理
 */
public class MainActivity extends BaseActivity {
    private ExpandableListView mExpandList;
    private ApiService apiService;
    private Map<Integer, UserProductionLine.DataBean> userProductionLineMap;
    private Map<Integer, Part.DataBean> partMap;
    private MyAdapter myAdapter;
    private Button mBtnAdd;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mExpandList = (ExpandableListView) findViewById(R.id.expand_list);
        mBtnAdd = (Button) findViewById(R.id.btn_add);

    }

    @Override
    protected void initEvent() {
        //列表子项点击事件，显示材料图片
        mExpandList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Bean.Data data = myAdapter.getDatas().get(groupPosition).getDatas().get(childPosition);
            String iconName = data.getIconName();
            //获取本地图片资源
            int drawableId = MainActivity.this.getResources().getIdentifier(iconName.toLowerCase(), "drawable", getPackageName());
            //创建对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = View.inflate(MainActivity.this, R.layout.layout_dialog_iamge, null);
            ImageView imageView = view.findViewById(R.id.iv);
            ImageUtils.setBitmapCenterCrop(MainActivity.this, drawableId, imageView);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });

        mBtnAdd.setOnClickListener(v -> showAddDialog());
    }


    //显示添加对话框
    private void showAddDialog() {
        View view = View.inflate(MainActivity.this, R.layout.dialog, null);
        Spinner mSp1;
        Spinner mSp2;
        Spinner mSp3;
        Button mBtnCancel;
        Button mBtnTrue;
        mSp1 = (Spinner) view.findViewById(R.id.sp1);
        mSp2 = (Spinner) view.findViewById(R.id.sp2);
        mSp3 = (Spinner) view.findViewById(R.id.sp3);
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
        mBtnTrue = (Button) view.findViewById(R.id.btn_true);
        //获取生产线数据
        List<Integer> lineList = new ArrayList<>();
        userProductionLineMap.forEach((integer, dataBean) -> lineList.add(integer));
        //获取原材料数据
        List<String> partList = new ArrayList<>();
        partMap.forEach((integer, dataBean) -> {
            partList.add(dataBean.getPartName());
        });
        //创建数量数据
        List<Integer> numList = new ArrayList<>();
        IntStream.range(1, 11).forEach(value -> numList.add(value));
        //设置spinner适配器
        mSp1.setAdapter(new ArrayAdapter<Integer>(this, R.layout.item_textview, lineList));
        mSp2.setAdapter(new ArrayAdapter<String>(this, R.layout.item_textview, partList));
        mSp3.setAdapter(new ArrayAdapter<Integer>(this, R.layout.item_textview, numList));
        //创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        //取消按钮
        mBtnCancel.setOnClickListener(v -> alertDialog.dismiss());
        //添加按钮
        mBtnTrue.setOnClickListener(v -> {
            int[] partID = new int[1];

            //找原材料id
            partMap.forEach((integer, dataBean) -> {
                if (dataBean.getPartName().equals(partList.get(mSp2.getSelectedItemPosition()))) {
                    partID[0] = dataBean.getId();
                }
            });

            //添加备料
            apiService.createUserParts(1, lineList.get(mSp1.getSelectedItemPosition()),
                    partID[0], mSp3.getSelectedItemPosition() + 1)
                    .map(CreatePart::getData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dataBeans -> {
                        query();
                        alertDialog.dismiss();
                    }, Throwable::printStackTrace);
        });
        //显示对话框
        alertDialog.show();
        //获取窗口
        Window window = alertDialog.getWindow();
        if (window != null)//设置窗口背景透明
            window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        query();
    }


    @SuppressWarnings("CheckResult")
    private void query() {
        //保存生产线数据
        userProductionLineMap = new HashMap<>();
        //保存原材料数据
        partMap = new HashMap<>();
        //获取生产线
        apiService.getAllUserProductionLine()
                .map(UserProductionLine::getData)
                .flatMap(dataBeans -> {
                    //保存生产线数据
                    dataBeans.forEach(dataBean -> userProductionLineMap.put(dataBean.getId(), dataBean));
                    //请求原材料数据
                    return apiService.getAllPart().map(Part::getData);
                })
                .flatMap(dataBeans -> {
                    //保存原材料数据
                    dataBeans.forEach(dataBean -> partMap.put(dataBean.getId(), dataBean));
                    //查询学生备料
                    return apiService.getAllUserParts().map(UserParts::getData);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    List<Integer> lineIdList = new ArrayList<>();
                    List<Bean> datas = new ArrayList<>();
                    //合成数据,准备二级列表数据
                    dataBeans.forEach(dataBean -> {
                        //创建一级菜单数据
                        int userProductionLineId = dataBean.getUserProductionLineId();
                        if (!lineIdList.contains(userProductionLineId)) {
                            lineIdList.add(userProductionLineId);
                            Bean bean = new Bean();
                            bean.setLineId(userProductionLineId);
                            if (userProductionLineMap.get(userProductionLineId) == null) {
                                bean.setAi(false);
                            } else {
                                bean.setAi(userProductionLineMap.get(userProductionLineId).getIsAI() == 1);
                            }
                            datas.add(bean);

                        }
                    });
                    //创建二级菜单数据
                    dataBeans.forEach(dataBean -> {
                        int userProductionLineId = dataBean.getUserProductionLineId();
                        for (int i = 0; i < datas.size(); i++) {
                            Bean bean = datas.get(i);
                            List<Bean.Data> data = bean.getDatas();
                            if (bean.getDatas() == null) {
                                data = new ArrayList<>();
                            }
                            bean.setDatas(data);
                            if (bean.getLineId() == userProductionLineId) {
                                Bean.Data data1 = new Bean.Data();
                                data1.setNum(dataBean.getNum());
                                data1.setArea(partMap.get(dataBean.getPartId()).getArea());
                                data1.setPratName(partMap.get(dataBean.getPartId()).getPartName());
                                data1.setIconName(partMap.get(dataBean.getPartId()).getIcon());
                                data.add(data1);
                            }
                        }
                    });
                    //设置适配器
                    myAdapter = new MyAdapter(datas);
                    mExpandList.setAdapter(myAdapter);
                    //列表展开
                    for (int i = 0; i < myAdapter.getGroupCount(); i++) {
                        mExpandList.expandGroup(i);
                    }
                }, Throwable::printStackTrace);
    }

    //二级列表适配器
    private class MyAdapter extends BaseExpandableListAdapter {

        private List<Bean> datas;

        public List<Bean> getDatas() {
            return datas;
        }

        public void setDatas(List<Bean> datas) {
            this.datas = datas;
        }

        public MyAdapter(List<Bean> datas) {
            this.datas = datas;
        }

        @Override
        public int getGroupCount() {
            return datas.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return datas.get(groupPosition).getDatas().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.item_group, null);
            TextView mTvLine = (TextView) view.findViewById(R.id.tv_line);
            TextView mTvAi = (TextView) view.findViewById(R.id.tv_ai);
            mTvLine.setText("生产线：" + datas.get(groupPosition).getLineId());
            mTvAi.setText(datas.get(groupPosition).isAi() ? "AI生产线" : "不是AI生产线");
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.item_child, null);
            TextView mTv1 = (TextView) view.findViewById(R.id.tv_1);
            TextView mTv2 = (TextView) view.findViewById(R.id.tv_2);
            TextView mTv3 = (TextView) view.findViewById(R.id.tv_3);
            Bean.Data data = datas.get(groupPosition).getDatas().get(childPosition);
            mTv1.setText(data.getPratName());
            mTv2.setText("数量：" + data.getNum());
            mTv3.setText("占地：" + data.getArea());
            return view;
        }

        //子item是否可点击
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
