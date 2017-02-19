package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.beans.PropertyBean;
import com.crazy.petter.warehouse.app.main.views.ReceiptView;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public class ReceiptPresenter extends BasePresenter {
    ReceiptView mReceiptView;

    public ReceiptPresenter(ReceiptView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        this.mReceiptView = iBaseView;
    }

    public void getDetials(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.GOODDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                GoodsBean goodsBean = JsonFormatter.getInstance().json2object(o.toString(), GoodsBean.class);
                mReceiptView.showGoods(goodsBean);
                context.stopProgress();
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
                mReceiptView.receiptOK();
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });
    }

    public void getProperty(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.SkuProperty, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PropertyBean goodsBean = JsonFormatter.getInstance().json2object(o.toString(), PropertyBean.class);
                mReceiptView.showProperty(goodsBean.getData());
            }

            @Override
            public void onFailure(String s) {
            }
        });
    }
}
