package com.yuqi.admin.py.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.Bean;
import com.yuqi.admin.py.bean.LoginBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.StringUtil;
import com.yuqi.admin.py.utils.ToastUtil;

/**
 * Created by Administrator on 2017/11/23.
 * 注册
 */
public class RegisterActivity extends BaseActivity{
    private EditText zc_shoujihao,zc_mima,zc_lishugongsi;
    //先定义
    SharedPreferences sp = null;

    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        init();

    }

    private void init() {
        zc_shoujihao = (EditText)findViewById(R.id.zc_shoujihao);
        zc_mima = (EditText)findViewById(R.id.zc_mima);
        zc_lishugongsi = (EditText)findViewById(R.id.zc_lishugongsi);
    }

    @Override
    public String title_text() {
        return "注册";
    }

    private String isContentEmpty(){
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.zc_lishugongsi))){return "请填写隶属公司";}
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.zc_shoujihao))){return "请填写账号";}
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.zc_mima))){return "请填写密码";}
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zc_zhuce:
                String mResult = isContentEmpty();
                String number = zc_shoujihao.getText().toString();
                boolean judge = StringUtil.isMobile(number);

                if (!StringUtil.isEmpty(mResult)){
                    ToastUtil.show(this,mResult);
                }else {
                    if (judge == true) {
                        submit();
                    } else {
                        ToastUtil.show(RegisterActivity.this, "请输入正确的手机号码!");
                        return;
                    }
                }
                break;

            case R.id.zc_laoyonghu:
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    private void submit() {

        RequestParams params = new RequestParams();
        params.addBodyParameter("accounts",zc_shoujihao.getText()+"");// 账号
        params.addBodyParameter("password",zc_mima.getText()+"");// 密码
        params.addBodyParameter("company",zc_lishugongsi.getText()+"");// 隶属公司
        HttpUtils http = new HttpUtils();

        http.configCurrentHttpCacheExpiry(1000*10);
        Log.e("注册请求数据=", "数据="+params);

        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL+"registration.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(RegisterActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        String  result =  responseInfo.result;
                        Log.e("注册数据=", "数据="+result);
                        Gson gson = new Gson();//初始化
                        Bean bean = gson.fromJson(result, Bean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                        String state = bean.getState();

                        switch (state){
                            case "200":
                                Intent intent = new Intent();
                                intent.setClass(RegisterActivity.this, LoginActivity.class);
                                ToastUtil.show(RegisterActivity.this,"注册成功");
                                startActivity(intent);
                                zh();
                                finish();
                                break;
                            case "210":
                                ToastUtil.show(RegisterActivity.this,"注册失败");
                                break;
                            case "400":
                                ToastUtil.show(RegisterActivity.this,"账号已存在");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e("注册请求是吧", "失败="+error.toString() + "/"+ msg);
                    }
                });
    }

    private void zh() {
        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、让setting处于编辑状态
        SharedPreferences.Editor editor = sp.edit();
        //3、存放数据
        editor.putString("zcname", zc_shoujihao.getText().toString().trim());
//        zc_mima.setText(sp.getString("zcpswd", null));
        //4、完成提交
        editor.commit();
    }
}
