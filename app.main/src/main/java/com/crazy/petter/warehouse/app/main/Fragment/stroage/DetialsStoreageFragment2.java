package com.crazy.petter.warehouse.app.main.Fragment.stroage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.SharedPreferencesUtil;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.MyDecoration;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.adapters.DetialsStoreageAdapter;
import com.crazy.petter.warehouse.app.main.beans.HistoryBean;
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

public class DetialsStoreageFragment2 extends Fragment implements DetialsStoreageFragmentView {
    SharedPreferencesUtil sp;
    DetialsStoreageFragmentPresenter mDetialsStoreageFragmentPresenter;
    @Bind(R.id.order_list)
    RecyclerView mOrderList;
    DetialsStoreageAdapter mDetialsStoreageAdapter;

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
        mDetialsStoreageAdapter = new DetialsStoreageAdapter(getActivity(), new DetialsStoreageAdapter.OrderTodoAdapterCallBack() {
            @Override
            public void click(int postion) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOrderList.setLayoutManager(layoutManager);
        mOrderList.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        mOrderList.setAdapter(mDetialsStoreageAdapter);
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
        if (!TextUtils.isEmpty(sp.getString("num"))) {
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
    public void showGoods(ArrayList<HistoryBean.DataEntity> data) {
        mDetialsStoreageAdapter.setList(data);
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(getActivity(), s);
    }
}
