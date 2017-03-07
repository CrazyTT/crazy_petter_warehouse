package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/3/7.
 */

public class SkuBean extends BaseBean {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * OwnerId : sample string 1
         * SkuId : sample string 2
         * SkuName1 : sample string 3
         * SkuName2 : sample string 4
         * BarCode1 : sample string 5
         * BarCode2 : sample string 6
         * BarCode3 : sample string 7
         * BarCode4 : sample string 8
         */

        public String OwnerId;
        public String SkuId;
        public String SkuName1;
        public String SkuName2;
        public String BarCode1;
        public String BarCode2;
        public String BarCode3;
        public String BarCode4;

        public String getOwnerId() {
            return OwnerId;
        }

        public void setOwnerId(String OwnerId) {
            this.OwnerId = OwnerId;
        }

        public String getSkuId() {
            return SkuId;
        }

        public void setSkuId(String SkuId) {
            this.SkuId = SkuId;
        }

        public String getSkuName1() {
            return SkuName1;
        }

        public void setSkuName1(String SkuName1) {
            this.SkuName1 = SkuName1;
        }

        public String getSkuName2() {
            return SkuName2;
        }

        public void setSkuName2(String SkuName2) {
            this.SkuName2 = SkuName2;
        }

        public String getBarCode1() {
            return BarCode1;
        }

        public void setBarCode1(String BarCode1) {
            this.BarCode1 = BarCode1;
        }

        public String getBarCode2() {
            return BarCode2;
        }

        public void setBarCode2(String BarCode2) {
            this.BarCode2 = BarCode2;
        }

        public String getBarCode3() {
            return BarCode3;
        }

        public void setBarCode3(String BarCode3) {
            this.BarCode3 = BarCode3;
        }

        public String getBarCode4() {
            return BarCode4;
        }

        public void setBarCode4(String BarCode4) {
            this.BarCode4 = BarCode4;
        }
    }
}
