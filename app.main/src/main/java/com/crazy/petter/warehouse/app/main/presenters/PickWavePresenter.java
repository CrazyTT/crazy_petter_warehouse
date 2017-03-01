package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.PickWaveBean;
import com.crazy.petter.warehouse.app.main.views.PickWaveView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickWavePresenter extends BasePresenter {
    PickWaveView mPickView;

    public PickWavePresenter(PickWaveView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPickView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryWavePick, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickWaveBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickWaveBean.class);
                if (scanStoreageBean.getData() != null && scanStoreageBean.getData().size() > 0) {
                    mPickView.setList(scanStoreageBean.getData());
                } else {
                    mPickView.getOrderFailure();
                    mPickView.showTips(scanStoreageBean.getMessage());
                }
                context.stopProgress();
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPickView.getOrderFailure();
                context.stopProgress();
            }
        });
    }
}
