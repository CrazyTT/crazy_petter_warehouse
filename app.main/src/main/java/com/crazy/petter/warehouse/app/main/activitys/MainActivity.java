package com.crazy.petter.warehouse.app.main.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {
    @Bind(R.id.storage)
    ButtonAutoBg mStorage;
    @Bind(R.id.storage_with_num)
    ButtonAutoBg mStorageWithNum;
    @Bind(R.id.shelves)
    ButtonAutoBg mShelves;
    @Bind(R.id.shelves_with_num)
    ButtonAutoBg mShelvesWithNum;
    @Bind(R.id.pick)
    ButtonAutoBg mPick;
    @Bind(R.id.pack)
    ButtonAutoBg mPack;
    @Bind(R.id.deliver)
    ButtonAutoBg mDeliver;
    @Bind(R.id.activity_main)
    LinearLayout mActivityMain;
    SharedPreferencesUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp = new SharedPreferencesUtil(this);
        initViews();
    }

    private void initViews() {
        mStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setBoolean("tray", false);
                startActivity(new Intent(MainActivity.this, StorageActivity.class));
            }
        });
        mStorageWithNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setBoolean("tray", true);
                startActivity(new Intent(MainActivity.this, TrayStorageActivity.class));
            }
        });
    }

    long mExitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
