package com.bjdv.lib.utils.entity;

public class callLog {
	private String woStaffId;// 员工ID
	private String woNbr;// 工单号
	private String contactInfo;// 客户号码
	private String callType; // 已接、未接 ，呼出
	private String callTime;
	private String duration;
	private String remark;// 备注
	private int state;// 上传状态： 0 未上传 1 已上传

	public callLog(String woStaffId, String woNbr, String contactInfo,
				   String callType, String callTime, String duration, String remark,
				   int state) {
		this.woStaffId = woStaffId;
		this.woNbr = woNbr;
		this.contactInfo = contactInfo;
		this.callType = callType;
		this.callTime = callTime;
		this.duration = duration;
		this.remark = remark;
		this.state = state;
	}

	public String getWoStaffId() {
		return woStaffId;
	}

	public void setWoStaffId(String woStaffId) {
		this.woStaffId = woStaffId;
	}

	public String getWoNbr() {
		return woNbr;
	}

	public void setWoNbr(String woNbr) {
		this.woNbr = woNbr;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
