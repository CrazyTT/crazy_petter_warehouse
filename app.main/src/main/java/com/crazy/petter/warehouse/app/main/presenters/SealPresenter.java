package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.crazy.petter.warehouse.app.main.views.SealView;

/**
 * Created by liuliuchen on 2017/2/21.
 */

public class SealPresenter extends BasePresenter {
    SealView mSealView;

    public SealPresenter(SealView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mSealView = iBaseView;
    }

    public void seal(String params) {
        context.showProgress("装箱中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.GOODDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mSealView.sealOK();
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });
    }
}
