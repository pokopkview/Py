package com.yuqi.admin.py.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.yuqi.admin.py.bean.Bean;
import com.yuqi.admin.py.bean.DingDanBean;
import com.yuqi.admin.py.bean.MyPendingPaymentBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ImageUtil;
import com.yuqi.admin.py.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 *     待收货
 */
public class MyGoodsReceiptActivity extends BaseActivity{
    private LinearLayout ll_wait;
    private List<DingDanBean.DingdanBean> dingDan;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wait);
        initView();
        // 获取待付款
        MyPendingPaymentHTTP();
    }

    private void MyPendingPaymentHTTP() {

        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id",CommonData.user_id+"");
        params1.addQueryStringParameter("orderStatus","待收货");

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL + "getOrderByStatus.action",
                params1,
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
                        Log.e(CommonData.REQUEST_SUCCESS, responseInfo.result);
                        String result = responseInfo.result;

                        Gson gson = new Gson();//初始化
                        MyPendingPaymentBean morendizhi = gson.fromJson(result, MyPendingPaymentBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String state = morendizhi.getState();
                        switch (state) {
                            case "200":
                                MyPendingPaymentView(morendizhi);
                                break;
                            case "210":
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        ToastUtil.show(MyGoodsReceiptActivity.this, CommonData.REQUEST_EXCEOTON);
                    }
                });

    }

    private void MyPendingPaymentView(MyPendingPaymentBean PendingPayment) {
        List<MyPendingPaymentBean.OrdersBean> bean =  PendingPayment.getOrders();
        List<String> commodityPictures = PendingPayment.getCommodityPictures();
        List<Integer> counts = PendingPayment.getCounts();
        List<Integer> commodity_ids = PendingPayment.getCommodity_ids();
        List<Double> commodityPrice = PendingPayment.getCommodityPrice();
        List<String> commodityName = PendingPayment.getCommodityName();

        View v;
        for(int i = 0 ; i < PendingPayment.getOrders().size() ;i++) {
            v = View.inflate(MyGoodsReceiptActivity.this, R.layout.item_wait, null);

            final LinearLayout ll_quanbu = (LinearLayout)v.findViewById(R.id.ll_quanbu1);
            TextView wait_dingdanhao = (TextView) v.findViewById(R.id.wait_dingdanhao);
            TextView  wait_shijian = (TextView) v.findViewById(R.id.wait_shijian);
            ImageView wait_tupian = (ImageView) v.findViewById(R.id.wait_tupian);
            TextView  wait_miaoshu = (TextView) v.findViewById(R.id.wait_miaoshu);
            TextView  wait_jiage = (TextView) v.findViewById(R.id.wait_jiage);
            TextView  wait_shuliang = (TextView) v.findViewById(R.id.wait_shuliang);
            TextView  wait_jindu = (TextView) v.findViewById(R.id.wait_jindu);
            TextView  wait_jianshu = (TextView) v.findViewById(R.id.wait_jianshu);
            TextView  wait_zongjinge = (TextView) v.findViewById(R.id.wait_zongjinge);

            TextView  wait_heise = (TextView) v.findViewById(R.id.wait_heise);
            TextView  wait_huangse = (TextView) v.findViewById(R.id.wait_huangse);
            wait_heise.setVisibility(View.GONE);
            wait_huangse.setText("确认收货");


            final int order_id = bean.get(i).getOrder_id();
            final int commodity_ids1 = commodity_ids.get(i);
            final int counts1 = counts.get(i);
            final String pictures = commodityPictures.get(i);
            final String name =  commodityName.get(i);
            final double price = bean.get(i).getOrderPrice()/counts.get(i);

            wait_dingdanhao.setText("订单编号:"+bean.get(i).getOrder_number());
            wait_shijian.setText(bean.get(i).getCreateTime());
            ImageUtil.loadImg(wait_tupian, commodityPictures.get(i));
            wait_miaoshu.setText(commodityName.get(i));
            double a = bean.get(i).getOrderPrice()/counts.get(i);
            wait_jiage.setText("￥"+a);//单价价格
            wait_shuliang.setText(""+counts.get(i));
            wait_jianshu.setText(counts.get(i)+"");
            double b = a*counts.get(i);//总价格
            wait_zongjinge.setText(b+"");
            wait_jindu.setText("卖家已发货");
            ll_wait.addView(v);

            //确认收货
            wait_huangse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog =  new  AlertDialog.Builder(MyGoodsReceiptActivity.this)
                            .setTitle("确认操作" )
                            .setMessage("确认已收货!" )
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    QueRenShouHuo(order_id,ll_quanbu);
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

                }
            });
        }
    }

    private void QueRenShouHuo(int order_id, final LinearLayout ll_quanbu) {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("order_id",order_id+"");
        params1.addQueryStringParameter("orderStatus",3+"");

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL + "deleteOrderByID.action",
                params1,
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
                        Log.e(CommonData.REQUEST_SUCCESS, responseInfo.result);
                        String result = responseInfo.result;

                        Gson gson = new Gson();//初始化
                        Bean morendizhi = gson.fromJson(result, Bean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String state = morendizhi.getState();
                        switch (state) {
                            case "200":
                                ToastUtil.show(MyGoodsReceiptActivity.this, "操作成功");
                                ll_quanbu.setVisibility(View.GONE);
                                break;
                            case "210":
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        ToastUtil.show(MyGoodsReceiptActivity.this, CommonData.REQUEST_EXCEOTON);
                    }
                });
    }


    private void initView() {
        ll_wait = (LinearLayout) findViewById(R.id.ll_wait);
    }

    @Override
    public String title_text() {
        return "待收货";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_wait :

                break;
        }
    }





}
