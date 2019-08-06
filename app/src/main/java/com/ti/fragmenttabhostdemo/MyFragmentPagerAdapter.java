package com.ti.fragmenttabhostdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {// 根据Item的位置返回对应位置的Fragment，绑定item和Fragment
        return list.get(i);
    }

    @Override
    public int getCount() {// 设置Item的数量
        return list.size();
    }
}
