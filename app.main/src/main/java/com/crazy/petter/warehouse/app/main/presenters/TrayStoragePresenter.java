package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
import com.crazy.petter.warehouse.app.main.views.TrayStorageView;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public class TrayStoragePresenter extends BasePresenter {
    TrayStorageView mTrayStorageView;

    public TrayStoragePresenter(TrayStorageView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        this.mTrayStorageView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.SACNSTOREAGEDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                ScanStoreageBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), ScanStoreageBean.class);
                mTrayStorageView.setList(scanStoreageBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mTrayStorageView.getOrderFailure();
                context.stopProgress();
            }
        });
    }
}
