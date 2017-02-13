package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public interface ReceiptView extends IBaseView {
    void showGoods(GoodsBean goodsBean);

    void receiptOK();
}
