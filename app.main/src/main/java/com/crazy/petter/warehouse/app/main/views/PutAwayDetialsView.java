package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.GoodsPutAwayBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public interface PutAwayDetialsView extends IBaseView {
    void showGoods(ArrayList<GoodsPutAwayBean.DataEntity> data);

    void commitOK();

}
