//package com.yuqi.admin.py.adapter;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.yuqi.admin.py.R;
//import com.yuqi.admin.py.bean.LoginBean;
//
///**
// * Created by Administrator on 2017/11/29.
// */
//public class PurchaseAdapter extends BaseAdapter {
//
//    private Context context;
//    private LoginBean data;
//
//    public PurchaseAdapter(Context context,  LoginBean data) {
//        this.context = context;
//        this.data = data;
//    }
//
//    @Override
//    public int getCount() {
//        return 10;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return data.getPrList().get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHold hold = null;
//
//        if (convertView == null) {
//            hold = new ViewHold();
//            convertView = View.inflate(context, R.layout.item_purchase, null);
//            hold.purchase_time = (TextView) convertView.findViewById(R.id.purchase_time);
//            hold.purchase_content = (TextView) convertView.findViewById(R.id.purchase_content);
//            hold.purchase_name = (TextView) convertView.findViewById(R.id.purchase_name);
//            hold.purchase_identity = (TextView) convertView.findViewById(R.id.purchase_identity);
//            hold.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
//            convertView.setTag(hold);
//        } else {
//            hold = (ViewHold) convertView.getTag();
//        }
//
//
//        hold.purchase_name.setText(data.getPrList().get(position).getName());
//        hold.purchase_content.setText(data.getPrList().get(position).getIntro());
//
//
//        hold.purchase_time.setText("10:32");
//
//        if (data.getPrList().size()>=1){
//            hold.tv_1.setVisibility(View.VISIBLE);
//        }
//
//        int finishState = data.getPrList().get(position).getFinishState();
//        if (finishState==1){
//            hold.purchase_identity.setBackgroundResource(R.mipmap.cg1);
//        }else {
//            hold.purchase_identity.setBackgroundResource(R.mipmap.cg2);
//        }
//
//        return convertView;
//
//
//    }
//
//    class ViewHold {
//        TextView purchase_time, purchase_content, purchase_name, purchase_identity, tv_1;
//    }
//}
//
