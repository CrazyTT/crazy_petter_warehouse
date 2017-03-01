package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.ScanSendBean;
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
                ScanSendBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), ScanSendBean.class);
                if (scanStoreageBean.getData() != null && scanStoreageBean.getData().size() > 0) {
                    mTraySendView.setList(scanStoreageBean.getData());
                } else {
                    mTraySendView.getOrderFailure();
                    mTraySendView.showTips(scanStoreageBean.getMessage());
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mTraySendView.getOrderFailure();
                context.stopProgress();
            }
        });

    }
}
