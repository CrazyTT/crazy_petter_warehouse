package com.crazy.petter.warehouse.app.main.activitys.in;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.TrayPutAwayDetialsAdapter;
import com.crazy.petter.warehouse.app.main.beans.GoodsPutAwayBean;
import com.crazy.petter.warehouse.app.main.beans.LocBean;
import com.crazy.petter.warehouse.app.main.beans.PutAwayBean;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
import com.crazy.petter.warehouse.app.main.presenters.PutAwayDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PutAwayDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrayPutAwayDetialsActivity extends BaseActivity implements PutAwayDetialsView {
    ScanStoreageBean.DataEntity mDataEntity;
    @Bind(R.id.edt_lpn)
    EditText mEdtLpn;
    @Bind(R.id.edt_loc)
    EditText mEdtLoc;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    TrayPutAwayDetialsAdapter scanOrderAdapter;
    PutAwayDetialsPresenter mPutAwayDetialsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tray_put_away_detials);
        ButterKnife.bind(this);
        mPutAwayDetialsPresenter = new PutAwayDetialsPresenter(this, this, "mPutAwayDetialsPresenter");
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanStoreageBean.DataEntity.class);
        initView();
    }

    private void initView() {
        scanOrderAdapter = new TrayPutAwayDetialsAdapter(this, new TrayPutAwayDetialsAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
                jump(postion);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mOrderList.setAdapter(scanOrderAdapter);
        InputMethodManager imm = (InputMethodManager) mEdtLpn.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEdtLpn.getApplicationWindowToken(), 0);
        }
        mEdtLpn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    getDetials();
                    return true;
                }
                return false;
            }
        });
        mEdtLoc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    checkLoc();
                    return true;
                }
                return false;
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdtLpn.getText().toString().trim())) {
                    ToastUtils.showLong(TrayPutAwayDetialsActivity.this, "请先扫描托盘号");
                    return;
                }
                if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                    ToastUtils.showLong(TrayPutAwayDetialsActivity.this, "请扫描上架货位");
                    return;
                }
                if (datas != null && datas.size() > 0) {
                    PutAwayBean receiptBean = new PutAwayBean();
                    receiptBean.setInboundId(mDataEntity.getInboundId());
                    ArrayList<PutAwayBean.DetailsEntity> entities = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        PutAwayBean.DetailsEntity detailsEntity = new PutAwayBean.DetailsEntity();
                        detailsEntity.setSeqNo(datas.get(i).getSeqNo());
                        detailsEntity.setSkuId(datas.get(i).getSkuId());
                        detailsEntity.setSkuName(datas.get(i).getSkuName());
                        detailsEntity.setQty(datas.get(i).getQty());
                        detailsEntity.setIbnReceiveInc(datas.get(i).getIbnReceiveInc());
                        detailsEntity.setLpnNo(mEdtLpn.getText().toString().trim());
                        detailsEntity.setToLoc(mEdtLoc.getText().toString().trim());
                        entities.add(detailsEntity);
                    }
                    receiptBean.setDetails(entities);
                    mPutAwayDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(receiptBean));
                } else {
                    ToastUtils.showLong(TrayPutAwayDetialsActivity.this, "没有明细，请重新扫描");
                }
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtLoc, false);
            setShowSoftInputOnFocus.invoke(mEdtLpn, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkLoc() {
        if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
            ToastUtils.showLong(this, "上架货位不能为空");
            new Handler().postDelayed(new Thread(new Runnable() {
                @Override
                public void run() {
                    mEdtLoc.requestFocus();
                }
            }), 300);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InboundId", mDataEntity.getInboundId());
            jsonObject.put("Loc", mEdtLoc.getText().toString().trim());
            jsonObject.put("ZoneId", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPutAwayDetialsPresenter.checkLoc(jsonObject.toString());
    }

    private void getDetials() {
        if (TextUtils.isEmpty(mEdtLpn.getText().toString().trim())) {
            ToastUtils.showLong(this, "托盘号不能为空");
            new Handler().postDelayed(new Thread(new Runnable() {
                @Override
                public void run() {
                    mEdtLpn.requestFocus();
                }
            }), 300);
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InboundId", mDataEntity.getInboundId());
            jsonObject.put("LpnNo", mEdtLpn.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPutAwayDetialsPresenter.getOrders(jsonObject.toString());
    }

    private void jump(int postion) {

    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
    }

    ArrayList<GoodsPutAwayBean.DataEntity> datas;

    @Override
    public void showGoods(ArrayList<GoodsPutAwayBean.DataEntity> data) {
        datas = data;
        scanOrderAdapter.setList(datas);
    }

    @Override
    public void commitOK() {
        ToastUtils.showLong(this, "收货成功");
        mEdtLpn.setText("");
        mEdtLpn.requestFocus();
        mEdtLoc.setText("");
        datas = new ArrayList<>();
        scanOrderAdapter.setList(datas);
    }

    LocBean.DataEntity locEntity;

    @Override
    public void showLoc(LocBean.DataEntity dataEntity) {
        locEntity = dataEntity;
        ToastUtils.showLong(this, "货位可用");
    }

    @Override
    public void checkLocFailure() {
        ToastUtils.showLong(this, "该货位无效");
        mEdtLoc.setText("");
        mEdtLoc.requestFocus();
    }

    @Override
    public void getorderFailure() {
        mEdtLoc.setText("");
        mEdtLpn.setText("");
        datas = new ArrayList<>();
        scanOrderAdapter.setList(datas);
        mEdtLpn.requestFocus();
    }

}
