package com.crazy.petter.warehouse.app.main.presenters;

import android.util.Log;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.entity.TitleBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.beans.PropertyBean;
import com.crazy.petter.warehouse.app.main.views.ReceiptView;

import org.json.JSONObject;

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
                SoundUtil.getInstance(context).play(1);
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mReceiptView.showNoGoods();
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
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
                SoundUtil.getInstance(context).play(1);
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
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

    public void getFinish(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.HISTORY, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                TitleBean titleBean = JsonUtil.getTitle(o.toString());
                int all = titleBean.getOrders().size();
                int finish = 0;
                for (JSONObject jsonObject : titleBean.getOrders()) {
                    if (JsonUtil.getDouble(jsonObject, "QTY") <= JsonUtil.getDouble(jsonObject, "RECEIVED_QTY")) {
                        finish++;
                    }
                }
                Log.e("==========", all + "/" + finish);
                mReceiptView.showFinish(finish == finish);
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                mReceiptView.showFinish(false);
            }
        });

    }
}
