package com.crazy.petter.warehouse.app.main;

import android.os.Bundle;
import android.util.Log;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.network.Connection;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtils.showShort(this, "欢迎你使用");
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("SENSORID", "500004DF6A4A");
        jsonObject.put("KEY", "v34uvm9y839vg6y23mhLSKDF84f10a");
        Connection.getInstance(this).requestData("http://weiguo.hanwei.cn/smart/hwmobile/smart/d002!retrieveRealData", jsonObject, "baidu", new RequestCallBack() {
            @Override
            public void onResponse(String s) {
                Log.e("qwe", s);

            }

            @Override
            public void onErrorResponse(String s) {

            }

            @Override
            public void onTokenExpire() {

            }
        });
    }
}
