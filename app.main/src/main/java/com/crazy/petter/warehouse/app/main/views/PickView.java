package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.PickBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public interface PickView extends IBaseView {

    void setList(String data);

    void getOrderFailure();

}
