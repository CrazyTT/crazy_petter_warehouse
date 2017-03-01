package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.QueryObnCartonBean;
import com.crazy.petter.warehouse.app.main.views.PackView;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PackPresenter extends BasePresenter {
    PackView mPackView;

    public PackPresenter(PackView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mPackView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.QueryObnCarton, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                QueryObnCartonBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), QueryObnCartonBean.class);
                if (scanStoreageBean.getData() != null && scanStoreageBean.getData().size() > 0) {
                    mPackView.setList(scanStoreageBean.getData());
                } else {
                    mPackView.getOrderFailure();
                    mPackView.showTips(scanStoreageBean.getMessage());
                }
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mPackView.getOrderFailure();
                context.stopProgress();
            }
        });
    }
}
