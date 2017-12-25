package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.adapter.AddressAdapter;
import com.yuqi.admin.py.bean.DshowShippingAddressALLBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.ToastUtil;

/**
 * Created by Administrator on 2017/12/21.
 *
 * 收货地址管理
 */
public class SEditorsActivity extends BaseActivity  {
    private ListView lv_tianxieshouhuodizhi;
    private AddressAdapter ress;
    DshowShippingAddressALLBean syDizhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_dizhibianji);
        init();

        AddressAdapterHTTP();
        // 在onCreate()中开启线程
        new Thread(new GameThread()).start();
    }

    class GameThread  implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // 使用postInvalidate可以直接在线程中更新界面
                AddressAdapterHTTP();
            }
        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        AddressAdapterHTTP();
//    }



    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void AddressAdapterHTTP() {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", CommonData.user_id+"");

        Log.e(CommonData.REQUEST_PARAMETER,"user_id"+ CommonData.user_id);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.GET,
                CommonData.URL + "showShippingAddressALL.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
//                        DialogUtil.start(SEditorsActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
//                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        DialogUtil.finish();
                        Log.e(CommonData.REQUEST_SUCCESS, responseInfo.result);
                        String  result =  responseInfo.result;
                        Gson gson = new Gson();//初始化
                        syDizhi = gson.fromJson(result, DshowShippingAddressALLBean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String state = syDizhi.getState();
                        switch (state) {
                            case "200":
                                //适配器处理展示所以地址
                                showShippingAddressALL(syDizhi);
                                break;
                            case "210":
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
//                        DialogUtil.finish();
                        ToastUtil.show(SEditorsActivity.this,CommonData.REQUEST_EXCEOTON);
                    }
                });

    }

    private void showShippingAddressALL(DshowShippingAddressALLBean syDizhi) {
        ress = new AddressAdapter(SEditorsActivity.this ,syDizhi);
        lv_tianxieshouhuodizhi.setAdapter(ress);
    }

    private void init() {
        lv_tianxieshouhuodizhi = (ListView)findViewById(R.id.lv_tianxieshouhuodizhi);
    }

    @Override
    public String title_text() {
        return "管理收货地址";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dzbj_baocun:
                Intent intent = new Intent();
                intent.setClass(SEditorsActivity.this,SEditors1Activity.class);
                startActivity(intent);
                break;
        }
    }


}
