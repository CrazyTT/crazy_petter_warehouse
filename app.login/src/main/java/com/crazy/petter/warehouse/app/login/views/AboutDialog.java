package com.crazy.petter.warehouse.app.login.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.crazy.petter.warehouse.app.login.R;

/**
 * Created by liuliuchen on 2017/3/6.
 */

public class AboutDialog extends Dialog {
    public AboutDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
    }
}
