package com.bjdv.lib.utils.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bjdv.lib.utils.network.EncryptUtil;
import com.bjdv.lib.utils.network.SysException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Title: MOS掌上运维应用<br>
 * Description: 本地储存数组，解决3.0之前的数组保存问题，以及默认set无序的问题<br>
 * Date: 2014-05-01 <br>
 * Copyright (c) 2013 DATANG BJDV<br>
 *
 * @author PHOON-THINK
 */

public class SharedPreferencesUtil {

    private static final String Tag = "SharedPreferencesUtil";
    private Context mContext;
    public SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context context) {
        this.mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getString(String key) {
        String str;
        str = sharedPreferences.getString(key, "");
        return str;
    }

    public Long getLong(String key) {
        long temp;
        temp = sharedPreferences.getLong(key, 0);
        return temp;
    }

    public void setLong(String key, long value) {
        Editor et = sharedPreferences.edit();
        et.putLong(key, value);
        et.commit();
    }

    public int getInt(String key) {
        int str;
        str = sharedPreferences.getInt(key, -1);
        return str;
    }

    public void setString(String key, String value) {
        Editor et = sharedPreferences.edit();
        et.putString(key, value);
        et.commit();
    }

    public Boolean getBoolean(String key) {
        Boolean str;
        str = sharedPreferences.getBoolean(key, false);
        return str;
    }

    public Boolean getBoolean(String key, boolean flag) {
        Boolean str;
        str = sharedPreferences.getBoolean(key, flag);
        return str;
    }

    public void setBoolean(String key, Boolean value) {
        Editor et = sharedPreferences.edit();
        et.putBoolean(key, value);
        et.commit();
    }

    public String[] getSharedPreference(String key) {
        String regularEx = "#";
        String[] str = null;
        String values;
        values = sharedPreferences.getString(key, "");
        str = values.split(regularEx);
        return str;
    }

    public ArrayList<String> getSharedPreferenceList(String key) {
        String regularEx = "#";
        ArrayList<String> list = null;
        String values;
        values = sharedPreferences.getString(key, "");
        list = new ArrayList<String>(Arrays.asList(values.split(regularEx)));
        return list;
    }

    public void setSharedPreference(String key, JSONArray values) {
        String regularEx = "#";
        String str = "";
        try {
            if (values != null && values.length() > 0) {
                for (int i = 0; i < values.length(); i++) {
                    str += values.getString(i);
                    str += regularEx;
                }
                Editor et = sharedPreferences.edit();
                et.putString(key, str);
                et.commit();
                Log.i(Tag, "str=" + str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setStaffInfo(String staffId, String workAreaId,
                             String workAreaName) {
        String regularEx = "#";
        String str = "";
        str = staffId + regularEx + workAreaId + regularEx + workAreaName + regularEx;
        Editor et = sharedPreferences.edit();
        et.putString(staffId, str);
        et.commit();
        Log.i(Tag, "info=" + str);
    }

    public String[] getStaffInfo(String staffId) {
        String regularEx = "#";
        String[] str = {};
        String values = "";
        try {
            values = sharedPreferences.getString(staffId, "");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
        str = values.split(regularEx);
        Log.i(Tag, "info=" + str);
        return str;
    }

    public void setEncryptString(String key, String value) {
        try {
            Editor et = sharedPreferences.edit();
            value = EncryptUtil.encryptThreeDESECB(value);
            et.putString(key, value);
            et.commit();
        } catch (SysException e) {
            e.printStackTrace();
        }
    }

    public String getEncryptString(String key) {
        String str;
        try {
            str = sharedPreferences.getString(key, "");
            str = EncryptUtil.decryptThreeDESECB(str);
            return str;
        } catch (SysException e) {
            e.printStackTrace();
            return "";
        }
    }


}
