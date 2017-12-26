package com.yuqi.admin.py.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.yuqi.admin.py.ObserverManager;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.APPqueryCommodityBean;
import com.yuqi.admin.py.bean.getShoppingtrolleyBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;
import com.yuqi.admin.py.view.ShoppCarViewBItem;
import com.yuqi.admin.py.wxapi.ThridSale;
import com.yuqi.admin.py.adapter.ShangPinAdapter;
import com.yuqi.admin.py.bean.ShoppingCarBean;
import com.yuqi.admin.py.view.MultiGridView;

/**
 * Created by Administrator on 2017/12/15.
 *  购物车
 */
public class SShoppingCartActivity extends BaseActivity{
    private ShangPinAdapter spAdapter;
    private MultiGridView gwc_gv;
    private TextView Jrgwc;
    private LinearLayout container,none;

    private getShoppingtrolleyBean CarBeanData;
    private LinearLayout mGallery;
    private LayoutInflater mInflater;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_gwc_gouwuche);
        mContext = this;
        init();
        getShoppingtrolleyHTTP();
    }

    private void getShoppingtrolleyHTTP() {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", CommonData.user_id+"");

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.POST,
                CommonData.URL + "getShoppingtrolley.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(SShoppingCartActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e("购物车=", responseInfo.result);
                        String result = responseInfo.result;
                        Gson gson = new Gson();//初始化
                        CarBeanData = gson.fromJson(result, getShoppingtrolleyBean.class);//存商品数据
                        String state = CarBeanData.getState();
                        switch (state) {
                            case "200":
//                                addView(CarBeanData);
//                                ShoppCarViewBItem bItem = new ShoppCarViewBItem(mContext);
                                initView();
//                                container.addView();



                                break;
                            case "210":
                                none.setVisibility(View.VISIBLE);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        ToastUtil.show(SShoppingCartActivity.this,"网络异常");
                    }
                });

    }

    private void init() {
        gwc_gv = (MultiGridView)findViewById(R.id.gwc_gv);
        none = (LinearLayout) findViewById(R.id.ll_none);
        container = (LinearLayout) findViewById(R.id.layout_container);

    }


    private void initView() {
        if(CarBeanData != null) {
            ShoppCarViewBItem item = new ShoppCarViewBItem(mContext, CarBeanData);
            container.addView(item);
        }

//        ObserverManager manager = ObserverManager.getInstance();
//        /**
//         * 如果没有购物车的数据就显示无数据的页面
//         *
//         * 如果有购物车的数据，就去获取购物车的数据并显示到页面上去
//         */
//        if(manager.getEntity().getGoodsLis().size()==0){
//            none.setVisibility(View.VISIBLE);
//        }else{
//                for(ShoppingCarBean.ShoppingcarData data : manager.getEntity().getGoodsLis()){
//                //在container里面添加通过数据新建的view
//
//
//                }
//        }
//        spAdapter = new ShangPinZhAdapter(SShoppingCartActivity.this);
//        gwc_gv.setAdapter(spAdapter);
    }

    @Override
    public String title_text() {
        return "购物车";
    }

    @Override
    public void onClick(View v) {

    }


}
