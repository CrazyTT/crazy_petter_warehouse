package com.bjdv.lib.utils.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bjdv.lib.utils.R;

/**
 * Created by liuliuchen on 2017/3/6.
 */

public class ToastDialog extends Dialog {
    private TextView txtMsg;
    private Context mContext;
    private CharSequence msg;
    private Button mButton;

    public ToastDialog(@NonNull Context context, @StyleRes int themeResId, CharSequence str) {
        super(context, themeResId);
        this.mContext = context;
        msg = str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_toast);
        txtMsg = (TextView) findViewById(R.id.txt_msg);
        txtMsg.setText(this.msg);
        mButton = (Button) findViewById(R.id.btn_commit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDialog.this.dismiss();
            }
        });
        setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ToastDialog.this.dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}
