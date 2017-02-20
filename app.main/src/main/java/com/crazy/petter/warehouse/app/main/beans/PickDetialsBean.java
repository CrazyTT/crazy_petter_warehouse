package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickDetialsBean extends BaseBean {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public class DataEntity {
        /**
         * OutboundId : sample string 1
         * SeqNo : 2
         * ObnPickInc : 3
         * SkuId : sample string 4
         * SkuName : sample string 5
         * ExtLot : sample string 6
         * PickLoc : sample string 7
         * Qty : 8.0
         * PickedQty : 9.0
         * WaitPickQty : 10.0
         * SkuProperty : sample string 11
         * ShelfLifeCtrl : true
         * ShelfLifeCtrlType : sample string 13
         * ShelfLifeDays : 14
         * ShelfLifeDaysIn : 15
         * ShelfLifeDaysOt : 16
         * SingleScan : true
         * LotProperty : [{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"},{"SeqNo":"sample string 1","LotCode":"sample string 2","LotValue":"sample string 3","RfVisible":"sample string 4"}]
         */

        public String OutboundId;
        public int SeqNo;
        public int ObnPickInc;
        public String SkuId;
        public String SkuName;
        public String ExtLot;
        public String PickLoc;
        public int Qty;
        public int PickedQty;
        public int WaitPickQty;
        public String SkuProperty;
        public boolean ShelfLifeCtrl;
        public String ShelfLifeCtrlType;
        public int ShelfLifeDays;
        public int ShelfLifeDaysIn;
        public int ShelfLifeDaysOt;
        public boolean SingleScan;
        public ArrayList<LotPropertyEntity> LotProperty;

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

        public String getExtLot() {
            return ExtLot;
        }

        public void setExtLot(String ExtLot) {
            this.ExtLot = ExtLot;
        }

        public String getPickLoc() {
            return PickLoc;
        }

        public void setPickLoc(String PickLoc) {
            this.PickLoc = PickLoc;
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

        public String getSkuProperty() {
            return SkuProperty;
        }

        public void setSkuProperty(String SkuProperty) {
            this.SkuProperty = SkuProperty;
        }

        public boolean isShelfLifeCtrl() {
            return ShelfLifeCtrl;
        }

        public void setShelfLifeCtrl(boolean ShelfLifeCtrl) {
            this.ShelfLifeCtrl = ShelfLifeCtrl;
        }

        public String getShelfLifeCtrlType() {
            return ShelfLifeCtrlType;
        }

        public void setShelfLifeCtrlType(String ShelfLifeCtrlType) {
            this.ShelfLifeCtrlType = ShelfLifeCtrlType;
        }

        public int getShelfLifeDays() {
            return ShelfLifeDays;
        }

        public void setShelfLifeDays(int ShelfLifeDays) {
            this.ShelfLifeDays = ShelfLifeDays;
        }

        public int getShelfLifeDaysIn() {
            return ShelfLifeDaysIn;
        }

        public void setShelfLifeDaysIn(int ShelfLifeDaysIn) {
            this.ShelfLifeDaysIn = ShelfLifeDaysIn;
        }

        public int getShelfLifeDaysOt() {
            return ShelfLifeDaysOt;
        }

        public void setShelfLifeDaysOt(int ShelfLifeDaysOt) {
            this.ShelfLifeDaysOt = ShelfLifeDaysOt;
        }

        public boolean isSingleScan() {
            return SingleScan;
        }

        public void setSingleScan(boolean SingleScan) {
            this.SingleScan = SingleScan;
        }

        public ArrayList<LotPropertyEntity> getLotProperty() {
            return LotProperty;
        }

        public void setLotProperty(ArrayList<LotPropertyEntity> LotProperty) {
            this.LotProperty = LotProperty;
        }

        public class LotPropertyEntity {
            /**
             * SeqNo : sample string 1
             * LotCode : sample string 2
             * LotValue : sample string 3
             * RfVisible : sample string 4
             */

            public String SeqNo;
            public String LotCode;
            public String LotValue;
            public String RfVisible;

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
