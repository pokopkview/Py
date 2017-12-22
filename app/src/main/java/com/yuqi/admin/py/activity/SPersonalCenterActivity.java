package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.LoginBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.ToastUtil;

/**
 * Created by Administrator on 2017/11/23.
 *      个人中心
 */
public class SPersonalCenterActivity extends BaseActivity{
    private TextView grzx_jifen,grzx_yue,grzx_zhanghao1;
    private LinearLayout grzx_denglu_zhuce,grzx_zhanghao;
    LoginBean login;


    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_grzx_gerenzhongxin);

        init();
    }

    private void init() {
        grzx_jifen = (TextView)findViewById(R.id.grzx_jifen);
        grzx_yue = (TextView)findViewById(R.id.grzx_yue);
        grzx_denglu_zhuce = (LinearLayout)findViewById(R.id.grzx_denglu_zhuce);
        grzx_zhanghao = (LinearLayout)findViewById(R.id.grzx_zhanghao);
        grzx_zhanghao1 =(TextView)findViewById(R.id.grzx_zhanghao1);

        if (CommonData.state == null || CommonData.state.length() ==0){
            grzx_denglu_zhuce.setVisibility(View.VISIBLE);
            grzx_zhanghao.setVisibility(View.INVISIBLE);
        }else{
            grzx_denglu_zhuce.setVisibility(View.INVISIBLE);
            grzx_zhanghao.setVisibility(View.VISIBLE);
            String balance = Double.toString(CommonData.balance);
            String company_integral = Integer.toString(CommonData.company_integral);

            grzx_yue.setText(balance);
            grzx_jifen.setText(company_integral);
        }
    }

    @Override
    public String title_text() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fh:
                finish();
                break;

            case R.id.zc:
                intent = new Intent(SPersonalCenterActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.dl:
                intent = new Intent(SPersonalCenterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
