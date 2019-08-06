package com.ti.fragmenttabhostdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LayoutInflater layoutInflater;
    // ViewPager的数据
    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vp;

    // FragmentTabHost的数据
    private FragmentTabHost mFragmentTabHost;
    // 每个tab标签要关联的fragment
    private Class fragmentArray[] = { HomeFragment.class, MeFragment.class };
    // 每个tab标签要显示的图片
    private int imageArray[] = { R.drawable.tab_home_btn, R.drawable.tab_me_btn};
    // 每个tab标签要显示的文字
    private String textArray[] = { "首页", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化控件
        initPage();//初始化页面
    }

    // 控件初始化控件
    private void initView(){
        layoutInflater = LayoutInflater.from(this);//加载布局管理器
        vp = findViewById(R.id.vp);
        // 实现OnPageChangeListener接口,监听Tab选项卡的变化，然后通知ViewPager适配器切换界面
        // 设置页面切换时的监听器,让ViewPager滑动的时候能够带着底部菜单联动
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            //  表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            //arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。
            }

            @Override
            public void onPageSelected(int arg0) {
                // arg0是表示你当前选中的页面位置Postion，这事件是在你页面跳转完毕的时候调用的。
                TabWidget widget = mFragmentTabHost.getTabWidget();
                int oldFocusability = widget.getDescendantFocusability();
                // 设置View覆盖子类控件而直接获得焦点
                widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                // 根据位置Postion设置当前的Tab
                mFragmentTabHost.setCurrentTab(arg0);
                // 设置取消分割线
                widget.setDescendantFocusability(oldFocusability);
            }

        });
        // 实例化FragmentTabHost对象
        mFragmentTabHost = findViewById(R.id.tab_host);
        // 绑定viewpager
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.vp);
        // 实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换
        // 当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {// Tab改变的时候调用
                int position = mFragmentTabHost.getCurrentTab();
                vp.setCurrentItem(position);// 把选中的Tab的位置赋给适配器，让它控制页面切换
            }
        });

        int count = textArray.length;

        // 新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置标签、图标和文字

            // 将xml布局转换为view对象
            View view = layoutInflater.inflate(R.layout.tab_content, null);
            // 利用view对象，找到布局中的组件,并设置内容，然后返回视图
            ImageView mImageView = view.findViewById(R.id.tab_imageview);
            TextView mTextView = view.findViewById(R.id.tab_textview);
            mImageView.setBackgroundResource(imageArray[i]);
            mTextView.setText(textArray[i]);
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(textArray[i]).setIndicator(view);
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            mFragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            mFragmentTabHost.setTag(i);
            // 设置Tab被选中的时候颜色改变
            mFragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }


    }

    // 初始化Fragment
    private void initPage() {
        HomeFragment fragment1 = new HomeFragment();
        MeFragment fragment2 = new MeFragment();
        list.add(fragment1);
        list.add(fragment2);
        //绑定Fragment适配器
        vp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), list));
        mFragmentTabHost.getTabWidget().setDividerDrawable(null);
    }



}
