package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.LocBean;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public interface PutAwayDetialsView extends IBaseView {
    void showGoods(String data);

    void commitOK();

    void showLoc(LocBean.DataEntity dataEntity);

    void checkLocFailure();

    void getorderFailure();

}
