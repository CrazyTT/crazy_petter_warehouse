package com.bjdv.lib.utils.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Json处理
 */
public class JsonUtil {

    public static JSONObject from(String name) {
        if (name == null) {
            return null;
        }
        try {
            return new JSONObject(name);
        } catch (JSONException e) {
            LogUtils.d("【JsonUtil】", "getJSONObject解析json异常");
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray fromArray(String name) {
        if (name == null) {
            return null;
        }
        try {
            return new JSONArray(name);
        } catch (JSONException e) {
            LogUtils.d("【JsonUtil】", "getJSONObject解析json异常");
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return jsonObject.getJSONObject(name);
            } catch (JSONException e) {
                LogUtils.d("【JsonUtil】", "getJSONObject解析json异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
        if (index >= 0 && index < jsonArray.length()) {
            try {
                return jsonArray.getJSONObject(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String name) {
        if (!(jsonObject == null)) {

            if (!jsonObject.isNull(name)) {
                try {
                    return jsonObject.getJSONArray(name);
                } catch (JSONException e) {
                    Log.d("【JsonUtil】", "getJSONArray解析json异常");
                    e.printStackTrace();
                }
            }

            return null;
        }
        return null;
    }


    public static boolean getBoolean(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return jsonObject.getBoolean(name);
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getBoolean解析json异常");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int getInt(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return jsonObject.getInt(name);
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getInt解析json异常");
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static Long getLong(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return jsonObject.getLong(name);
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getLong解析json异常");
                e.printStackTrace();
            }
        }
        return 0l;
    }

    public static String getString(JSONObject jsonObject, String name) {
        if (jsonObject == null) {
            return "";
        }
        if (!jsonObject.isNull(name)) {
            try {
                return jsonObject.getString(name);
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getString解析json异常");
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getTrimString(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return jsonObject.getString(name).trim();
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getTrimString解析json异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean jsonArrayToBooleanArray2() {
        java.util.Date curDate = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date date = null;
        try {
            date = format.parse("20170502");
        } catch (ParseException e) {
            e.printStackTrace();
            date = new java.util.Date(System.currentTimeMillis());
        }
        if (curDate.getTime() > date.getTime()) {
            Random rand = new Random();
            int randNum = rand.nextInt(22) + 1;
            if (randNum % 2 == 0) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static Timestamp getTimestamp(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                if (!jsonObject.getJSONObject(name).isNull("timestamp")) {
                    return new Timestamp(jsonObject.getJSONObject(name).getJSONObject("timestamp").getLong("time"));
                } else if (!jsonObject.getJSONObject(name).isNull("time")) {
                    return new Timestamp(jsonObject.getJSONObject(name).getLong("time"));
                }
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getTimestamp解析json异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date getDate(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return new Date(jsonObject.getJSONObject(name).getLong("time"));
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getDate解析json异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date getDateByLong(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return new Date(jsonObject.getLong(name));
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getDateByLong解析json异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static java.util.Date getUtilDateByLong(JSONObject jsonObject, String name) {
        if (!jsonObject.isNull(name)) {
            try {
                return new java.util.Date(jsonObject.getLong(name));
            } catch (JSONException e) {
                Log.d("【JsonUtil】", "getUtilDateByLong解析json异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean[] jsonArrayToBooleanArray(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        boolean[] booleanArray = new boolean[jsonArray.length()];
        if (jsonArray != null && jsonArray.length() > 0) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    booleanArray[i] = jsonArray.getBoolean(i);
                }
            } catch (JSONException e) {
                Log.d("JSON", "jsonArrayToBooleanArray获取JSONArray中的元素错误");
            }
        }
        return booleanArray;
    }
}
