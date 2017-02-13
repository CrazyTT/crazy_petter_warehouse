package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.views.DetialsStoreageFragmentView;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public class DetialsStoreageFragmentPresenter extends BasePresenter {
    DetialsStoreageFragmentView mDetialsStoreageFragmentView;

    public DetialsStoreageFragmentPresenter(DetialsStoreageFragmentView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mDetialsStoreageFragmentView = iBaseView;
    }

    public void getOrder(String params) {
        context.showProgress("查询中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.GOODDETIALS, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                GoodsBean goodsBean = JsonFormatter.getInstance().json2object(o.toString(), GoodsBean.class);
                mDetialsStoreageFragmentView.showGoods(goodsBean.getData());
                context.stopProgress();
            }

            @Override
            public void onFailure(String s) {
                context.stopProgress();
            }
        });

    }
}
