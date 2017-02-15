package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.ScanSendBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public interface PackView extends IBaseView {
    void setList(ArrayList<ScanSendBean.DataEntity> data);

    void getOrderFailure();
}
