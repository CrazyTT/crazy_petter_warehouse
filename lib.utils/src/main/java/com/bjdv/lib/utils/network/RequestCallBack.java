package com.bjdv.lib.utils.network;

/**
 * Title: <br>
 * Description: <br>
 * Date: 16/5/16 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public interface RequestCallBack {

    public abstract void onResponse(String response);

    public void onErrorResponse(String errorInfo);

}
