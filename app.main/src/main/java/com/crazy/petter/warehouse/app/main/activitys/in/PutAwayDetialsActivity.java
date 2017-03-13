package com.crazy.petter.warehouse.app.main.activitys.in;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.entity.TitleBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.OrderSelectAdapter;
import com.crazy.petter.warehouse.app.main.beans.LocBean;
import com.crazy.petter.warehouse.app.main.beans.PutAwayBean;
import com.crazy.petter.warehouse.app.main.presenters.PutAwayDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PutAwayDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PutAwayDetialsActivity extends BaseActivity implements PutAwayDetialsView {
    JSONObject mDataEntity;
    @Bind(R.id.edt_skuid)
    EditText mEdtSkuid;
    @Bind(R.id.edt_skuqty)
    EditText mEdtSkuqty;
    @Bind(R.id.edt_loc)
    EditText mEdtLoc;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    @Bind(R.id.qty)
    TextView mQty;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    PutAwayDetialsPresenter mPutAwayDetialsPresenter;
    private String barCode = "";
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;
    OrderSelectAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away_detials);
        ButterKnife.bind(this);
        mPutAwayDetialsPresenter = new PutAwayDetialsPresenter(this, this, "mPutAwayDetialsPresenter");
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mOrderAdapter = new OrderSelectAdapter(this, new OrderSelectAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }

            @Override
            public void change() {

            }
        }, new ArrayList<OrderBean.CaptionEntity>());
        InputMethodManager imm = (InputMethodManager) mEdtSkuid.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEdtSkuid.getApplicationWindowToken(), 0);
        }
        mEdtSkuid.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    barCode = mEdtSkuid.getText().toString().trim();
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
                if (datas != null && datas.size() > 0) {
                    PutAwayBean receiptBean = new PutAwayBean();
                    receiptBean.setInboundId(JsonUtil.getString(mDataEntity, "IBN_ID"));
                    ArrayList<PutAwayBean.DetailsEntity> entities = new ArrayList<>();
                    HashMap<Integer, Boolean> choiceItem = mOrderAdapter.getIsSelected();
                    Iterator<Integer> iter = choiceItem.keySet().iterator();
                    while (iter.hasNext()) {
                        int key = iter.next();
                        Boolean val = choiceItem.get(key);
                        if (val) {
                            PutAwayBean.DetailsEntity detailsEntity = new PutAwayBean.DetailsEntity();
                            detailsEntity.setSeqNo(JsonUtil.getInt(datas.get(key), "IBN_SEQ"));
                            detailsEntity.setSkuId(JsonUtil.getString(datas.get(key), "SKU_ID"));
                            detailsEntity.setSkuName(JsonUtil.getString(datas.get(key), "SKU_NAME"));
                            if (TextUtils.isEmpty(mEdtSkuqty.getText().toString().trim())) {
                                ToastUtils.showLong(PutAwayDetialsActivity.this, "请输入上架数量");
                                return;
                            }
                            if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                                ToastUtils.showLong(PutAwayDetialsActivity.this, "请输入上架货位");
                                return;
                            }
                            detailsEntity.setQty(Integer.parseInt(mEdtSkuqty.getText().toString().trim()));
                            detailsEntity.setIbnReceiveInc(JsonUtil.getInt(datas.get(key), "IBN_RECEIVE_INC"));//需要确定
                            detailsEntity.setLpnNo("");
                            detailsEntity.setToLoc(mEdtLoc.getText().toString().trim());
                            entities.add(detailsEntity);
                        }
                    }
                    receiptBean.setDetails(entities);
                    mPutAwayDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(receiptBean));
                } else {
                    ToastUtils.showLong(PutAwayDetialsActivity.this, "没有明细，无法上架");
                }
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtSkuqty, false);
            setShowSoftInputOnFocus.invoke(mEdtLoc, false);
            setShowSoftInputOnFocus.invoke(mEdtSkuid, false);
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
            jsonObject.put("InboundId", JsonUtil.getString(mDataEntity, "IBN_ID"));
            jsonObject.put("Loc", mEdtLoc.getText().toString().trim());
            jsonObject.put("ZoneId", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPutAwayDetialsPresenter.checkLoc(jsonObject.toString());
    }

    private void showQTY() {
        HashMap<Integer, Boolean> choiceItem = mOrderAdapter.getIsSelected();
        Iterator<Integer> iter = choiceItem.keySet().iterator();
        int count = 0;
        while (iter.hasNext()) {
            int key = iter.next();
            Boolean val = choiceItem.get(key);
            if (val) {
                count += JsonUtil.getInt(datas.get(key), "WAIT_PTED_QTY");
            }
        }
        final String finalCount = count + "";
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mQty.setText(finalCount);
            }
        }), 300);
    }

    private void getDetials() {
        if (TextUtils.isEmpty(mEdtSkuid.getText().toString().trim())) {
            ToastUtils.showLong(this, "货品代码不能为空");
            new Handler().postDelayed(new Thread(new Runnable() {
                @Override
                public void run() {
                    mEdtSkuid.requestFocus();
                }
            }), 300);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InboundId", JsonUtil.getString(mDataEntity, "IBN_ID"));
            jsonObject.put("BarCode", barCode);
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

    ArrayList<JSONObject> datas;

    @Override
    public void showGoods(String data) {
        TitleBean titleBean = JsonUtil.getTitle(data);
        mLlTitle.removeAllViews();
        for (int i = 0; i < titleBean.getCaptionEntities().size(); i++) {
            if (titleBean.getCaptionEntities().get(i).getVISIBLE() != null && titleBean.getCaptionEntities().get(i).getVISIBLE().equalsIgnoreCase("N")) {
                continue;
            }
            TextView temp = (TextView) LayoutInflater.from(this).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setGravity(Gravity.CENTER);
            temp.setText(titleBean.getCaptionEntities().get(i).getCAPTION());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLlTitle.addView(temp, layoutParams);
        }
        mOrderAdapter = new OrderSelectAdapter(this, new OrderSelectAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
                jump(postion);
            }

            @Override
            public void change() {
                showQTY();
            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
        datas = titleBean.getOrders();
        //显示代码
        if (datas.size() > 0) {
            mEdtSkuid.setText(JsonUtil.getString(datas.get(0), "SKU_ID"));
        }
    }

    @Override
    public void commitOK() {
        ToastUtils.showLong(this, "上架成功");
        mOrderAdapter.initIsSelected();
        getDetials();
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
        datas = new ArrayList<>();
        mOrderAdapter.setList(datas);
        mEdtSkuid.requestFocus();
        mEdtSkuid.setText("");
        mEdtSkuqty.setText("");
        mEdtLoc.setText("");
        mQty.setText("0");
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
