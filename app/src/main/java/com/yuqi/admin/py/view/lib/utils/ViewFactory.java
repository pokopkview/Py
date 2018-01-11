package com.yuqi.admin.py.view.lib.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.view.ResizableImageView;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 *
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
//		ResizableImageView imageView = (ResizableImageView)LayoutInflater.from(context).inflate(
//				R.layout.view_banner1, null);
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}


}
