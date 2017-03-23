package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.content.Intent;
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
import com.bjdv.lib.utils.entity.OrderBeanPack;
import com.bjdv.lib.utils.entity.TitleBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.SoundUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.OrderAdapter;
import com.crazy.petter.warehouse.app.main.beans.ConfirmObnCartonBean;
import com.crazy.petter.warehouse.app.main.beans.PackBean;
import com.crazy.petter.warehouse.app.main.beans.SkuBean;
import com.crazy.petter.warehouse.app.main.presenters.PackDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PackDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PackDetialsActivity extends BaseActivity implements PackDetialsView {
    JSONObject mDataEntity;
    PackDetialsPresenter mPackDetialsPresenter;
    @Bind(R.id.txt_orderNum)
    TextView mTxtOrderNum;
    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.edt_packNum)
    EditText mEdtPackNum;
    @Bind(R.id.edt_packstyle)
    EditText mEdtPackstyle;
    @Bind(R.id.edt_skuid)
    EditText mEdtSkuid;
    @Bind(R.id.edt_qty)
    EditText mEdtQty;
    @Bind(R.id.txt_reminder)
    TextView mTxtReminder;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    @Bind(R.id.btn_add)
    ButtonAutoBg mBtnAdd;
    private String barCode = "";
    double Volume = 0;
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;
    OrderAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
        mPackDetialsPresenter = new PackDetialsPresenter(this, this, "PackDetialsActivity");
        initViews();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtPackNum, false);
            setShowSoftInputOnFocus.invoke(mEdtPackstyle, false);
            setShowSoftInputOnFocus.invoke(mEdtSkuid, false);
            setShowSoftInputOnFocus.invoke(mEdtQty, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    double weight = 0;
    double weightBox = 0;

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
        mTxtOrderNum.setText(JsonUtil.getString(mDataEntity, "OBN_ID"));
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() <= 0) {
                    ToastUtils.showLong(PackDetialsActivity.this, "还没有添加任何明细");
                    return;
                }
                weight += Double.parseDouble(JsonUtil.getString(mList.get(0), "TOTAL_GROSS_WEIGHT"));//这里需要确定
                Intent intent = new Intent(PackDetialsActivity.this, SealActivity.class);
                intent.putExtra("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
                intent.putExtra("CartonId", mEdtPackNum.getText().toString().trim());
                if (TextUtils.isEmpty(cartonTypeId)) {
                    ToastUtils.showLong(PackDetialsActivity.this, "请先扫描箱型");
                    return;
                }
                intent.putExtra("CartonTypeId", cartonTypeId);
                intent.putExtra("weight", weight);
                intent.putExtra("Volume", Volume);
                startActivityForResult(intent, 0x123);
            }
        });
        mEdtPackNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (TextUtils.isEmpty(mEdtPackNum.getText().toString().trim())) {
                        ToastUtils.showLong(PackDetialsActivity.this, "请扫描输入箱号");
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mEdtPackNum.requestFocus();
                            }
                        }), 300);
                        return true;
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("SkuId", "");
                        jsonObject.put("CartonId", mEdtPackNum.getText().toString().trim());
                        jsonObject.put("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPackDetialsPresenter.getList(jsonObject.toString());
                    return true;
                }
                return false;
            }
        });


        mEdtPackNum.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(mEdtPackNum.getText().toString().trim())) {
//                        ToastUtils.showLong(PackDetialsActivity.this, "请扫描输入箱型");
//                        new Handler().postDelayed(new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mEdtPackstyle.requestFocus();
//                            }
//                        }), 300);
                        return;
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("SkuId", "");
                        jsonObject.put("CartonId", mEdtPackNum.getText().toString().trim());
                        jsonObject.put("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPackDetialsPresenter.getList(jsonObject.toString());
                }
            }
        });


        mEdtPackstyle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (TextUtils.isEmpty(mEdtPackstyle.getText().toString().trim())) {
                        ToastUtils.showLong(PackDetialsActivity.this, "请扫描输入箱型");
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mEdtPackstyle.requestFocus();
                            }
                        }), 300);
                        return true;
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("TypeId", mEdtPackstyle.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPackDetialsPresenter.getPackType(jsonObject.toString());
                    return true;
                }
                return false;
            }
        });
        mEdtPackstyle.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(mEdtPackstyle.getText().toString().trim())) {
//                        ToastUtils.showLong(PackDetialsActivity.this, "请扫描输入箱型");
//                        new Handler().postDelayed(new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mEdtPackstyle.requestFocus();
//                            }
//                        }), 300);
                        return;
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("TypeId", mEdtPackstyle.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPackDetialsPresenter.getPackType(jsonObject.toString());
                }
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入明细
                if (TextUtils.isEmpty(mEdtPackNum.getText().toString().trim()) ||
                        TextUtils.isEmpty(mEdtPackstyle.getText().toString().trim()) ||
                        TextUtils.isEmpty(mEdtQty.getText().toString().trim()) ||
                        TextUtils.isEmpty(mEdtSkuid.getText().toString().trim())) {
                    ToastUtils.showLong(PackDetialsActivity.this, "请将信息补充完整");
                    return;

                }
                addlist();
            }
        });
        //测试代码
        mEdtQty.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    //加入明细
                    if (TextUtils.isEmpty(mEdtPackNum.getText().toString().trim()) ||
                            TextUtils.isEmpty(mEdtPackstyle.getText().toString().trim()) ||
                            TextUtils.isEmpty(mEdtQty.getText().toString().trim()) ||
                            TextUtils.isEmpty(mEdtSkuid.getText().toString().trim())) {
                        ToastUtils.showLong(PackDetialsActivity.this, "请将信息补充完整");
                        return true;

                    }
                    addlist();
                    return true;
                }
                return false;
            }
        });
        mEdtSkuid.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    //获取商品信息
                    if (TextUtils.isEmpty(mEdtSkuid.getText().toString().trim())) {
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showLong(PackDetialsActivity.this, "商品条码不能为空");
                                mEdtSkuid.requestFocus();
                            }
                        }), 300);
                        return true;
                    }
                    getSkuInfo();
                    return true;
                }
                return false;
            }
        });
        mEdtSkuid.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //获取商品信息
                    if (TextUtils.isEmpty(mEdtSkuid.getText().toString().trim())) {
//                        new Handler().postDelayed(new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ToastUtils.showLong(PackDetialsActivity.this, "商品条码不能为空");
//                                mEdtSkuid.requestFocus();
//                            }
//                        }), 300);
                        return;
                    }
                    getSkuInfo();
                }
            }
        });
    }

    private void getSkuInfo() {
        barCode = mEdtSkuid.getText().toString().trim();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OwnerId", JsonUtil.getString(mDataEntity, "OWNER_ID"));
            jsonObject.put("BarCode", barCode);
            jsonObject.put("SkuId", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPackDetialsPresenter.getSkuInfo(jsonObject.toString());
    }

    @Override
    public void showSkuInfo(ArrayList<SkuBean.DataEntity> data) {
        SkuBean.DataEntity temp = data.get(0);
        mEdtSkuid.setText(temp.getSkuId());
        mEdtQty.requestFocus();
    }

    @Override
    public void getListFailure() {
        mEdtPackNum.setText("");
        mEdtPackNum.requestFocus();
    }

    @Override
    public void getSkuFailure() {
        mEdtSkuid.setText("");
        ToastUtils.showLong(this, "查询不到此条码的商品");
        mEdtSkuid.requestFocus();
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
        mEdtPackstyle.requestFocus();
    }

    ArrayList<JSONObject> mList = new ArrayList<>();


    private void addlist() {
        ConfirmObnCartonBean temp = new ConfirmObnCartonBean();
        temp.setOutboundId(JsonUtil.getString(mDataEntity, "OBN_ID"));
        temp.setAutoCartonId(false);
        temp.setBarCode(barCode);
        temp.setCartonId(mEdtPackNum.getText().toString().trim());
        temp.setCartonTypeId(cartonTypeId);
        temp.setQty(Double.parseDouble(mEdtQty.getText().toString().trim()));
        temp.setSkuId(mEdtSkuid.getText().toString().trim());
        mPackDetialsPresenter.addList(JsonFormatter.getInstance().object2Json(temp));
    }

    @Override
    public void addListFailure() {
        mEdtQty.requestFocus();
    }

    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    @Override
    public void setConfirmResult(String data) {
        OrderBeanPack orderBean = JsonFormatter.getInstance().json2object(data.toString(), OrderBeanPack.class);
        mTxtReminder.setText(decimalFormat.format(orderBean.getTotalPackageQty()) + "    /   " + decimalFormat.format(orderBean.getTotalPickedQty()));
        TitleBean titleBean = JsonUtil.getTitle(data);
        if (titleBean == null) {
            return;
        }
        mLlTitle.removeAllViews();
        for (int i = 0; i < titleBean.getCaptionEntities().size(); i++) {
            if (titleBean.getCaptionEntities().get(i).getVISIBLE() != null && titleBean.getCaptionEntities().get(i).getVISIBLE().equalsIgnoreCase("N")) {
                continue;
            }
            TextView temp = (TextView) LayoutInflater.from(PackDetialsActivity.this).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setGravity(Gravity.CENTER);
            temp.setText(titleBean.getCaptionEntities().get(i).getCAPTION());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLlTitle.addView(temp, layoutParams);
        }
        mOrderAdapter = new OrderAdapter(PackDetialsActivity.this, new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
        mList = titleBean.getOrders();

        mEdtSkuid.requestFocus();
        mEdtSkuid.setText("");
        mEdtQty.setText("");
        if (orderBean.getTotalPackageQty() >= orderBean.getTotalPickedQty()) {
            isFinsh = true;
        } else {
            isFinsh = false;
        }
    }

    boolean isFinsh = false;

    @Override
    public void getPackFailure() {
        mEdtPackstyle.requestFocus();
        mEdtPackstyle.setText("");
        weight = 0;
        weightBox = 0;
    }

    @Override
    public void setPackInfo(ArrayList<PackBean.DataEntity> data) {
        if (data.size() <= 0) {
            ToastUtils.showLong(this, "不存在此箱");
            mEdtPackstyle.requestFocus();
            mEdtPackstyle.setText("");
            Volume = 0;
            SoundUtil.getInstance(PackDetialsActivity.this).play(0);
            cartonTypeId = "";
            weight = 0;
            weightBox = 0;
            return;
        }
        cartonTypeId = data.get(0).getCartonTypeId();
        mEdtPackstyle.setText(data.get(0).getCartonTypeId());
        Volume = data.get(0).getVolume();
        weight = data.get(0).getWeight();
        weightBox = data.get(0).getWeight();
        mEdtSkuid.requestFocus();
    }

    private String cartonTypeId = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        weight = weightBox;//初始化总的重量
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                mEdtPackNum.setText("");
                mEdtPackstyle.setText("");
                mEdtSkuid.setText("");
                mEdtQty.setText("");
                mList = new ArrayList<>();
                mOrderAdapter.setList(mList);
                mEdtPackNum.requestFocus();
                if (isFinsh) {
                    this.finish();
                    ToastUtils.showLong(PackDetialsActivity.this, "装箱结束");
                } else {
                    ToastUtils.showLong(PackDetialsActivity.this, "请继续装箱");
                }
            }
        }
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
