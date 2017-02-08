package com.bjdv.lib.utils.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Title:Fragment基类 <br>
 * Description: 接受广播<br>
 * Date: 16/5/16 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public abstract class BaseBroadcastFragment extends BaseFragment {
    private FragmentBroadCastReceiver broadcastReceiver;

    public class FragmentBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onBroadcastReceive(context, intent);
        }
    }
    public abstract void onBroadcastReceive(Context context, Intent intent);
    public void registerBroadcast(Context context, String...actions){
        broadcastReceiver = new FragmentBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        for(String s:actions){
            filter.addAction(s);
        }
        context.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver!=null) getActivity().unregisterReceiver(broadcastReceiver);
    }
}
