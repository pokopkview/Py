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
import com.yuqi.admin.py.utils.ToastUtil;

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
        bt_yuqi.setImageResource(R.mipmap.b2);
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
                break;

            case R.id.bt_yuqi:
//                intent = new Intent(YuqActivity.this, YuqActivity.class);
//                startActivity(intent);
                break;

            case R.id.bt_anli:
                intent = new Intent(YuqActivity.this, AnliActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_women:
                intent = new Intent(YuqActivity.this, WomActivity.class);
                startActivity(intent);
                break;

            case R.id.llImageView:
                intent = new Intent(YuqActivity.this, SIntegralMallActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**处理两次点击手机返回键退出*/
    long backtime = 0;
    @Override
    public void onBackPressed() {
        long clicktime = System.currentTimeMillis();
        if (backtime == 0 || clicktime - backtime > 1500) {
            backtime = clicktime;
            ToastUtil.show(this, "再次点击退出程序");
        } else {
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
        }
    }
}