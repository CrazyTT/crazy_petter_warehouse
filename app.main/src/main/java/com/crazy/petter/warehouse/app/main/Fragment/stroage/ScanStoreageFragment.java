package com.crazy.petter.warehouse.app.main.Fragment.stroage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.entity.TitleBean;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.activitys.in.ReceiptActivity;
import com.crazy.petter.warehouse.app.main.adapters.OrderAdapter;
import com.crazy.petter.warehouse.app.main.adapters.ScanOrderAdapter;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;
import com.crazy.petter.warehouse.app.main.presenters.ScanStoreageFragmentPresenter;
import com.crazy.petter.warehouse.app.main.views.ScanStoreageFragmentView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuliuchen on 2017/2/11.
 */

public class ScanStoreageFragment extends Fragment implements ScanStoreageFragmentView {
    @Bind(R.id.edt_order_num)
    EditText mEdtOrderNum;
    @Bind(R.id.btn_query)
    ButtonAutoBg mBtnQuery;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    ScanOrderAdapter scanOrderAdapter;
    SharedPreferencesUtil mSharedPreferencesUtil;
    ScanStoreageFragmentPresenter mScanStoreageFragmentPresenter;
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;
    OrderAdapter mOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_storeage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedPreferencesUtil = new SharedPreferencesUtil(getActivity());
        mScanStoreageFragmentPresenter = new ScanStoreageFragmentPresenter(this, (BaseActivity) getActivity(), "ScanStoreageFragment");
        initView();
        scanOrderAdapter = new ScanOrderAdapter(getActivity(), new ScanOrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
                jump(postion);

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        mOrderList.setAdapter(scanOrderAdapter);
        InputMethodManager imm = (InputMethodManager) mEdtOrderNum.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEdtOrderNum.getApplicationWindowToken(), 0);
        }
    }

    private void jump(int postion) {
        Intent intent = new Intent(getActivity(), ReceiptActivity.class);
        mEdtOrderNum.setText(scanOrderAdapter.getList().get(postion).getInboundId());
        mEdtOrderNum.setSelection(scanOrderAdapter.getList().get(postion).getInboundId().length());
        mSharedPreferencesUtil.setString("num", mEdtOrderNum.getText().toString().trim());
        intent.putExtra("detials", JsonFormatter.getInstance().object2Json(scanOrderAdapter.getList().get(postion)));
        startActivity(intent);
    }

    private void initView() {
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
        // test();
    }

    private void test() {
        String str = "{\n" +
                "    \"success\": true,\n" +
                "    \"message\": \"\",\n" +
                "    \"count\": 1,\n" +
                "    \"caption\": [\n" +
                "        {\n" +
                "            \"SEQ_NO\": 10,\n" +
                "            \"FIELD_NAME\": \"OWNER_ID\",\n" +
                "            \"CAPTION\": \"货主\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"SEQ_NO\": 20,\n" +
                "            \"FIELD_NAME\": \"IBN_EXT_ID\",\n" +
                "            \"CAPTION\": \"外部单号\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"SEQ_NO\": 30,\n" +
                "            \"FIELD_NAME\": \"IBN_ID\",\n" +
                "            \"CAPTION\": \"入库单号\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"SEQ_NO\": 40,\n" +
                "            \"FIELD_NAME\": \"IBN_PLAN_ID\",\n" +
                "            \"CAPTION\": \"计划单号\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"SEQ_NO\": 50,\n" +
                "            \"FIELD_NAME\": \"TRADE_PARTNER_ID\",\n" +
                "            \"CAPTION\": \"供应商\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"SEQ_NO\": 60,\n" +
                "            \"FIELD_NAME\": \"ORDER_DATE\",\n" +
                "            \"CAPTION\": \"订单日期\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"value\": [\n" +
                "        {\n" +
                "            \"OWNER_ID\": \"MTS01\",\n" +
                "            \"IBN_EXT_ID\": \"DD00022\",\n" +
                "            \"IBN_ID\": \"IB160612000001\",\n" +
                "            \"IBN_PLAN_ID\": \"IP160606000004\",\n" +
                "            \"TRADE_PARTNER_ID\": \"ARST\",\n" +
                "            \"ORDER_DATE\": \"2016-05-31T23:30:12\"\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";

        TitleBean titleBean = JsonUtil.getTitle(str);
        for (int i = 0; i < titleBean.getCaptionEntities().size(); i++) {
            TextView temp = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setText(titleBean.getCaptionEntities().get(i).getCAPTION());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(170, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLlTitle.addView(temp, layoutParams);
        }
        mOrderAdapter = new OrderAdapter(getActivity(), new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
    }


    private void getDetials() {
        if (TextUtils.isEmpty(mEdtOrderNum.getText().toString().trim())) {
            new Handler().postDelayed(new Thread(new Runnable() {
                @Override
                public void run() {
                    mEdtOrderNum.requestFocus();
                }
            }), 300);
//            ToastUtils.showShort(getActivity(), "单号不能为空");
//            return;
        }
        mSharedPreferencesUtil.setString("num", mEdtOrderNum.getText().toString().trim());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocNo", mEdtOrderNum.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mScanStoreageFragmentPresenter.getOrders(jsonObject.toString());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(getActivity(), s);

    }

    @Override
    public void setList(ArrayList<ScanStoreageBean.DataEntity> data) {
        scanOrderAdapter.setList(data);
    }

    @Override
    public void getOrderFailure() {
        ArrayList<ScanStoreageBean.DataEntity> data = new ArrayList<>();
        scanOrderAdapter.setList(data);
        new Handler().postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                mEdtOrderNum.requestFocus();
            }
        }), 300);
    }
}
