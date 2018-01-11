package com.yuqi.admin.py.view.lib;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义高度的viewpapger
 */
public class BaseViewPager extends ViewPager {
	private boolean scrollable = true;

	public BaseViewPager(Context context) {
		super(context);
	}

	public BaseViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置viewpager是否可以滚动
	 * 
	 * @param enable
	 */
	public void setScrollable(boolean enable) {
		scrollable = enable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scrollable) {
			return super.onInterceptTouchEvent(event);
		} else {
			return false;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//		//获取到宽度的模式
//		int width_mode=MeasureSpec.getMode(widthMeasureSpec);
//		//获取到屏幕的宽度
//		int width=MeasureSpec.getSize(widthMeasureSpec);
//		//高度的大小
//		int height=0;
//		//说明是填充父窗体
//		double scale=203.0/381.00;
//		if(width_mode==MeasureSpec.EXACTLY){
//			height= (int)(width*scale+0.5f);
//		}
//		widthMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
//		heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
//		super.onMeasure(widthMeasureSpec,heightMeasureSpec);


		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
				height = (int)(h*0.75);
		}
		int width = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), heightMeasureSpec);
			int h = child.getMeasuredWidth();
			if (h > width)
				width = (int)(h*0.835);
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


}