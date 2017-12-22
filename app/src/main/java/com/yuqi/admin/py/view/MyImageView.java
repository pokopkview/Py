//package com.yuqi.admin.py.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//
///**
// * Created by Administrator on 2017/12/5.
// */
//public class MyImageView {
//    /**
//     * 相片按相框的比例动态缩放
//     * @param context
//     * @param width 模板宽度
//     * @param height 模板高度
//     * @return
//     */
//    public static Bitmap upImageSize(Context context, xBitmap bmp, int width, int height) {
//        if(bmp==null){
//            return null;
//        }
//        // 计算比例
//        float scaleX = (float)width / bmp.getWidth();// 宽的比例
//        float scaleY = (float)height / bmp.getHeight();// 高的比例
//        //新的宽高
//        int newW = 0;
//        int newH = 0;
//        if(scaleX > scaleY){
//            newW = (int) (bmp.getWidth() * scaleX);
//            newH = (int) (bmp.getHeight() * scaleX);
//        }else if(scaleX <= scaleY){
//            newW = (int) (bmp.getWidth() * scaleY);
//            newH = (int) (bmp.getHeight() * scaleY);
//        }
//        return Bitmap.createScaledBitmap(bmp, newW, newH, true);
//    }
//}
