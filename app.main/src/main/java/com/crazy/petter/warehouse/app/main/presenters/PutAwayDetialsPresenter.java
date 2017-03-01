package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.GoodsPutAwayBean;
import com.crazy.petter.warehouse.app.main.beans.LocBean;
import com.crazy.petter.warehouse.app.main.views.PutAwayDetialsView;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public class PutAwayDetialsPresenter extends BasePresenter {
    PutAwayDetialsView mPutAwayDetialsView;

    public PutAwayDetialsPresenter(PutAwayDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPutAwayDetialsView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.PUTAWAYDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                GoodsPutAwayBean goodsBean = JsonFormatter.getInstance().json2object(o.toString(), GoodsPutAwayBean.class);
                mPutAwayDetialsView.showGoods(goodsBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPutAwayDetialsView.getorderFailure();
                context.stopProgress();
            }
        });
    }

    public void commit(String params) {
        context.showProgress("确认收货中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmPutAway, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                mPutAwayDetialsView.commitOK();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });

    }

    public void checkLoc(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryPutAwayLocs, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                LocBean locBean = JsonFormatter.getInstance().json2object(o.toString(), LocBean.class);
                if (locBean.getData() != null && locBean.getData().size() > 0) {
                    mPutAwayDetialsView.showLoc(locBean.getData().get(0));
                } else {
                    mPutAwayDetialsView.checkLocFailure();
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPutAwayDetialsView.checkLocFailure();
                context.stopProgress();
            }
        });
    }
}
