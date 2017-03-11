package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.SoundUtil;
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
                mTrayStorageView.setList(o.toString());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                mTrayStorageView.getOrderFailure();
                context.stopProgress();
                SoundUtil.getInstance(context).play(0);
            }
        });
    }
}
