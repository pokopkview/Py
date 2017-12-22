package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/12/19.
 * 立即兑换
 */
public class SRedeemNowActivity extends BaseActivity {
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_jfsc_lijiduihuan);
    }

    @Override
    public String title_text() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dh_lijiduihuan:
                intent.setClass(SRedeemNowActivity.this,SExchangeApplicationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
