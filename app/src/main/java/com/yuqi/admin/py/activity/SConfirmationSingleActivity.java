package com.yuqi.admin.py.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuqi.admin.py.BaseActivity;

import com.yuqi.admin.py.R;
import com.yuqi.admin.py.adapter.DingDanAdapter;
import com.yuqi.admin.py.alipay.PayResult;
import com.yuqi.admin.py.bean.AlipayBean;
import com.yuqi.admin.py.bean.Bean;
import com.yuqi.admin.py.bean.DingDanBean;
import com.yuqi.admin.py.bean.DshowDefaultShippingAddressBean;
import com.yuqi.admin.py.bean.WeChatHaymentBean;
import com.yuqi.admin.py.bean.XiuGdzBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.data.CommonDataDingDan;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;
import com.yuqi.admin.py.wxapi.WXPayEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 *      确认下单
 */
public class SConfirmationSingleActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private ImageView dd_songhuo1,dd_ziqu1,dd_weixin1,dd_weixin2,
            dd_zhifubao1,dd_zhifubao2,dd_yue1,dd_yue2;
    private TextView qrxd_shouhuodizhi, qrxd_shoujianren,qrxd_shoujihao,qrxd_lishugongsi,jianshu2,heji2;
    private EditText maijialiuyan;
    ListView lv_querendingdan;
    DingDanAdapter dingdanAdapter;

    Intent intent = new  Intent();
    private List<DingDanBean.DingdanBean> commodity;


    String peisong ="送货上门";
    String zhifu ="支付宝";

    private int commodity_id;
    private int counts;
    private int address_id;
    private String messages;
    private Double CommodityPrice;

    AlipayBean alipayBean;
    WeChatHaymentBean weChatHaymentBean;
    WeChatHaymentBean.ResultMapBean credential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_querenxiadan);
        //获取商品订单数据
        commodity = (List<DingDanBean.DingdanBean>) getIntent().getSerializableExtra("dingDan");
        //获取默认地址
        showDefaultShippingAddressHttp(CommonData.user_id);
        initView();
        //适配器处理展示商品
        commodityBean(commodity);


        XiuGdzBean person  = (XiuGdzBean) getIntent().getSerializableExtra("xgdz");
        if(person != null){
            //选择地址
            qrxd_shouhuodizhi.setText(person.getShippingAddress()+person.getStringdetailedAddress());
            qrxd_shoujihao.setText(person.getPhoneNumber());
            qrxd_shoujianren.setText(person.getConsignee());
            qrxd_lishugongsi.setText(person.getShippingAddress());
        }
    }

    private void initView() {
        lv_querendingdan = (ListView) findViewById(R.id.lv_querendingdan);

        dd_songhuo1 = (ImageView)findViewById(R.id.dd_songhuo1);
        dd_ziqu1 = (ImageView)findViewById(R.id.dd_ziqu1);
        dd_weixin1 = (ImageView)findViewById(R.id.dd_weixin1);
        dd_weixin2 = (ImageView)findViewById(R.id.dd_weixin11);
        dd_zhifubao1 = (ImageView)findViewById(R.id.dd_zhifubao1);
        dd_zhifubao2 = (ImageView)findViewById(R.id.dd_zhifubao11);
        dd_yue1 = (ImageView)findViewById(R.id.dd_yue1);
        dd_yue2 = (ImageView)findViewById(R.id.dd_yue11);

        jianshu2 = (TextView)this.findViewById(R.id.jianshu2);
        heji2 = (TextView)this.findViewById(R.id.heji2);
        maijialiuyan= (EditText)findViewById(R.id.maijialiuyan1);

        qrxd_shouhuodizhi=(TextView)findViewById(R.id.qrxd_shouhuodizhi);
        qrxd_shoujihao=(TextView)findViewById(R.id.qrxd_shoujihao);
        qrxd_shoujianren=(TextView)findViewById(R.id.qrxd_shoujianren);
        qrxd_lishugongsi=(TextView)findViewById(R.id.qrxd_lishugongsi);

    }
    //获取默认地址
    private void showDefaultShippingAddressHttp(int user_id) {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", user_id+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        Log.e("获取默认地址=",user_id+"");
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL + "showDefaultShippingAddress.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(SConfirmationSingleActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);

                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e("返回默认地址=", responseInfo.result);
                        String  result =  responseInfo.result;
                        Gson gson = new Gson();//初始化
                        DshowDefaultShippingAddressBean mRdizhi = gson.fromJson(result, DshowDefaultShippingAddressBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String state = mRdizhi.getState();
                        switch (state) {
                            case "200":
                                address_id = mRdizhi.getObject().getId();
                                XiuGdzBean person  = (XiuGdzBean) getIntent().getSerializableExtra("xgdz");
                                if(person != null){
                                    //选择地址
                                    qrxd_shouhuodizhi.setText(person.getStringdetailedAddress());
                                    qrxd_shoujihao.setText(person.getPhoneNumber());
                                    qrxd_shoujianren.setText(person.getConsignee());
                                    qrxd_lishugongsi.setText(person.getShippingAddress());

                                }else{
                                    qrxd_shouhuodizhi.setText(mRdizhi.getObject().getDetailedAddress());
                                    qrxd_shoujihao.setText(mRdizhi.getObject().getPhoneNumber());
                                    qrxd_shoujianren.setText(mRdizhi.getObject().getConsignee());
                                    qrxd_lishugongsi.setText(mRdizhi.getObject().getShippingAddress());
                                }
                                //适配器处理展示商品
                                commodityBean(commodity);
                                break;
                            case "210":

                                    AlertDialog dialog =  new  AlertDialog.Builder(SConfirmationSingleActivity.this)
                                            .setTitle("确认操作" )
                                            .setMessage("您还没设置默认地址,是否现在编辑")
                                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    intent.setClass(SConfirmationSingleActivity.this,SEditorsActivity.class);
                                                    intent.putExtra("dingDan", (Serializable) commodity);
                                                    startActivity(intent);
                                                    finish();
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();

                                                }
                                            })
                                            .create();
                                    dialog.show();

                                XiuGdzBean person1  = (XiuGdzBean) getIntent().getSerializableExtra("xgdz");
                                if(person1 != null){
                                    //选择地址
                                    qrxd_shouhuodizhi.setText(person1.getStringdetailedAddress());
                                    qrxd_shoujihao.setText(person1.getPhoneNumber());
                                    qrxd_shoujianren.setText(person1.getConsignee());
                                    qrxd_lishugongsi.setText(person1.getShippingAddress());
                                }
                                //适配器处理展示商品
                                commodityBean(commodity);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                    }
                });
    }
    //适配器处理展示商品
    private void commodityBean(List<DingDanBean.DingdanBean> dingdanBean) {
        if (commodity !=null) {
            //        件数 和  金额合计  控件赋值
            int a1 = commodity.get(0).getOrderNumber();
            jianshu2.setText(a1 + "");
            int a = Integer.parseInt(jianshu2.getText().toString());
            double tradeMoney1 = (commodity.get(0).getCommodityPrice());

            double heji = a * tradeMoney1;
            heji2.setText("￥" + heji);
        }else {
            return;
        }
        dingdanAdapter = new DingDanAdapter(SConfirmationSingleActivity.this ,dingdanBean);
        lv_querendingdan.setAdapter(dingdanAdapter);
    }
    @Override
    public String title_text() {
        return "确认下单";
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tijiaodd://提交订单
                //获取详情传过来的值
                for (int i =0;i<commodity.size();i++){
                    commodity_id = commodity.get(i).getCommodity_id();//商品id
                    counts = commodity.get(i).getOrderNumber();//商品数量
                    CommodityPrice = commodity.get(i).getCommodityPrice();//商品价格
                }

                Double jiage =  counts*CommodityPrice;
                if (jiage < 200){
                    ToastUtil.show(SConfirmationSingleActivity.this,"采购满200元才可以为隶属公司挣取积分哦!\n要不在逛逛呗!");
                    return;
                }


                int address_id1 = address_id;
                //立即支付
                int user_id = CommonData.user_id;//用户id
                int address_id = address_id1 ;//地址ID
                String payWay = zhifu;//支付方式
                String payAccount = CommonData.accounts;//支付账号
                String buyWay = peisong;//配送方式

                if (maijialiuyan.getText().toString().length()==0){
                    messages= "无";
                }else {
                    messages = maijialiuyan.getText().toString();
                }
                if(zhifu.equals("支付宝")) {
                    AlipayToPay(user_id, commodity_id, counts, address_id, payWay, buyWay, messages);
                }else if(zhifu.equals("微信")){
                    WeChatHayment(user_id, commodity_id, counts, address_id, payWay, buyWay, messages);
                }else  {
                    BalancePay(user_id, commodity_id,counts, address_id,payWay,buyWay,messages);
                }

                Log.e("111111111","用户id="+user_id+",地址ID="+address_id+
                        ",支付方式="+payWay+",支付账号="+payAccount+",商品id="+commodity_id+",商品数量="+counts
                        +"卖家留言="+messages  +"配送方式="+buyWay);
                break;
            case R.id.sjxx://收件地址
                intent.setClass(SConfirmationSingleActivity.this,SEditorsActivity.class);
                intent.putExtra("dingDan", (Serializable) commodity);
                startActivity(intent);
                finish();
                break;

            case R.id.dd_songhuo:
                dd_songhuo1.setImageResource(R.mipmap.xuanzhong);
                dd_ziqu1.setImageResource(R.mipmap.meixuanzhong);
                peisong ="送货";
                break;
            case R.id.dd_ziqu:
                dd_songhuo1.setImageResource(R.mipmap.meixuanzhong);
                dd_ziqu1.setImageResource(R.mipmap.xuanzhong);
                peisong ="自取";
                break;
            case R.id.dd_weixin:
                dd_weixin1.setImageResource(R.mipmap.wx2);
                dd_weixin2.setImageResource(R.mipmap.xuanzhong);
                dd_zhifubao1.setImageResource(R.mipmap.zfb1);
                dd_zhifubao2.setImageResource(R.mipmap.meixuanzhong);
                dd_yue1.setImageResource(R.mipmap.zhye1);
                dd_yue2.setImageResource(R.mipmap.meixuanzhong);
                zhifu ="微信";
                break;
            case R.id.dd_zhifubao:
                dd_weixin1.setImageResource(R.mipmap.wx1);
                dd_weixin2.setImageResource(R.mipmap.meixuanzhong);
                dd_zhifubao1.setImageResource(R.mipmap.zfb2);
                dd_zhifubao2.setImageResource(R.mipmap.xuanzhong);
                dd_yue1.setImageResource(R.mipmap.zhye1);
                dd_yue2.setImageResource(R.mipmap.meixuanzhong);
                zhifu ="支付宝";
                break;
            case R.id.dd_yue:
                dd_weixin1.setImageResource(R.mipmap.wx1);
                dd_weixin2.setImageResource(R.mipmap.meixuanzhong);
                dd_zhifubao1.setImageResource(R.mipmap.zfb1);
                dd_zhifubao2.setImageResource(R.mipmap.meixuanzhong);
                dd_yue1.setImageResource(R.mipmap.zhye2);
                dd_yue2.setImageResource(R.mipmap.xuanzhong);
                zhifu ="余额";
                break;


        }
    }
    //微信支付
    private void WeChatHayment(int user_id, int commodity_id, int counts, int address_id, String payWay, String buyWay, String messages) {
        final RequestParams params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id+"");
        params.addQueryStringParameter("commodity_id", commodity_id+"");
        params.addQueryStringParameter("counts", counts+"");
        params.addQueryStringParameter("address_id", address_id+"");
        params.addQueryStringParameter("payWay", payWay);
        params.addQueryStringParameter("BuyWay", buyWay);
        params.addQueryStringParameter("order_id", "0");
        params.addQueryStringParameter("messages", messages);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
        Log.e("请求数据=", "请求数据="+params);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.weixinURL+"createOrder.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_SUCCESS, responseInfo.result + "/");
                        String  result =  responseInfo.result;
                        //微信支付
                        if (!TextUtils.isEmpty(result)){
                            Gson gson = new Gson();//初始化
                            weChatHaymentBean = gson.fromJson(result, WeChatHaymentBean.class);
                            credential  = weChatHaymentBean.getResultMap();
                            final IWXAPI iwxapi = WXAPIFactory.createWXAPI(SConfirmationSingleActivity.this,CommonData.APP_ID); //初始化微信api
                            iwxapi.registerApp(CommonData.APP_ID); //注册appid  appid可以在开发平台获取
                            String partnerId = credential.getPartnerid();
                            String prepayId = credential.getPrepayid();
                            String nonceStr =  credential.getNoncestr();
                            String timeStamp = credential.getTimestamp();
                            String sign = credential.getSign();

                            PayReq request = new PayReq(); //调起微信APP的对象
                            //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                            request.appId = CommonData.APP_ID;
                            request.partnerId = partnerId;
                            request.prepayId = prepayId;
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = nonceStr;
                            request.timeStamp = timeStamp;
                            request.sign = sign;
                            iwxapi.sendReq(request);//发送调起微信的请求
                        }
                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_EXCEOTON, "失败="+error.toString() + "/"+ msg);
                    }
                });
    }

    //余额
    private void BalancePay(final int user_id, final int commodity_id,
                                        final int counts, final int comaddress_id,  final String payWay, final String BuyWay,
                                        final String messages) {

        RequestParams params = new RequestParams();

        params.addQueryStringParameter("user_id", user_id+"");
        params.addQueryStringParameter("commodity_id", commodity_id+"");
        params.addQueryStringParameter("counts", counts+"");
        params.addQueryStringParameter("address_id", comaddress_id+"");
        params.addQueryStringParameter("payWay", payWay);
        params.addQueryStringParameter("BuyWay", BuyWay);
        params.addQueryStringParameter("order_id", "0");
        params.addQueryStringParameter("messages", messages);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL+"APPbalancePay.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(SConfirmationSingleActivity.this);
                    }
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_SUCCESS, responseInfo.result + "/");
                        String  result =  responseInfo.result;
//                        intent.setClass(SConfirmationSingleActivity.this,SSettlementActivity.class);
//                        startActivity(intent);
//                        finish();

                        Gson gson = new Gson();//初始化
                        Bean bean = gson.fromJson(result, Bean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String credential  = bean.getState();
                        switch (credential) {
                            case "200":
//                                Intent intent = new Intent();
//                                intent.setClass(SConfirmationSingleActivity.this, SSettlementActivity.class);
//                                Bundle bundle= new Bundle();
//                                bundle.putString("heji2",heji2+"");
//                                intent.putExtras(bundle);
//                                startActivity(intent);
                                ToastUtil.show(SConfirmationSingleActivity.this,"余额支付成功");
                                finish();
                                break;
                            case "210":
                                ToastUtil.show(SConfirmationSingleActivity.this,"您的余额不足");
                                break;
                        }
                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_EXCEOTON, "失败="+error.toString() + "/"+ msg);
                    }
                });
    }

    //支付宝
    private void AlipayToPay(final int user_id, final int commodity_id,
                                        final int counts, final int address_id,
                                        final String payWay, final String buyWay,
                                        final String messages) {

        RequestParams params = new RequestParams();

        params.addQueryStringParameter("user_id", user_id+"");
        params.addQueryStringParameter("commodity_id", commodity_id+"");
        params.addQueryStringParameter("counts", counts+"");
        params.addQueryStringParameter("address_id", address_id+"");
        params.addQueryStringParameter("payWay", payWay);
        params.addQueryStringParameter("BuyWay", buyWay);
        params.addQueryStringParameter("order_id", "0");
        params.addQueryStringParameter("messages", messages);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
         Log.e("请求数据=", "请求数据="+params);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.alipayURL+"createOrder.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
//                        DialogUtil.start(SConfirmationSingleActivity.this);
                    }
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_SUCCESS, responseInfo.result + "/");
                        String  result =  responseInfo.result;
                        //支付宝支付
                        if (!TextUtils.isEmpty(result)){
                            Gson gson = new Gson();//初始化
                            alipayBean = gson.fromJson(result, AlipayBean.class);
                            String credential  = alipayBean.getResult();
                            Log.e("orderInfo=", credential);
                            alipay(credential);
                        }
                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_EXCEOTON, "失败="+error.toString() + "/"+ msg);
                    }
                });


    }

    private void alipay(final String orderInfo) {
        Runnable payRunnable  = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SConfirmationSingleActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);

            }
        };
        // 必须异步调用
        Thread payThread  = new Thread(payRunnable );
        payThread .start();
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(SConfirmationSingleActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(SConfirmationSingleActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

}
