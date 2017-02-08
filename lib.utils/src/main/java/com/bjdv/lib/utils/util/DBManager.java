package com.bjdv.lib.utils.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.bjdv.lib.utils.R;
import com.bjdv.lib.utils.entity.Traffic;
import com.bjdv.lib.utils.entity.callLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {
	private static final String Tag = "DBManager";

	private DBHelper dbHelper;
	private SQLiteDatabase db;

	private final int BUFFER_SIZE = 400000;
	public static final String PACKAGE_NAME = "com.bjdv.oss";
	public static final String DB_NAME = "myapp.db";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME + "/databases";

	private Context context;

	public DBManager(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 创建流量统计表
	 */
	public void createTrafficTable() {
		db.execSQL("Create table if not exists t_traffic(id INTEGER primary key autoincrement,date TEXT,GPRSRxBytes TEXT,GPRSTxBytes TEXT,WifiRxBytes TEXT,WifiTxBytes TEXT);");
	}
	/**
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一段时间内的总流量
	 */
	public Traffic getTraffics(String beginTime, String endTime) {
		String sql = "select * from t_traffic where datetime(date)>=datetime(?) and datetime(date)<=datetime(?)";
		Cursor cursor = db.rawQuery(sql, new String[] { beginTime, endTime });
		Traffic traffic = new Traffic();
		long totalGprsRxTraffics = 0;
		long totalGprsTxTraffics = 0;
		long totalWifiRxTraffics = 0;
		long totalWifiTxTraffics = 0;
		while (cursor.moveToNext()) {
			totalGprsRxTraffics += cursor.getLong(2);
			totalGprsTxTraffics += cursor.getLong(3);
			totalWifiRxTraffics += cursor.getLong(4);
			totalWifiTxTraffics += cursor.getLong(5);
		}
		traffic.setGprsRxTraffic(totalGprsRxTraffics);
		traffic.setGprsTxTraffic(totalGprsTxTraffics);
		traffic.setWifiRxTraffic(totalWifiRxTraffics);
		traffic.setWifiTxTraffic(totalWifiTxTraffics);
		return traffic;
	}
	/**
	 * 获得指定时间之后的统计数据
	 */
	public Traffic getTrafficsAfter(String time) {
		String sql = "select * from t_traffic where datetime(date) > datetime(?)";
		Cursor cursor = db.rawQuery(sql, new String[] { time});
		Traffic traffic = new Traffic();
		long totalGprsRxTraffics = 0;
		long totalGprsTxTraffics = 0;
		long totalWifiRxTraffics = 0;
		long totalWifiTxTraffics = 0;
		while (cursor.moveToNext()) {
			totalGprsRxTraffics += cursor.getLong(2);
			totalGprsTxTraffics += cursor.getLong(3);
			totalWifiRxTraffics += cursor.getLong(4);
			totalWifiTxTraffics += cursor.getLong(5);
		}
		traffic.setGprsRxTraffic(totalGprsRxTraffics);
		traffic.setGprsTxTraffic(totalGprsTxTraffics);
		traffic.setWifiRxTraffic(totalWifiRxTraffics);
		traffic.setWifiTxTraffic(totalWifiTxTraffics);
		return traffic;
	}
	/**
	 * 获得指定时间之前的统计数据
	 */
	public Traffic getTrafficsBefore(String time) {
		String sql = "select * from t_traffic where datetime(date) <= datetime(?)";
		Cursor cursor = db.rawQuery(sql, new String[] { time});
		Traffic traffic = new Traffic();
		long totalGprsRxTraffics = 0;
		long totalGprsTxTraffics = 0;
		long totalWifiRxTraffics = 0;
		long totalWifiTxTraffics = 0;
		while (cursor.moveToNext()) {
			totalGprsRxTraffics += cursor.getLong(2);
			totalGprsTxTraffics += cursor.getLong(3);
			totalWifiRxTraffics += cursor.getLong(4);
			totalWifiTxTraffics += cursor.getLong(5);
		}
		traffic.setGprsRxTraffic(totalGprsRxTraffics);
		traffic.setGprsTxTraffic(totalGprsTxTraffics);
		traffic.setWifiRxTraffic(totalWifiRxTraffics);
		traffic.setWifiTxTraffic(totalWifiTxTraffics);
		return traffic;
	}
	/**
	 * 删除指定时间之前的所有记录
	 */
	public void deleteBeforeTime(String time){
		String sql="delete from t_traffic where datetime(date) <= datetime(?)";
		db.execSQL(sql,new String[]{time});
	}

	/**
	 * 获取所有保存的流量信息
	 */
	/*public Traffic getAllSavedTraffics() {
		String sql = "select * from t_traffic";
		Cursor cursor = db.rawQuery(sql, null);
		Traffic traffic = new Traffic();
		long totalGprsRxTraffics = 0;
		long totalGprsTxTraffics = 0;
		long totalWifiRxTraffics = 0;
		long totalWifiTxTraffics = 0;
		while (cursor.moveToNext()) {
			totalGprsRxTraffics += cursor.getLong(2);
			totalGprsTxTraffics += cursor.getLong(3);
			totalWifiRxTraffics += cursor.getLong(4);
			totalWifiTxTraffics += cursor.getLong(5);
		}
		traffic.setGprsRxTraffic(totalGprsRxTraffics);
		traffic.setGprsTxTraffic(totalGprsTxTraffics);
		traffic.setWifiRxTraffic(totalWifiRxTraffics);
		traffic.setWifiTxTraffic(totalWifiTxTraffics);
		return traffic;
	}*/

	/**
	 *插入流量统计信息
	 */
	public void insertTraffics(boolean isGPRS, long rxTraffic, long txTraffic,
			String date) {
		String sql;
		if (isGPRS)
			sql = "insert into t_traffic(date,GPRSRxBytes,GPRSTxBytes,WifiRxBytes,WifiTxBytes) values (?,?,?,'0','0')";
		else
			sql = "insert into t_traffic(date,GPRSRxBytes,GPRSTxBytes,WifiRxBytes,WifiTxBytes) values (?,'0','0',?,?)";
		db.execSQL(sql, new String[] { date, rxTraffic + "", txTraffic + "" });
	}


	/**
	 * 增加一条通话记录
	 * 
	 * @return
	 */
	public int addCallLog(callLog calllog) {
		Log.d(Tag, "【DBManager】 addCallLog...");
		ContentValues values = new ContentValues();
		values.put("woStaffId", calllog.getWoStaffId());
		values.put("woNbr", calllog.getWoNbr());
		values.put("contactInfo", calllog.getContactInfo());
		values.put("callType", calllog.getCallType());
		values.put("callTime", calllog.getCallTime());
		values.put("duration", calllog.getDuration());
		values.put("remark", calllog.getRemark());
		values.put("state", calllog.getState());
		int id = (int) db.insert("CallLog", null, values);
		return id;
	}

	/**
	 * 更新通话记录
	 * 
	 * @param remark
	 *            备注
	 * @param state
	 *            上传标志
	 * @param callTime
	 *            通话时间
	 * @return 影响行数
	 */
	public int update(String remark, int state, String callTime) {
		Log.d(Tag, "【DBManager】 update...");
		// 得到数据库操作实例
		ContentValues values = new ContentValues();
		// 键值与列名相同
		values.put("remark", remark);
		values.put("state", state);
		int row = db.update("callLog", values, "callTime=?",
				new String[] { callTime });
		return row;
	}

	/**
	 * 查询通话记录列表
	 * 
	 * @param woStaffId
	 *            员工号
	 * @param woNbr
	 *            工单号
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getCallLogs(String woStaffId,
														  String woNbr) {
		Log.d(Tag, "【DBManager】 getCallLogs...");
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Cursor cursor = db.query("CallLog", null, " woStaffId=? and woNbr=? ",
				new String[] { woStaffId, woNbr }, null, null, "callTime desc");
		while (cursor.moveToNext()) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("woStaffId",
					cursor.getString(cursor.getColumnIndex("woStaffId")));
			hashMap.put("woNbr",
					cursor.getString(cursor.getColumnIndex("woNbr")));
			hashMap.put("contactInfo",
					cursor.getString(cursor.getColumnIndex("contactInfo")));
			hashMap.put("callType",
					cursor.getString(cursor.getColumnIndex("callType")));
			hashMap.put("callTime",
					cursor.getString(cursor.getColumnIndex("callTime")));
			hashMap.put("duration",
					cursor.getString(cursor.getColumnIndex("duration")));
			hashMap.put("remarks",
					cursor.getString(cursor.getColumnIndex("remark")));
			hashMap.put("state", cursor.getInt(cursor.getColumnIndex("state")));
			list.add(hashMap);
		}
		cursor.close();
		return list;
	}

	/**
	 * 查询是否有该条通话记录
	 * 
	 * @param woStaffId
	 * @param woNbr
	 * @param callTime
	 * @return
	 */
	public boolean queryCallLog(String woStaffId, String woNbr, String callTime) {
		Log.d(Tag, "【DBManager】 queryCallLog...");
		Cursor cursor = db.query("CallLog", null,
				" woStaffId=? and woNbr=? and callTime=? ", new String[] {
						woStaffId, woNbr, callTime }, null, null, null);
		while (cursor.moveToNext()) {
			cursor.close();
			return true;
		}
		return false;
	}

	/**
	 * 删除一条本地数据库中的通话记录
	 * 
	 * @param woStaffId
	 *            员工号
	 * @param woNbr
	 *            工单号
	 * @param callTime
	 *            通话时间
	 * @return 影响行数
	 */
	public int deleteCallLog(String woStaffId, String woNbr, String callTime) {
		Log.d(Tag, "【DBManager】 deleteCallLog...");
		int row = db.delete("CallLog",
				"woStaffId=? and woNbr=? and callTime=?", new String[] {
						woStaffId, woNbr, callTime });
		return row;
	}

	public void closeDB() {
		Log.d(Tag, "【DBManager】 closeDB...");
		db.close();
	}

	/** copy the database under raw */
	public void copyDatabase() {

		File file = new File(DB_PATH);
		if (!file.isDirectory())
			file.mkdir();
		String dbfile = DB_PATH + "/" + DB_NAME;

		try {
			if (new File(dbfile).length() == 0) {

				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];

				readDB(fos, buffer, R.raw.citychina);

				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void readDB(FileOutputStream fos, byte[] buffer, int db_id)
			throws IOException {
		int count;
		InputStream is;
		is = this.context.getResources().openRawResource(db_id);
		while ((count = is.read(buffer)) > 0) {
			fos.write(buffer, 0, count);
		}
		is.close();
	}
}
