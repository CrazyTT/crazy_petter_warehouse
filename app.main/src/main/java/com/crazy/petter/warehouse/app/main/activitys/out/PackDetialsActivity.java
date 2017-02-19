package com.crazy.petter.warehouse.app.main.activitys.out;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bjdv.lib.utils.base.BaseActivity;
import com.bjdv.lib.utils.util.JsonFormatter;
import com.bjdv.lib.utils.util.ToastUtils;
import com.bjdv.lib.utils.widgets.ButtonAutoBg;
import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.ScanSendBean;
import com.crazy.petter.warehouse.app.main.presenters.PackDetialsPresenter;
import com.crazy.petter.warehouse.app.main.views.PackDetialsView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PackDetialsActivity extends BaseActivity implements PackDetialsView {
    ScanSendBean.DataEntity mDataEntity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_detials);
        ButterKnife.bind(this);
        mDataEntity = JsonFormatter.getInstance().json2object(getIntent().getStringExtra("detials"), ScanSendBean.DataEntity.class);
        mPackDetialsPresenter = new PackDetialsPresenter(this, this, "PackDetialsActivity");
        initViews();
    }

    private void initViews() {
        mTxtOrderNum.setText(mDataEntity.getOutboundId());
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(PackDetialsActivity.this, "你点击了确定按钮");
            }
        });
    }

    @Override
    public void showTips(String s) {
        ToastUtils.showShort(this, s);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CALL) {
            mBtnCommit.performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
