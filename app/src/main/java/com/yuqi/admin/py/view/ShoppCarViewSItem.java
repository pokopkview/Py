package com.yuqi.admin.py.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuqi.admin.py.ObserverManager;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.ShoppingCarBean;
import com.yuqi.admin.py.bean.getShoppingtrolleyBean;
import com.yuqi.admin.py.interfaces.Deletesth;

import org.w3c.dom.Text;

/**
 * Created by zhanghongwei on 2017/12/22.
 */

public class ShoppCarViewSItem extends LinearLayout {

    private ImageView iv_11,iv_22,iv_33,iv_55;
    private EditText et_44;
    private TextView tv_delete,tv_singleprice;
    private getShoppingtrolleyBean.ObjectEntity.手机Entity data;
    private Context mContext;
    private Deletesth deletesth;
    private ShoppCarViewSItem shoppCarViewBItem;

    public ShoppCarViewSItem(Context context, getShoppingtrolleyBean.ObjectEntity.手机Entity data) {
        this(context,null,data);
    }

    public ShoppCarViewSItem(Context context, @Nullable AttributeSet attrs,getShoppingtrolleyBean.ObjectEntity.手机Entity data) {
        this(context, attrs,0,data);
    }

    public ShoppCarViewSItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr,getShoppingtrolleyBean.ObjectEntity.手机Entity data) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        shoppCarViewBItem = this;
        LayoutInflater.from(context).inflate(R.layout.item_gwc_weibianji,this);
        this.data = data;
        initView();
    }
    public void setCallback(Deletesth deletesth){
        this.deletesth = deletesth;
    }

    private void initView(){
        final ObserverManager manager = ObserverManager.getInstance();
        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_55 = (ImageView) findViewById(R.id.iv_55);
        et_44 = (EditText) findViewById(R.id.et_44);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_singleprice = (TextView) findViewById(R.id.tv_singleprice);
        /**
         * 将data的数据设定到上面的控件上去，并添加相对于的点击事件
         */
        Glide.with(mContext)
                .load(data.getPicture())
                .placeholder(R.mipmap.icon_empty)
                .error(R.mipmap.icon_error)
                .into(iv_22);

        et_44.setText(String.valueOf(data.getCommodityNumber()));
        tv_singleprice.setText(String.valueOf(data.getCommodityPrice()));
        iv_33.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_44.getText().toString().equals("0")) {
                    System.out.println(et_44.getText().toString());
                    et_44.setText(String.valueOf(Integer.valueOf(et_44.getText().toString()) - 1));
                }
            }
        });
        iv_55.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_44.setText(String.valueOf(Integer.valueOf(et_44.getText().toString())+1));
            }
        });
        tv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog =  new  AlertDialog.Builder(mContext)
                        .setTitle("确认操作" )
                        .setMessage("确定删除商品吗？" )
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletesth.deletGoodsId(data.getCommodity_id(),shoppCarViewBItem);
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






//        tv_delete.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                manager.removeGoods(12);
//                manager.changeGoodsNum(12,2);
//            }
//        });



    }





}
