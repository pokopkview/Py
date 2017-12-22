package com.yuqi.admin.py.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;

/**
 * Created by Administrator on 2017/11/24.
 *      编辑发布信息
 */
public class ReleaseInformationActivity extends BaseActivity {
    private LinearLayout ri_choose_nan,ri_choose_nv;
    private ImageView ri_nan,ri_nv;
    int choose1 = 1;

    private EditText ri_name,ri_phone,ri_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_wycg_tianxiexinxi);
        init();

        ri_choose_nan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ri_nan.setImageResource(R.drawable.choose2);
                    ri_nv.setImageResource(R.drawable.choose1);
                    choose1 =1;
                }
        });

        ri_choose_nv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ri_nan.setImageResource(R.drawable.choose1);
                    ri_nv.setImageResource(R.drawable.choose2);
                    choose1=0;
                }
        });

    }

    private void init() {
        ri_choose_nan = (LinearLayout) findViewById(R.id.ri_choose_nan);
        ri_choose_nv = (LinearLayout) findViewById(R.id.ri_choose_nv);
        ri_nan = (ImageView)findViewById(R.id.ri_nan);
        ri_nv = (ImageView)findViewById(R.id.ri_nv);
        ri_name = (EditText)findViewById(R.id.ri_name);
        ri_phone=(EditText)findViewById(R.id.ri_phone);
        ri_content = (EditText)findViewById(R.id.ri_content);

        ri_nan.setImageResource(R.drawable.choose2);
    }

    @Override
    public String title_text() {
        return "填写信息";
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ri_submit:

                intent = new Intent(ReleaseInformationActivity.this ,PublishSuccessfullyActivity.class);
                startActivity(intent);
                finish();

                break;

        }
    }


}
