package com.yuqi.admin.py.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.ObserverManager;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.ThridSale;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_gwc_gouwuche);

        init();
    }

    private void init() {
        gwc_gv = (MultiGridView)findViewById(R.id.gwc_gv);
        none = (LinearLayout) findViewById(R.id.ll_none);
        container = (LinearLayout) findViewById(R.id.shop_container);
        initView();
    }

    private void initView() {
        ObserverManager manager = ObserverManager.getInstance();
        /**
         * 如果没有购物车的数据就显示无数据的页面
         *
         * 如果有购物车的数据，就去获取购物车的数据并显示到页面上去
         */
        if(manager.getEntity().getGoodsLis().size()==0){
            none.setVisibility(View.VISIBLE);
        }else{
                for(ShoppingCarBean.ShoppingcarData data : manager.getEntity().getGoodsLis()){
                //在container里面添加通过数据新建的view
//
                    ThridSale.AliPay("");
                }
        }
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
