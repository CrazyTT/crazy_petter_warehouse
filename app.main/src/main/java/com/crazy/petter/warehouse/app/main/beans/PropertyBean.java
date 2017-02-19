package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/19.
 */

public class PropertyBean extends BaseBean {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * SkuPropertyCode : Bad
         * SkuPropertyDesc : 坏货
         */

        public String SkuPropertyCode;
        public String SkuPropertyDesc;

        public String getSkuPropertyCode() {
            return SkuPropertyCode;
        }

        public void setSkuPropertyCode(String SkuPropertyCode) {
            this.SkuPropertyCode = SkuPropertyCode;
        }

        public String getSkuPropertyDesc() {
            return SkuPropertyDesc;
        }

        public void setSkuPropertyDesc(String SkuPropertyDesc) {
            this.SkuPropertyDesc = SkuPropertyDesc;
        }
    }
}
