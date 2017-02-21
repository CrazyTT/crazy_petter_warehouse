package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.RemarkAdapter;
import com.crazy.petter.warehouse.app.main.beans.ConfirmObnPickBean;
import com.crazy.petter.warehouse.app.main.beans.PickBean;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;
import com.crazy.petter.warehouse.app.main.presenters.PickDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PickDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PickDetialsActivity extends BaseActivity implements PickDetialsView {

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
    PickBean.DataEntity mDataEntity;
    PickDetialsPresenter mPickDetialsPresenter;
    @Bind(R.id.rl_remark)
    RecyclerView mRlRemark;
    RemarkAdapter mRemarkAdapter;
    ArrayList<PickDetialsBean.DataEntity> datas = new ArrayList<>();
    ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> reMarks = new ArrayList<>();
    boolean isFirst = true;
    PickDetialsBean.DataEntity temp = new PickDetialsBean.DataEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), PickBean.DataEntity.class);
        mPickDetialsPresenter = new PickDetialsPresenter(this, this, "PickDetialsActivity");
        initViews();
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
        mTxtOrderNum.setText(mDataEntity.getOutboundId());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OutboundId", mDataEntity.getOutboundId());
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
                        jsonObject.put("OutboundId", mDataEntity.getOutboundId());
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
//                        jsonObject.put("OutboundId", mDataEntity.getOutboundId());
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
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交数据
                ConfirmObnPickBean confirmObnPickBean = new ConfirmObnPickBean();
                ArrayList<ConfirmObnPickBean.DetailsEntity> detailsEntities = new ArrayList<>();
                ConfirmObnPickBean.DetailsEntity detailsEntity = new ConfirmObnPickBean.DetailsEntity();
                detailsEntity.setObnPickInc(temp.getObnPickInc());
                detailsEntity.setLpnNo("");
                if (TextUtils.isEmpty(mEdtQty.getText().toString().trim())) {
                    ToastUtils.showShort(PickDetialsActivity.this, "数量不能为空");
                    return;
                }
                detailsEntity.setQty(Integer.parseInt(mEdtQty.getText().toString().trim()));
                detailsEntity.setSeqNo(temp.getSeqNo());
                detailsEntity.setSkuId(temp.getSkuId());
                detailsEntity.setSkuName(temp.getSkuName());
                detailsEntity.setToLoc(temp.getPickLoc());
                detailsEntities.add(detailsEntity);
                confirmObnPickBean.setDetails(detailsEntities);
                confirmObnPickBean.setOutboundId(mDataEntity.getOutboundId());
                mPickDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(confirmObnPickBean));
            }
        });
    }

    @Override
    public void commitOk() {
        //提交成功
        if (mTxtQty.getText().toString().trim().equals(mEdtQty.getText().toString().trim())) {
            //这条记录拣货完毕了
            mEdtSkuname.setText("");
            mEdtSkuid.setText("");
            mEdtLoc.setText("");
            mEdtQty.setText("");
            current++;
            if (current >= datas.size()) {
                current = 0;
            }
            setCurrent(current);
            finish++;
            initBottom();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("OutboundId", mDataEntity.getOutboundId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getOrders(jsonObject.toString(), true);
            mEdtQty.setText("");
        }

    }

    private void getOrders(String params, boolean isFirst) {
        this.isFirst = isFirst;
        mPickDetialsPresenter.getOrders(params);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    int current = 0;

    @Override
    public void setList(ArrayList<PickDetialsBean.DataEntity> data) {
        if (data == null || data.size() <= 0) {
            ToastUtils.showShort(this, "此单没有明细");
            return;
        }
        if (isFirst) {
            datas = data;
            all = datas.size();
            for (PickDetialsBean.DataEntity temp : datas) {
                if (temp.getWaitPickQty() == 0) {
                    finish++;
                }
            }
            setCurrent(current);
            initBottom();
        } else {
            temp = data.get(0);
            showInfo(temp);
            //并且得到操作的postion
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getPickLoc().equalsIgnoreCase(temp.getPickLoc()) && datas.get(i).getSkuId().equalsIgnoreCase(temp.getSkuId())) {
                    current = i;
                    setCurrent(current);
                }
            }
        }
        showRemark(0);
    }

    private void showRemark(int postion) {
        mRemarkAdapter = new RemarkAdapter(this);
        reMarks = datas.get(postion).getLotProperty();
        mRemarkAdapter.setList(reMarks);
        mRlRemark.setAdapter(mRemarkAdapter);
    }

    private void showInfo(PickDetialsBean.DataEntity dataEntity) {
        mEdtLoc.setText(dataEntity.getPickLoc());
        mEdtSkuid.setText(dataEntity.getSkuId());
        mEdtSkuname.setText(dataEntity.getSkuName());
    }

    private void setCurrent(int postion) {
        mTxtLoc.setText(datas.get(postion).getPickLoc());
        mTxtSkuid.setText(datas.get(postion).getSkuId());
        mTxtQty.setText(datas.get(postion).getWaitPickQty() + "");
    }

    int all = 0;
    int finish = 0;

    private void initBottom() {
        mTxtBottom.setText("共计" + all + "条/待处理" + (all - finish) + "条/已完成" + finish + "条");
    }
}
