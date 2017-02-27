package com.crazy.petter.warehouse.app.main.beans;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/27.
 */

public class TraySendCommitBean {

    /**
     * OutboundId : sample string 1
     * LpnNo : sample string 2
     * details : [{"OutboundId":"sample string 1","SeqNo":2,"ObnPickInc":3,"SkuId":"sample string 4","SkuName":"sample string 5","ShipQty":6,"SkuProperty":"sample string 7","ExtLot":"sample string 8"},{"OutboundId":"sample string 1","SeqNo":2,"ObnPickInc":3,"SkuId":"sample string 4","SkuName":"sample string 5","ShipQty":6,"SkuProperty":"sample string 7","ExtLot":"sample string 8"}]
     */

    private String OutboundId;
    private String LpnNo;
    private ArrayList<DetailsEntity> details;

    public String getOutboundId() {
        return OutboundId;
    }

    public void setOutboundId(String OutboundId) {
        this.OutboundId = OutboundId;
    }

    public String getLpnNo() {
        return LpnNo;
    }

    public void setLpnNo(String LpnNo) {
        this.LpnNo = LpnNo;
    }

    public ArrayList<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DetailsEntity> details) {
        this.details = details;
    }

    public static class DetailsEntity {
        /**
         * OutboundId : sample string 1
         * SeqNo : 2
         * ObnPickInc : 3
         * SkuId : sample string 4
         * SkuName : sample string 5
         * ShipQty : 6.0
         * SkuProperty : sample string 7
         * ExtLot : sample string 8
         */

        private String OutboundId;
        private int SeqNo;
        private int ObnPickInc;
        private String SkuId;
        private String SkuName;
        private int ShipQty;
        private String SkuProperty;
        private String ExtLot;

        public String getOutboundId() {
            return OutboundId;
        }

        public void setOutboundId(String OutboundId) {
            this.OutboundId = OutboundId;
        }

        public int getSeqNo() {
            return SeqNo;
        }

        public void setSeqNo(int SeqNo) {
            this.SeqNo = SeqNo;
        }

        public int getObnPickInc() {
            return ObnPickInc;
        }

        public void setObnPickInc(int ObnPickInc) {
            this.ObnPickInc = ObnPickInc;
        }

        public String getSkuId() {
            return SkuId;
        }

        public void setSkuId(String SkuId) {
            this.SkuId = SkuId;
        }

        public String getSkuName() {
            return SkuName;
        }

        public void setSkuName(String SkuName) {
            this.SkuName = SkuName;
        }

        public int getShipQty() {
            return ShipQty;
        }

        public void setShipQty(int ShipQty) {
            this.ShipQty = ShipQty;
        }

        public String getSkuProperty() {
            return SkuProperty;
        }

        public void setSkuProperty(String SkuProperty) {
            this.SkuProperty = SkuProperty;
        }

        public String getExtLot() {
            return ExtLot;
        }

        public void setExtLot(String ExtLot) {
            this.ExtLot = ExtLot;
        }
    }
}
