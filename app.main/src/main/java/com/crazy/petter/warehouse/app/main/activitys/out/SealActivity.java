package com.crazy.petter.warehouse.app.main.activitys.out;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.SealBean;
import com.crazy.petter.warehouse.app.main.presenters.SealPresenter;
import com.crazy.petter.warehouse.app.main.views.SealView;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SealActivity extends BaseActivity implements SealView {

    @Bind(R.id.txt_orderNum)
    TextView mTxtOrderNum;
    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.edt_packNum)
    EditText mEdtPackNum;
    @Bind(R.id.edt_weight)
    EditText mEdtWeight;
    @Bind(R.id.edt_volume)
    EditText mEdtVolume;
    @Bind(R.id.activity_seal)
    LinearLayout mActivitySeal;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    SealPresenter mSealPresenter;
    String OutboundId = "";
    String CartonId = "";
    String CartonTypeId = "";
    double weight = 0;
    double Volume = 0;
//    intent.putExtra("OutboundId", mDataEntity.getOutboundId());
//    intent.putExtra("CartonId", mEdtPackNum.getText().toString().trim());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seal);
        ButterKnife.bind(this);
        mSealPresenter = new SealPresenter(this, this, "SealActivity");
        OutboundId = getIntent().getStringExtra("OutboundId");
        CartonId = getIntent().getStringExtra("CartonId");
        CartonTypeId = getIntent().getStringExtra("CartonTypeId");
        weight = getIntent().getDoubleExtra("weight", 0);
        Volume = getIntent().getDoubleExtra("Volume", 0);
        mTxtOrderNum.setText(OutboundId);
        mEdtPackNum.setText(CartonId);
        mEdtWeight.setText(weight + "");
        mEdtVolume.setText(Volume + "");
        initViews();
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtPackNum, false);
            setShowSoftInputOnFocus.invoke(mEdtWeight, false);
            setShowSoftInputOnFocus.invoke(mEdtVolume, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        mEdtWeight.requestFocus();
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdtVolume.getText().toString().trim()) || TextUtils.isEmpty(mEdtWeight.getText().toString().trim())) {
                    ToastUtils.showLong(SealActivity.this, "请将信息补充完整");
                    return;
                }
                SealBean sealBean = new SealBean();
                sealBean.setCartonId(CartonId);
                sealBean.setCartonTypeId(CartonTypeId);
                sealBean.setIsSeal(true);
                sealBean.setOutboundId(OutboundId);
                sealBean.setActualVolume(mEdtVolume.getText().toString().trim());
                sealBean.setActualWeight(mEdtWeight.getText().toString().trim());
                mSealPresenter.seal(JsonFormatter.getInstance().object2Json(sealBean));
            }
        });
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
    }

    @Override
    public void sealOK() {
        ToastUtils.showLong(this, "装箱成功");
        setResult(RESULT_OK);
        this.finish();
    }
}
