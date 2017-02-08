package com.bjdv.lib.utils.base;

/**
 * Title: <br>
 * Description: <br>
 * Date: 16/5/31 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public interface IBaseView {
    /**
     * 显示进度条
     */
    public void showLoading();

    /**
     * 关闭进度条
     */
    public void hideLoading();

    /**
     * 提示信息
     * @param tips
     */
    public void showTips(String tips);

}
