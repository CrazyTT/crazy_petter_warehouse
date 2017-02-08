package com.bjdv.lib.utils.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Title:
 * Desc:
 * Created by PHOON-THINK on 2015/4/16.
 * Copyright (c) 2015 DATANG BJDV
 */
public class ToastUtils {
    public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;
    private static android.widget.Toast toast;
    private static Handler handler = new Handler();
    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        toast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        toast(context, message, duration);
    }


    /**
     * <p/>
     *
     * @param ctx
     * @param msg
     * @param duration
     * @author chenliuliu, 2014-9-15
     */
    public static void toast(Context ctx, CharSequence msg, int duration) {
        handler.removeCallbacks(run);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 3000;
                break;
            default:
                break;
        }
        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = android.widget.Toast.makeText(ctx, msg, duration);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }
}

