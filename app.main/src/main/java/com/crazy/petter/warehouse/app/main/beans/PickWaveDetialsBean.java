package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickWaveDetialsBean extends BaseBean {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * WaveId : sample string 1
         * LocSeq : 2
         * PickLoc : sample string 3
         * SkuId : sample string 4
         * SkuName : sample string 5
         * Qty : 6.0
         * PickedQty : 7.0
         * WaitPickQty : 8.0
         * LotProperty : [{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"},{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"}]
         */

        public String WaveId;
        public int LocSeq;
        public String PickLoc;
        public String SkuId;
        public String SkuName;
        public int Qty;
        public int PickedQty;
        public int WaitPickQty;
        public ArrayList<LotPropertyEntity> LotProperty;

        public String getWaveId() {
            return WaveId;
        }

        public void setWaveId(String WaveId) {
            this.WaveId = WaveId;
        }

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

        public int getPickedQty() {
            return PickedQty;
        }

        public void setPickedQty(int PickedQty) {
            this.PickedQty = PickedQty;
        }

        public int getWaitPickQty() {
            return WaitPickQty;
        }

        public void setWaitPickQty(int WaitPickQty) {
            this.WaitPickQty = WaitPickQty;
        }

        public ArrayList<LotPropertyEntity> getLotProperty() {
            return LotProperty;
        }

        public void setLotProperty(ArrayList<LotPropertyEntity> LotProperty) {
            this.LotProperty = LotProperty;
        }

        public static class LotPropertyEntity {
            /**
             * SeqNo : sample string 1
             * LotCode : sample string 2
             * LotValue : sample string 3
             * RfVisible : sample string 4
             */

            public String SeqNo;
            public String LotCode;
            public String LotValue;
            public String LotLabel;
            public String RfVisible;

            public String getLotLabel() {
                return LotLabel;
            }

            public void setLotLabel(String lotLabel) {
                LotLabel = lotLabel;
            }

            public String getSeqNo() {
                return SeqNo;
            }

            public void setSeqNo(String SeqNo) {
                this.SeqNo = SeqNo;
            }

            public String getLotCode() {
                return LotCode;
            }

            public void setLotCode(String LotCode) {
                this.LotCode = LotCode;
            }

            public String getLotValue() {
                return LotValue;
            }

            public void setLotValue(String LotValue) {
                this.LotValue = LotValue;
            }

            public String getRfVisible() {
                return RfVisible;
            }

            public void setRfVisible(String RfVisible) {
                this.RfVisible = RfVisible;
            }
        }
    }
}
