package com.bjdv.lib.utils.base;

/**
 * Title: <br>
 * Description: <br>
 * Date: 16/6/6 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public interface DataCallBack<T> {
    public void onSuccess(T data);
    public void onFailure(String data);
}
