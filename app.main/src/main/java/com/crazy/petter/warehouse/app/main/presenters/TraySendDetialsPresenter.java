package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.beans.TraySendDetialsBean;
import com.crazy.petter.warehouse.app.main.views.TraySendDetialsView;

/**
 * Created by liuliuchen on 2017/2/14.
 */

public class TraySendDetialsPresenter extends BasePresenter {
    TraySendDetialsView mTraySendDetialsView;

    public TraySendDetialsPresenter(TraySendDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mTraySendDetialsView = iBaseView;
    }

    public void getOrder(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.TRAYSENDDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                TraySendDetialsBean traySendDetialsBean = JsonFormatter.getInstance().json2object(o.toString(), TraySendDetialsBean.class);
                mTraySendDetialsView.showGoods(traySendDetialsBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mTraySendDetialsView.getOrderFailure();
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
            }
        });
    }

    public void commit(String params) {
        context.showProgress("确认发货中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmObnShip, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mTraySendDetialsView.commitOK();
                SoundUtil.getInstance(context).play(1);
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
            }
        });
    }
}
