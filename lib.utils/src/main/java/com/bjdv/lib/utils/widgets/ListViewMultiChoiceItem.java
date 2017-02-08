package com.bjdv.lib.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import com.bjdv.lib.utils.R;


/**
 * Created by zhangxiansheng on 15/11/12.
 */
public class ListViewMultiChoiceItem extends RelativeLayout implements Checkable {
    private boolean mChecked;
    private CheckBox checkBox;

    public ListViewMultiChoiceItem(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkBox = (CheckBox) findViewById(R.id.checkbox);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        checkBox.setChecked(mChecked);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
