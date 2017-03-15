package com.crazy.petter.warehouse.app.main.beans;

/**
 * Created by liuliuchen on 2017/2/28.
 */

public class ConfirmRebinWallWaveBean {

    /**
     * WaveId : sample string 1
     * OutboundId : sample string 2
     * ObnSeq : sample string 3
     * ContainerId : sample string 4
     * SkuId : sample string 5
     * PickQty : 6.0
     */

    private String WaveId;
    private String OutboundId;
    private String ObnSeq;
    private String ContainerId;
    private String SkuId;
    private int PickQty;
    private String WavePickInc;

    public String getWavePickInc() {
        return WavePickInc;
    }

    public void setWavePickInc(String wavePickInc) {
        WavePickInc = wavePickInc;
    }

    public String getWaveId() {
        return WaveId;
    }

    public void setWaveId(String WaveId) {
        this.WaveId = WaveId;
    }

    public String getOutboundId() {
        return OutboundId;
    }

    public void setOutboundId(String OutboundId) {
        this.OutboundId = OutboundId;
    }

    public String getObnSeq() {
        return ObnSeq;
    }

    public void setObnSeq(String ObnSeq) {
        this.ObnSeq = ObnSeq;
    }

    public String getContainerId() {
        return ContainerId;
    }

    public void setContainerId(String ContainerId) {
        this.ContainerId = ContainerId;
    }

    public String getSkuId() {
        return SkuId;
    }

    public void setSkuId(String SkuId) {
        this.SkuId = SkuId;
    }

    public int getPickQty() {
        return PickQty;
    }

    public void setPickQty(int PickQty) {
        this.PickQty = PickQty;
    }
}
