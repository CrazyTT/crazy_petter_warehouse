package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
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
        requestData(Constant.SERVER_URL_BASE + "WmsRf/QueryInboundDtByLpn", params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                OrderBean orderBean = JsonFormatter.getInstance().json2object(o.toString(), OrderBean.class);
                if (orderBean.getCount() <= 0) {
                    context.stopProgress();
                    mTrayReceiptView.failure();
                    SoundUtil.getInstance(context).play(0);
                }
                context.stopProgress();
                mTrayReceiptView.showGoods(o.toString());
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                mTrayReceiptView.failure();
                SoundUtil.getInstance(context).play(0);
            }
        });

    }

    public void receipt(String params) {
        context.showProgress("确认收货中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.RECEIPT, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mTrayReceiptView.receiptOK();
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
