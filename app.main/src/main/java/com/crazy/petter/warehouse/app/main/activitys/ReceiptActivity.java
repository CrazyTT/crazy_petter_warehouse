package com.crazy.petter.warehouse.app.main.activitys;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.beans.ReceiptBean;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
import com.crazy.petter.warehouse.app.main.presenters.ReceiptPresenter;
import com.crazy.petter.warehouse.app.main.views.ReceiptView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReceiptActivity extends BaseActivity implements ReceiptView {
    @Bind(R.id.txt_orderNum)
    EditText mTxtOrderNum;
    @Bind(R.id.edt_good_id)
    EditText mEdtGoodId;
    @Bind(R.id.edt_good_name)
    EditText mEdtGoodName;
    @Bind(R.id.edt_num)
    EditText mEdtNum;
    @Bind(R.id.edt_lpn)
    EditText mEdtLpn;
    @Bind(R.id.txt_date)
    TextView mTxtDate;
    @Bind(R.id.edt_date)
    EditText mEdtDate;
    @Bind(R.id.sp_good_Property)
    Spinner mSpGoodProperty;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    private ScanStoreageBean.DataEntity mDataEntity;
    ReceiptPresenter mReceiptPresenter;
    @Bind(R.id.activity_receipt)
    LinearLayout mActivityReceipt;

    private String[] SkuPropertyNames = {"GOOD", "NOTGOOD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        ButterKnife.bind(this);
        mReceiptPresenter = new ReceiptPresenter(this, this, "ReceiptActivity");
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanStoreageBean.DataEntity.class);
        mTxtOrderNum.setText(mDataEntity.getInboundId());
        mEdtGoodId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("InboundId", mDataEntity.getInboundId());
                        jsonObject.put("SkuId", mEdtGoodId.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(mEdtGoodId.getText().toString().trim())) {
                        mReceiptPresenter.getDetials(jsonObject.toString());
                    }

                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
        Calendar c = Calendar.getInstance();
        mEdtDate.setText(c.get(Calendar.YEAR) + "");
        mEdtGoodId.addTextChangedListener(textWatcher);
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                "InboundId": "IB160612000001",
//                        "ReceiptDate": "2017-02-11",
//                        "details": [
//                {
//                    "SeqNo": "2",
//                        "SkuId": "007233",
//                        "SkuName": "RSTPQ200-OPTIMA",
//                        "ReceiptQty": "10",
//                        "SkuProperty": "good",
//                        "LpnNo": "1123",
//                        "ExtLot": "",
//                        "ProduceDate": "20170211",
//                        "ExpiredDate": "20170221"
//                }
//                ]
                //收货
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                ReceiptBean receiptBean = new ReceiptBean();
                receiptBean.setInboundId(mTxtOrderNum.getText().toString().trim());
                receiptBean.setReceiptDate(str);
                ArrayList<ReceiptBean.DetailsEntity> entities = new ArrayList<>();
                ReceiptBean.DetailsEntity detailsEntity = new ReceiptBean.DetailsEntity();
                detailsEntity.setExtLot(goodsBean.getData().get(0).getExtLot());
                detailsEntity.setLpnNo(goodsBean.getData().get(0).getLPN());
                detailsEntity.setExpiredDate(mEdtDate.getText().toString().trim());
                detailsEntity.setProduceDate(mEdtDate.getText().toString().trim());
                detailsEntity.setReceiptQty(mEdtNum.getText().toString().trim());
                detailsEntity.setSeqNo(goodsBean.getData().get(0).getSeqNo());
                detailsEntity.setSkuId(goodsBean.getData().get(0).getSkuId());
                detailsEntity.setSkuName(goodsBean.getData().get(0).getSkuName());
                detailsEntity.setSkuProperty(SkuProperty);
                entities.add(detailsEntity);
                receiptBean.setDetails(entities);
                mReceiptPresenter.receipt(JsonFormatter.getInstance().object2Json(receiptBean));
            }
        });
        mSpGoodProperty.setOnItemSelectedListener(new SelectedListener());
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<>(ReceiptActivity.this, R.layout.item_spinner_work_left, SkuPropertyNames);
        arrayAdapter5.setDropDownViewResource(R.layout.item_spinner_work_query_center);
        mSpGoodProperty.setAdapter(arrayAdapter5);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtDate, false);
            setShowSoftInputOnFocus.invoke(mEdtGoodId, false);
            setShowSoftInputOnFocus.invoke(mTxtOrderNum, false);
            setShowSoftInputOnFocus.invoke(mEdtNum, false);
            setShowSoftInputOnFocus.invoke(mEdtGoodName, false);
            setShowSoftInputOnFocus.invoke(mEdtLpn, false);


        } catch (Exception e) {
            e.printStackTrace();
        }
        mEdtGoodId.requestFocus();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    String SkuProperty = "";

    class SelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            SkuProperty = SkuPropertyNames[arg2];
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    GoodsBean goodsBean;

    @Override
    public void showGoods(GoodsBean bean) {
        goodsBean = bean;
        init();
    }

    @Override
    public void receiptOK() {
        ToastUtils.showShort(this, "收货成功");
        mEdtGoodId.setText("");
        mEdtGoodName.setText("");
        Calendar c = Calendar.getInstance();
        mEdtDate.setText(c.get(Calendar.YEAR) + "");
        mEdtNum.setText("");
        mEdtGoodId.requestFocus();
    }

    private void init() {
        mEdtGoodName.setText(goodsBean.getData().get(0).getSkuName());
        if ("E".equalsIgnoreCase(goodsBean.getData().get(0).getShelfLifeCtrlType())) {
            mTxtDate.setText("失效日期");
        } else if ("P".equalsIgnoreCase(goodsBean.getData().get(0).getShelfLifeCtrlType())) {
            mTxtDate.setText("生产日期");
        }
    }
}
