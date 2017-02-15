package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.TratSendAdapter;
import com.crazy.petter.warehouse.app.main.beans.ScanSendBean;
import com.crazy.petter.warehouse.app.main.beans.TraySendDetialsBean;
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
    ScanSendBean.DataEntity mDataEntity;
    TratSendAdapter scanOrderAdapter;
    @Bind(R.id.btn_cancle)
    Button mBtnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tray_send_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanSendBean.DataEntity.class);
        mTraySendDetialsPresenter = new TraySendDetialsPresenter(this, this, "mTraySendDetialsPresenter");
        initViews();
    }

    private void initViews() {
        scanOrderAdapter = new TratSendAdapter(this, new TratSendAdapter.OrderTodoAdapterCallBack() {
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
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
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
                //确认发货
            }
        });
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消按钮的事件
            }
        });
    }

    private void getDetials() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OutboundId", mDataEntity.getOutboundId());
            jsonObject.put("LpnNo", mEdtOrderNum.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTraySendDetialsPresenter.getOrder(jsonObject.toString());
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    ArrayList<TraySendDetialsBean.DataEntity> datas;

    @Override
    public void showGoods(ArrayList<TraySendDetialsBean.DataEntity> data) {
        datas = data;
        scanOrderAdapter.setList(datas);
    }
}