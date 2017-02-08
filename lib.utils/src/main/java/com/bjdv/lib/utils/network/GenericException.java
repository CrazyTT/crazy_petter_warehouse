package com.bjdv.lib.utils.network;


public class GenericException
        extends java.lang.Exception {

    private static final long serialVersionUID = 1L;
    public static int EXCEPTION_LEVEL_SYS = 1;
    public static int EXCEPTION_LEVEL_APP = 2;


    String errId;

    String errMsg;

    String errMsgOri;
    Exception errOri;

    public GenericException() {
    }

    public GenericException(Exception e) {
        super(e);
    }

    public void writeLog(int level) {

        if (level == EXCEPTION_LEVEL_SYS) {
        } else if (level == EXCEPTION_LEVEL_APP) {
        }
    }

    public String getErrId() {
        return errId;
    }

    public void setErrId(String errId) {
        this.errId = errId;
    }

    public String getErrMsg() {
        return errMsg;
    }


    public String getMessage() {
        return errMsg;
    }

    public String getErrMsgOri() {
        return errMsgOri;
    }

    public Exception getErrOri() {
        return errOri;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setErrMsgOri(String errMsgOri) {
        this.errMsgOri = errMsgOri;
    }

    public void setErrOri(Exception errOri) {
        this.errOri = errOri;
    }


}
