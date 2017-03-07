package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.PackDetialsAdapter;
import com.crazy.petter.warehouse.app.main.beans.ConfirmObnCartonBean;
import com.crazy.petter.warehouse.app.main.beans.PackBean;
import com.crazy.petter.warehouse.app.main.beans.PackDetialsBean;
import com.crazy.petter.warehouse.app.main.beans.QueryObnCartonBean;
import com.crazy.petter.warehouse.app.main.beans.SkuBean;
import com.crazy.petter.warehouse.app.main.presenters.PackDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PackDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PackDetialsActivity extends BaseActivity implements PackDetialsView {
    QueryObnCartonBean.DataEntity mDataEntity;
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
    PackDetialsAdapter mPackDetialsAdapter;
    @Bind(R.id.btn_add)
    ButtonAutoBg mBtnAdd;
    private String barCode = "";
    double Volume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), QueryObnCartonBean.DataEntity.class);
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
        mPackDetialsAdapter = new PackDetialsAdapter(this, new PackDetialsAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }
        });
        mPackDetialsAdapter.setList(mList);
        mOrderList.setAdapter(mPackDetialsAdapter);
    }

    private void initViews() {
        mTxtOrderNum.setText(mDataEntity.getOutboundId());
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() <= 0) {
                    ToastUtils.showLong(PackDetialsActivity.this, "还没有添加任何明细");
                    return;
                }
                double weight = 0;
                for (PackDetialsBean.DataEntity dataEntity : mList) {
                    weight += dataEntity.getTotalGrossWeight();
                }
                Intent intent = new Intent(PackDetialsActivity.this, SealActivity.class);
                intent.putExtra("OutboundId", mDataEntity.getOutboundId());
                intent.putExtra("CartonId", mEdtPackNum.getText().toString().trim());
                intent.putExtra("CartonTypeId", mEdtPackstyle.getText().toString().trim());
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
                        jsonObject.put("TypeId", mEdtPackNum.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPackDetialsPresenter.getPackType(jsonObject.toString());
                    return true;
                }
                return false;
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
                        jsonObject.put("TypeDesc", mEdtPackstyle.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPackDetialsPresenter.getPackType(jsonObject.toString());
                    return true;
                }
                return false;
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
    }

    private void getSkuInfo() {
        barCode = mEdtSkuid.getText().toString().trim();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OwnerId", mDataEntity.getOwnerId());
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
    public void getSkuFailure() {
        mEdtSkuid.setText("");
        ToastUtils.showLong(this, "查询不到此条码的商品");
        mEdtSkuid.requestFocus();
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
        mEdtPackNum.requestFocus();
    }

    ArrayList<PackDetialsBean.DataEntity> mList = new ArrayList<>();

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CALL && event.getAction() == KeyEvent.ACTION_DOWN) {
            mBtnCommit.performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addlist() {
        ConfirmObnCartonBean temp = new ConfirmObnCartonBean();
        temp.setOutboundId(mDataEntity.getOutboundId());
        temp.setAutoCartonId(true);
        temp.setBarCode(barCode);
        temp.setCartonId(mEdtPackNum.getText().toString().trim());
        temp.setCartonTypeId(mEdtPackstyle.getText().toString().trim());
        temp.setQty(Integer.parseInt(mEdtQty.getText().toString().trim()));
        temp.setSkuId(mEdtSkuid.getText().toString().trim());
        mPackDetialsPresenter.addList(JsonFormatter.getInstance().object2Json(temp));
    }

    @Override
    public void addListFailure() {
        mEdtQty.requestFocus();
    }

    @Override
    public void setConfirmResult(PackDetialsBean scanStoreageBean) {
        mList = scanStoreageBean.getData();
        mEdtSkuid.requestFocus();
        mEdtSkuid.setText("");
        mEdtQty.setText("");
        mPackDetialsAdapter.notifyDataSetChanged();
        mTxtReminder.setText(scanStoreageBean.getTotalPackageQty() + "    /   " + scanStoreageBean.getTotalPickedQty());
    }

    @Override
    public void getPackFailure() {
        mEdtPackNum.requestFocus();
        mEdtPackNum.setText("");
        mEdtPackstyle.setText("");
    }

    @Override
    public void setPackInfo(ArrayList<PackBean.DataEntity> data) {
        if (data.size() <= 0) {
            ToastUtils.showLong(this, "不存在此箱");
            mEdtPackNum.requestFocus();
            mEdtPackNum.setText("");
            mEdtPackstyle.setText("");
            Volume = 0;
            return;
        }
        mEdtPackNum.setText(data.get(0).getCartonTypeId());
        Volume = data.get(0).getVolume();
        mEdtPackstyle.setText(data.get(0).getCartonTypeDesc());
        mEdtSkuid.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                this.finish();
            }
        }
    }
}
