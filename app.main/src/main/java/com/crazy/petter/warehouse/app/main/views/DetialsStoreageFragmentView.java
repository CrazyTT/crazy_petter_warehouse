package com.crazy.petter.warehouse.app.main.views;

import com.bjdv.lib.utils.base.IBaseView;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public interface DetialsStoreageFragmentView extends IBaseView {
    void showGoods(ArrayList<GoodsBean.DataEntity> data);
}
