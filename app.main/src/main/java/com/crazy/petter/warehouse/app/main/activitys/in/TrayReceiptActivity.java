package com.crazy.petter.warehouse.app.main.activitys.in;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.TratReciptAdapter;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.beans.ReceiptBean;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
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
    TratReciptAdapter scanOrderAdapter;
    ScanStoreageBean.DataEntity mDataEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tray_receipt);
        mTrayReceiptPresenter = new TrayReceiptPresenter(this, this, "TrayReceiptActivity");
        ButterKnife.bind(this);
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanStoreageBean.DataEntity.class);
        initView();
    }

    private void initView() {
        scanOrderAdapter = new TratReciptAdapter(this, new TratReciptAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mOrderList.setAdapter(scanOrderAdapter);
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
                    receiptBean.setInboundId(mDataEntity.getInboundId());
                    receiptBean.setReceiptDate(str);
                    ArrayList<ReceiptBean.DetailsEntity> entities = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        ReceiptBean.DetailsEntity detailsEntity = new ReceiptBean.DetailsEntity();
                        detailsEntity.setExtLot(datas.get(i).getExtLot());
                        detailsEntity.setLpnNo(datas.get(i).getLPN());
                        if (!TextUtils.isEmpty(datas.get(i).getShelfLifeCtrlType()) && "E".equals(datas.get(i).getShelfLifeCtrlType())) {
                        }
                        detailsEntity.setExpiredDate("");
                        detailsEntity.setProduceDate("");
                        detailsEntity.setReceiptQty(datas.get(i).getReceivedQty());
                        detailsEntity.setSeqNo(datas.get(i).getSeqNo());
                        detailsEntity.setSkuId(datas.get(i).getSkuId());
                        detailsEntity.setSkuName(datas.get(i).getSkuName());
                        detailsEntity.setSkuProperty(datas.get(i).getSkuProperty());
                        entities.add(detailsEntity);
                    }
                    receiptBean.setDetails(entities);
                    mTrayReceiptPresenter.receipt(JsonFormatter.getInstance().object2Json(receiptBean));
                }
            }
        });

    }

    private void getDetials() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InboundId", mDataEntity.getInboundId());
            jsonObject.put("LpnNo", mEdtOrderNum.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTrayReceiptPresenter.getOrder(jsonObject.toString());
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    ArrayList<GoodsBean.DataEntity> datas;

    @Override
    public void showGoods(ArrayList<GoodsBean.DataEntity> data) {
        datas = data;
        scanOrderAdapter.setList(datas);
        if (mCbDefault.isChecked()) {
            mBtnCommit.performClick();
        }
    }

    @Override
    public void receiptOK() {
        mEdtOrderNum.setText("");
        datas.clear();
        scanOrderAdapter.notifyDataSetChanged();
    }
}
