package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

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

        jianshu2 = (TextView)findViewById(R.id.jianshu2);
        heji2 = (TextView)findViewById(R.id.heji2);
        maijialiuyan= (EditText)findViewById(R.id.maijialiuyan1);
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
                    tradeMoney = commodity.get(i).getCommodityPrice()+"";//订单金额是变量
                    tradeMoney = commodity.get(i).getCommodityPrice()+"";//订单金额是变量
                }
                int address_id1 = address_id;
                tradeMoney = (counts * Integer.parseInt(tradeMoney))+"";
//                //件数 和  金额合计  控件赋值
//                jianshu2.setText(counts);
//                heji2.setText("￥"+tradeMoney);
                //立即支付
                int user_id = CommonData.user_id;//用户id
                int address_id = address_id1 ;//地址ID
                String payWay = peisong;//支付方式
                String payAccount = CommonData.accounts;//支付账号

                if (maijialiuyan.getText().toString().length()==0){
                    messages= "无";
                }else {
                    messages = maijialiuyan.getText().toString();
                }
                addShoppingtrolleyHttp(user_id, commodity_id,counts,address_id,payWay,payAccount,tradeMoney,messages);

                Log.e("111111111","用户id="+user_id+",地址ID="+address_id+
                        ",支付方式="+payWay+",支付账号="+payAccount+",商品id="+commodity_id+",商品数量="+counts
                        +",总额="+tradeMoney+",卖家留言="+messages);

                break;
            case R.id.sjxx://收件地址
                intent.setClass(SConfirmationSingleActivity.this,SEditorsActivity.class);
                startActivity(intent);
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

    private void addShoppingtrolleyHttp(final int user_id, final int commodity_id, final int counts, final int address_id, final String payWay, final String payAccount, final String tradeMoney, final String messages) {

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id+"");
        params.addQueryStringParameter("commodity_id", commodity_id+"");
        params.addQueryStringParameter("counts", counts+"");
        params.addQueryStringParameter("address_id", address_id+"");
        params.addQueryStringParameter("payWay", payWay);
        params.addQueryStringParameter("payAccount", payAccount);
        params.addQueryStringParameter("tradeMoney", tradeMoney );
        params.addQueryStringParameter("messages", messages);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000*10);
        Log.e("请求数据=", "请求数据="+params);
        http.send(HttpRequest.HttpMethod.GET,
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

                        Gson gson = new Gson();//初始化
                        alipayBean = gson.fromJson(result, AlipayBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                        String credential  = alipayBean.getResult();
                        Log.e("orderInfo=", credential);
                        alipay(credential);

                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_EXCEOTON, "失败="+error.toString() + "/"+ msg);
                    }
                });


    }


    private void alipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SConfirmationSingleActivity.this);
                //传入支付订单信息,设置ture表示显示支付的loading
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.e("调起支付界面=", result.toString() + "/");
                Message msg = new Message();
                Log.e("支付宝返回结果=",msg + "/");
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    //PayResult非常简单的工具类,把map里的结果取出来(来自支付宝demo)
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    //对于支付结果，请商户依赖服务端的异步通知结果
                    //同步通知结果，仅作为支付结束的通知
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();//状态码
                    String memo = payResult.getMemo();//附加信息,如果不为空可以提示该内容
                    switch (resultStatus) {
                        case "9000":
                            //支付成功
                            break;
                        case "8000":
                            //支付结果确认中
                            break;
                        case "4000":
                            //订单支付失败
                            break;
                        case "5000":
                            //重复请求
                            break;
                        case "6001":
                            //用户中途取消
                            break;
                        case "6002":
                            //网络连接出错
                            break;
                        case "6004":
                            //支付结果未知,请查询订单
                            break;
                        default:
                            //其它支付错误
                            break;
                    }
                    Log.e("TAG", "handleMessage: " + payResult.toString());
                    break;
                }
                default:
                    break;
            }
        }
    };
}
