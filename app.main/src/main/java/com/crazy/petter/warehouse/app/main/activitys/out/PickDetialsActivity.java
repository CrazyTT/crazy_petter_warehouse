package com.crazy.petter.warehouse.app.main.activitys.out;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.StringUtils;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.RemarkAdapter2;
import com.crazy.petter.warehouse.app.main.beans.ConfirmObnPickBean;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;
import com.crazy.petter.warehouse.app.main.presenters.PickDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PickDetialsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PickDetialsActivity extends BaseActivity implements PickDetialsView {

    @Bind(R.id.txt_orderNum)
    TextView mTxtOrderNum;
    @Bind(R.id.txt_loc)
    TextView mTxtLoc;
    @Bind(R.id.edt_loc)
    EditText mEdtLoc;
    @Bind(R.id.txt_skuid)
    TextView mTxtSkuid;
    @Bind(R.id.edt_skuid)
    TextView mEdtSkuid;
    @Bind(R.id.edt_skuname)
    TextView mEdtSkuname;
    @Bind(R.id.txt_qty)
    TextView mTxtQty;
    @Bind(R.id.edt_qty)
    EditText mEdtQty;
    @Bind(R.id.btn_commit)
    ButtonAutoBg mBtnCommit;
    @Bind(R.id.txt_bottom)
    TextView mTxtBottom;
    @Bind(R.id.activity_pick_detials)
    LinearLayout mActivityPickDetials;
    JSONObject mDataEntity;
    PickDetialsPresenter mPickDetialsPresenter;
    @Bind(R.id.rl_remark)
    ListView mRlRemark;
    RemarkAdapter2 mRemarkAdapter;
    ArrayList<PickDetialsBean.DataEntity> datas = new ArrayList<>();
    ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> reMarks = new ArrayList<>();
    boolean isFirst = true;
    PickDetialsBean.DataEntity temp = new PickDetialsBean.DataEntity();
    int current = 0;
    String currentLoc = "";
    String currentSkuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonUtil.from(getIntent().getStringExtra("detials"));
        mPickDetialsPresenter = new PickDetialsPresenter(this, this, "PickDetialsActivity");
        initViews();
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(mEdtLoc, false);
            setShowSoftInputOnFocus.invoke(mEdtSkuid, false);
            setShowSoftInputOnFocus.invoke(mEdtSkuname, false);
            setShowSoftInputOnFocus.invoke(mEdtQty, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEdtQty.addTextChangedListener(textWatcher);
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
            if (TextUtils.isEmpty(mEdtQty.getText().toString().trim())) {
                mEdtQty.setTextColor(Color.rgb(0, 0, 0));
                return;
            }
            if (Double.parseDouble(mTxtQty.getText().toString().trim()) < Double.parseDouble(mEdtQty.getText().toString().trim())) {
                mEdtQty.setTextColor(Color.rgb(255, 0, 0));
            } else {
                mEdtQty.setTextColor(Color.rgb(0, 0, 0));
            }

        }
    };

    private void initViews() {
        mTxtOrderNum.setText(JsonUtil.getString(mDataEntity, "OBN_ID"));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getOrders(jsonObject.toString(), true);
        mEdtLoc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                        ToastUtils.showLong(PickDetialsActivity.this, "请先输入货位");
                        new Handler().postDelayed(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mEdtLoc.requestFocus();
                            }
                        }), 300);
                        return true;
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
                        jsonObject.put("PickLoc", mEdtLoc.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getOrders(jsonObject.toString(), false);
                    return true;
                }
                return false;
            }
        });
        mEdtQty.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    new Handler().postDelayed(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnCommit.requestFocus();
                            mBtnCommit.performClick();
                        }
                    }), 300);
                    return true;
                }
                return false;
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交数据
                if (TextUtils.isEmpty(mEdtLoc.getText().toString().trim())) {
                    ToastUtils.showLong(PickDetialsActivity.this, "货位不能为空,请重新扫描");
                    mEdtLoc.requestFocus();
                    return;
                }
                ConfirmObnPickBean confirmObnPickBean = new ConfirmObnPickBean();
                ArrayList<ConfirmObnPickBean.DetailsEntity> detailsEntities = new ArrayList<>();
                ConfirmObnPickBean.DetailsEntity detailsEntity = new ConfirmObnPickBean.DetailsEntity();
                detailsEntity.setObnPickInc(temp.getObnPickInc());
                detailsEntity.setLpnNo("");
                if (TextUtils.isEmpty(mEdtQty.getText().toString().trim())) {
                    ToastUtils.showLong(PickDetialsActivity.this, "数量不能为空");
                    mEdtQty.requestFocus();
                    return;
                }
                if (!StringUtils.isDouble(mEdtQty.getText().toString().trim())) {
                    ToastUtils.showLong(PickDetialsActivity.this, "请输入合法数量");
                    mEdtQty.requestFocus();
                    return;
                }
                if (Double.parseDouble(mEdtQty.getText().toString().trim()) == 0) {
                    //// TODO: 2017/3/17  
                    commitOk();
                    return;
                }
                detailsEntity.setQty(Double.parseDouble(mEdtQty.getText().toString().trim()));
                detailsEntity.setSeqNo(temp.getSeqNo());
                detailsEntity.setSkuId(temp.getSkuId());
                detailsEntity.setSkuName(temp.getSkuName());
                detailsEntity.setToLoc(temp.getPickLoc());
                detailsEntities.add(detailsEntity);
                confirmObnPickBean.setDetails(detailsEntities);
                confirmObnPickBean.setOutboundId(JsonUtil.getString(mDataEntity, "OBN_ID"));
                if (Double.parseDouble(mEdtQty.getText().toString().trim()) > Double.parseDouble(mTxtQty.getText().toString())) {
                    ToastUtils.showLong(PickDetialsActivity.this, "拣货数量超出范围了");
                    mEdtQty.requestFocus();
                    return;
                }
                mPickDetialsPresenter.commit(JsonFormatter.getInstance().object2Json(confirmObnPickBean));
            }
        });
    }

    @Override
    public void commitOk() {
        //提交成功
        if (Double.parseDouble(mTxtQty.getText().toString().trim()) == Double.parseDouble(mEdtQty.getText().toString().trim()) || Double.parseDouble(mEdtQty.getText().toString().trim()) == 0) {
            //这条记录拣货完毕了
            boolean exist = false;//是否存在相同位置，并且拣货数量>0的货品
            datas.get(current).setWaitPickQty(Double.parseDouble(mTxtQty.getText().toString().trim()) - Double.parseDouble(mEdtQty.getText().toString().trim()));
            int first = 0;
            if (Double.parseDouble(mEdtQty.getText().toString().trim()) != 0) {
                finish++;
                first = 0;
            } else {
                first = current + 1;
            }
            initBottom();
            mEdtQty.setText("");
            for (int i = first; i < datas.size(); i++) {
                if (datas.get(i).getPickLoc().equalsIgnoreCase(currentLoc) && datas.get(i).getWaitPickQty() > 0) {
                    exist = true;
                    current = i;
                    setCurrent(current);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("OutboundId", JsonUtil.getString(mDataEntity, "OBN_ID"));
                        jsonObject.put("PickLoc", mEdtLoc.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getOrders(jsonObject.toString(), false);
                    break;
                }

            }
            if (exist) {
                return;
            }
            mEdtSkuname.setText("");
            mEdtSkuid.setText("");
            mEdtLoc.setText("");
            mEdtLoc.requestFocus();
            //判断吓下一跳数据
            int count = 0;
            boolean exist2 = false;
            while (count <= datas.size()) {
                current++;
                if (current >= datas.size()) {
                    current = 0;
                }
                if (datas.get(current).getWaitPickQty() > 0) {
                    exist2 = true;
                    break;
                }
                count++;
            }
            if (current >= datas.size()) {
                current = 0;
            }
            if (exist2) {
                setCurrent(current);
            } else {
                setCurrent(0);
            }
        } else {
            datas.get(current).setWaitPickQty(Double.parseDouble(mTxtQty.getText().toString().trim()) - Double.parseDouble(mEdtQty.getText().toString().trim()));
            mTxtQty.setText(decimalFormat.format(Double.parseDouble(mTxtQty.getText().toString().trim()) - Double.parseDouble(mEdtQty.getText().toString().trim())));
            mEdtQty.setText("");
            mEdtQty.requestFocus();
        }

    }

    @Override
    public void getOrderFailure() {
        mEdtLoc.setText("");
        mEdtSkuid.setText("");
        mEdtSkuname.setText("");
        mEdtQty.setText("");
        mEdtLoc.requestFocus();
        if (isFirst) {
            mTxtBottom.setText("共计" + "xx" + "条/待处理" + "xx" + "条/已完成" + "xx" + "条");
        }
    }

    @Override
    public void commitFailure() {
        mEdtQty.requestFocus();
    }

    private void getOrders(String params, boolean isFirst) {
        this.isFirst = isFirst;
        mPickDetialsPresenter.getOrders(params);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showLong(this, s);
    }

    @Override
    public void setList(ArrayList<PickDetialsBean.DataEntity> data) {
        if (data == null || data.size() <= 0) {
            ToastUtils.showLong(this, "此单没有明细");
            return;
        }
        if (isFirst) {
            datas = data;
            all = datas.size();
            for (PickDetialsBean.DataEntity temp : datas) {
                if (temp.getWaitPickQty() <= 0) {
                    finish++;
                }
            }
            initBottom();
            int temp = -1;
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getWaitPickQty() > 0) {
                    temp = i;
                    break;
                }
            }
            if (temp != -1) {
                current = temp;
                setCurrent(current);
                showRemark(temp);
            } else {
                current = 0;
                setCurrent(current);
                showRemark(0);
            }
        } else {
            boolean exist = false;
            String skuName = "";
            double count = -1;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getSeqNo() == currentSeqNo && data.get(i).getObnPickInc() == currentObnPickInc && data.get(i).getSkuId().equalsIgnoreCase(currentSkuid) && data.get(i).getWaitPickQty() > 0) {
                    exist = true;
                    temp = data.get(i);
                    break;
                }
            }
            if (!exist) {
                temp = data.get(0);
            }
            skuName = temp.getSkuName();
            count = temp.getWaitPickQty();
            showInfo(temp);
            //并且得到操作的postion
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getWaitPickQty() == count && datas.get(i).getPickLoc().equalsIgnoreCase(temp.getPickLoc()) && datas.get(i).getSkuId().equalsIgnoreCase(temp.getSkuId()) && datas.get(i).getSkuName().equalsIgnoreCase(skuName)) {
                    current = i;
                    setCurrent(current);
                    break;
                }
            }
            showRemark(current);
        }

    }

    private void showRemark(int postion) {
        mRemarkAdapter = new RemarkAdapter2(this);
        ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> temp = datas.get(postion).getLotProperty();
        mRlRemark.setAdapter(mRemarkAdapter);
        reMarks.clear();
        for (PickDetialsBean.DataEntity.LotPropertyEntity lotPropertyEntity : temp) {
            if ("Y".equalsIgnoreCase(lotPropertyEntity.getRfVisible())) {
                reMarks.add(lotPropertyEntity);
            }
        }
        mRemarkAdapter.setList(reMarks);
    }

    private void showInfo(PickDetialsBean.DataEntity dataEntity) {
        mEdtLoc.setText(dataEntity.getPickLoc());
        mEdtSkuid.setText(dataEntity.getSkuId());
        mEdtSkuname.setText(dataEntity.getSkuName());
        mEdtQty.setText("");
        mEdtQty.requestFocus();
        currentLoc = dataEntity.getPickLoc();
    }

    private void setCurrent(int postion) {
        mTxtLoc.setText(datas.get(postion).getPickLoc());
        mTxtSkuid.setText(datas.get(postion).getSkuId());
        mTxtQty.setText(decimalFormat.format(datas.get(postion).getWaitPickQty()));
        currentSkuid = datas.get(postion).getSkuId();
        currentSeqNo = datas.get(postion).getSeqNo();
        currentObnPickInc = datas.get(postion).getObnPickInc();
    }

    int currentSeqNo = -1;
    int currentObnPickInc = -1;

    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    int all = 0;
    int finish = 0;

    private void initBottom() {
        if (all == finish) {
            ToastUtils.showLong(this, "全部拣货完成");
            if (!isFirst) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PickDetialsActivity.this);
                builder.setTitle("提示");
                builder.setMessage("拣货货已全部完成！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PickDetialsActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog = builder.create();
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            PickDetialsActivity.this.finish();
                            return true;
                        }
                        return false;
                    }
                });
                dialog.show();
            }
        } else if (finish > all) {
            finish = all;
            mTxtBottom.setText("共计" + all + "条/待处理" + (all - finish) + "条/已完成" + finish + "条");

        } else {
            mTxtBottom.setText("共计" + all + "条/待处理" + (all - finish) + "条/已完成" + finish + "条");
        }
    }


    @Override
    public void onBackPressed() {
        if (all != 0 && all != finish) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PickDetialsActivity.this);
            builder.setTitle("提示");
            builder.setMessage("拣货未完成，确认退出？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PickDetialsActivity.this.finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            Dialog dialog = builder.create();
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        PickDetialsActivity.this.finish();
                        return true;
                    }
                    return false;
                }
            });
            dialog.show();
        } else {
            super.onBackPressed();
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
