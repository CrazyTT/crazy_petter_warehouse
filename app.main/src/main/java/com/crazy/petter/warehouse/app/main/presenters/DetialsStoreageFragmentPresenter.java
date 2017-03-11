package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.crazy.petter.warehouse.app.main.views.DetialsStoreageFragmentView;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public class DetialsStoreageFragmentPresenter extends BasePresenter {
    DetialsStoreageFragmentView mDetialsStoreageFragmentView;

    public DetialsStoreageFragmentPresenter(DetialsStoreageFragmentView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mDetialsStoreageFragmentView = iBaseView;
    }

    public void getOrder(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.HISTORY, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mDetialsStoreageFragmentView.showGoods(o.toString());
            }

            @Override
            public void onFailure(String s) {
                mDetialsStoreageFragmentView.showFailure();
            }
        });

    }
}
