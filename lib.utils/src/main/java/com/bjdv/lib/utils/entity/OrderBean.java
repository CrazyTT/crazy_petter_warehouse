package com.bjdv.lib.utils.entity;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/3/7.
 */

public class OrderBean extends BaseBean {

    public ArrayList<CaptionEntity> caption;

    public ArrayList<CaptionEntity> getCaption() {
        return caption;
    }

    public void setCaption(ArrayList<CaptionEntity> caption) {
        this.caption = caption;
    }

    public static class CaptionEntity {
        /**
         * SEQ_NO : 10
         * FIELD_NAME : OWNER_ID
         * CAPTION : 货主
         */

        public int SEQ_NO;
        public String FIELD_NAME;
        public String CAPTION;

        public String getVISIBLE() {
            return VISIBLE;
        }

        public void setVISIBLE(String VISIBLE) {
            this.VISIBLE = VISIBLE;
        }

        public String VISIBLE;

        public int getSEQ_NO() {
            return SEQ_NO;
        }

        public void setSEQ_NO(int SEQ_NO) {
            this.SEQ_NO = SEQ_NO;
        }

        public String getFIELD_NAME() {
            return FIELD_NAME;
        }

        public void setFIELD_NAME(String FIELD_NAME) {
            this.FIELD_NAME = FIELD_NAME;
        }

        public String getCAPTION() {
            return CAPTION;
        }

        public void setCAPTION(String CAPTION) {
            this.CAPTION = CAPTION;
        }
    }

}
