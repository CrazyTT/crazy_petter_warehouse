package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public interface PickDetialsView extends IBaseView {
    void setList(ArrayList<PickDetialsBean.DataEntity> data);

    void commitOk();

    void getOrderFailure();
}
