package com.bjdv.lib.utils.base;

import java.io.Serializable;

/**
 * Created by liuliuchen on 2017/2/11.
 */

public class BaseBean implements Serializable {
    private static final long serialVersionUID = 2046605496826333213L;
    private boolean success;
    private String message;
    private int count;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
