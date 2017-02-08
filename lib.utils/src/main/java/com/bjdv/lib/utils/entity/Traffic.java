package com.bjdv.lib.utils.entity;

public class Traffic {
	private long gprsRxTraffic;// GPRS下载流量
	private long gprsTxTraffic;// GPRS上传流量
	private long wifiRxTraffic;// WIFI下载流量
	private long wifiTxTraffic;// WIFI上传流量

	public long getGprsRxTraffic() {
		return gprsRxTraffic;
	}

	public void setGprsRxTraffic(long gprsRxTraffic) {
		this.gprsRxTraffic = gprsRxTraffic;
	}

	public long getGprsTxTraffic() {
		return gprsTxTraffic;
	}

	public void setGprsTxTraffic(long gprsTxTraffic) {
		this.gprsTxTraffic = gprsTxTraffic;
	}

	public long getWifiRxTraffic() {
		return wifiRxTraffic;
	}

	public void setWifiRxTraffic(long wifiRxTraffic) {
		this.wifiRxTraffic = wifiRxTraffic;
	}

	public long getWifiTxTraffic() {
		return wifiTxTraffic;
	}

	public void setWifiTxTraffic(long wifiTxTraffic) {
		this.wifiTxTraffic = wifiTxTraffic;
	}

	public long getWifiTotal() {
		return wifiRxTraffic + wifiTxTraffic;
	}

	public long getGprsTotal() {
		return gprsTxTraffic + gprsRxTraffic;
	}

	public long getRxTotal() {
		return wifiRxTraffic + gprsRxTraffic;
	}

	public long getTxTotal() {
		return gprsTxTraffic + wifiTxTraffic;
	}
	public long getTotal() {
		return gprsTxTraffic + wifiTxTraffic+wifiRxTraffic + gprsRxTraffic;
	}
}