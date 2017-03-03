package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDetialsBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public interface PickWaveDetialsView extends IBaseView {
    void setList(ArrayList<PickWaveDetialsBean.DataEntity> data);

    void commitOk();

    void getOrderFailure();

}
