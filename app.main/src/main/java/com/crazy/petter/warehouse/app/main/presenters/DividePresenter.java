package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.PickWaveBean;
import com.crazy.petter.warehouse.app.main.views.DivideView;

/**
 * Created by liuliuchen on 2017/2/16.
 */

public class DividePresenter extends BasePresenter {
    DivideView mDivideView;

    public DividePresenter(DivideView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mDivideView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryReBinWallWave, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickWaveBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickWaveBean.class);
                mDivideView.setList(scanStoreageBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mDivideView.getOrderFailure();
                context.stopProgress();
            }
        });

    }
}
