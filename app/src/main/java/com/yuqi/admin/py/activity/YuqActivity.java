package com.yuqi.admin.py.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.MyFragment;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/11/23.
 *   羽琪科技
 */
public class YuqActivity  extends BaseActivity implements View.OnClickListener{
    private ImageView bt_shouye,bt_yuqi,bt_anli,bt_women,back;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layou_yuqi);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void init() {
        bt_yuqi = (ImageView) this.findViewById(R.id.bt_yuqi);
        back = (ImageView) this.findViewById(R.id.back);
        inits();
    }
    private void inits() {
        back.setVisibility(View.INVISIBLE);
        bt_yuqi.setImageResource(R.mipmap.b3);
    }

    @Override
    public String title_text() {
        return "羽琪科技";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_shouye:
                intent = new Intent(YuqActivity.this, ShouyActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.bt_yuqi:
//                intent = new Intent(YuqActivity.this, YuqActivity.class);
//                startActivity(intent);
//                finish();
                break;

            case R.id.bt_anli:
                intent = new Intent(YuqActivity.this, AnliActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.bt_women:
                intent = new Intent(YuqActivity.this, WomActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}