package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.ConfirmRebinWallWaveBean;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDtBean;
import com.crazy.petter.warehouse.app.main.presenters.DivideDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.DivideDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DivideDetialsActivity extends BaseActivity implements DivideDetialsView {
    DivideDetialsPresenter mDivideDetialsPresenter;
    @Bind(R.id.txt_order_num)
    TextView mTxtOrderNum;
    @Bind(R.id.edt_sku_id)
    EditText mEdtSkuId;
    @Bind(R.id.txt_container)
    TextView mTxtContainer;
    @Bind(R.id.txt_qty)
    TextView mTxtQty;
    @Bind(R.id.txt_wave_num)
    TextView mTxtWaveNum;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    @Bind(R.id.txt_bottom)
    TextView mTxtBottom;
    JSONObject mDataEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
        mTxtOrderNum.setText(JsonUtil.getString(mDataEntity, "WAVE_ID"));
        mDivideDetialsPresenter = new DivideDetialsPresenter(this, this, "DivideDetialsActivity");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtSkuId, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEdtSkuId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("WaveId",JsonUtil.getString(mDataEntity, "WAVE_ID"));
                        jsonObject.put("SkuId", mEdtSkuId.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDivideDetialsPresenter.getOrderOne(jsonObject.toString());
                    return true;
                }
                return false;
            }
        });
        initAll();
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdtSkuId.getText().toString().trim())) {
                    ToastUtils.showLong(DivideDetialsActivity.this, "请先扫描");
                } else if (one == null) {
                    ToastUtils.showLong(DivideDetialsActivity.this, "没有该商品的明细");
                    mEdtSkuId.setText("");
                    return;
                }
                ConfirmRebinWallWaveBean waveBean = new ConfirmRebinWallWaveBean();
                waveBean.setOutboundId("");
                waveBean.setSkuId(one.getSkuId());
                waveBean.setContainerId(one.getContainerId());
                waveBean.setObnSeq(one.getObnSeq() + "");
                waveBean.setPickQty(one.getPickQty());
                waveBean.setWaveId(JsonUtil.getString(mDataEntity, "WAVE_ID"));
                mDivideDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(waveBean));
            }
        });

    }

    private void initAll() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WaveId", JsonUtil.getString(mDataEntity, "WAVE_ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mDivideDetialsPresenter.getOrderAll(jsonObject.toString());
    }

    @Override
    public void showTips(String s) {
        mEdtSkuId.requestFocus();
        ToastUtils.showLong(this, s);
    }

    @Override
    public void setBottom(int totalQty, int totalPickQty) {
        if (totalQty == totalPickQty) {
            ToastUtils.showLong(this, "此单已经分获完毕");
        }
        mTxtBottom.setText("总数量" + totalQty + "/已经分货数量" + totalPickQty);
    }

    @Override
    public void getOrderAllFailure() {
        mEdtSkuId.requestFocus();
        mTxtBottom.setText("总数量xx/已经分货数量xx");
    }

    PickWaveDtBean.DataEntity one;

    @Override
    public void setOne(PickWaveDtBean.DataEntity dataEntity) {
        mTxtContainer.setText(dataEntity.getContainerId());
        mTxtQty.setText(dataEntity.getPickQty());
        mTxtWaveNum.setText(dataEntity.getExtId());
        mEdtSkuId.setText(dataEntity.getSkuId());
        one = dataEntity;
    }

    @Override
    public void commitOk() {
        mTxtContainer.setText("");
        mTxtQty.setText("");
        mTxtWaveNum.setText("");
        one = null;
        mEdtSkuId.requestFocus();
        initAll();
    }
}
