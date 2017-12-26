package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yuqi.admin.py.BaseActivity;

import com.yuqi.admin.py.R;
import com.yuqi.admin.py.adapter.DingDanAdapter;
import com.yuqi.admin.py.alipay.PayResult;
import com.yuqi.admin.py.bean.AlipayBean;
import com.yuqi.admin.py.bean.DingDanBean;
import com.yuqi.admin.py.bean.DshowDefaultShippingAddressBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;

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



    LinearLayout sjxx;
    ListView lv_querendingdan;
    DingDanAdapter dingdanAdapter;

    Intent intent = new  Intent();
    List<DingDanBean.DingdanBean> commodity;

    String peisong ="支付宝";
    String peisong1 ="送货上门";
    String zhifu ="";

    private int commodity_id;
    private int counts;
    private String tradeMoney;
    private int address_id;
    private String messages;

    AlipayBean alipayBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_querenxiadan);

        //获取详情传过来的值
        commodity = (List<DingDanBean.DingdanBean>) getIntent().getSerializableExtra("dingDan");
        //获取默认地址
        showDefaultShippingAddressHttp(CommonData.user_id);

        init();
        //适配器处理展示商品
        commodityBean(commodity);
    }

    private void init() {
        lv_querendingdan = (ListView)findViewById(R.id.lv_querendingdan);

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

////        件数 和  金额合计  控件赋值
//        jianshu2.setText(commodity.get(0).getOrderNumber());
//        int a = Integer.parseInt(jianshu2.getText().toString());
//        double tradeMoney1=  ( a * (commodity.get(0).getCommodityPrice()));
//        heji2.setText("￥"+tradeMoney1);

    }
    //获取默认地址
    private void showDefaultShippingAddressHttp(int user_id) {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", user_id+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        Log.e("默认地址=",user_id+"");
        http.send(HttpRequest.HttpMethod.GET,
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
                        Log.e("默认地址=", responseInfo.result);
                        String  result =  responseInfo.result;
                        Gson gson = new Gson();//初始化
                        DshowDefaultShippingAddressBean mRdizhi = gson.fromJson(result, DshowDefaultShippingAddressBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                        address_id = mRdizhi.getObject().getId();
                        String state = mRdizhi.getState();
                        switch (state) {
                            case "200":
                                qrxd_shouhuodizhi=(TextView)findViewById(R.id.qrxd_shouhuodizhi);
                                qrxd_shoujihao=(TextView)findViewById(R.id.qrxd_shoujihao);
                                qrxd_shoujianren=(TextView)findViewById(R.id.qrxd_shoujianren);
                                qrxd_lishugongsi=(TextView)findViewById(R.id.qrxd_lishugongsi);
                                qrxd_shouhuodizhi.setText(mRdizhi.getObject().getDetailedAddress());
                                qrxd_shoujihao.setText(mRdizhi.getObject().getPhoneNumber());
                                qrxd_shoujianren.setText(mRdizhi.getObject().getConsignee());
                                qrxd_lishugongsi.setText(mRdizhi.getObject().getShippingAddress());

                                break;
                            case "210":
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        ToastUtil.show(SConfirmationSingleActivity.this,"网络异常");
                    }
                });
    }
    //适配器处理展示商品
    private void commodityBean(List<DingDanBean.DingdanBean> dingdanBean) {
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
                    tradeMoney = String.valueOf(commodity.get(i).getCommodityPrice());//订单金额是变量
                }
                int address_id1 = address_id;
//                tradeMoney = (counts * Integer.parseInt(tradeMoney))+"";
                tradeMoney = 1+"";
                //立即支付
                int user_id = CommonData.user_id;//用户id
                int address_id = address_id1 ;//地址ID
                String payWay = peisong;//支付方式
                String payAccount = CommonData.accounts;//支付账号
                String buyWay = peisong;//配送方式

                if (maijialiuyan.getText().toString().length()==0){
                    messages= "无";
                }else {
                    messages = maijialiuyan.getText().toString();
                }
                addShoppingtrolleyHttp(user_id, commodity_id,counts,address_id,payWay,buyWay,messages);

//                addShoppingtrolleyHttp(14, 4,2,2,"支付宝支付","送货上门","无");

                Log.e("111111111","用户id="+user_id+",地址ID="+address_id+
                        ",支付方式="+payWay+",支付账号="+payAccount+",商品id="+commodity_id+",商品数量="+counts
                        +"卖家留言="+messages  +"配送方式="+buyWay);

                break;
            case R.id.sjxx://收件地址
                intent.setClass(SConfirmationSingleActivity.this,SEditorsActivity.class);
                startActivity(intent);
                break;

            case R.id.dd_songhuo:
                dd_songhuo1.setImageResource(R.mipmap.xuanzhong);
                dd_ziqu1.setImageResource(R.mipmap.meixuanzhong);
                peisong1 ="送货";
                break;
            case R.id.dd_ziqu:
                dd_songhuo1.setImageResource(R.mipmap.meixuanzhong);
                dd_ziqu1.setImageResource(R.mipmap.xuanzhong);
                peisong1 ="自取";
                break;
            case R.id.dd_weixin:
                dd_weixin1.setImageResource(R.mipmap.wx2);
                dd_weixin2.setImageResource(R.mipmap.xuanzhong);
                dd_zhifubao1.setImageResource(R.mipmap.zfb1);
                dd_zhifubao2.setImageResource(R.mipmap.meixuanzhong);
                dd_yue1.setImageResource(R.mipmap.zhye1);
                dd_yue2.setImageResource(R.mipmap.meixuanzhong);
                peisong ="微信";
                break;
            case R.id.dd_zhifubao:
                dd_weixin1.setImageResource(R.mipmap.wx1);
                dd_weixin2.setImageResource(R.mipmap.meixuanzhong);
                dd_zhifubao1.setImageResource(R.mipmap.zfb2);
                dd_zhifubao2.setImageResource(R.mipmap.xuanzhong);
                dd_yue1.setImageResource(R.mipmap.zhye1);
                dd_yue2.setImageResource(R.mipmap.meixuanzhong);
                peisong ="支付宝";
                break;
            case R.id.dd_yue:
                dd_weixin1.setImageResource(R.mipmap.wx1);
                dd_weixin2.setImageResource(R.mipmap.meixuanzhong);
                dd_zhifubao1.setImageResource(R.mipmap.zfb1);
                dd_zhifubao2.setImageResource(R.mipmap.meixuanzhong);
                dd_yue1.setImageResource(R.mipmap.zhye2);
                dd_yue2.setImageResource(R.mipmap.xuanzhong);
                peisong ="支付宝";
                break;


        }
    }

    private void addShoppingtrolleyHttp(final int user_id, final int commodity_id,
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
                        //支付宝支付
                        if (!TextUtils.isEmpty(result)){
                            Gson gson = new Gson();//初始化
                            alipayBean = gson.fromJson(result, AlipayBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
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
