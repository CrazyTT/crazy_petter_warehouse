package com.crazy.petter.warehouse.app.login;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private List<ImageView> list;
    private List<String> titleList;
    public MyViewPagerAdapter(List<ImageView> mList, List<String> titleList) {
        this.list = mList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));//添加页卡
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);//页卡标题
    }
}
