package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.views.TrayReceiptView;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public class TrayReceiptPresenter extends BasePresenter {
    TrayReceiptView mTrayReceiptView;

    public TrayReceiptPresenter(TrayReceiptView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        this.mTrayReceiptView = iBaseView;
    }

    public void getOrder(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.GOODDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                GoodsBean goodsBean = JsonFormatter.getInstance().json2object(o.toString(), GoodsBean.class);
                context.stopProgress();
                mTrayReceiptView.showGoods(goodsBean.getData());
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });

    }

    public void receipt(String params) {
        context.showProgress("确认收货中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.RECEIPT, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mTrayReceiptView.receiptOK();
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });

    }
}
