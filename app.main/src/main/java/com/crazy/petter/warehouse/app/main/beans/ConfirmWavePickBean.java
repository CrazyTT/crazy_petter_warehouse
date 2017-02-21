package com.crazy.petter.warehouse.app.main.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/21.
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ConfirmWavePickBean {
    /**
     * WaveId : sample string 1
     * details : [{"LocSeq":1,"PickLoc":"sample string 2","SkuId":"sample string 3","SkuName":"sample string 4","Qty":5,"LotProperty":[{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"},{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"}]},{"LocSeq":1,"PickLoc":"sample string 2","SkuId":"sample string 3","SkuName":"sample string 4","Qty":5,"LotProperty":[{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"},{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"}]}]
     */

    private String WaveId;
    private ArrayList<DetailsEntity> details;

    public String getWaveId() {
        return WaveId;
    }

    public void setWaveId(String WaveId) {
        this.WaveId = WaveId;
    }

    public ArrayList<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DetailsEntity> details) {
        this.details = details;
    }

    public static class DetailsEntity {
        /**
         * LocSeq : 1
         * PickLoc : sample string 2
         * SkuId : sample string 3
         * SkuName : sample string 4
         * Qty : 5.0
         * LotProperty : [{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"},{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"}]
         */

        private int LocSeq;
        private String PickLoc;
        private String SkuId;
        private String SkuName;
        private int Qty;
        private ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> LotProperty;

        public int getLocSeq() {
            return LocSeq;
        }

        public void setLocSeq(int LocSeq) {
            this.LocSeq = LocSeq;
        }

        public String getPickLoc() {
            return PickLoc;
        }

        public void setPickLoc(String PickLoc) {
            this.PickLoc = PickLoc;
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

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> getLotProperty() {
            return LotProperty;
        }

        public void setLotProperty(ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> lotProperty) {
            LotProperty = lotProperty;
        }
    }
}
