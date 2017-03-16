package com.crazy.petter.warehouse.app.main.presenters;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.base.BasePresenter;
import com.bjdv.lib.utils.base.DataCallBack;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.SoundUtil;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDtBean;
import com.crazy.petter.warehouse.app.main.views.DivideDetialsView;

import org.json.JSONObject;

/**
 * Created by liuliuchen on 2017/2/16.
 */

public class DivideDetialsPresenter extends BasePresenter {
    DivideDetialsView mDivideDetialsView;

    public DivideDetialsPresenter(DivideDetialsView iBaseView, BaseActivity context, String tag) {
        super(iBaseView, context, tag);
        mDivideDetialsView = iBaseView;
    }

    public void getCount(String params) {
        requestData2(Constant.SERVER_URL_BASE + Constant.QueryReBinWallWaveDt, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(String s) {
                JSONObject jsonObject = JsonUtil.from(s);
                mDivideDetialsView.setBottom(JsonUtil.getInt(jsonObject, "TotalQty"), JsonUtil.getInt(jsonObject, "TotalPickQty"));
            }
        });

    }

    public void getOrderOne(final String params) {
        requestData(Constant.SERVER_URL_BASE + Constant.QueryReBinWallWaveDt, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                PickWaveDtBean scanStoreageBean = JsonFormatter.getInstance().json2object(o.toString(), PickWaveDtBean.class);
                if (scanStoreageBean.getData() != null && scanStoreageBean.getData().size() > 0) {
                    mDivideDetialsView.setOne(scanStoreageBean.getData());
                    getCount(params);
                } else {
                    mDivideDetialsView.getOrderOneFailure();
                    mDivideDetialsView.showTips("没有查到明细");
                    SoundUtil.getInstance(context).play(0);
                }

            }

            @Override
            public void onFailure(String s) {
                mDivideDetialsView.getOrderOneFailure();
                SoundUtil.getInstance(context).play(0);
            }
        });
    }

    public void commit(String params) {
        context.showProgress("确认分货中...", false);
        requestData(Constant.SERVER_URL_BASE + Constant.ConfirmRebinWallWave, params, new DataCallBack() {
            @Override
            public void onSuccess(Object o) {
                context.stopProgress();
                mDivideDetialsView.commitOk();
                SoundUtil.getInstance(context).play(1);
            }

            @Override
            public void onFailure(String s) {
                mDivideDetialsView.commitfailure();
                SoundUtil.getInstance(context).play(0);
                context.stopProgress();
            }
        });
    }
}
