package com.bjdv.lib.utils.entity;

/**
 * Created by liuliuchen on 2017/3/12.
 */

public class OrderBeanPack extends OrderBean {

    /**
     * TotalPackageQty : 3.0
     * TotalPickedQty : 4.0
     * caption : null
     * value : []
     */

    public double TotalPackageQty;
    public double TotalPickedQty;

    public double getTotalPackageQty() {
        return TotalPackageQty;
    }

    public void setTotalPackageQty(double TotalPackageQty) {
        this.TotalPackageQty = TotalPackageQty;
    }

    public double getTotalPickedQty() {
        return TotalPickedQty;
    }

    public void setTotalPickedQty(double TotalPickedQty) {
        this.TotalPickedQty = TotalPickedQty;
    }
}
