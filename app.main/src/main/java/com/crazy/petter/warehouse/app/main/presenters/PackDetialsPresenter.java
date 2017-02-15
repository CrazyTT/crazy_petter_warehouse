package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.crazy.petter.warehouse.app.main.views.PackDetialsView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PackDetialsPresenter extends BasePresenter {
    PackDetialsView mPackDetialsView;

    public PackDetialsPresenter(PackDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPackDetialsView = iBaseView;
    }
}
