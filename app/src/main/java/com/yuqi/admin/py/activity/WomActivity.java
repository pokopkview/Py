package com.yuqi.admin.py.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.BaseMainActivity;
import com.yuqi.admin.py.MyFragment;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/11/23.
 *      联系我们
 */
public class WomActivity extends BaseActivity implements View.OnClickListener{
    private ImageView bt_shouye,bt_yuqi,bt_anli,bt_women,back;

    Intent intent;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layou_women);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void init() {
        bt_women = (ImageView) this.findViewById(R.id.bt_women);
        back = (ImageView) this.findViewById(R.id.back);

        inits();
    }
    private void inits() {
        back.setVisibility(View.INVISIBLE);
        bt_women.setImageResource(R.mipmap.b1);
    }

    @Override
    public String title_text() {
        return "联系我们";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_shouye:
                intent  = new Intent(WomActivity.this,ShouyActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.bt_yuqi:
                intent = new Intent(WomActivity.this,YuqActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.bt_anli:
                intent = new Intent(WomActivity.this,AnliActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.bt_women:
//                intent = new Intent(WomActivity.this,WomActivity.class);
//                startActivity(intent);
//                finish();
                break;

        }
    }
}