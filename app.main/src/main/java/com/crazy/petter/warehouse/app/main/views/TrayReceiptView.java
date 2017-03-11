package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public interface TrayReceiptView extends IBaseView {
    void showGoods(String data);

    void receiptOK();

    void failure();

}
