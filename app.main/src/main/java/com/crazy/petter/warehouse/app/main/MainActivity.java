package com.crazy.petter.warehouse.app.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bjdv.lib.utils.util.ToastUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtils.showShort(this, "欢迎你使用");
    }
}
