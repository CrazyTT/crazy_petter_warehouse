package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.RemarkWaveAdapter;
import com.crazy.petter.warehouse.app.main.beans.PickWaveBean;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDetialsBean;
import com.crazy.petter.warehouse.app.main.presenters.PickWaveDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PickWaveDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PickWaveDetialsActivity extends BaseActivity implements PickWaveDetialsView {

    @Bind(R.id.txt_orderNum)
    TextView mTxtOrderNum;
    @Bind(R.id.txt_loc)
    TextView mTxtLoc;
    @Bind(R.id.edt_loc)
    EditText mEdtLoc;
    @Bind(R.id.txt_skuid)
    TextView mTxtSkuid;
    @Bind(R.id.edt_skuid)
    EditText mEdtSkuid;
    @Bind(R.id.edt_skuname)
    EditText mEdtSkuname;
    @Bind(R.id.txt_qty)
    TextView mTxtQty;
    @Bind(R.id.edt_qty)
    EditText mEdtQty;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    @Bind(R.id.txt_bottom)
    TextView mTxtBottom;
    @Bind(R.id.activity_pick_detials)
    LinearLayout mActivityPickDetials;
    PickWaveBean.DataEntity mDataEntity;
    PickWaveDetialsPresenter mPickDetialsPresenter;
    @Bind(R.id.rl_remark)
    RecyclerView mRlRemark;
    RemarkWaveAdapter mRemarkAdapter;
    ArrayList<PickWaveDetialsBean.DataEntity> datas = new ArrayList<>();
    ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> reMarks = new ArrayList<>();
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_wave_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), PickWaveBean.DataEntity.class);
        mPickDetialsPresenter = new PickWaveDetialsPresenter(this, this, "PickDetialsActivity");
        initViews();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtLoc, false);
            setShowSoftInputOnFocus.invoke(mEdtSkuid, false);
            setShowSoftInputOnFocus.invoke(mEdtSkuname, false);
            setShowSoftInputOnFocus.invoke(mEdtQty, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        mTxtOrderNum.setText(mDataEntity.getWaveDocId());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WaveId", mDataEntity.getWaveDocId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getOrders(jsonObject.toString(), true);
        mEdtLoc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("WaveId", mDataEntity.getWaveDocId());
                        jsonObject.put("PickLoc", mEdtLoc.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getOrders(jsonObject.toString(), false);
                    return true;
                }
                return false;
            }
        });
//        mEdtSkuid.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//                    }
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("WaveId", mDataEntity.getWaveDocId());
//                        jsonObject.put("SkuId", mEdtSkuid.getText().toString().trim());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    getOrders(jsonObject.toString(), false);
//                    return true;
//                }
//                return false;
//            }
//        });

    }


    private void getOrders(String params, boolean isFirst) {
        this.isFirst = isFirst;
        mPickDetialsPresenter.getOrders(params);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }


    @Override
    public void setList(ArrayList<PickWaveDetialsBean.DataEntity> data) {
        if (data == null || data.size() <= 0) {
            ToastUtils.showShort(this, "此单没有拣货明细");
            return;
        }
        if (isFirst) {
            datas = data;
            setFist();
        } else {
            showInfo(data.get(0));
        }
        showRemark(0);
    }

    private void showRemark(int postion) {
        mRemarkAdapter = new RemarkWaveAdapter(this);
        reMarks = datas.get(postion).getLotProperty();
        mRemarkAdapter.setList(reMarks);
        mRlRemark.setAdapter(mRemarkAdapter);
    }


    private void showInfo(PickWaveDetialsBean.DataEntity dataEntity) {
        mEdtLoc.setText(dataEntity.getPickLoc());
        mEdtSkuid.setText(dataEntity.getSkuId());
        mEdtSkuname.setText(dataEntity.getSkuName());
    }

    private void setFist() {
        mTxtLoc.setText(datas.get(0).getPickLoc());
        mTxtSkuid.setText(datas.get(0).getSkuId());
        mTxtQty.setText(datas.get(0).getWaitPickQty() + "");
        initBottom();
    }

    private void initBottom() {
        int all = datas.size();
        int finish = 0;
        for (PickWaveDetialsBean.DataEntity data : datas) {
            if (data.getWaitPickQty() == 0) {
                finish++;
            }
        }
        mTxtBottom.setText("共计" + all + "条/待处理" + (all - finish) + "条/已完成" + finish + "条");
    }
}
