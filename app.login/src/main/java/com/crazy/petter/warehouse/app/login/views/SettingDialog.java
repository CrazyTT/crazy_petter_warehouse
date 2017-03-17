package com.crazy.petter.warehouse.app.login.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.CustomHorizontalScrollView;
import com.crazy.petter.warehouse.app.login.R;

/**
 * Created by liuliuchen on 2017/3/6.
 */

public class SettingDialog extends Dialog {

    private SharedPreferencesUtil sp;
    private EditText mEditText;
    private Button btnCommit, btnCancle;
    CustomHorizontalScrollView mCustomHorizontalScrollView;

    public SettingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        sp = new SharedPreferencesUtil(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        mEditText = (EditText) findViewById(R.id.edt_url);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnCancle = (Button) findViewById(R.id.btn_cancle);
        mCustomHorizontalScrollView = (CustomHorizontalScrollView) findViewById(R.id.hsv);
        if (TextUtils.isEmpty(sp.getString("baseUrl"))) {
            mEditText.setText(Constant.SERVER_URL_BASE);
        } else {
            mEditText.setText(sp.getString("baseUrl"));
        }
        mEditText.setSelection(mEditText.getText().toString().trim().length());
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog.this.dismiss();
            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditText.getText().toString().trim())) {
                    ToastUtils.showLong(getContext(), "不能为空");
                    return;
                }
                Constant.SERVER_URL_BASE = mEditText.getText().toString().trim();
                sp.setString("baseUrl", mEditText.getText().toString().trim());
                ToastUtils.showLong(getContext(), "设定成功");
                SettingDialog.this.dismiss();
            }
        });
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mCustomHorizontalScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }), 150);
    }
}
