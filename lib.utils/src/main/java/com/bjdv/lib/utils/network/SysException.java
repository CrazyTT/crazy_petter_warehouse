package com.bjdv.lib.utils.network;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class SysException extends GenericException {

	private static final long serialVersionUID = 1L;

	private String trace = "";

	public SysException() {
	}

	public SysException(String errId, String errOwnMsg, Exception oriEx) {

			
		super(oriEx);
		this.errId = errId;
		this.errMsg = errOwnMsg;
		this.errOri = oriEx;
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);
		this.errMsgOri = os.toString().trim();
		this.writeSysException();
	}

	public SysException(String errId, Exception oriEx) {
		super(oriEx);
		
		this.errId = errId;
		this.errOri = oriEx;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);
		this.errMsgOri = os.toString().trim();
		this.writeSysException();
	}

	public void appendMsg(String msg) {
		trace += msg;
	}

	public String getTrace() {
		return trace;
	}

	public String getMessage() {
		String message = "";
		message = errMsg + "[" + errMsgOri + "]";
		return message;
	}

	public void printDebug() {
		this.errOri.printStackTrace();
	}

	private void writeSysException() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);

	}

}
