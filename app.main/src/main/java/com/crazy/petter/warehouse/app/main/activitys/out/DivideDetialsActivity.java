package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

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
                        jsonObject.put("WaveId", JsonUtil.getString(mDataEntity, "WAVE_ID"));
                        jsonObject.put("BarCode", mEdtSkuId.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDivideDetialsPresenter.getOrderOne(jsonObject.toString());
                    return true;
                }
                return false;
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdtSkuId.getText().toString().trim())) {
                    ToastUtils.showLong(DivideDetialsActivity.this, "请先扫描");
                    return;
                } else if (dataEntity.size() <= 0) {
                    ToastUtils.showLong(DivideDetialsActivity.this, "没有该商品的明细");
                    mEdtSkuId.setText("");
                    return;
                }
                ConfirmRebinWallWaveBean waveBean = new ConfirmRebinWallWaveBean();
                waveBean.setOutboundId(dataEntity.get(0).getOutboundId());
                waveBean.setSkuId(dataEntity.get(0).getSkuId());
                waveBean.setContainerId(dataEntity.get(0).getContainerId());
                waveBean.setObnSeq(dataEntity.get(0).getObnSeq() + "");
                waveBean.setPickQty(dataEntity.get(0).getPickQty());
                waveBean.setWaveId(JsonUtil.getString(mDataEntity, "WAVE_ID"));
                waveBean.setWavePickInc(dataEntity.get(0).getWavePickInc());
                mDivideDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(waveBean));
            }
        });

        getCount();

    }

    private void getCount() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WaveId", JsonUtil.getString(mDataEntity, "WAVE_ID"));
            jsonObject.put("BarCode", mEdtSkuId.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mDivideDetialsPresenter.getCount(jsonObject.toString());
    }


    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
    }

    @Override
    public void setBottom(int totalQty2, int totalPickQty2) {
        mTxtBottom.setText("总数量" + totalQty2 + "/已经分货数量" + totalPickQty2);
        if (totalQty2 == totalPickQty2) {
            ToastUtils.showLong(this, "此单已经分获完毕");
            if (isCommit) {
                DivideDetialsActivity.this.finish();
            }
        }
    }

    ArrayList<PickWaveDtBean.DataEntity> dataEntity = new ArrayList<>();

    @Override
    public void setOne(ArrayList<PickWaveDtBean.DataEntity> dataEntity2) {
        dataEntity = dataEntity2;
        setCurrent(0);
    }

    private void setCurrent(int index) {
        mTxtContainer.setText("C" + dataEntity.get(index).getContainerId());
        mTxtQty.setText(decimalFormat.format(dataEntity.get(index).getPickQty()));
        mTxtWaveNum.setText(dataEntity.get(index).getOutboundId());
        mEdtSkuId.setText(dataEntity.get(index).getSkuId());
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mEdtSkuId.requestFocus();
            }
        }), 300);
    }

    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    private boolean isCommit = false;

    @Override
    public void commitOk() {
        ToastUtils.showLong(this, "分货完成");
        isCommit = true;
        mTxtContainer.setText("");
        mTxtQty.setText("");
        mTxtWaveNum.setText("");
        mEdtSkuId.setText("");
        mEdtSkuId.requestFocus();
        dataEntity = new ArrayList<>();
        mEdtSkuId.requestFocus();
    }

    @Override
    public void getOrderOneFailure() {
        mEdtSkuId.setText("");
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mEdtSkuId.requestFocus();
            }
        }), 300);
    }

    @Override
    public void commitfailure() {
        mEdtSkuId.requestFocus();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            mBtnCommit.performClick();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENDCALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
