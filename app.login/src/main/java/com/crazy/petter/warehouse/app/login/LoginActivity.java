package com.crazy.petter.warehouse.app.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.network.Connection;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.util.StringUtils;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;

import net.wequick.small.Small;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.edt_name)
    EditText mEdtName;
    @Bind(R.id.edt_psd)
    EditText mEdtPsd;
    @Bind(R.id.btn_login)
    ButtonAutoBg mBtnLogin;
    private SharedPreferencesUtil sp;
    String szImei = "";//设备唯一标识Imei

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = new SharedPreferencesUtil(this);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            szImei = TelephonyMgr.getDeviceId();
            //测试代码
            test();
        }
    }

    private void test() {
        mEdtName.setText("admin");
        mEdtPsd.setText("123");
        mBtnLogin.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    szImei = TelephonyMgr.getDeviceId();
                    //测试代码
                    test();
                } else {
                    ToastUtils.showShort(this, "授权失败");
                }
                return;
            }
        }
    }

    private void login() {
        final String userName = mEdtName.getText().toString().trim();
        final String passWord = mEdtPsd.getText().toString().trim();
        if (StringUtils.isBlank(userName)) {
            ToastUtils.showShort(LoginActivity.this, getResources().getString(R.string.hint_account));
            return;
        } else if (StringUtils.isBlank(passWord)) {
            ToastUtils.showShort(LoginActivity.this, getResources().getString(R.string.hint_pwd));
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", userName);
            jsonObject.put("UserPwd", passWord);
            jsonObject.put("SymbolId", szImei);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress("正在登录中...", false);
        Connection.getInstance(this).requestData(Constant.SERVER_URL_BASE + Constant.LOGIN, jsonObject.toString(), "login", new RequestCallBack() {
            @Override
            public void onResponse(String s) {
                stopProgress();
                JSONObject jsonObject1 = JsonUtil.from(s);
                if (JsonUtil.getBoolean(jsonObject1, "success")) {
                    sp.setString("userName", userName);
                    sp.setString("passWord", passWord);
                    Small.openUri("main", LoginActivity.this);
                    ToastUtils.showShort(LoginActivity.this, "登录成功");
                    LoginActivity.this.finish();
                } else {
                    ToastUtils.showShort(LoginActivity.this, JsonUtil.getString(jsonObject1, "message"));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                stopProgress();
                ToastUtils.showShort(LoginActivity.this, s);
            }
        });
    }
}
