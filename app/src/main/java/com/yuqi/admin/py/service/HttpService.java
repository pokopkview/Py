package com.yuqi.admin.py.service;

import java.io.File;
import java.net.Authenticator.RequestorType;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.AlertDialog;
import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

/**
 * 网络请求，保存上传文件，获取数据，下载文件
 *
 * @author Administrator
 *
 */
public class HttpService {

	protected AlertDialog alertDialog;
	public HttpService instance;
	private AlertDialog dialog;

	/**
	 * XUtilsPost请求
	 *
	 * @param context
	 * @param url
	 * @param serviceParams
	 * @param ishow
	 */
	public void xUtiulsPost(final Context context, String url,
							List<NameValuePair> serviceParams, boolean ishow) {
		LogUtils.d("发送广播1");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.configTimeout(10 * 1000);
		RequestParams params = getPramas(serviceParams);
		httpUtils.send(HttpRequest.HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						dialog = new AlertDialog.Builder(context).create();
						LogUtils.d("发送广播2");
					}

					@Override
					public void onLoading(long total, long current,
										  boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
						dialog.show();
						LogUtils.d("发送广播3");
					}

					@Override
					public void onFailure(HttpException arg0, String result) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						LogUtils.d("发送广播4" + result);
					}

					@Override
					public void onSuccess(ResponseInfo<String> result) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						LogUtils.d("发送广播5" + result.toString());
					}
				});
	}

	/**
	 * XutilsGet请求
	 *
	 * @param context
	 * @param url
	 * @param serviceParams
	 * @param ishow
	 */
	public void xUtiulsGet(final Context context, String url,
						   List<NameValuePair> serviceParams, boolean ishow) {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.configTimeout(10 * 1000);
		httpUtils.send(HttpRequest.HttpMethod.GET, "https://www.baidu.com/",
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						dialog = new AlertDialog.Builder(context).create();
						LogUtils.d("发送广播2");
					}

					@Override
					public void onLoading(long total, long current,
										  boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
						dialog.show();
						LogUtils.d("发送广播3");
					}

					@Override
					public void onFailure(HttpException arg0, String result) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						LogUtils.d("发送广播4" + result);
					}

					@Override
					public void onSuccess(ResponseInfo<String> result) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						LogUtils.d("发送广播5" + result.result);
					}
				});
	}

	/**
	 * Xutils下载
	 *
	 * @param context
	 * @param url
	 * @param path
	 */
	public void HttpsDownLoad(Context context, String url, String path) {
		HttpUtils httpUtils = new HttpUtils();
		HttpHandler httpHandler = httpUtils.download(url, path,
				new RequestCallBack<File>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * 上传文件
	 *
	 * @param context
	 * @param url
	 * @param path
	 */
	public void HttpsUpdate(Context context, String url, String path) {
		HttpUtils httpUtils = new HttpUtils();
//		 params.addQueryStringParameter("method", "upload");
//		params.addQueryStringParameter("path", "/apps/测试应用/test.zip");
//		  // 请在百度的开放access_tokenapi测试页面找到自己的access_token
//		 params.addQueryStringParameter("access_token", "3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");  
//		params.addBodyParameter("file", new File("/sdcard/test.zip"));  

		HttpHandler httpHandler = httpUtils.send(HttpMethod.POST, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String result) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> result) {
						// TODO Auto-generated method stub

					}
				});
	}

	public RequestParams getPramas(List<NameValuePair> serviceParams) {
		RequestParams params = new RequestParams();
		params.addBodyParameter(serviceParams);
		return params;
	}
}
