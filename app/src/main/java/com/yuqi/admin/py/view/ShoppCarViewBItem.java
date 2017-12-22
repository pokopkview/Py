package com.yuqi.admin.py.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuqi.admin.py.ObserverManager;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.ShoppingCarBean;

/**
 * Created by zhanghongwei on 2017/12/22.
 */

public class ShoppCarViewBItem extends RelativeLayout {

    private ImageView iv_select;
    private TextView tv_type,tv_success;
    private LinearLayout shop_container;
    private ShoppingCarBean bean;
    private Context mContext;

    private ShoppCarViewBItem shoppCarViewBItem;

    public ShoppCarViewBItem(Context context,ShoppingCarBean bean) {
        this(context,null,null);
    }

    public ShoppCarViewBItem(Context context, AttributeSet attrs,ShoppingCarBean bean) {
        this(context, attrs,0,null);
    }

    public ShoppCarViewBItem(Context context, AttributeSet attrs, int defStyleAttr,ShoppingCarBean bean) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_gwc_bianji,this);
        shoppCarViewBItem = this;
        this.bean = bean;
        initView();
    }
    private void initView(){
        iv_select = (ImageView) shoppCarViewBItem.findViewById(R.id.iv_select);
        tv_success = (TextView) shoppCarViewBItem.findViewById(R.id.tv_success);
        tv_type = (TextView) shoppCarViewBItem.findViewById(R.id.tv_type);
        shop_container = (LinearLayout) shoppCarViewBItem.findViewById(R.id.shop_container);



        tv_success.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }

    private void CreateView(){
        for(ShoppingCarBean.ShoppingcarData data : bean.getGoodsLis()){
            if(data != null){
                ShoppCarViewSItem shoppCarViewSItem = new ShoppCarViewSItem(mContext,data);
                shop_container.addView(shoppCarViewSItem);
            }
        }
    }




}
