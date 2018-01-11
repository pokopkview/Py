package com.yuqi.admin.py.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yuqi.admin.py.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by Admin on 2017/3/13.
 * <p>
 * 图片工具类
 */

public class ImageUtil {
//    public static void ImageInit(Context context) {
//        File cacheDir = new File("/sdcard/jsms/img/");
//        if (!cacheDir.exists()) cacheDir.mkdirs();
//        DisplayImageOptions defaultOptions = new DisplayImageOptions
//                .Builder()
//                .showImageOnLoading(R.mipmap.ic_launcher)
//                .showImageForEmptyUri(R.mipmap.ic_launcher)
//                .showImageOnFail(R.mipmap.ic_launcher)
//                .cacheInMemory(true)
//                .cacheOnDisc(true)
//                .resetViewBeforeLoading(true)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration
//                .Builder(context)
//                .defaultDisplayImageOptions(defaultOptions)
//                .memoryCacheExtraOptions(480, 800)
//                .threadPoolSize(4)
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCacheSize(1 * 1024 * 1024)
//                .discCacheSize(50 * 1024 * 1024)//
//                .discCacheFileCount(100)
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//                .discCache(new UnlimitedDiscCache(cacheDir))
//                .writeDebugLogs()
//                .imageDownloader(new AuthImageDownloader(context))
//                .build();
//        ImageLoader.getInstance().init(config);
//    }

    //图片加载
    public static void loadImg(ImageView imageView, String url) {
        try {
            if (StringUtil.isEmpty(url))
                return;
            if (url.startsWith("http")) {
                ImageLoader.getInstance().displayImage(url, imageView);
            } else {
                imageView.setBackgroundResource(R.mipmap.ic_launcher);
            }
        } catch (Exception e) {
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }


    //图片加载ArrayList
    public static void loadImg(ImageView imageView, ArrayList<String> url) {
        try {
            if (url.equals("")||url == null)
                return;
            if (url.get(0).startsWith("http")) {
                ImageLoader.getInstance().displayImage(url.get(0), imageView);
            } else {
                imageView.setBackgroundResource(R.mipmap.ic_launcher);
            }
        } catch (Exception e) {
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }



    public static void loadCommDialogImg(ImageView imageView, String url, Context context, View progress) {

        try {
            if (url.startsWith("http")) {
//				imageLoader.showImageAsyn(imageView,url,progress,context);
                ImageLoader.getInstance().displayImage(url, imageView, imgListener(progress, context));
            } else {
//                ImageLoader.getInstance().displayImage(CommonData.URL+url, imageView,imgListener(progress, context));
//				imageLoader.showImageAsyn(imageView,Comm_Args.IMG_URL+ url,progress,context);
            }
        } catch (Exception e) {
            progress.setVisibility(View.GONE);
            Log.e("erro", e + "");
        }
    }

    public static SimpleImageLoadingListener imgListener(final View progress, final Context context) {
        return new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progress.setVisibility(View.VISIBLE);
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {

                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "下载错误";
                        break;
                    case DECODING_ERROR:
                        message = "图片无法显示";
                        break;
                    case NETWORK_DENIED:
                        message = "网络有问题，无法下载";
                        break;
                    case OUT_OF_MEMORY:
                        message = "图片太大无法显示";
                        break;
                    case UNKNOWN:
                        message = "未知的错";
                        break;
                }
                progress.setVisibility(View.GONE);
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view,
                                          Bitmap loadedImage) {
                progress.setVisibility(View.GONE);
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        };
    }

    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64Encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    //byte[] 转换 Bitmap
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length > 0 && b != null) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * @方法:showBigImageView
     * @描述:加载大图片
     * @参数:bitmap（图片资源），context（上下文）
     */
    public static void showBigImageView(Bitmap bitmap, final Context context) {
        final Dialog dialog = new Dialog(context, R.style.Transparent);
        dialog.setContentView(R.layout.dialog_showbig_image);
        PhotoView img = (PhotoView) dialog.findViewById(R.id.dialog_img);
        img.setImageBitmap(bitmap);
        img.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }








}
