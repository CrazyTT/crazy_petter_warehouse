package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
import com.crazy.petter.warehouse.app.main.views.ScanStoreageFragmentView;

/**
 * Created by liuliuchen on 2017/2/11.
 */

public class ScanStoreageFragmentPresenter extends BasePresenter {
    private ScanStoreageFragmentView mScanStoreageFragmentView;

    public ScanStoreageFragmentPresenter(ScanStoreageFragmentView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mScanStoreageFragmentView = iBaseView;
    }

    public void getOrders(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.SACNSTOREAGEDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                ScanStoreageBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), ScanStoreageBean.class);
                mScanStoreageFragmentView.setList(scanStoreageBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mScanStoreageFragmentView.getOrderFailure();
                context.stopProgress();
            }
        });
    }
}
