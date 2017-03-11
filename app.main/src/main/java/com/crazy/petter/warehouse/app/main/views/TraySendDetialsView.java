package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.TraySendDetialsBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/14.
 */

public interface TraySendDetialsView extends IBaseView {
    void showGoods(ArrayList<TraySendDetialsBean.DataEntity> data);

    void commitOK();

    void getOrderFailure();
}
