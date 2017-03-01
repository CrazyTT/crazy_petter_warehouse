package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.PackBean;
import com.crazy.petter.warehouse.app.main.beans.PackDetialsBean;
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
                PackDetialsBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PackDetialsBean.class);
                mPackDetialsView.setConfirmResult(scanStoreageBean);
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPackDetialsView.addListFailure();
                context.stopProgress();
            }
        });

    }
}
