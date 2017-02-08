package com.bjdv.lib.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.bjdv.lib.utils.base.IApplicationiml;
import com.bjdv.lib.utils.util.DensityUtils;
import com.bjdv.lib.utils.util.ScreenUtils;


/**
 * Title:
 * Desc:
 * Created by PHOON-THINK on 2015/4/22.
 * Copyright (c) 2015 DATANG BJDV
 */
public class IndicatorHorizontalScrollView extends HorizontalScrollView {
    private static final String TAG = "IndicatorHorizontalScrollView";

    public IndicatorHorizontalScrollView(Context context) {
        super(context);
    }

    public IndicatorHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void fling(int velocityX) {
        super.fling(velocityX / 4);
    }

    public void scrollTitle(int x) {
        smoothScrollTo(x / 4 * (ScreenUtils.getScreenWidth(getContext()) - DensityUtils.dip2px(IApplicationiml.getInstance(), 50)), 0);
    }
}
