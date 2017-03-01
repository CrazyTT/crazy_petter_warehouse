package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/3/1.
 */

public class LocBean extends BaseBean {

    private ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * WhId : sample string 1
         * LocSeq : 2
         * Loc : sample string 3
         * LocProperty : sample string 4
         * LocType : sample string 5
         * LocMultiSku : true
         * LocMultiLot : true
         */

        private String WhId;
        private int LocSeq;
        private String Loc;
        private String LocProperty;
        private String LocType;
        private boolean LocMultiSku;
        private boolean LocMultiLot;

        public String getWhId() {
            return WhId;
        }

        public void setWhId(String WhId) {
            this.WhId = WhId;
        }

        public int getLocSeq() {
            return LocSeq;
        }

        public void setLocSeq(int LocSeq) {
            this.LocSeq = LocSeq;
        }

        public String getLoc() {
            return Loc;
        }

        public void setLoc(String Loc) {
            this.Loc = Loc;
        }

        public String getLocProperty() {
            return LocProperty;
        }

        public void setLocProperty(String LocProperty) {
            this.LocProperty = LocProperty;
        }

        public String getLocType() {
            return LocType;
        }

        public void setLocType(String LocType) {
            this.LocType = LocType;
        }

        public boolean isLocMultiSku() {
            return LocMultiSku;
        }

        public void setLocMultiSku(boolean LocMultiSku) {
            this.LocMultiSku = LocMultiSku;
        }

        public boolean isLocMultiLot() {
            return LocMultiLot;
        }

        public void setLocMultiLot(boolean LocMultiLot) {
            this.LocMultiLot = LocMultiLot;
        }
    }
}
