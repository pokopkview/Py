package com.yuqi.admin.py.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.yuqi.admin.py.bean.LoginBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.StringUtil;
import com.yuqi.admin.py.utils.ToastUtil;
/**
 * Created by Administrator on 2017/11/22.
 *      登录界面
 */
public class LoginActivity extends BaseActivity {
    private EditText ll_shoujihao ,ll_mima;
    //先定义
    SharedPreferences sp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        init();
    }

    private void init() {
        ll_shoujihao = (EditText)findViewById(R.id.ll_shoujihao);
        ll_mima = (EditText)findViewById(R.id.ll_mima);

        //1、获取Preferences
        SharedPreferences settings = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        //2、取出数据
        String name = settings.getString("zcname","" );
        ll_shoujihao.setText(name);
    }


    @Override
    public String title_text() {
        return null;
    }

    private String isContentEmpty(){
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.ll_shoujihao))){return "请填写账号";}
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.ll_mima))){return "请填写密码";}
        return "";
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.l_login:
                String mResult = isContentEmpty();
                String number = ll_shoujihao.getText().toString();
                boolean judge = StringUtil.isMobile(number);

                if (!StringUtil.isEmpty(mResult)){
                    ToastUtil.show(this,mResult);
                }else {
                    if (judge == true) {
                        submit();
                    } else {
                        ToastUtil.show(LoginActivity.this, "请输入正确的手机号码!");
                        return;
                    }
                }
                break;

            case R.id.l_Register_new_account:
                    intent.setClass(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                break;

            case R.id.l_Forgot_password:
                    intent.setClass(LoginActivity.this, PasswordActivity1.class);
                    startActivity(intent);
                break;
        }
    }


    private void submit() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("accounts",ll_shoujihao.getText()+"");// 账号
        params.addBodyParameter("password",ll_mima.getText()+"");// 密码

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
        Log.e("请求数据=", "请求数据="+params);

        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL+"login.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(LoginActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e("请求成功后的回调方法", responseInfo.result + "/");
                        String  result =  responseInfo.result;
                        boolean a = false;


                            Gson gson = new Gson();//初始化
                            LoginBean login = gson.fromJson(result, LoginBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                            String  state = login.getState();
                            switch (state){
                                case "200":
                                    CommonData.accounts = login.getObject().getUser().getAccounts();
                                    CommonData.password = login.getObject().getUser().getPassword();
                                    CommonData.token = (String) login.getObject().getUser().getToken();
                                    CommonData.user_id = login.getObject().getUserinfo().getUser_id();
                                    CommonData.portrait = login.getObject().getUserinfo().getPortrait();
                                    CommonData.nickname = login.getObject().getUserinfo().getNickname();
                                    CommonData.gender = login.getObject().getUserinfo().getGender();
                                    CommonData.mailbox = (String) login.getObject().getUserinfo().getMailbox();
                                    CommonData.phoneNumber = login.getObject().getUserinfo().getPhoneNumber();
                                    CommonData.companyName = (String) login.getObject().getUserinfo().getCompanyName();
                                    CommonData.balance = login.getObject().getUserinfo().getBalance();
                                    CommonData.creationTime = login.getObject().getUserinfo().getCreationTime();
                                    CommonData.company_integral = login.getObject().getUserinfo().getCompany_integral();

                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, ShouyActivity.class);
                                    CommonData.state = state;
//                                    ToastUtil.show(LoginActivity.this,"登录成功");
                                    startActivity(intent);
                                    finish();
                                    dl();
                                    break;
                                case "500":
                                    ToastUtil.show(LoginActivity.this,"密码错误");
                                    break;
                                case "404":
                                    ToastUtil.show(LoginActivity.this,"帐号不存在");
                                    break;
                            }

                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e("请求异常后的回调方法", "失败="+error.toString() + "/"+ msg);
                    }
                });
    }

    private void dl() {
            sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            //2、让setting处于编辑状态
            SharedPreferences.Editor editor = sp.edit();
            //3、存放数据
            editor.putString("zcname", ll_shoujihao.getText().toString().trim());
            //4、完成提交
            editor.commit();
    }
}
