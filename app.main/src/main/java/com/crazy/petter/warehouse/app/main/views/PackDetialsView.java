package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.PackBean;
import com.crazy.petter.warehouse.app.main.beans.SkuBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public interface PackDetialsView extends IBaseView {
    void setPackInfo(ArrayList<PackBean.DataEntity> data);

    void setConfirmResult(String scanStoreageBean);

    void getPackFailure();

    void addListFailure();

    void getSkuFailure();

    void showSkuInfo(ArrayList<SkuBean.DataEntity> data);

    void getListFailure();
}
