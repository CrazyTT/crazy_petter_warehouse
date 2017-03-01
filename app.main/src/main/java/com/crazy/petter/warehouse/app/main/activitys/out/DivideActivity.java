package com.crazy.petter.warehouse.app.main.activitys.out;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.PickWaveAdapter;
import com.crazy.petter.warehouse.app.main.beans.PickWaveBean;
import com.crazy.petter.warehouse.app.main.presenters.DividePresenter;
import com.crazy.petter.warehouse.app.main.views.DivideView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DivideActivity extends BaseActivity implements DivideView {
    DividePresenter mDividePresenter;
    @Bind(R.id.edt_order_num)
    EditText mEdtOrderNum;
    @Bind(R.id.btn_query)
    ButtonAutoBg mBtnQuery;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    PickWaveAdapter scanOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide);
        ButterKnife.bind(this);
        mDividePresenter = new DividePresenter(this, this, "DivideActivity");
        initViews();
    }

    private void initViews() {
        scanOrderAdapter = new PickWaveAdapter(this, new PickWaveAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
                jump(postion);

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
    }

    private void getDetials() {
        if (TextUtils.isEmpty(mEdtOrderNum.getText().toString().trim())) {
            new Handler().postDelayed(new Thread(new Runnable() {
                @Override
                public void run() {
                    mEdtOrderNum.requestFocus();
                }
            }), 300);
            ToastUtils.showShort(this, "单号不能为空");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WaveId", mEdtOrderNum.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mDividePresenter.getOrders(jsonObject.toString());
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    private void jump(int postion) {
        mEdtOrderNum.setText(scanOrderAdapter.getList().get(postion).getWaveDocId());
        Intent intent = new Intent(this, DivideDetialsActivity.class);
        intent.putExtra("detials", JsonFormatter.getInstance().object2Json(scanOrderAdapter.getList().get(postion)));
        startActivity(intent);
    }


    @Override
    public void setList(ArrayList<PickWaveBean.DataEntity> data) {
        if (data == null) {
            return;
        }
        scanOrderAdapter.setList(data);
    }

    @Override
    public void getOrderFailure() {
        ArrayList<PickWaveBean.DataEntity> data = new ArrayList<>();
        scanOrderAdapter.setList(data);
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mEdtOrderNum.requestFocus();
            }
        }), 300);
    }
}
