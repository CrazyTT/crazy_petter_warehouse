package com.crazy.petter.warehouse.app.main.activitys.in;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.PutAwayDetialsAdapter;
import com.crazy.petter.warehouse.app.main.beans.GoodsPutAwayBean;
import com.crazy.petter.warehouse.app.main.beans.PutAwayBean;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
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
    ScanStoreageBean.DataEntity mDataEntity;
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
    PutAwayDetialsAdapter scanOrderAdapter;
    PutAwayDetialsPresenter mPutAwayDetialsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away_detials);
        ButterKnife.bind(this);
        mPutAwayDetialsPresenter = new PutAwayDetialsPresenter(this, this, "mPutAwayDetialsPresenter");
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanStoreageBean.DataEntity.class);
        initView();
    }

    private void initView() {
        scanOrderAdapter = new PutAwayDetialsAdapter(this, new PutAwayDetialsAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
                jump(postion);
            }

            @Override
            public void change() {
                showQTY();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mOrderList.setAdapter(scanOrderAdapter);
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
                    getDetials();
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
                    receiptBean.setInboundId(mDataEntity.getInboundId());
                    ArrayList<PutAwayBean.DetailsEntity> entities = new ArrayList<>();
                    HashMap<Integer, Boolean> choiceItem = scanOrderAdapter.getIsSelected();
                    Iterator<Integer> iter = choiceItem.keySet().iterator();
                    while (iter.hasNext()) {
                        int key = iter.next();
                        Boolean val = choiceItem.get(key);
                        if (val) {
                            PutAwayBean.DetailsEntity detailsEntity = new PutAwayBean.DetailsEntity();
                            detailsEntity.setSeqNo(datas.get(key).getSeqNo());
                            detailsEntity.setSkuId(datas.get(key).getSkuId());
                            detailsEntity.setSkuName(datas.get(key).getSkuName());
                            if (TextUtils.isEmpty(mEdtSkuqty.getText().toString().trim())) {
                                ToastUtils.showShort(PutAwayDetialsActivity.this, "请输入上架数量");
                                return;
                            }
                            if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                                ToastUtils.showShort(PutAwayDetialsActivity.this, "请输入上架货位");
                                return;
                            }
                            detailsEntity.setQty(Integer.parseInt(mEdtSkuqty.getText().toString().trim()));
                            detailsEntity.setIbnReceiveInc(datas.get(key).getIbnReceiveInc());
                            detailsEntity.setLpnNo("");
                            detailsEntity.setToLoc(mEdtLoc.getText().toString().trim());
                            entities.add(detailsEntity);
                        }
                    }
                    receiptBean.setDetails(entities);
                    mPutAwayDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(receiptBean));
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

    private void showQTY() {
        HashMap<Integer, Boolean> choiceItem = scanOrderAdapter.getIsSelected();
        Iterator<Integer> iter = choiceItem.keySet().iterator();
        int count = 0;
        while (iter.hasNext()) {
            int key = iter.next();
            Boolean val = choiceItem.get(key);
            if (val) {
                count += datas.get(key).getQty();
            }
        }
        mQty.setText(count + "");

    }

    private void getDetials() {
        if (TextUtils.isEmpty(mEdtSkuid.getText().toString().trim())) {
            ToastUtils.showShort(this, "货品代码不能为空");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InboundId", mDataEntity.getInboundId());
            jsonObject.put("SkuId", mEdtSkuid.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPutAwayDetialsPresenter.getOrders(jsonObject.toString());
    }

    private void jump(int postion) {

    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    ArrayList<GoodsPutAwayBean.DataEntity> datas;

    @Override
    public void showGoods(ArrayList<GoodsPutAwayBean.DataEntity> data) {
        datas = data;
        scanOrderAdapter.setList(datas);
    }

    @Override
    public void commitOK() {
        scanOrderAdapter.initIsSelected();
        getDetials();
    }
}
