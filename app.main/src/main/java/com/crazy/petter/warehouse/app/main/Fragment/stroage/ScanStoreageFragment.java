package com.crazy.petter.warehouse.app.main.Fragment.stroage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.entity.TitleBean;
import com.bjdv.lib.utils.util.JsonUtil;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.activitys.in.ReceiptMixActivity;
import com.crazy.petter.warehouse.app.main.adapters.OrderAdapter;
import com.crazy.petter.warehouse.app.main.beans.EventClick;
import com.crazy.petter.warehouse.app.main.presenters.ScanStoreageFragmentPresenter;
import com.crazy.petter.warehouse.app.main.views.ScanStoreageFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuliuchen on 2017/2/11.
 */

public class ScanStoreageFragment extends Fragment implements ScanStoreageFragmentView {
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        mOrderAdapter = new OrderAdapter(getActivity(), new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }
        }, new ArrayList<OrderBean.CaptionEntity>());
        mOrderList.setLayoutManager(layoutManager);
//        mOrderList.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
//        mOrderList.setAdapter(scanOrderAdapter);
        EventBus.getDefault().register(this);

    }

    private void jump(int postion) {
        EventClick eventClick = new EventClick();
        Intent intent = new Intent(getActivity(), ReceiptMixActivity.class);
        eventClick.setOrder(JsonUtil.getString(mOrderAdapter.getList().get(postion), "IBN_ID"));
        EventBus.getDefault().post(eventClick);
        mSharedPreferencesUtil.setString("num", JsonUtil.getString(mOrderAdapter.getList().get(postion), "IBN_ID"));
        intent.putExtra("detials", mOrderAdapter.getList().get(postion).toString());
        startActivity(intent);
    }

    private void initView() {
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
        mLlTitle.removeAllViews();
        for (int i = 0; i < titleBean.getCaptionEntities().size(); i++) {
            TextView temp = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setGravity(Gravity.CENTER);
            temp.setText(titleBean.getCaptionEntities().get(i).getCAPTION());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(210, LinearLayout.LayoutParams.WRAP_CONTENT);
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


    private void getDetials(String string) {
        mSharedPreferencesUtil.setString("num", string);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocNo", string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mScanStoreageFragmentPresenter.getOrders(jsonObject.toString());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(String event) {
        getDetials(event);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(getActivity(), s);

    }

    @Override
    public void setList(String data) {
        TitleBean titleBean = JsonUtil.getTitle(data);
        if (titleBean == null) {
            getOrderFailure();
            return;
        }
        mLlTitle.removeAllViews();
        for (int i = 0; i < titleBean.getCaptionEntities().size(); i++) {
            if (titleBean.getCaptionEntities().get(i).getVISIBLE() != null && titleBean.getCaptionEntities().get(i).getVISIBLE().equalsIgnoreCase("N")) {
                continue;
            }
            TextView temp = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setGravity(Gravity.CENTER);
            temp.setText(titleBean.getCaptionEntities().get(i).getCAPTION());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLlTitle.addView(temp, layoutParams);
        }
        mOrderAdapter = new OrderAdapter(getActivity(), new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {
                jump(postion);

            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
    }

    @Override
    public void getOrderFailure() {
        ArrayList<JSONObject> temp = new ArrayList<>();
        mOrderAdapter.setList(temp);
    }
}
