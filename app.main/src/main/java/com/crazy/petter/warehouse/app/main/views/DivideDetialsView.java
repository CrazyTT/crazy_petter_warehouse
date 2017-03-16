package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDtBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/16.
 */

public interface DivideDetialsView extends IBaseView {
    void setBottom(int totalQty, int totalPickQty);

    void setOne(ArrayList<PickWaveDtBean.DataEntity> dataEntity);

    void commitOk();

    void getOrderOneFailure();

    void commitfailure();
}
