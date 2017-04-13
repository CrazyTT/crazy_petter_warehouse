package com.crazy.petter.warehouse.app.main.activitys.in;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.crazy.petter.warehouse.app.main.Fragment.stroage.DetialsStoreageFragment2;
import com.crazy.petter.warehouse.app.main.Fragment.stroage.ReceiptFragment;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.FragAdapter;
import com.crazy.petter.warehouse.app.main.beans.ChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.grantland.widget.AutofitTextView;

public class ReceiptMixActivity extends BaseActivity {

    @Bind(R.id.main_tab)
    TabLayout mMainTab;
    @Bind(R.id.main_viewpager)
    ViewPager mMainViewpager;
    @Bind(R.id.activity_storage)
    LinearLayout mActivityStorage;
    @Bind(R.id.edt_order_num)
    AutofitTextView mEdtOrderNum;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> rbs = new ArrayList<>();
    FragAdapter tabAdapter;
    SharedPreferencesUtil sp;
    ReceiptFragment tabOne;
    DetialsStoreageFragment2 tabTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        sp = new SharedPreferencesUtil(this);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rbs.add("扫描收货");
        rbs.add("收货明细");
        tabOne = new ReceiptFragment();
        tabTwo = new DetialsStoreageFragment2();
        fragments.add(tabOne);
        fragments.add(tabTwo);
        tabAdapter = new FragAdapter(getSupportFragmentManager(), fragments, rbs);
        mMainTab.setupWithViewPager(mMainViewpager);
        mMainViewpager.setAdapter(tabAdapter);
        mEdtOrderNum.setText(sp.getString("num"));
        mMainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                if (position == 1) {
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

    int currentIndex = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENDCALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (currentIndex == 0) {
                mMainViewpager.setCurrentItem(1);
            } else {
                mMainViewpager.setCurrentItem(0);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_CALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (currentIndex == 0) {
                ChangeEvent changeEvent = new ChangeEvent();
                changeEvent.setEvent("commit");
                EventBus.getDefault().post(changeEvent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        if (tabOne.isFinish()) {
            builder.setMessage("收货已全部完成！");
        } else {
            builder.setMessage("收货未完成，确认退出？");
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReceiptMixActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ReceiptMixActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }
}
