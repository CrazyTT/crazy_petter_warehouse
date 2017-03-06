package com.bjdv.lib.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliuchen on 2017/3/6.
 */

public class EditTextAuto extends android.support.v7.widget.AppCompatEditText {
    public EditTextAuto(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    EditTextAuto.this.setSelection(EditTextAuto.this.getText().toString().trim().length());
                }
            }
        });
    }

    public EditTextAuto(Context context) {
        super(context);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    EditTextAuto.this.setSelection(EditTextAuto.this.getText().toString().trim().length());
                }
            }
        });
    }
}
