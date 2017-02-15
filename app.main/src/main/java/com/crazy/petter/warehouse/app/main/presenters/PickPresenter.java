package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.PickBean;
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
                PickBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickBean.class);
                mPickView.setList(scanStoreageBean.getData());
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
