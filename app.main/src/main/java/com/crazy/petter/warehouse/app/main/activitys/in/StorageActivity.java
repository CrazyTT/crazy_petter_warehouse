package com.crazy.petter.warehouse.app.main.activitys.in;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.crazy.petter.warehouse.app.main.Fragment.stroage.DetialsStoreageFragment;
import com.crazy.petter.warehouse.app.main.Fragment.stroage.ScanStoreageFragment;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.FragAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StorageActivity extends BaseActivity {

    @Bind(R.id.main_tab)
    TabLayout mMainTab;
    @Bind(R.id.main_viewpager)
    ViewPager mMainViewpager;
    @Bind(R.id.activity_storage)
    LinearLayout mActivityStorage;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> rbs = new ArrayList<>();
    FragAdapter tabAdapter;
    SharedPreferencesUtil sp;
    Fragment tabOne;
    Fragment tabTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        sp = new SharedPreferencesUtil(this);
        sp.setString("num", "");
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rbs.add("扫描收货");
        rbs.add("扫描收货");
        tabOne = new ScanStoreageFragment();
        tabTwo = new DetialsStoreageFragment();
        fragments.add(tabOne);
        fragments.add(tabTwo);
        tabAdapter = new FragAdapter(getSupportFragmentManager(), fragments, rbs);
        mMainTab.setupWithViewPager(mMainViewpager);
        mMainViewpager.setAdapter(tabAdapter);
        mMainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    sp.setBoolean("isRefresh", true);
                    tabTwo.onResume();
                } else {
                    sp.setBoolean("isRefresh", false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            mMainViewpager.setCurrentItem(0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENDCALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            mMainViewpager.setCurrentItem(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
