package com.crazy.petter.warehouse.app.main.Fragment.stroage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.crazy.petter.warehouse.app.main.adapters.OrderAdapter;
import com.crazy.petter.warehouse.app.main.presenters.DetialsStoreageFragmentPresenter;
import com.crazy.petter.warehouse.app.main.views.DetialsStoreageFragmentView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuliuchen on 2017/2/11.
 */

public class DetialsStoreageFragment extends Fragment implements DetialsStoreageFragmentView {
    SharedPreferencesUtil sp;
    DetialsStoreageFragmentPresenter mDetialsStoreageFragmentPresenter;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    OrderAdapter mOrderAdapter;
    @Bind(R.id.ll_title)
    LinearLayout mLlTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detials_storeage, container, false);
        ButterKnife.bind(this, view);
        mDetialsStoreageFragmentPresenter = new DetialsStoreageFragmentPresenter(this, (BaseActivity) getActivity(), "");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        mOrderAdapter = new OrderAdapter(getActivity(), new OrderAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }
        }, new ArrayList<OrderBean.CaptionEntity>());
        this.getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sp = new SharedPreferencesUtil(getActivity());
        if (!TextUtils.isEmpty(sp.getString("num")) && sp.getBoolean("isRefresh")) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("InboundId", sp.getString("num"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mDetialsStoreageFragmentPresenter.getOrder(jsonObject.toString());
        }
    }

    @Override
    public void showGoods(String data) {
        TitleBean titleBean = JsonUtil.getTitle(data);
        if(titleBean==null){
            showFailure();
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

            }
        }, titleBean.getCaptionEntities());
        mOrderList.setAdapter(mOrderAdapter);
        mOrderAdapter.setList(titleBean.getOrders());
    }

    @Override
    public void showFailure() {
        ArrayList<JSONObject> temp = new ArrayList<>();
        mOrderAdapter.setList(temp);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(getActivity(), s);
    }
}
