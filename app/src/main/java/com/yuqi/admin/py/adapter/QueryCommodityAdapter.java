package com.yuqi.admin.py.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.BitmapUtils;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.APPqueryCommodityBean;
import com.yuqi.admin.py.view.ResizableImageView;

/**
 * Created by Administrator on 2017/12/20.
 * 商品详情界面 底部图片
 */
public class QueryCommodityAdapter extends BaseAdapter {
    private Context context;
    APPqueryCommodityBean data ;


    public QueryCommodityAdapter(Context context, APPqueryCommodityBean data ) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.getObject().getCommodityparticulars().size();

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
            convertView = View.inflate(context, R.layout.view_cycle_viewpager_indicator, null);

            hold.image_indicator =(ResizableImageView) convertView.findViewById(R.id.image_indicator);

            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }

        BitmapUtils bitmapUtils = new BitmapUtils(context);
        // 加载网络图片
        bitmapUtils.display(hold.image_indicator,data.getObject().getCommodityparticulars().get(position).getPicture());


        return convertView;
    }



    class ViewHold {
        ResizableImageView image_indicator;
    }
}
