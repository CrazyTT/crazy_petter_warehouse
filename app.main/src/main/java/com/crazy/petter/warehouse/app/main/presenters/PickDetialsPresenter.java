package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;
import com.crazy.petter.warehouse.app.main.views.PickDetialsView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickDetialsPresenter extends BasePresenter {
    PickDetialsView mPickDetialsView;

    public PickDetialsPresenter(PickDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPickDetialsView = iBaseView;
    }

    public void getOrders(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.PICKDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickDetialsBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickDetialsBean.class);
                mPickDetialsView.setList(scanStoreageBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPickDetialsView.getOrderFailure();
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
            }
        });
    }

    public void commit(String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmObnPick, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                mPickDetialsView.commitOk();
                SoundUtil.getInstance(context).play(1);
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                SoundUtil.getInstance(context).play(0);
                context.stopProgress();
            }
        });
    }
}
