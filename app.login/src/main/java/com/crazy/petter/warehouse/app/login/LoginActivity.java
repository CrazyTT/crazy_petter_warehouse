package com.crazy.petter.warehouse.app.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.constants.Constant;
import com.bjdv.lib.utils.network.Connection;
import com.bjdv.lib.utils.network.RequestCallBack;
import com.bjdv.lib.utils.util.JsonUtil;
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
        String userName = mEdtName.getText().toString().trim();
        String passWord = mEdtPsd.getText().toString().trim();
        if (StringUtils.isBlank(userName)) {
            ToastUtils.showShort(LoginActivity.this, getResources().getString(R.string.hint_account));
            return;
        } else if (StringUtils.isBlank(passWord)) {
            ToastUtils.showShort(LoginActivity.this, getResources().getString(R.string.hint_pwd));
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "admin");
            jsonObject.put("UserPwd", "123");
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