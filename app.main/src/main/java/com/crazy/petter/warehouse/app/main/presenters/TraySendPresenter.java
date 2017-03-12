package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.views.TraySendView;

/**
 * Created by liuliuchen on 2017/2/14.
 */

public class TraySendPresenter extends BasePresenter {
    TraySendView mTraySendView;

    public TraySendPresenter(TraySendView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mTraySendView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.TRAYSEND, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                OrderBean orderBean = JsonFormatter.getInstance().json2object(o.toString(), OrderBean.class);
                if (orderBean.getCount() <= 0) {
                    mTraySendView.getOrderFailure();
                    mTraySendView.showTips(orderBean.getMessage());
                    SoundUtil.getInstance(context).play(0);
                } else {
                    mTraySendView.setList(o.toString());
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mTraySendView.getOrderFailure();
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
            }
        });

    }
}
