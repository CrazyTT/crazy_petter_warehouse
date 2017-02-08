package com.bjdv.lib.utils.base;

import android.app.Application;

/**
 * Created by liuliuchen on 2016/11/16.
 */

public class IApplicationiml extends Application {
    protected static IApplicationiml instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static IApplicationiml getInstance() {
        return instance;
    }


}
