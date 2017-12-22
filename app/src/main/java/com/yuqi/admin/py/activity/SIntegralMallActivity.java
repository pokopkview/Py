package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/12/18.
 * 积分商品
 */
public class SIntegralMallActivity extends BaseActivity {
    Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_jfsc_jifenshangcheng);
    }

    @Override
    public String title_text() {
        return "积分商城";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.duihuan_wzdj:
                intent.setClass(SIntegralMallActivity.this, SRedeemNowActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
