package com.bjdv.lib.utils.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.TrafficStats;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bjdv.lib.utils.entity.Traffic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TrafficStatistic {
	private SharedPreferences sharePreferences;
	private DBManager db;
	private int uid;
	private final String TAG = "TrafficStatistic";
	private int currentNetType = -1;

	public TrafficStatistic(Context context) {
		sharePreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		db = new DBManager(context.getApplicationContext());
		uid = context.getApplicationInfo().uid;
		currentNetType = getConnectedNetType(context.getApplicationContext());
	}

	public int getConnectedNetType(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State gprsState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		int currentNetType = -1;// 当前网络类型(0代表GPRS,1代表WIFI，-1代表没有网络连接)
		if (State.CONNECTED == gprsState) {
			currentNetType = 0;
		} else if (State.CONNECTED == wifiState) {
			currentNetType = 1;
		} else {
			currentNetType = -1;
		}
		return currentNetType;
	}

	/**
	 * 根据网络情况决定是否保存当前数据流量
	 */
	public void judgeToSave() {
		Log.e(TAG, "-------judgeToSave------");
		int lastNetType = getLastNetType();
		Log.e(TAG, "------lastNetType------：" + getLastNetType());
		Log.e(TAG, "------currentNetType------：" + currentNetType);
		if (currentNetType > -1) {// 连网
			if (lastNetType > -1 && currentNetType != lastNetType)
				saveLastTraffic();
			else
				setLastNetType(currentNetType);
		} else {// 断网
			if (lastNetType > -1)
				saveLastTraffic();
			else
				setLastNetType(currentNetType);
		}
	}

	public void setStartTime() {
		Editor editor = sharePreferences.edit();
		editor.putString("startTime", getCurrentTime());
		editor.commit();
	}

	public void setLastNetType(int lastNetType) {
		Editor editor = sharePreferences.edit();
		editor.putInt("lastNetType", lastNetType);
		editor.commit();
	}

	public int getLastNetType() {
		return sharePreferences.getInt("lastNetType", -1);
	}

	public String getStartTime() {
		return sharePreferences.getString("startTime", getCurrentTime());
	}

	/**
	 * 获取当前时间
	 */
	private String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(date);
		return currentTime;
	}

	/**
	 * 清除startTime之前的数据并保存当前数据
	 */
	public void doClearTraffic() {
		// 保存清除时间
		Editor editor = sharePreferences.edit();
		editor.putString("trafficClearTime", getCurrentTime());
		editor.commit();
		if (currentNetType > -1)
			saveCurrentTraffic();
		db.deleteBeforeTime(getStartTime());
	}

	/**
	 * 获取当前所有流量信息
	 */
	public Traffic getTotalTraffic() {
		Traffic totalTraffic = new Traffic();
		long rxBytes = TrafficStats.getUidRxBytes(uid);
		long txBytes = TrafficStats.getUidTxBytes(uid);
		rxBytes = rxBytes > 0 ? rxBytes : 0;
		txBytes = txBytes > 0 ? txBytes : 0;
		// 获取开机到现在所有未保存的流量信息
		long unSavedRxBytes = 0;
		long unSavedTxBytes = 0;
		Log.e(TAG, "-------currentNetType-------" + currentNetType);
		if (currentNetType > -1) {// 只有存在网络连接时才可能有未保存的数据
			Traffic traffic = db.getTrafficsAfter(getStartTime());
			Log.e(TAG, "开始时间到现在数据库中保存的：" + traffic.getTotal());
			unSavedRxBytes = rxBytes - traffic.getRxTotal();
			unSavedTxBytes = txBytes - traffic.getTxTotal();
		}
		Traffic savedTraffics = db.getTrafficsAfter(sharePreferences.getString(
				"trafficClearTime", "2012-12-12 12:12:12"));
		Log.e(TAG, "开始时间到现在手机记录的：" + (txBytes + rxBytes));
		long totalGprsRx = 0, totalGprsTx = 0, totalWifiRx = 0, totalWifiTx = 0;
		if (currentNetType == 0) {// GPRS=未保存的+已存的 WIFI=已存的
			totalGprsRx = unSavedRxBytes + savedTraffics.getGprsRxTraffic();
			totalGprsTx = unSavedTxBytes + savedTraffics.getGprsTxTraffic();
			totalWifiRx = savedTraffics.getWifiRxTraffic();
			totalWifiTx = savedTraffics.getWifiTxTraffic();
		} else if (currentNetType == 1) {
			totalGprsRx = savedTraffics.getGprsRxTraffic();
			totalGprsTx = savedTraffics.getGprsTxTraffic();
			totalWifiRx = unSavedRxBytes + savedTraffics.getWifiRxTraffic();
			totalWifiTx = unSavedTxBytes + savedTraffics.getWifiTxTraffic();
		} else {
			totalGprsRx = savedTraffics.getGprsRxTraffic();
			totalGprsTx = savedTraffics.getGprsTxTraffic();
			totalWifiRx = savedTraffics.getWifiRxTraffic();
			totalWifiTx = savedTraffics.getWifiTxTraffic();
		}
		totalTraffic.setGprsRxTraffic(totalGprsRx);
		totalTraffic.setGprsTxTraffic(totalGprsTx);
		totalTraffic.setWifiRxTraffic(totalWifiRx);
		totalTraffic.setWifiTxTraffic(totalWifiTx);
		return totalTraffic;
	}

	/**
	 * 保存上次网络数据流量
	 */
	public void saveLastTraffic() {
		saveTraffic(getCurrentTime(), getLastNetType());
		setLastNetType(currentNetType);
	}

	/**
	 * 保存当前网络数据流量
	 */
	public void saveCurrentTraffic() {
		if (currentNetType > -1)
			saveTraffic(getCurrentTime(), currentNetType);
	}

	/**
	 * 保存前一天的网络数据流量
	 */
	public void saveDayTraffic() {
		if (currentNetType > -1) {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int day = c.get(Calendar.DAY_OF_MONTH) - 1;// 获取当前日期的前一天
			String monthStr = month > 9 ? month + "" : "0" + month;
			String date = year + "-" + monthStr + "-" + day + " 23:59:59";
			saveTraffic(date, currentNetType);
		}
	}

	/**
	 * 将数据插入到数据库
	 */
	private void saveTraffic(String time, int netType) {
		// 获得自开机以来的上传流量和下载流量
		Traffic recordTraffic = db.getTraffics(getStartTime(), time);
		boolean isGPRS = true;
		if (netType == 1)
			isGPRS = false;
		db.insertTraffics(isGPRS, TrafficStats.getUidRxBytes(uid)
				- recordTraffic.getRxTotal(), TrafficStats.getUidTxBytes(uid)
				- recordTraffic.getTxTotal(), time);
		Log.e(TAG, "------保存数据-----" + getLastNetType());
	}
}
