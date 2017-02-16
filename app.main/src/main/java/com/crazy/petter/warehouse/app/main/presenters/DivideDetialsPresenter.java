package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.crazy.petter.warehouse.app.main.views.DivideDetialsView;

/**
 * Created by liuliuchen on 2017/2/16.
 */

public class DivideDetialsPresenter extends BasePresenter {
    DivideDetialsView mDivideDetialsView;

    public DivideDetialsPresenter(DivideDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mDivideDetialsView = iBaseView;
    }
}
