package com.crazy.petter.warehouse.app.main.activitys.out;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.crazy.petter.warehouse.app.main.adapters.OrderAdapter;
import com.crazy.petter.warehouse.app.main.beans.TraySendCommitBean;
import com.crazy.petter.warehouse.app.main.presenters.TraySendDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.TraySendDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TraySendDetialsActivity extends BaseActivity implements TraySendDetialsView {

    @Bind(R.id.edt_order_num)
    EditText mEdtOrderNum;
    @Bind(R.id.btn_query)
    ButtonAutoBg mBtnQuery;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    @Bind(R.id.btn_commit)
    Button mBtnCommit;
    TraySendDetialsPresenter mTraySendDetialsPresenter;
    JSONObject mDataEntity;
    @Bind(R.id.btn_cancle)
    Button mBtnCancle;
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;
    OrderAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tray_send_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
        mTraySendDetialsPresenter = new TraySendDetialsPresenter(this, this, "mTraySendDetialsPresenter");
        initViews();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mOrderAdapter = new OrderAdapter(this, new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }
        }, new ArrayList<OrderBean.CaptionEntity>());
        InputMethodManager imm = (InputMethodManager) mEdtOrderNum.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEdtOrderNum.getApplicationWindowToken(), 0);
        }
        mEdtOrderNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (TextUtils.isEmpty(mEdtOrderNum.getText().toString().trim())) {
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                showTips("请扫描托盘号");
                            }
                        }), 300);
                        return true;
                    }
                    getDetials();
                    return true;
                }
                return false;
            }
        });
        mBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                if (TextUtils.isEmpty(mEdtOrderNum.getText().toString().trim())) {
                    new Handler().postDelayed(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            showTips("请扫描托盘号");
                        }
                    }), 300);
                    return;
                }
                getDetials();
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认发货
                if (TextUtils.isEmpty(mEdtOrderNum.getText().toString().trim())) {
                    ToastUtils.showLong(TraySendDetialsActivity.this, "请输入托盘号");
                    return;
                }
                if (datas.size() <= 0) {
                    ToastUtils.showLong(TraySendDetialsActivity.this, "没有明细，无法发货");
                    return;
                }
                TraySendCommitBean traySendCommitBean = new TraySendCommitBean();
                traySendCommitBean.setOutboundId(JsonUtil.getString(mDataEntity, "OBN_ID"));
                traySendCommitBean.setLpnNo(mEdtOrderNum.getText().toString().trim());
                ArrayList<TraySendCommitBean.DetailsEntity> detailsEntities = new ArrayList<>();
                for (JSONObject data : datas) {
                    TraySendCommitBean.DetailsEntity temp = new TraySendCommitBean.DetailsEntity();

                    temp.setOutboundId(JsonUtil.getString(data, "OBN_ID"));
                    temp.setExtLot(JsonUtil.getString(data, "EXT_LOT"));
                    temp.setObnPickInc(JsonUtil.getInt(data, "OBN_PICK_INC"));
                    temp.setSeqNo(JsonUtil.getInt(data, "OBN_SEQ"));
                    temp.setShipQty(JsonUtil.getInt(data, "PICK_QTY"));
                    temp.setSkuId(JsonUtil.getString(data, "SKU_ID"));
                    temp.setSkuName(JsonUtil.getString(data, "SKU_NAME"));
                    temp.setSkuProperty(JsonUtil.getString(data, "SKU_PROPERTY"));
                    detailsEntities.add(temp);
                }
                traySendCommitBean.setDetails(detailsEntities);
                mTraySendDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(traySendCommitBean));
            }
        });
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消按钮的事件
                TraySendDetialsActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public void commitOK() {
        ToastUtils.showLong(this, "发货成功");
        datas = new ArrayList<>();
        mOrderAdapter.setList(datas);
        mEdtOrderNum.setText("");
        mEdtOrderNum.requestFocus();
    }

    @Override
    public void getOrderFailure() {
        datas = new ArrayList<>();
        mOrderAdapter.setList(datas);
    }

    private void getDetials() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
            jsonObject.put("LpnNo", mEdtOrderNum.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTraySendDetialsPresenter.getOrder(jsonObject.toString());
    }

    @Override
    public void showTips(String s) {
        mEdtOrderNum.requestFocus();
        ToastUtils.showLong(this, s);
    }

    ArrayList<JSONObject> datas = new ArrayList<>();

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
        mOrderAdapter = new OrderAdapter(this, new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
        datas = titleBean.getOrders();
    }
}