package com.crazy.petter.warehouse.app.main.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.entity.GridViewItem;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.activitys.in.PutAwayActivity;
import com.crazy.petter.warehouse.app.main.activitys.in.StorageActivity;
import com.crazy.petter.warehouse.app.main.activitys.in.TrayPutAwayActivity;
import com.crazy.petter.warehouse.app.main.activitys.in.TrayStorageActivity;
import com.crazy.petter.warehouse.app.main.activitys.out.DivideActivity;
import com.crazy.petter.warehouse.app.main.activitys.out.PackActivity;
import com.crazy.petter.warehouse.app.main.activitys.out.PickActivity;
import com.crazy.petter.warehouse.app.main.activitys.out.PickWaveActivity;
import com.crazy.petter.warehouse.app.main.activitys.out.TraySendActivity;
import com.crazy.petter.warehouse.app.main.adapters.GridAdapter;

import java.util.ArrayList;

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
    @Bind(R.id.bocidan)
    ButtonAutoBg mWave;
    @Bind(R.id.fenhuo)
    ButtonAutoBg mFenhuo;
    @Bind(R.id.gr_in)
    GridView mGrIn;
    @Bind(R.id.gr_out)
    GridView mGrOut;
    GridAdapter mAdapterIn, mAdapterOut;

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
                startActivity(new Intent(MainActivity.this, StorageActivity.class));
            }
        });
        mStorageWithNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrayStorageActivity.class));
            }
        });
        mShelves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PutAwayActivity.class));
            }
        });
        mShelvesWithNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrayPutAwayActivity.class));
            }
        });
        mDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TraySendActivity.class));
            }
        });
        mPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PackActivity.class));
            }
        });
        mPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PickActivity.class));
            }
        });
        mWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PickWaveActivity.class));
            }
        });
        mFenhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DivideActivity.class));
            }
        });
        initIn();
        initOut();
    }

    private void initIn() {
        mAdapterIn = new GridAdapter(this, 0);
        ArrayList<GridViewItem> mlist = new ArrayList<>();
        GridViewItem gridViewItem1 = new GridViewItem(R.mipmap.icon_zsk, "扫描收货");
        mlist.add(gridViewItem1);
        GridViewItem gridViewItem2 = new GridViewItem(R.mipmap.icon_gg, "按托盘号扫描收货");
        mlist.add(gridViewItem2);
        GridViewItem gridViewItem3 = new GridViewItem(R.mipmap.icon_saoysao, "扫描上架");
        mlist.add(gridViewItem3);
        GridViewItem gridViewItem4 = new GridViewItem(R.mipmap.icon_kdcs, "按托盘号扫描上架");
        mlist.add(gridViewItem4);
        mGrIn.setAdapter(mAdapterIn);
        mAdapterIn.setList(mlist);
        mGrIn.setOnItemClickListener(new GridViewInOnClickListener());
    }

    private void initOut() {
        mAdapterOut = new GridAdapter(this, 4);
        ArrayList<GridViewItem> mlist = new ArrayList<>();
        GridViewItem gridViewItem1 = new GridViewItem(R.mipmap.icon_wgjyx, "扫描拣货");
        mlist.add(gridViewItem1);
        GridViewItem gridViewItem2 = new GridViewItem(R.mipmap.icon_rwgl, "扫描装箱");
        mlist.add(gridViewItem2);
        GridViewItem gridViewItem3 = new GridViewItem(R.mipmap.icon_tjbb, "按托盘号扫描发货");
        mlist.add(gridViewItem3);
        GridViewItem gridViewItem4 = new GridViewItem(R.mipmap.icon_wghyw, "波次单扫描拣货");
        mlist.add(gridViewItem4);
        GridViewItem gridViewItem5 = new GridViewItem(R.mipmap.icon_khgl, "分货");
        mlist.add(gridViewItem5);
        mGrOut.setAdapter(mAdapterOut);
        mAdapterOut.setList(mlist);
        mGrOut.setOnItemClickListener(new GridViewOutOnClickListener());
    }

    public class GridViewInOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(MainActivity.this, StorageActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, TrayStorageActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, PutAwayActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, TrayPutAwayActivity.class));
                    break;
            }
        }
    }

    public class GridViewOutOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(MainActivity.this, PickActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, PackActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, TraySendActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, PickWaveActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, DivideActivity.class));
                    break;
            }
        }
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
        if (keyCode == KeyEvent.KEYCODE_1 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, StorageActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_2 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, TrayStorageActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_3 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, PutAwayActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_4 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, TrayPutAwayActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_5 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, PickActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_6 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, PackActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_7 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, TraySendActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_8 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, PickWaveActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_9 && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(MainActivity.this, DivideActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_CALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENDCALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
