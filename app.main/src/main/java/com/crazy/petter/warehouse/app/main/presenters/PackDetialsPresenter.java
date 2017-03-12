package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.beans.PackBean;
import com.crazy.petter.warehouse.app.main.beans.SkuBean;
import com.crazy.petter.warehouse.app.main.views.PackDetialsView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PackDetialsPresenter extends BasePresenter {
    PackDetialsView mPackDetialsView;

    public PackDetialsPresenter(PackDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPackDetialsView = iBaseView;
    }

    public void getPackType(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryCartonType, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PackBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PackBean.class);
                mPackDetialsView.setPackInfo(scanStoreageBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                SoundUtil.getInstance(context).play(0);
                context.stopProgress();
                mPackDetialsView.getPackFailure();
            }
        });
    }

    public void addList(String params) {
        context.showProgress("加入明细中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmObnCarton, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                OrderBean orderBean = JsonFormatter.getInstance().json2object(o.toString(), OrderBean.class);
                if (orderBean.getCount() <= 0) {
                    mPackDetialsView.addListFailure();
                    mPackDetialsView.showTips(orderBean.getMessage());
                    SoundUtil.getInstance(context).play(0);
                } else {
                    SoundUtil.getInstance(context).play(1);
                    mPackDetialsView.setConfirmResult(o.toString());
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                SoundUtil.getInstance(context).play(0);
                mPackDetialsView.addListFailure();
                context.stopProgress();
            }
        });

    }

    public void getSkuInfo(String params) {
        context.showProgress("查询商品中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QuerySkuData, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                SkuBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), SkuBean.class);
                if (scanStoreageBean.getData() != null && scanStoreageBean.getData().size() > 0) {
                    mPackDetialsView.showSkuInfo(scanStoreageBean.getData());
                } else {
                    SoundUtil.getInstance(context).play(0);
                    mPackDetialsView.getSkuFailure();
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                SoundUtil.getInstance(context).play(0);
                mPackDetialsView.getSkuFailure();
                context.stopProgress();
            }
        });

    }
}
