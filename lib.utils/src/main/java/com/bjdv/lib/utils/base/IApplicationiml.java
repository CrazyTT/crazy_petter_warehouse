package com.bjdv.lib.utils.base;

import android.app.Application;

/**
 * Created by liuliuchen on 2016/11/16.
 */

public class IApplicationiml extends Application {
    protected volatile static IApplicationiml instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static IApplicationiml getInstance() {
        if (instance == null) {
            synchronized (IApplicationiml.class) {
                if (instance == null) {
                    instance = new IApplicationiml();
                }
            }
        }
        return instance;
    }
}
