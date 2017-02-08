package com.bjdv.lib.utils.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Title:Handler类
 * Desc:
 * Created by PHOON-THINK on 2015/3/24.
 * Copyright (c) 2015 DATANG BJDV
 */
public class WeakReferenceHandler<T> extends Handler {

    private WeakReference<T> mReference;
    private HandlerInterface handler;

	public WeakReferenceHandler(T reference) {
		mReference = new WeakReference<T>(reference);
	}

	@Override
	public void handleMessage(Message msg) {
		if (mReference==null||handler==null)
			return;
        handler.handleMessage(msg);
	}

    public void setHandler(HandlerInterface handler) {
        this.handler = handler;
    }

    public static abstract  interface  HandlerInterface{
        public abstract void handleMessage(Message msg);
    }
}
