package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.views.PutAwayView;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public class PutAwayPresenter extends BasePresenter {
    PutAwayView mPutAwayView;

    public PutAwayPresenter(PutAwayView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPutAwayView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.PUTAWAY, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                OrderBean orderBean = JsonFormatter.getInstance().json2object(o.toString(), OrderBean.class);
                if (orderBean.getCount() <= 0) {
                    mPutAwayView.getOrderFailure();
                    mPutAwayView.showTips(orderBean.getMessage());
                    SoundUtil.getInstance(context).play(0);
                } else {
                    mPutAwayView.setList(o.toString());
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPutAwayView.getOrderFailure();
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
            }
        });


    }
}
