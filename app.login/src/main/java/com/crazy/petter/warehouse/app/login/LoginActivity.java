package com.crazy.petter.warehouse.app.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.network.Connection;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.util.JsonUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "admin");
            jsonObject.put("UserPwd", "123");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void login() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "admin");
            jsonObject.put("UserPwd", "123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Connection.getInstance(this).requestData("http://115.159.197.221:9898/WebAPI/api/WmsRf/LoginWmsRf", jsonObject.toString(), "login", new RequestCallBack() {
            @Override
            public void onResponse(String s) {
                JSONObject jsonObject1 = JsonUtil.from(s);
                if (JsonUtil.getBoolean(jsonObject1, "success")) {
                    Small.openUri("main", LoginActivity.this);
                    ToastUtils.showShort(LoginActivity.this, "登录成功");
                    LoginActivity.this.finish();
                } else {
                    ToastUtils.showShort(LoginActivity.this, JsonUtil.getString(jsonObject1, "message"));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                ToastUtils.showShort(LoginActivity.this, s);
            }

            @Override
            public void onTokenExpire() {

            }
        });


    }
}
