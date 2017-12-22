package com.yuqi.admin.py.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.adapter.DingDanAdapter;
import com.yuqi.admin.py.adapter.ShangPinzhAdapter;
import com.yuqi.admin.py.bean.APPCommodityBean;
import com.yuqi.admin.py.bean.DingDanBean;
import com.yuqi.admin.py.data.CommonData;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 *      确认下单
 */
public class SConfirmationSingleActivity extends BaseActivity {
    private ImageView dd_songhuo1,dd_ziqu1,dd_weixin1,dd_weixin2,
            dd_zhifubao1,dd_zhifubao2,dd_yue1,dd_yue2;

    LinearLayout sjxx;
    ListView lv_querendingdan;
    DingDanAdapter dingdanAdapter;

    Intent intent = new  Intent();
    List<DingDanBean.DingdanBean> commodity;

    String peisong ="";
    String zhifu ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spdd_querenxiadan);
        //获取详情传过来的值
        commodity = (List<DingDanBean.DingdanBean>) getIntent().getSerializableExtra("dingDan");


        init();
        //展示商品
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




    }
    //展示商品
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
            case R.id.tijiaodd:
                //获取详情传过来的值
                for (int i =0;i<commodity.size();i++){
                    int commodity_id = commodity.get(i).getCommodity_id();
                    int orderNumber = commodity.get(i).getOrderNumber();
                    int orderPrice = commodity.get(i).getCommodityPrice();//订单金额是变量
                }

                //立即支付
                int user_id = CommonData.user_id;//用户id
                int address_id = CommonData.user_id;//地址ID
                int payWay = 1;//支付方式
                int payAccount = 1;//支付账号

//                addShoppingtrolleyHttp(user_id,commodity_id,orderNumber,orderPrice,address_id,payWay,payAccount);

                intent.setClass(SConfirmationSingleActivity.this,SSettlementActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sjxx:
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



}
