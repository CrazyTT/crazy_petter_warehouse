package com.crazy.petter.warehouse.app.main.activitys.out;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.presenters.DivideDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.DivideDetialsView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_detials);
        ButterKnife.bind(this);
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
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }
}
