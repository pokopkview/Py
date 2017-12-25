package com.yuqi.admin.py.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.activity.SEditors2Activity;
import com.yuqi.admin.py.bean.Bean;
import com.yuqi.admin.py.bean.DshowShippingAddressALLBean;
import com.yuqi.admin.py.bean.XiuGdzBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/23.
 *
 * 地址 管理 适配器
 */
public class AddressAdapter  extends BaseAdapter {
    private Context context;
    private DshowShippingAddressALLBean data;

    public AddressAdapter(Context context,DshowShippingAddressALLBean data ) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.getObject().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold hold = null;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = View.inflate(context, R.layout.item_dizhixinxi, null);
            hold.dz_xm =(TextView) convertView.findViewById(R.id.dz_xm);
            hold.dz_sjh =(TextView) convertView.findViewById(R.id.dz_sjh);
            hold.dz_lsgs =(TextView) convertView.findViewById(R.id.dz_lsgs);
            hold.dz_xxdz =(TextView) convertView.findViewById(R.id.dz_xxdz);
            hold.dz_bj =(ImageView) convertView.findViewById(R.id.dz_bj);
            hold.dz_sc =(ImageView) convertView.findViewById(R.id.dz_sc);
            hold.dz_mr =(ImageView) convertView.findViewById(R.id.dz_mr);

            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        final ViewHold finalHold = hold;

        hold.dz_xm.setText(data.getObject().get(position).getConsignee());
        hold.dz_sjh.setText(data.getObject().get(position).getPhoneNumber());
        hold.dz_lsgs.setText(data.getObject().get(position).getShippingAddress());
        hold.dz_xxdz.setText(data.getObject().get(position).getDetailedAddress());

        int ifDefaultAddress = data.getObject().get(position).getIfDefaultAddress();

        if (ifDefaultAddress==1){
            finalHold.dz_mr.setImageResource(R.mipmap.xuanzhong);
        }else {
            finalHold.dz_mr.setImageResource(R.mipmap.meixuanzhong);
        }

        hold.dz_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiuGdzBean xgdz = new XiuGdzBean();
                xgdz.setId(data.getObject().get(position).getId()+"");
                xgdz.setConsignee( finalHold.dz_xm.getText().toString());
                xgdz.setPhoneNumber(finalHold.dz_sjh.getText().toString());
                xgdz.setShippingAddress(finalHold.dz_lsgs.getText().toString());
                xgdz.setStringdetailedAddress(finalHold.dz_xxdz.getText().toString());
                xgdz.setIfDefaultAddress(data.getObject().get(position).getIfDefaultAddress()+"");
                Intent intent = new Intent(context, SEditors2Activity.class);
                intent.putExtra("xgdz", (Serializable) xgdz);
                context.startActivity(intent);
                data.getObject().clear();
            }
        });

        hold.dz_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteShippingAddress(data.getObject().get(position).getId());
            }
        });

//        BitmapUtils bitmapUtils = new BitmapUtils(context);
//        // 加载网络图片
//        bitmapUtils.display(hold.qrddxx_tupian,data.get(position).getPicture());

        return convertView;
    }

    private void deleteShippingAddress(int id) {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("id",id+"");

        Log.e("请求=",id+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.GET,
                CommonData.URL + "deleteShippingAddress.action",
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
                                ToastUtil.show(context, "删除成功");
                                break;
                            case "210":
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        ToastUtil.show(context, CommonData.REQUEST_EXCEOTON);
                    }
                });
    }


    class ViewHold {
        TextView dz_xm,dz_sjh,dz_lsgs,dz_xxdz;
        ImageView dz_bj,dz_sc,dz_mr;
    }
}
