package com.yuqi.admin.py.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuqi.admin.py.ObserverManager;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.ShoppingCarBean;

import org.w3c.dom.Text;

/**
 * Created by zhanghongwei on 2017/12/22.
 */

public class ShoppCarViewSItem extends LinearLayout {

    private ImageView iv_11,iv_22,iv_33,iv_55;
    private EditText et_44;
    private TextView tv_delete;
    private ShoppingCarBean.ShoppingcarData data;

    public ShoppCarViewSItem(Context context, ShoppingCarBean.ShoppingcarData data) {
        this(context,null,null);
    }

    public ShoppCarViewSItem(Context context, @Nullable AttributeSet attrs,ShoppingCarBean.ShoppingcarData data) {
        this(context, attrs,0,null);
    }

    public ShoppCarViewSItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr,ShoppingCarBean.ShoppingcarData data) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_gwc_weibianji,this);
        this.data = data;
    }

    private void initView(){
        final ObserverManager manager = ObserverManager.getInstance();
        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_55 = (ImageView) findViewById(R.id.iv_55);
        et_44 = (EditText) findViewById(R.id.et_44);
        tv_delete = (TextView) findViewById(R.id.tv_delete);

        /**
         * 将data的数据设定到上面的控件上去，并添加相对于的点击事件
         */

        tv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.removeGoods(12);
                manager.changeGoodsNum(12,2);
            }
        });



    }





}
