package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.RemarkWaveAdapter2;
import com.crazy.petter.warehouse.app.main.beans.ConfirmWavePickBean;
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
    JSONObject mDataEntity;
    PickWaveDetialsPresenter mPickDetialsPresenter;
    @Bind(R.id.rl_remark)
    ListView mRlRemark;
    RemarkWaveAdapter2 mRemarkAdapter;
    ArrayList<PickWaveDetialsBean.DataEntity> datas = new ArrayList<>();
    ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> reMarks = new ArrayList<>();
    boolean isFirst = true;
    PickWaveDetialsBean.DataEntity temp = new PickWaveDetialsBean.DataEntity();
    int current = 0;
    String currentLoc = "";
    String currentSkuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_wave_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
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
        mTxtOrderNum.setText(JsonUtil.getString(mDataEntity, "WAVE_ID"));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WaveId", JsonUtil.getString(mDataEntity, "WAVE_ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getOrders(jsonObject.toString(), true);
        mEdtLoc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                        ToastUtils.showLong(PickWaveDetialsActivity.this, "请先扫描货位");
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mEdtLoc.requestFocus();
                            }
                        }), 300);
                        return true;
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("WaveId", JsonUtil.getString(mDataEntity, "WAVE_ID"));
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
        mEdtQty.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    new Handler().postDelayed(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnCommit.requestFocus();
                            mBtnCommit.performClick();
                        }
                    }), 300);
                    return true;
                }
                return false;
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交数据
                if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                    ToastUtils.showLong(PickWaveDetialsActivity.this, "货位不能为空,请重新扫描");
                    mEdtLoc.requestFocus();
                    return;
                }
                ConfirmWavePickBean confirmObnPickBean = new ConfirmWavePickBean();
                ArrayList<ConfirmWavePickBean.DetailsEntity> detailsEntities = new ArrayList<>();
                ConfirmWavePickBean.DetailsEntity detailsEntity = new ConfirmWavePickBean.DetailsEntity();
                if (TextUtils.isEmpty(mEdtQty.getText().toString().trim())) {
                    ToastUtils.showLong(PickWaveDetialsActivity.this, "数量不能为空");
                    return;
                }
                detailsEntity.setQty(Integer.parseInt(mEdtQty.getText().toString().trim()));
                detailsEntity.setSkuId(temp.getSkuId());
                detailsEntity.setSkuName(temp.getSkuName());
                detailsEntity.setLocSeq(temp.getLocSeq());
                detailsEntity.setPickLoc(temp.getPickLoc());
                detailsEntity.setLotProperty(temp.getLotProperty());
                detailsEntities.add(detailsEntity);
                confirmObnPickBean.setWaveId(JsonUtil.getString(mDataEntity, "WAVE_ID"));
                confirmObnPickBean.setDetails(detailsEntities);
                if (Integer.parseInt(mEdtQty.getText().toString().trim()) > Integer.parseInt(mTxtQty.getText().toString())) {
                    ToastUtils.showLong(PickWaveDetialsActivity.this, "拣货数量超出范围了");
                    return;
                }
                currentLocSeq = temp.getLocSeq();
                mPickDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(confirmObnPickBean));
            }
        });

    }

    private int currentLocSeq = -1;

    @Override
    public void commitOk() {
        //提交成功
        if (mTxtQty.getText().toString().trim().equals(mEdtQty.getText().toString().trim())) {
            //这条记录拣货完毕了
            boolean exist = false;//是否存在相同位置，并且拣货数量>0的货品
            datas.get(current).setWaitPickQty(0);
            finish++;
            initBottom();
            mEdtQty.setText("");
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getPickLoc().equalsIgnoreCase(currentLoc) && datas.get(i).getWaitPickQty() > 0) {
                    exist = true;
                    current = i;
                    setCurrent(current);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("WaveId", JsonUtil.getString(mDataEntity, "WAVE_ID"));
                        jsonObject.put("PickLoc", mEdtLoc.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getOrders(jsonObject.toString(), false);
                    break;
                }

            }
            if (exist) {
                return;
            }
            mEdtSkuname.setText("");
            mEdtSkuid.setText("");
            mEdtLoc.setText("");
            mEdtLoc.requestFocus();
            //判断吓一跳数据
            int count = 0;
            boolean exist2 = false;//是否存在拣货数量不为0的下一个拣货货位
            while (count <= datas.size()) {
                current++;
                if (current >= datas.size()) {
                    current = 0;
                }
                if (datas.get(current).getWaitPickQty() > 0) {
                    exist2 = true;
                    break;
                }
                count++;
            }
            if (current >= datas.size()) {
                current = 0;
            }
            if (exist2) {
                setCurrent(current);
            } else {
                setCurrent(0);
            }
        } else {
            datas.get(current).setWaitPickQty(Integer.parseInt(mTxtQty.getText().toString().trim()) - Integer.parseInt(mEdtQty.getText().toString().trim()));
            mTxtQty.setText(Integer.parseInt(mTxtQty.getText().toString().trim()) - Integer.parseInt(mEdtQty.getText().toString().trim()) + "");
            mEdtQty.setText("");
            mEdtQty.requestFocus();
        }
    }

    @Override
    public void getOrderFailure() {
        mEdtLoc.setText("");
        mEdtSkuid.setText("");
        mEdtSkuname.setText("");
        mEdtQty.setText("");
        mEdtLoc.requestFocus();
        if (isFirst) {
            mTxtBottom.setText("共计" + "xx" + "条/待处理" + "xx" + "条/已完成" + "xx" + "条");
        }
    }

    private void getOrders(String params, boolean isFirst) {
        this.isFirst = isFirst;
        mPickDetialsPresenter.getOrders(params);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
    }

    @Override
    public void setList(ArrayList<PickWaveDetialsBean.DataEntity> data) {
        if (data == null || data.size() <= 0) {
            ToastUtils.showLong(this, "此单没有明细");
            return;
        }
        if (isFirst) {
            datas = data;
            all = datas.size();
            for (PickWaveDetialsBean.DataEntity temp : datas) {
                if (temp.getWaitPickQty() <= 0) {
                    finish++;
                }
            }
            initBottom();
            int temp = -1;
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getWaitPickQty() > 0) {
                    temp = i;
                    break;
                }
            }
            if (temp != -1) {
                current = temp;
                setCurrent(current);
                showRemark(temp);
            } else {
                current = 0;
                setCurrent(current);
                showRemark(0);
            }
            Log.e("current", current + "");
        } else {
            boolean exist = false;//是否存相同
            String skuName = "";
            int count = -1;
            for (int i = 0; i < data.size(); i++) {//我自己都不知道什么意思了
                if (data.get(i).getSkuId().equalsIgnoreCase(currentSkuid) && data.get(i).getWaitPickQty() > 0) {
                    exist = true;
                    temp = data.get(i);
                    break;
                }
            }
            if (!exist) {
                temp = data.get(0);
            }
            skuName = temp.getSkuName();
            count = temp.getWaitPickQty();
            showInfo(temp);
            //并且得到操作的postion
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getWaitPickQty() == count && datas.get(i).getPickLoc().equalsIgnoreCase(temp.getPickLoc()) && datas.get(i).getSkuId().equalsIgnoreCase(temp.getSkuId()) && datas.get(i).getSkuName().equalsIgnoreCase(skuName)) {
                    current = i;
                    setCurrent(current);
                    break;
                }
            }
            showRemark(current);
            Log.e("current", current + "");
        }
    }

    private void showRemark(int postion) {
        mRemarkAdapter = new RemarkWaveAdapter2(this);
        ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> temp = datas.get(postion).getLotProperty();
        mRlRemark.setAdapter(mRemarkAdapter);
        reMarks.clear();
        for (PickWaveDetialsBean.DataEntity.LotPropertyEntity lotPropertyEntity : temp) {
            if ("Y".equalsIgnoreCase(lotPropertyEntity.getRfVisible())) {
                reMarks.add(lotPropertyEntity);
            }
        }
        mRemarkAdapter.setList(reMarks);
    }

    private void showInfo(PickWaveDetialsBean.DataEntity dataEntity) {
        mEdtLoc.setText(dataEntity.getPickLoc());
        mEdtSkuid.setText(dataEntity.getSkuId());
        mEdtSkuname.setText(dataEntity.getSkuName());
        mEdtQty.setText("");
        mEdtQty.requestFocus();
        currentLoc = dataEntity.getPickLoc();
    }

    private void setCurrent(int postion) {
        mTxtLoc.setText(datas.get(postion).getPickLoc());
        mTxtSkuid.setText(datas.get(postion).getSkuId());
        mTxtQty.setText(datas.get(postion).getWaitPickQty() + "");
        currentSkuid = datas.get(postion).getSkuId();
    }

    int all = 0;
    int finish = 0;

    private void initBottom() {
        if (all == finish) {
            ToastUtils.showLong(this, "全部拣货完成");
            if (!isFirst) {
                PickWaveDetialsActivity.this.finish();
            }
        } else if (finish > all) {
            finish = all;
            mTxtBottom.setText("共计" + all + "条/待处理" + (all - finish) + "条/已完成" + finish + "条");

        } else {
            mTxtBottom.setText("共计" + all + "条/待处理" + (all - finish) + "条/已完成" + finish + "条");
        }
    }
}
