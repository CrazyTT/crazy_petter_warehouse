package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDetialsBean;
import com.crazy.petter.warehouse.app.main.views.PickWaveDetialsView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickWaveDetialsPresenter extends BasePresenter {
    PickWaveDetialsView mPickDetialsView;

    public PickWaveDetialsPresenter(PickWaveDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPickDetialsView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询数据中。。。", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryWavePickDt, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                PickWaveDetialsBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickWaveDetialsBean.class);
                mPickDetialsView.setList(scanStoreageBean.getData());
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                mPickDetialsView.getOrderFailure();
                SoundUtil.getInstance(context).play(0);
            }
        });
    }

    public void commit(String params) {
        context.showProgress("确认拣货中。。。", false);
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmWavePic, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                mPickDetialsView.commitOk();
                SoundUtil.getInstance(context).play(1);
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                mPickDetialsView.commitFauilure();
                SoundUtil.getInstance(context).play(0);
            }
        });

    }
}
