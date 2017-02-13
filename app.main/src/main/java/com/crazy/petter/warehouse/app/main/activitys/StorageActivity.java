package com.crazy.petter.warehouse.app.main.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.crazy.petter.warehouse.app.main.Fragment.stroage.DetialsStoreageFragment;
import com.crazy.petter.warehouse.app.main.Fragment.stroage.ScanStoreageFragment;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.FragmentTabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StorageActivity extends BaseActivity {
    @Bind(R.id.rb_home)
    RadioButton mRbHome;
    @Bind(R.id.rb_work)
    RadioButton mRbWork;
    @Bind(R.id.rg_content)
    RadioGroup mRgContent;
    @Bind(R.id.fragment_content)
    FrameLayout mFragmentContent;
    @Bind(R.id.activity_storage)
    LinearLayout mActivityStorage;
    private List<Fragment> fragments = new ArrayList<>();
    private ArrayList<RadioButton> rbs = new ArrayList<>();
    FragmentTabAdapter tabAdapter;
    private Fragment mTab1;
    private Fragment mTab2;
    SharedPreferencesUtil mSharedPreferencesUtil;


    public static String getTemp() {
        return temp;
    }

    public static void setTemp(String temp) {
        StorageActivity.temp = temp;
    }

    public static String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        mSharedPreferencesUtil = new SharedPreferencesUtil(this);
        mSharedPreferencesUtil.setString("num", "");
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rbs.add(mRbHome);
        rbs.add(mRbWork);
        fragments.add(new ScanStoreageFragment());
        fragments.add(new DetialsStoreageFragment());
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fragment_content, mRgContent);
        mRgContent.setOnCheckedChangeListener(tabAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

}
