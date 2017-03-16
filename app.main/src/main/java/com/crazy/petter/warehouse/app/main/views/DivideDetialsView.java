package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDtBean;

/**
 * Created by liuliuchen on 2017/2/16.
 */

public interface DivideDetialsView extends IBaseView {
    void setBottom(int totalQty, int totalPickQty);

    void getOrderAllFailure();

    void setOne(PickWaveDtBean.DataEntity dataEntity,int totalQty, int totalPickQty);

    void commitOk();

    void getOrderOneFailure();

}
