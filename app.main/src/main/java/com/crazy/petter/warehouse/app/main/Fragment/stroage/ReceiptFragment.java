package com.crazy.petter.warehouse.app.main.Fragment.stroage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.util.StringUtils;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.ChangeEvent;
import com.crazy.petter.warehouse.app.main.beans.GoodsBean;
import com.crazy.petter.warehouse.app.main.beans.PropertyBean;
import com.crazy.petter.warehouse.app.main.beans.ReceiptBean;
import com.crazy.petter.warehouse.app.main.presenters.ReceiptPresenter;
import com.crazy.petter.warehouse.app.main.views.ReceiptView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

/**
 * Created by liuliuchen on 2017/3/8.
 */

public class ReceiptFragment extends Fragment implements ReceiptView {
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
    ReceiptPresenter mReceiptPresenter;
    @Bind(R.id.activity_receipt)
    LinearLayout mActivityReceipt;
    SharedPreferencesUtil sp;
    private String[] SkuPropertyNames = {};
    private String[] SkuPropertyCodes = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_receipt, container, false);
        sp = new SharedPreferencesUtil(getActivity());
        mReceiptPresenter = new ReceiptPresenter(this, (BaseActivity) getActivity(), "ReceiptActivity");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEdtGoodBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("InboundId", sp.getString("num"));
                        jsonObject.put("BarCode", mEdtGoodBar.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(mEdtGoodBar.getText().toString().trim())) {
                        mReceiptPresenter.getDetials(jsonObject.toString());
                    } else {
                        ToastUtils.showLong(getActivity(), "条码不能为空");
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
                    ToastUtils.showLong(getActivity(), "请先扫描货品条码");
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                ReceiptBean receiptBean = new ReceiptBean();
                receiptBean.setInboundId(sp.getString("num"));
                receiptBean.setReceiptDate(str);
                ArrayList<ReceiptBean.DetailsEntity> entities = new ArrayList<>();
                ReceiptBean.DetailsEntity detailsEntity = new ReceiptBean.DetailsEntity();
                detailsEntity.setExtLot(goodsBean.getData().get(0).getExtLot());
                detailsEntity.setLpnNo(mEdtLpn.getText().toString().trim());
                if (isctrl) {
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
                        ToastUtils.showLong(getActivity(), "请输入正确的日期");
                        return;
                    }
                    if (!isP) {
                        detailsEntity.setExpiredDate(formatter.format(date));
                        detailsEntity.setProduceDate("");
                    } else {
                        detailsEntity.setProduceDate(formatter.format(date));
                        detailsEntity.setExpiredDate("");
                    }
                } else {
                    detailsEntity.setProduceDate("");
                    detailsEntity.setExpiredDate("");
                }
                if (TextUtils.isEmpty(mEdtNum.getText().toString().trim())) {
                    ToastUtils.showLong(getActivity(), "请输入数量");
                    return;
                }
                if (!StringUtils.isDouble(mEdtNum.getText().toString().trim())) {
                    ToastUtils.showLong(getActivity(), "请输入合法数量");
                    return;
                }
                detailsEntity.setReceiptQty(Double.parseDouble(mEdtNum.getText().toString().trim()));
                detailsEntity.setSeqNo(goodsBean.getData().get(0).getSeqNo());
                detailsEntity.setSkuId(goodsBean.getData().get(0).getSkuId());
                detailsEntity.setSkuName(goodsBean.getData().get(0).getSkuName());
                detailsEntity.setSkuProperty(SkuProperty);
                entities.add(detailsEntity);
                receiptBean.setDetails(entities);
                mReceiptPresenter.receipt(JsonFormatter.getInstance().object2Json(receiptBean));
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtDate, false);
            setShowSoftInputOnFocus.invoke(mEdtGoodBar, false);
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
        EventBus.getDefault().register(this);
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

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    GoodsBean goodsBean;

    @Override
    public void showGoods(GoodsBean bean) {
        goodsBean = bean;
        init();
    }

    @Override
    public void receiptOK() {
        ToastUtils.showLong(getActivity(), "收货成功");
        isctrl = false;
        mEdtGoodBar.setText("");
        mEdtGoodName.setText("");
        mEdtLpn.setText("");
        mEdtDate.setText("");
        mEdtNum.setText("");
        mEdtGoodBar.requestFocus();
        goodsBean = null;
        mTxtDate.setVisibility(View.INVISIBLE);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner_work_query_center, SkuPropertyNames);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner_work_query_center);
        mSpGoodProperty.setAdapter(arrayAdapter);
    }

    @Override
    public void showNoGoods() {
        isctrl = false;
        mEdtGoodBar.setText("");
        mEdtGoodName.setText("");
        mEdtLpn.setText("");
        mEdtDate.setText("");
        mEdtNum.setText("");
        mSpGoodProperty.setSelection(0);
        mTxtDate.setVisibility(View.INVISIBLE);
        mEdtGoodBar.requestFocus();
        goodsBean = null;
    }

    boolean isP = true;
    boolean isctrl = false;

    private void init() {
        mEdtGoodBar.setText(goodsBean.getData().get(0).getSkuId());
        mEdtGoodName.setText(goodsBean.getData().get(0).getSkuName());
        isctrl = goodsBean.getData().get(0).isShelfLifeCtrl();
        if (isctrl) {
            mTxtDate.setVisibility(View.VISIBLE);
            if ("E".equalsIgnoreCase(goodsBean.getData().get(0).getShelfLifeCtrlType())) {
                mTxtDate.setText("失效日期");
                isP = false;
            } else {
                mTxtDate.setText("生产日期");
                isP = true;
            }
            Calendar c = Calendar.getInstance();
            mEdtDate.setText(c.get(Calendar.YEAR) + "");
        } else {
            mTxtDate.setVisibility(View.INVISIBLE);
        }
        mEdtLpn.setText(goodsBean.getData().get(0).getLPN());
        mEdtNum.requestFocus();
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(getActivity(), s);
    }

    @Subscribe
    public void onEventMainThread(ChangeEvent event) {
        if ("commit".equals(event.getEvent())) {
            mBtnCommit.performClick();
        }

    }

}

