package com.bjdv.lib.utils.constants;

/**
 * Title: <br>
 * Description: <br>
 * Date: 2016/8/23 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author PHOON-THINK
 */
public class Constant {
    // public static String SERVER_URL_BASE = "http://218.90.150.118:8101/OSS/";//无锡外网地址
    //public static String SERVER_URL_BASE = "http://10.20.88.20:8090/OSS/";
    //public static String SERVER_URL_BASE = "http://172.17.0.10:8090/OSS/";//无锡内网
    //public static String SERVER_URL_BASE = "http://172.31.81.83:7009/mobile/";//南京内网


    public static String SERVER_URL_BASE = "http://122.97.216.14:7005/mobile/";//南京测试外网
    //public static String SERVER_URL_BASE = "http://122.97.216.15:7005/mobile/";//南京生产外网


    public static String SERVICE_CODE = "webservice/commonRs/";
    public static String SERVER_URL = SERVER_URL_BASE + SERVICE_CODE;


    public static String LOGIN_AUTH = "login";//登录
    public static String SHEET_COUNT_QUERY = "SHEET_COUNT_QUERY";
    public static String GET_BUSI_LIST = "GET_BUSI_LIST";
    public static String GET_STAFF_LIST = "GET_STAFF_LIST";
    public static String TODO_SHEET_QUERY = "TODO_SHEET_QUERY";
    public static String SHEET_DETAIL = "SHEET_DETAIL";
    public static String SHEET_FETCH = "SHEET_FETCH";
    public static String WORKDETAIL = "SHEET_DETAIL";//详情查询
    public static String PRODMAININFO = "SO_MAINPROD_QUERY";//主产品
    public static String PRODSUBINFO = "SO_SUBPROD_QUERY";//附属产品
    public static String EQPTPRODINFO = "SO_EQPTPROD_QUERY";//终端
    public static String GETWOATTACH = "PIC_INFO";//拍照展示
    public static String RELATEDWOLIST = "WO_BACKLIST_QUERY";//协作单列表查询
    public static String MATERIALFILL = "MATERIAL_BACKFILL_QUERY";//已回填材料列表展示
    public static String GETHANDLELIST = "WO_HANDLE_QUERY";//操作日志查询
    public static String INSERT_MATERIAL_DATE = "INSERT_MATERIAL_DATE";
    public static String GET_MATERIAL_QUERY = "GET_MATERIAL_QUERY";
    public static String FAILREASONINFO = "FAIL_REASON_QUERY";//失败原因
    public static String WODISPATCH = "WO_DISPATCH";//工单转派
    public static String GETCALLLIST = "WO_DIAL_QUERY";//获取通话记录
    public static String UPLOADCALLLIST = "WO_SAVE_DIAL";//上传通话记录
    public static String WO_ATTACH_UPLOAD = "WO_ATTACH_UPLOAD";
    public static String WO_RETURN = "WO_RETURN";
    public static String GET_FAULT_REASON_LIST = "GET_FAULT_REASON_LIST";
    public static String SHEET_QUERY = "SHEET_QUERY";
    public static String RESET_PASSWORD = "RESET_PASSWORD";
    public static String GET_FAULTLOCATION_URL = "GET_FAULTLOCATION_URL";
    public static String HANGUP_SHEET_QUERY = "HANGUP_SHEET_QUERY";
    public static String HANDLE_SHEET_QUERY = "HANDLE_SHEET_QUERY";
    public static String ENUM_VALUE_QUERY = "ENUM_VALUE_QUERY";
    public static String BREAKDOWNINFO = "GET_FAULT_LIST";//故障信息
    public static String MAINTAININFO = "REL_FAULT_LIST_QUERY";//维修记录
    public static String EXCHLIST = "GET_EXCH_LIST";//网格清单查询
    public static String PRODUCTLIST = "GET_PROD_LIST";//产品列表查询
    public static String WOCOUNT = "GET_COUNT_SELECT";//统计数据
    public static String GETFAULTSELF = "GET_FAULT_SELF";// 获取故障列表
    public static String FAULTBIND = "FAULT_WO_BIND";// 故障绑定
    public static String FAULTUNBIND = "FAULT_WO_UNBIND";// 故障解绑
    public static String FAULT_LOCATE_INFO = "GET_FAULTLOCATION_INFO";// 3.31	故障定位信息查询
    public static String GETREMOTEDATA = "SHEET_TRANSFER";//通用跳转接口
    public static String ALL_STAFF_COUNT = "ALL_STAFF_COUNT";//报表查询所有人
    public static String WORKMARK = "SHEET_REMARK";//工单备注
    public static String RECEVIETERMINAL = "RECEIVE_TERMINAL";//领取/更换终端
    public static String GET_APPVERSION_INFO = "GET_APPVERSION_INFO";
    public static String CELL_REPAIR_COUNT = "CELL_REPAIR_COUNT";
    public static String CELL_REPAIR_TYPE_COUNT = "CELL_REPAIR_TYPE_COUNT";
    public static String EXCH_REPAIR_COUNT = "EXCH_REPAIR_COUNT";
    public static String EXCH_REPAIR_TYPE_COUNT = "EXCH_REPAIR_TYPE_COUNT";
    public static String GET_KB_URL = "GET_KB_URL";//知识库地址
    public static String FAULT_LOC = "FAULT_LOC";
    public static String INFOMATION_LIST_QUERY = "INFOMATION_LIST_QUERY";
    public static String BULLETIN_ATTACH_DOWNLOAD = "download";// 3.28	公告附件下载
    public static String GET_INFOMATION_ATTACHS = "GET_INFOMATION_ATTACHS";
    public static String GET_INFOMATION = "GET_INFOMATION";
    public static String WO_DIAL_QUERY = "WO_DIAL_QUERY";
    public static String PUSH_MSG_SETTING = "PUSH_MSG_SETTING";
    public static String WORKAREA_LIST = "WORKAREA_LIST";//工区列表接口
    public static String WORKAREA_LIST_DISPATCH = "WORKAREA_LIST_DISPATCH";//转派工区获取


    //测试
    //public static String ServerURL = "http://115.57.127.178:8090/mos/"; //外网
    public static String BUSINESS = "webservice/busiRs/getBusiList";//业务类型列表展示
    public static String UPLOADFILES = "webservice/upLoadFileRs/uploadFile";//上传数据
}
