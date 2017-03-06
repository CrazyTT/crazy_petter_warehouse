package com.crazy.petter.warehouse.app.main.activitys.in;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.crazy.petter.warehouse.app.main.beans.PropertyBean;
import com.crazy.petter.warehouse.app.main.beans.ReceiptBean;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
import com.crazy.petter.warehouse.app.main.presenters.ReceiptPresenter;
import com.crazy.petter.warehouse.app.main.views.ReceiptView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReceiptActivity extends BaseActivity implements ReceiptView {
    @Bind(R.id.txt_orderNum)
    EditText mTxtOrderNum;
    @Bind(R.id.edt_good_bar)
    EditText mEdtGoodBar;
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

    private String[] SkuPropertyNames = {};
    private String[] SkuPropertyCodes = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        ButterKnife.bind(this);
        mReceiptPresenter = new ReceiptPresenter(this, this, "ReceiptActivity");
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanStoreageBean.DataEntity.class);
        mTxtOrderNum.setText(mDataEntity.getInboundId());
        mEdtGoodBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("InboundId", mDataEntity.getInboundId());
                        jsonObject.put("BarCode", mEdtGoodBar.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(mEdtGoodBar.getText().toString().trim())) {
                        mReceiptPresenter.getDetials(jsonObject.toString());
                    } else {
                        ToastUtils.showShort(ReceiptActivity.this, "条码不能为空");
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mEdtGoodBar.requestFocus();
                            }
                        }), 300);
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
                if (goodsBean == null) {
                    ToastUtils.showShort(ReceiptActivity.this, "请先扫描货品条码");
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                ReceiptBean receiptBean = new ReceiptBean();
                receiptBean.setInboundId(mTxtOrderNum.getText().toString().trim());
                receiptBean.setReceiptDate(str);
                ArrayList<ReceiptBean.DetailsEntity> entities = new ArrayList<>();
                ReceiptBean.DetailsEntity detailsEntity = new ReceiptBean.DetailsEntity();
                detailsEntity.setExtLot(goodsBean.getData().get(0).getExtLot());
                detailsEntity.setLpnNo(mEdtLpn.getText().toString().trim());
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                format.setLenient(false);
                boolean dateflag = true;
                Date date = null;
                try {
                    date = format.parse(mEdtDate.getText().toString().trim());
                } catch (ParseException e) {
                    dateflag = false;
                } finally {
                    System.out.println("日期是否满足要求" + dateflag);
                }
                if (!dateflag) {
                    ToastUtils.showShort(ReceiptActivity.this, "请输入正确的日期");
                    return;
                }
                if (!isP) {
                    detailsEntity.setExpiredDate(formatter.format(date));
                    detailsEntity.setProduceDate("");
                } else {
                    detailsEntity.setProduceDate(formatter.format(date));
                    detailsEntity.setExpiredDate("");
                }
                if (TextUtils.isEmpty(mEdtNum.getText().toString().trim())) {
                    ToastUtils.showShort(ReceiptActivity.this, "请输入数量");
                    return;
                }
                detailsEntity.setReceiptQty(Integer.parseInt(mEdtNum.getText().toString().trim()));
                detailsEntity.setSeqNo(goodsBean.getData().get(0).getSeqNo());
                detailsEntity.setSkuId(goodsBean.getData().get(0).getSkuId());
                detailsEntity.setSkuName(goodsBean.getData().get(0).getSkuName());
                detailsEntity.setSkuProperty(SkuProperty);
                entities.add(detailsEntity);
                receiptBean.setDetails(entities);
                mReceiptPresenter.receipt(JsonFormatter.getInstance().object2Json(receiptBean));
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtDate, false);
            setShowSoftInputOnFocus.invoke(mEdtGoodBar, false);
            setShowSoftInputOnFocus.invoke(mTxtOrderNum, false);
            setShowSoftInputOnFocus.invoke(mEdtNum, false);
            setShowSoftInputOnFocus.invoke(mEdtGoodName, false);
            setShowSoftInputOnFocus.invoke(mEdtLpn, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEdtGoodBar.requestFocus();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SkuPropertyCode", "");
            jsonObject.put("SkuPropertyDesc", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mReceiptPresenter.getProperty(jsonObject.toString());
//        mEdtDate.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mEdtDate.setSelection(mEdtDate.getText().toString().trim().length());
//                }
//            }
//        });
    }

    String SkuProperty = "";

    class SelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            SkuProperty = SkuPropertyCodes[arg2];
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
        mEdtGoodBar.setText("");
        mEdtGoodName.setText("");
        mEdtLpn.setText("");
        Calendar c = Calendar.getInstance();
        mEdtDate.setText(c.get(Calendar.YEAR) + "");
        mEdtNum.setText("");
        mEdtGoodBar.requestFocus();
        goodsBean = null;
    }

    @Override
    public void showProperty(ArrayList<PropertyBean.DataEntity> data) {
        SkuPropertyNames = new String[data.size()];
        SkuPropertyCodes = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            SkuPropertyNames[i] = data.get(i).getSkuPropertyDesc();
            SkuPropertyCodes[i] = data.get(i).getSkuPropertyCode();
        }
        mSpGoodProperty.setOnItemSelectedListener(new SelectedListener());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ReceiptActivity.this, R.layout.item_spinner_work_query_center, SkuPropertyNames);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner_work_query_center);
        mSpGoodProperty.setAdapter(arrayAdapter);
    }

    @Override
    public void showNoGoods() {
        mEdtGoodBar.setText("");
        mEdtGoodName.setText("");
        mEdtLpn.setText("");
        Calendar c = Calendar.getInstance();
        mEdtDate.setText(c.get(Calendar.YEAR) + "");
        mEdtNum.setText("");
        mSpGoodProperty.setSelection(0);
        mEdtGoodBar.requestFocus();
        goodsBean = null;
    }

    boolean isP = true;

    private void init() {
        mEdtGoodBar.setText(goodsBean.getData().get(0).getSkuId());
        mEdtGoodName.setText(goodsBean.getData().get(0).getSkuName());
        if ("E".equalsIgnoreCase(goodsBean.getData().get(0).getShelfLifeCtrlType())) {
            mTxtDate.setText("失效日期");
            isP = false;
        } else {
            mTxtDate.setText("生产日期");
            isP = true;
        }
        mEdtLpn.setText(goodsBean.getData().get(0).getLPN());
        mEdtNum.requestFocus();
    }
}
