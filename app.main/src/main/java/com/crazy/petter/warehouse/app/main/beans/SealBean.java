package com.crazy.petter.warehouse.app.main.beans;

/**
 * Created by liuliuchen on 2017/2/21.
 */

public class SealBean {

    /**
     * OutboundId : sample string 1
     * CartonId : sample string 2
     * CartonTypeId : sample string 3
     * IsSeal : true
     * ActualWeight : 5.0
     * ActualVolume : 6.0
     */

    private String OutboundId;
    private String CartonId;
    private String CartonTypeId;
    private boolean IsSeal;
    private String ActualWeight;
    private String ActualVolume;

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

    public boolean isIsSeal() {
        return IsSeal;
    }

    public void setIsSeal(boolean IsSeal) {
        this.IsSeal = IsSeal;
    }

    public String getActualWeight() {
        return ActualWeight;
    }

    public void setActualWeight(String ActualWeight) {
        this.ActualWeight = ActualWeight;
    }

    public String getActualVolume() {
        return ActualVolume;
    }

    public void setActualVolume(String ActualVolume) {
        this.ActualVolume = ActualVolume;
    }
}
