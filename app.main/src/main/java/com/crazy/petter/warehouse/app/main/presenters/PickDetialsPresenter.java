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
        context.showProgress("查询数据中。。。", false);
        requestData(Constant.SERVER_URL_BASE + Constant.PICKDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                PickDetialsBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickDetialsBean.class);
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
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmObnPick, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                mPickDetialsView.commitOk();
                SoundUtil.getInstance(context).play(1);
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
                mPickDetialsView.commitFailure();
                SoundUtil.getInstance(context).play(0);

            }
        });
    }
}
