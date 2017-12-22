package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by Administrator on 2017/11/27.
 */
public class PasswordActivity1 extends BaseActivity {
    private EditText wjmm_shoujihao,wjmm_zhenshimingzi,wjmm_gongsimingcheng,wjmm_youxiang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password1);
        init();
    }

    private void init() {
        wjmm_shoujihao= (EditText)findViewById(R.id.wjmm_shoujihao);
        wjmm_zhenshimingzi= (EditText)findViewById(R.id.wjmm_zhenshimingzi);
        wjmm_gongsimingcheng= (EditText)findViewById(R.id.wjmm_gongsimingcheng);
        wjmm_youxiang= (EditText)findViewById(R.id.wjmm_youxiang);
    }

    @Override
    public String title_text() {
        return "找回密码";
    }

    private String isContentEmpty(){
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.wjmm_shoujihao))){return "请填写手机号";}
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.wjmm_zhenshimingzi))){return "请填写姓名";}
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.wjmm_gongsimingcheng))){return "请填写公司名称";}
        if(StringUtil.isEmpty(StringUtil.getText(this, R.id.wjmm_youxiang))){return "请填写邮箱";}
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wjmm_tijiao:
                String mResult = isContentEmpty();
                String number = wjmm_shoujihao.getText().toString();
                String youxiang = wjmm_youxiang.getText().toString();
                boolean judge = StringUtil.isMobile(number);
                boolean judge1 = StringUtil.isEmail(youxiang);

                if (!StringUtil.isEmpty(mResult)){
                    ToastUtil.show(this,mResult);
                }else {
                    if (judge == true && judge1 ==true) {
                        submit();
                    } else if (judge == false){
                        ToastUtil.show(PasswordActivity1.this, "请输入正确的手机号码!");
                        return;
                    }else if (judge1 == false){
                        ToastUtil.show(PasswordActivity1.this, "请输入正确的邮箱地址!");
                        return;
                    }
                }
                break;
        }
    }


    private void submit() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("account",wjmm_shoujihao.getText()+"");// 账号
        params.addBodyParameter("userName",wjmm_zhenshimingzi.getText()+"");// 用户名
        params.addBodyParameter("companyName",wjmm_gongsimingcheng.getText()+"");// 公司名称
        params.addBodyParameter("mailbox",wjmm_youxiang.getText()+"");// 邮箱
        HttpUtils http = new HttpUtils();

        http.configCurrentHttpCacheExpiry(1000*10);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL+"forgetPassword.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(PasswordActivity1.this);
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
                        Gson gson = new Gson();//初始化
                        Bean bean = gson.fromJson(result, Bean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String state = bean.getState();
                        switch (state){
                            case "200":
                                Intent intent = new Intent();
                                intent.setClass(PasswordActivity1.this, PasswordActivity2.class);
                                ToastUtil.show(PasswordActivity1.this,"修改成功");
                                startActivity(intent);
                                finish();
                                break;
                            case "210":
                                ToastUtil.show(PasswordActivity1.this,"修改失败");
                                break;
                            case "500":
                                ToastUtil.show(PasswordActivity1.this,"密码错误");
                                break;
                            case "404":
                                ToastUtil.show(PasswordActivity1.this,"帐号不存在");
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
}
