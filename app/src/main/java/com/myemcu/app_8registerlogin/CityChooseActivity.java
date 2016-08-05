package com.myemcu.app_8registerlogin;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CityChooseActivity extends ExpandableListActivity {

    private String[]   provinces = new String[] {"北京","湖北","四川"}; // 省会
    private String[][] cities = new String[][] {
            {"朝阳","海淀","丰台","大兴"},
            {"武昌","汉口","汉阳","十堰"},
            {"成都","绵阳","盐亭","江油"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            // 获取(指定组位置)的(指定子列表项)数据
            public Object getChild(int groupPosition, int childPosition) {
                return cities[groupPosition][childPosition];
            }

            @Override
            // 获取子列表项Id
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            // 获取指定组的列表项数,即:provinces数组中的条目数
            public int getChildrenCount(int groupPosition) {
                return cities[groupPosition].length;
            }

            private TextView getTextView() { // 显示文本信息

                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,128);    // 布局高128

                TextView txt = new TextView(CityChooseActivity.this);

                txt.setLayoutParams(lp);                                // 设置布局
                txt.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT); // 设置控件对齐方式
                txt.setPadding(100,0,0,0);                              // 文本左边距
                txt.setTextSize(20);                                    // 设置字体大小

                return txt;
            }

            @Override
            // 每个子选项的外观设置
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

                TextView txt = getTextView();
                txt.setText(getChild(groupPosition, childPosition).toString());

                return txt;
            }

            //--------------------------------------------------------------------------------------

            @Override
            // 获取指定组位置处的组数据
            public Object getGroup(int groupPosition) {
                return provinces[groupPosition];
            }

            @Override
            // 获取该扩展列表组数
            public int getGroupCount() {
                return provinces.length;
            }

            @Override
            // 获取组Id
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            // 该选项决定每个组的外观
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                LinearLayout ll = new LinearLayout(CityChooseActivity.this); // 创建线性布局
                ll.setOrientation(LinearLayout.VERTICAL);                    // 设定垂直方向
                ImageView logo = new ImageView(CityChooseActivity.this);
                ll.addView(logo);

                TextView txt = getTextView();
                txt.setText(getGroup(groupPosition).toString());
                ll.addView(txt);

                return ll;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };

        setListAdapter(adapter); // 设置适配器

        getExpandableListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("city", cities[groupPosition][childPosition]);
                intent.putExtras(bundle);

                CityChooseActivity.this.setResult(0,intent);    // 设置结果码和退回的Activity
                CityChooseActivity.this.finish();               // 结束本Activity

                return false; // 格式需要
            }
        });

    }

}
