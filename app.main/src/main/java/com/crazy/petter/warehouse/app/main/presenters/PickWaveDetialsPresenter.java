package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;
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
        requestData(Constant.SERVER_URL_BASE + Constant.PICKDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickDetialsBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickDetialsBean.class);
                mPickDetialsView.setList(scanStoreageBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });
    }
}
