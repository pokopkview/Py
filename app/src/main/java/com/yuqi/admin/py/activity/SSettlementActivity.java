package com.yuqi.admin.py.activity;

import android.os.Bundle;
import android.view.View;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/12/15.
 * 余额结算
 */
public class SSettlementActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_jiesuan);
    }

    @Override
    public String title_text() {
        return "结算";
    }

    @Override
    public void onClick(View v) {

    }
}
