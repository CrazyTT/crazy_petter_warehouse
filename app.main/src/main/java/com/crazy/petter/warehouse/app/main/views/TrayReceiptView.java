package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public interface TrayReceiptView extends IBaseView {
    void showGoods(ArrayList<GoodsBean.DataEntity> data);

    void receiptOK();

}
