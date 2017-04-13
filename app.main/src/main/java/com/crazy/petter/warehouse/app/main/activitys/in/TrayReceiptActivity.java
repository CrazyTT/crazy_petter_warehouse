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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.crazy.petter.warehouse.app.main.beans.ReceiptBean;
import com.crazy.petter.warehouse.app.main.presenters.TrayReceiptPresenter;
import com.crazy.petter.warehouse.app.main.views.TrayReceiptView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrayReceiptActivity extends BaseActivity implements TrayReceiptView {
    @Bind(R.id.edt_order_num)
    EditText mEdtOrderNum;
    @Bind(R.id.btn_query)
    ButtonAutoBg mBtnQuery;
    @Bind(R.id.cb_default)
    CheckBox mCbDefault;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    @Bind(R.id.btn_commit)
    Button mBtnCommit;
    TrayReceiptPresenter mTrayReceiptPresenter;
    JSONObject mDataEntity;
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;
    OrderAdapter mOrderAdapter;
    @Bind(R.id.txt_orderNum)
    TextView mTxtOrderNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tray_receipt);
        mTrayReceiptPresenter = new TrayReceiptPresenter(this, this, "TrayReceiptActivity");
        ButterKnife.bind(this);
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
        initView();
    }

    private void initView() {
        mTxtOrderNum.setText(JsonUtil.getString(mDataEntity, "IBN_ID"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
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
                getDetials();
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datas != null && datas.size() > 0) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    ReceiptBean receiptBean = new ReceiptBean();
                    receiptBean.setInboundId(JsonUtil.getString(mDataEntity, "IBN_ID"));
                    receiptBean.setReceiptDate(str);
                    ArrayList<ReceiptBean.DetailsEntity> entities = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        ReceiptBean.DetailsEntity detailsEntity = new ReceiptBean.DetailsEntity();
                        detailsEntity.setExtLot(JsonUtil.getString(datas.get(i), "EXT_LOT"));
                        detailsEntity.setLpnNo(mEdtOrderNum.getText().toString().trim());
                        detailsEntity.setExpiredDate("");
                        detailsEntity.setProduceDate("");
                        detailsEntity.setReceiptQty(JsonUtil.getInt(datas.get(i), "WAIT_RECEIVE_QTY"));
                        detailsEntity.setSeqNo(JsonUtil.getString(datas.get(i), "IBN_SEQ"));
                        detailsEntity.setSkuId(JsonUtil.getString(datas.get(i), "SKU_ID"));
                        detailsEntity.setSkuName(JsonUtil.getString(datas.get(i), "SKU_NAME"));
                        detailsEntity.setSkuProperty(JsonUtil.getString(datas.get(i), "SKU_PROPERTY"));
                        entities.add(detailsEntity);
                    }
                    receiptBean.setDetails(entities);
                    mTrayReceiptPresenter.receipt(JsonFormatter.getInstance().object2Json(receiptBean));
                } else {
                    ToastUtils.showLong(TrayReceiptActivity.this, "没有明细，无法收货");
                }
            }
        });

    }

    private void getDetials() {
        if (TextUtils.isEmpty(mEdtOrderNum.getText().toString().trim())) {
            ToastUtils.showLong(this, "托盘号不能为空");
            new Handler().postDelayed(new Thread(new Runnable() {
                @Override
                public void run() {
                    mEdtOrderNum.requestFocus();
                }
            }), 300);
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InboundId", JsonUtil.getString(mDataEntity, "IBN_ID"));
            jsonObject.put("LpnNo", mEdtOrderNum.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTrayReceiptPresenter.getOrder(jsonObject.toString());
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
        mEdtOrderNum.setText("");
        mEdtOrderNum.requestFocus();
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
        mOrderAdapter = new OrderAdapter(this, new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
        datas = titleBean.getOrders();
        if (mCbDefault.isChecked()) {
            mBtnCommit.performClick();
        }
    }

    @Override
    public void receiptOK() {
        ToastUtils.showLong(this, "收货成功");
        mEdtOrderNum.setText("");
        mEdtOrderNum.requestFocus();
        datas.clear();
        mOrderAdapter.notifyDataSetChanged();
    }

    @Override
    public void failure() {
        ArrayList<JSONObject> temp = new ArrayList<>();
        mOrderAdapter.setList(temp);
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mEdtOrderNum.requestFocus();
            }
        }), 300);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            mBtnCommit.performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
