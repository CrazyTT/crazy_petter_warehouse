package com.crazy.petter.warehouse.app.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.crazy.petter.warehouse.app.main.R;

import java.util.List;

/**
 * Created by liuliuchen on 15/10/30.
 */
public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private RadioGroup rgs;
    private FragmentActivity fragmentActivity;
    private int fragmentContentId;

    private int currentTab;

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;
    private SharedPreferencesUtil sp;

    public FragmentTabAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentActivity = fragmentActivity;
        this.fragmentContentId = fragmentContentId;
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.commit();
        RadioButton radioButton = (RadioButton) rgs.getChildAt(0);
        radioButton.setChecked(true);
        rgs.setOnCheckedChangeListener(this);
        sp = new SharedPreferencesUtil(fragmentActivity);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgs.getChildCount(); i++) {
            if (rgs.getChildAt(i).getId() == checkedId) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                getCurrentFragment().onPause();
                if (i == 1) {
                    sp.setBoolean("isRefresh", true);
                } else {
                    sp.setBoolean("isRefresh", false);
                }
                if (fragment.isAdded()) {
                    fragment.onResume();
                } else {
                    ft.add(fragmentContentId, fragment);
                }
                showTab(i);
                ft.commit();
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }

            }
        }

    }

    private void showTab(int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
//        Fragment fragment = fragments.get(idx);
//        FragmentTransaction ft = obtainFragmentTransaction(idx);
//        ft.replace(fragmentContentId,fragment);
//
//        ft.commit();


        currentTab = idx;
    }

    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if (index > currentTab) {
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    public interface OnRgsExtraCheckedChangedListener {
        void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
    }

}