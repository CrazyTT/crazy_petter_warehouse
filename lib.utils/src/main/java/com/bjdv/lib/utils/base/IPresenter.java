package com.bjdv.lib.utils.base;

/**
 * Title: <br>
 * Description: <br>
 * Date: 16/5/31 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public interface IPresenter<T> {

    /**
     * 请求数据
     *
     * @param url
     * @param params
     */
    public void requestData(String url, String params, DataCallBack<T> dataCallBack);

    /**
     * 中断数据请求
     */
    public void discardData();

}
