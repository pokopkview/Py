package com.yuqi.admin.py.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.yuqi.admin.py.bean.APPHomePageBean;
import com.yuqi.admin.py.bean.DshowDefaultShippingAddressBean;
import com.yuqi.admin.py.bean.LoginBean;
import com.yuqi.admin.py.bean.PersonalCenterBean;
import com.yuqi.admin.py.bean.XiuGdzBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.fragment.SpFragment;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/11/23.
 *      个人中心
 */
public class SPersonalCenterActivity extends BaseActivity{


    private TextView zicaigoucishu,zicaigoujine,zijifen,zikeyongyue;
    private TextView lishucaigoucishu,lishucaigoujine,lishujifen,lishuzhanghushuliang;

    private TextView zc,dl;
    PersonalCenterBean SPersonal;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_grzx_gerenzhongxin);
        init();
        //账户中心请求
        APPPersonalHttp();
    }
    private void init() {
        //此子账户
        zicaigoucishu = (TextView)findViewById(R.id.zhzx_cgcs);
        zicaigoujine = (TextView)findViewById(R.id.zhzx_cgje);
        zijifen = (TextView)findViewById(R.id.zhzx_szjf);
        zikeyongyue = (TextView)findViewById(R.id.zhzx_kyye);
        //隶属公司
        lishucaigoucishu = (TextView)findViewById(R.id.zhzx_lscgcs);
        lishucaigoujine = (TextView)findViewById(R.id.zhzx_lscgje);
        lishujifen = (TextView)findViewById(R.id.zhzx_ljjf);
        lishuzhanghushuliang = (TextView)findViewById(R.id.zhzx_zhsl);

        zc = (TextView)findViewById(R.id.zc);
        dl =(TextView)findViewById(R.id.dl);

        zc.setText(CommonData.phoneNumber);
        dl.setText(CommonData.companyName);

    }
    @Override
    public String title_text() {
        return null;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.fh:
                finish();
                break;

            case R.id.guanyuwomen://关于我们
                intent.setClass(SPersonalCenterActivity.this,AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.yijianfankui://意见反馈
                intent.setClass(SPersonalCenterActivity.this,MyFeedbackActivity.class);
                startActivity(intent);
                break;

            case R.id.zhanghuxinix://账户信息
                intent.setClass(SPersonalCenterActivity.this,MyPersonalInformation.class);
                intent.putExtra("phoneNumber",SPersonal.getUserinfo().getPhoneNumber());
                intent.putExtra("CompanyName",SPersonal.getUserinfo().getCompanyName());
                intent.putExtra("creationTime",SPersonal.getUserinfo().getCreationTime());
                startActivity(intent);
                break;
            case R.id.daifukuan:
                intent.setClass(SPersonalCenterActivity.this,MyPendingPaymentActivity.class);
                startActivity(intent);
                break;
            case R.id.daifahou:
                intent.setClass(SPersonalCenterActivity.this,MyPendingDeliveryActivity.class);
                startActivity(intent);
                break;
            case R.id.daishouhuo:
                intent.setClass(SPersonalCenterActivity.this,MyGoodsReceiptActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void APPPersonalHttp() {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", CommonData.user_id+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);

        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL + "personalCenter.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(SPersonalCenterActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e("SPersonal=", responseInfo.result);
                        String  result =  responseInfo.result;
                        Gson gson = new Gson();//初始化
                        SPersonal = gson.fromJson(result, PersonalCenterBean.class);
                        String state = SPersonal.getState();
                        switch (state) {
                            case "200":
                                zicaigoucishu.setText(SPersonal.getPersonalBuyCount()+"");//采购次数
                                zicaigoujine.setText(Double.toString(SPersonal.getPersonalBuyMoney()));
                                zijifen .setText(SPersonal.getPersonalIntegral()+"");
                                zikeyongyue.setText(Double.toString(SPersonal.getBalance()));
                                lishucaigoucishu.setText(SPersonal.getCompanyBuyCount()+"");
                                DecimalFormat df = new DecimalFormat("######0.00");
                                lishucaigoujine.setText(df.format(SPersonal.getCompanyBuyMoney()));
                                lishujifen.setText(SPersonal.getCompanyIntegral()+"");
                                lishuzhanghushuliang.setText(SPersonal.getAccountCount()+"");
                                break;
                            case "210":

                                break;
                        }
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                    }
                });
    }
}
