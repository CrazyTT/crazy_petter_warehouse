package com.crazy.petter.warehouse.app.main.beans;

/**
 * Created by liuliuchen on 2017/2/21.
 */

public class ConfirmObnCartonBean  {

    /**
     * OutboundId : sample string 1
     * CartonId : sample string 2
     * CartonTypeId : sample string 3
     * AutoCartonId : true
     * BarCode : sample string 5
     * SkuId : sample string 6
     * Qty : 7.0
     */

    private String OutboundId;
    private String CartonId;
    private String CartonTypeId;
    private boolean AutoCartonId;
    private String BarCode;
    private String SkuId;
    private double Qty;

    public String getOutboundId() {
        return OutboundId;
    }

    public void setOutboundId(String OutboundId) {
        this.OutboundId = OutboundId;
    }

    public String getCartonId() {
        return CartonId;
    }

    public void setCartonId(String CartonId) {
        this.CartonId = CartonId;
    }

    public String getCartonTypeId() {
        return CartonTypeId;
    }

    public void setCartonTypeId(String CartonTypeId) {
        this.CartonTypeId = CartonTypeId;
    }

    public boolean isAutoCartonId() {
        return AutoCartonId;
    }

    public void setAutoCartonId(boolean AutoCartonId) {
        this.AutoCartonId = AutoCartonId;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public String getSkuId() {
        return SkuId;
    }

    public void setSkuId(String SkuId) {
        this.SkuId = SkuId;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double Qty) {
        this.Qty = Qty;
    }
}
