package com.yuqi.admin.py.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/12/21.
 */
public class SEditorsActivity extends BaseActivity{
    private EditText dzbj_shouhuoren,dzbj_lishugongsi,dzbj_lianxifangshi,dzbj_shouhuodizhi;
    private TextView dzbj_baocun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_dizhibianji);

        init();
    }

    private void init() {
        dzbj_shouhuoren = (EditText)findViewById(R.id.dzbj_shouhuoren);
        dzbj_lishugongsi = (EditText)findViewById(R.id.dzbj_lishugongsi);
        dzbj_lianxifangshi = (EditText)findViewById(R.id.dzbj_lianxifangshi);
        dzbj_shouhuodizhi = (EditText)findViewById(R.id.dzbj_shouhuodizhi);
    }

    @Override
    public String title_text() {
        return "编辑收货信息";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dzbj_baocun:

                break;
        }
    }
}
