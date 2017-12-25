package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.yuqi.admin.py.adapter.AddressAdapter;
import com.yuqi.admin.py.bean.Bean;
import com.yuqi.admin.py.bean.DshowShippingAddressALLBean;
import com.yuqi.admin.py.bean.XiuGdzBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;

/**
 * Created by Administrator on 2017/12/21.
 * 添加收货地址
 */
public class SEditors1Activity extends BaseActivity{
    private EditText shoujianrenxingming,shoujihao,shouhuodizhi,xiangxidizhi;
    private ImageView moren;
    private TextView submit;
    private boolean moRen =true;
    private int moR;

    private XiuGdzBean person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_dizhixinxixiugai);

        init();
    }

    private void init() {
        shoujianrenxingming = (EditText)findViewById(R.id.shoujianrenxingming);
        shoujihao = (EditText)findViewById(R.id.shoujihao);
        shouhuodizhi = (EditText)findViewById(R.id.shouhuodizhi);
        xiangxidizhi = (EditText)findViewById(R.id.xiangxidizhi);
        moren = (ImageView) findViewById(R.id.moren);

        submit = (TextView)findViewById(R.id.submit);
        submit.setVisibility(View.VISIBLE);
        submit.setText("完成");

        moren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moRen){
                    moren.setImageResource(R.mipmap.moren);
                    moR = 0;
                }else {
                    moren.setImageResource(R.mipmap.moren1);
                    moR = 1;
                }

            }
        });

    }

    @Override
    public String title_text() {
        return "添加收货地址";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                updateShippingAddressHTTP();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateShippingAddressHTTP();
    }


    private void updateShippingAddressHTTP() {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", CommonData.user_id+"");
        params1.addQueryStringParameter("consignee", shoujianrenxingming.getText().toString());
        params1.addQueryStringParameter("phoneNumber", shoujihao.getText().toString());
        params1.addQueryStringParameter("shippingAddress", shouhuodizhi.getText().toString());
        params1.addQueryStringParameter("detailedAddress", xiangxidizhi.getText().toString());
        params1.addQueryStringParameter("ifDefaultAddress", moRen+"" );

        Log.e("请求=",moRen+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.GET,
                CommonData.URL + "addShippingAddress.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
//                        DialogUtil.start(SEditors2Activity.this);
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
                        Bean xiugai = gson.fromJson(result, Bean.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：

                        String state = xiugai.getState();
                        switch (state) {
                            case "200":
                                ToastUtil.show(SEditors1Activity.this, "地址添加成功");
                                finish();
                                break;
                            case "210":
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        ToastUtil.show(SEditors1Activity.this, CommonData.REQUEST_EXCEOTON);
                    }
                });

    }
}
