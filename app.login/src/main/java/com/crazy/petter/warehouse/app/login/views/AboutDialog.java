package com.crazy.petter.warehouse.app.login.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.TextView;

import com.bjdv.lib.utils.util.AppUtils;
import com.crazy.petter.warehouse.app.login.R;

/**
 * Created by liuliuchen on 2017/3/6.
 */

public class AboutDialog extends Dialog {
    private TextView txtVerson;
    private Context mContext;

    public AboutDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        txtVerson = (TextView) findViewById(R.id.txt_version);
        txtVerson.setText(AppUtils.getVersionName(mContext) + " ( " + AppUtils.getVersionCode(mContext) + " )");
    }
}
