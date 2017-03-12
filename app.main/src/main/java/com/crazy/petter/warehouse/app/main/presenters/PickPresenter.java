package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.views.PickView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickPresenter extends BasePresenter {
    PickView mPickView;

    public PickPresenter(PickView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPickView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.PICK, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                OrderBean orderBean = JsonFormatter.getInstance().json2object(o.toString(), OrderBean.class);
                if (orderBean.getCount() <= 0) {
                    mPickView.getOrderFailure();
                    mPickView.showTips(orderBean.getMessage());
                    SoundUtil.getInstance(context).play(0);
                } else {
                    mPickView.setList(o.toString());
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPickView.getOrderFailure();
                SoundUtil.getInstance(context).play(0);
                context.stopProgress();
            }
        });

    }
}
