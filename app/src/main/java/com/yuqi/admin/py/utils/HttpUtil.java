package com.yuqi.admin.py.utils;



import android.util.Log;

import com.yuqi.admin.py.data.CommonData;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



public class HttpUtil {
    public static final int HTTP_START = 0;
    public static final int HTTP_LOADING = 1;
    public static final int HTTP_FINISH = 2;
    public static final int HTTP_SUCCESE = 3;
    public static final int HTTP_FAILD = 4;
    public static final int HTTP_ERRO = -1;
    public static final int SOCKET_TIME = 20000;
    public static ExecutorService myThreadService = Executors.newFixedThreadPool(6);
    private static TrustManager CustomX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }
    };
    private static HostnameVerifier CustomHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub  
            return true;
        }
    };
    public static void getData(final String mUrl, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.obtainMessage(HTTP_START).sendToTarget();
                    URL url = new URL(mUrl);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    //SSL证书设置
                    SSLContext sslContext = SSLContext.getInstance("TLSv1");
                    sslContext.init(null, new TrustManager[]{CustomX509TrustManager}, new java.security.SecureRandom());
                    // 从上述SSLContext对象中得到SSLSocketFactory对象
                    urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                    urlConnection.setHostnameVerifier(CustomHostnameVerifier);

                    urlConnection.setReadTimeout(SOCKET_TIME);
                    urlConnection.setConnectTimeout(SOCKET_TIME);
                    urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
                    int response = urlConnection.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = urlConnection.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = urlConnection.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    urlConnection.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();
                }
            }
        });
    }

    public static void postData(final String strUrlPath, final Object params, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {
                handler.obtainMessage(HTTP_START).sendToTarget();
                String json = "";
                if (params instanceof List) {
                    json = JsonUtil.toJsonArray((List) params);
                } else if (params instanceof HashMap) {
                    json = JsonUtil.toJsonObj((Map) params);
                } else {
                    json = "{" + params + "}";
                }
                byte[] data = json.getBytes();
                try {
                    URL url = new URL(strUrlPath);
                    HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(SOCKET_TIME);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");


                    //SSL证书设置
                    SSLContext sslContext = SSLContext.getInstance("TLSv1");
                    sslContext.init(null, new TrustManager[]{CustomX509TrustManager}, new java.security.SecureRandom());
                    // 从上述SSLContext对象中得到SSLSocketFactory对象
                    httpURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                    httpURLConnection.setHostnameVerifier(CustomHostnameVerifier);

                    httpURLConnection.setUseCaches(false);
//        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
//        if (!StringUtil.isEmpty(ConstData.token)) {
//            httpURLConnection.setRequestProperty("Authorization", ConstData.token);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(data);
                    int response = httpURLConnection.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = httpURLConnection.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = httpURLConnection.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    outputStream.close();;
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();
                }
            }
        });
    }

    public static void postData(final Map params,final String strDo, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {

                handler.obtainMessage(HTTP_START).sendToTarget();
                Map<String, String> map = new HashMap<String, String>();
                map.put("do",strDo);
                Log.e("-----",JsonUtil.toJsonObj(params));
                if (params != null)
                map.put("data",JsonUtil.toJsonObj(params));
                String json = getRequestData(map, "utf-8")+"";
                byte[] data = json.getBytes();
                try {
                    URL url = new URL(CommonData.URL);
                    HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(SOCKET_TIME);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");

                    //SSL证书设置
                    SSLContext sslContext = SSLContext.getInstance("TLSv1");
                    sslContext.init(null, new TrustManager[]{CustomX509TrustManager}, new java.security.SecureRandom());
                    // 从上述SSLContext对象中得到SSLSocketFactory对象
                    httpURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                    httpURLConnection.setHostnameVerifier(CustomHostnameVerifier);

                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
//        if (!StringUtil.isEmpty(ConstData.token)) {
//            httpURLConnection.setRequestProperty("Authorization", ConstData.token);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(data);
                    int response = httpURLConnection.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = httpURLConnection.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = httpURLConnection.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    outputStream.close();
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();

                }
            }
        });
    }
    public static void postData(final String strUrlPath, final Map params,final String strDo, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {

                handler.obtainMessage(HTTP_START).sendToTarget();
                String json = getRequestData(params, "utf-8")+"";
                byte[] data = json.getBytes();//���������?
                try {
                    URL url = new URL(strUrlPath);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(SOCKET_TIME);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");

                    //SSL证书设置
//                    SSLContext sslContext = SSLContext.getInstance("TLSv1");
//                    sslContext.init(null, new TrustManager[]{CustomX509TrustManager}, new java.security.SecureRandom());
//                    // 从上述SSLContext对象中得到SSLSocketFactory对象
//                    httpURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
//                    httpURLConnection.setHostnameVerifier(CustomHostnameVerifier);

                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
//        if (!StringUtil.isEmpty(ConstData.token)) {
//            httpURLConnection.setRequestProperty("Authorization", ConstData.token);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(data);
                    int response = httpURLConnection.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = httpURLConnection.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = httpURLConnection.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    outputStream.close();
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();

                }
            }
        });
    }


    private static StringBuffer getRequestData(Map params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (params == null)
                return stringBuffer;
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue()+"", encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }


    private static String dealResponseResult(InputStream inputStream) {
        String resultData = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultData = new String(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
            inputStream.close();
        }catch (Exception e){

        }
        return resultData;
    }


    public static  void upDataMore(final String actionUrl, final Map<String, String> params, final List<File> files, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    handler.obtainMessage(HTTP_START).sendToTarget();
                    String BOUNDARY = java.util.UUID.randomUUID().toString();
                    String PREFIX = "--", LINEND = "\r\n";
                    String MULTIPART_FROM_DATA = "multipart/form-data";
                    String CHARSET = "UTF-8";
                    URL uri = new URL(actionUrl);
                    HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
                    conn.setReadTimeout(SOCKET_TIME);
                    conn.setConnectTimeout(SOCKET_TIME);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Charsert", "UTF-8");
                    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                            + ";boundary=" + BOUNDARY);
                    DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
                    if (params != null) {
                        StringBuilder sb = new StringBuilder();
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            sb.append(PREFIX);
                            sb.append(BOUNDARY);
                            sb.append(LINEND);
                            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                            sb.append(LINEND);
                            sb.append(entry.getValue());
                            sb.append(LINEND);
                        }
                        outStream.write(sb.toString().getBytes());
                    }
                    if (files != null)
                        for (int i = 0; i < files.size(); i++) {
                            File file = files.get(i);
                            StringBuilder sb1 = new StringBuilder();
                            sb1.append(PREFIX);
                            sb1.append(BOUNDARY);
                            sb1.append(LINEND);
                            sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINEND);
                            sb1.append("Content-Type: multipart/form-data; charset=" + CHARSET + LINEND);
                            sb1.append(LINEND);
                            outStream.write(sb1.toString().getBytes());
                            InputStream is = new FileInputStream(file);
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while ((len = is.read(buffer)) != -1) {
                                outStream.write(buffer, 0, len);
                            }
                            is.close();
                            outStream.write(LINEND.getBytes());
                        }
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
                    outStream.write(end_data);
                    outStream.flush();
                    int response = conn.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = conn.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = conn.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    outStream.close();
                    conn.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();
                }
            }
        });
    }


    public static void upData(final String actionUrl, final File file, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    handler.obtainMessage(HTTP_START).sendToTarget();
                    String BOUNDARY = java.util.UUID.randomUUID().toString();
                    String PREFIX = "--", LINEND = "\r\n";
                    String MULTIPART_FROM_DATA = "multipart/form-data";
                    String CHARSET = "UTF-8";
                    URL uri = new URL(actionUrl);
                    HttpsURLConnection conn = (HttpsURLConnection) uri.openConnection();
                    conn.setReadTimeout(SOCKET_TIME);
                    conn.setConnectTimeout(SOCKET_TIME);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");

                    //SSL证书设置
                    SSLContext sslContext = SSLContext.getInstance("TLSv1");
                    sslContext.init(null, new TrustManager[]{CustomX509TrustManager}, new java.security.SecureRandom());
                    // 从上述SSLContext对象中得到SSLSocketFactory对象
                    conn.setSSLSocketFactory(sslContext.getSocketFactory());
                    conn.setHostnameVerifier(CustomHostnameVerifier);

                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Charsert", "UTF-8");
                    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                            + ";boundary=" + BOUNDARY);
//  if (!StringUtil.isEmpty(ConstData.token)) {
//	  conn.setRequestProperty("Authorization", ConstData.token);
//	}
                    OutputStream outStream = conn.getOutputStream();
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINEND);
                    sb1.append("Content-Type: multipart/form-data; charset=" + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());
                    InputStream is = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    is.close();
                    outStream.write(LINEND.getBytes());
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
                    outStream.write(end_data);
                    outStream.flush();
                    int response = conn.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = conn.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = conn.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    outStream.close();
                    conn.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();
                }

            }
        });
    }
    public static void upData(final String actionUrl, final InputStream in,final String fileName, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.obtainMessage(HTTP_START).sendToTarget();
                    String BOUNDARY = java.util.UUID.randomUUID().toString();
                    String PREFIX = "--", LINEND = "\r\n";
                    String MULTIPART_FROM_DATA = "multipart/form-data";
                    String CHARSET = "UTF-8";
                    URL uri = new URL(actionUrl);
                    HttpsURLConnection conn = (HttpsURLConnection) uri.openConnection();
                    conn.setReadTimeout(SOCKET_TIME);
                    conn.setConnectTimeout(SOCKET_TIME);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");

                    //SSL证书设置
                    SSLContext sslContext = SSLContext.getInstance("TLSv1");
                    sslContext.init(null, new TrustManager[]{CustomX509TrustManager}, new java.security.SecureRandom());
                    // 从上述SSLContext对象中得到SSLSocketFactory对象
                    conn.setSSLSocketFactory(sslContext.getSocketFactory());
                    conn.setHostnameVerifier(CustomHostnameVerifier);

                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Charsert", "UTF-8");
                    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                            + ";boundary=" + BOUNDARY);
//  if (!StringUtil.isEmpty(ConstData.token)) {
//	  conn.setRequestProperty("Authorization", ConstData.token);
//	}
                    OutputStream outStream = conn.getOutputStream();
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + LINEND);
                    sb1.append("Content-Type: multipart/form-data; charset=" + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    in.close();
                    outStream.write(LINEND.getBytes());
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
                    outStream.write(end_data);
                    outStream.flush();
                    int response = conn.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = conn.getInputStream();
                        handler.obtainMessage(HTTP_SUCCESE, dealResponseResult(inptStream)).sendToTarget();
                    } else {
                        InputStream inptStream = conn.getErrorStream();
                        handler.obtainMessage(HTTP_FAILD, dealResponseResult(inptStream)).sendToTarget();
                    }
                    outStream.close();
                    conn.disconnect();
                } catch (Exception e) {
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();
                }

            }
        });
    }
    public static void down(final String urlStr, final String path, final HttpHandle handler) {
        myThreadService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.obtainMessage(HTTP_START).sendToTarget();
                    // 构造URL
                    URL url = new URL(urlStr);
                    // 打开连接
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //获得文件的长度
                    int contentLength = con.getContentLength();
                    // 输入流
                    InputStream is = con.getInputStream();
                    // 1K的数据缓冲
                    byte[] bs = new byte[1024];
                    // 读取到的数据长度
                    int len;
                    int progress = 0;
                    int start = 0;
                    // 输出的文件流
                    File f = new File(path);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    String name = urlStr.substring(urlStr.lastIndexOf("/"));
                    f = new File(path + "/" + name);
                    OutputStream os = new FileOutputStream(f);
                    // 开始读取
                    while ((len = is.read(bs)) != -1) {
                        start += len;
                        int bb = (int) ((float) start / contentLength * 100);
                        if (bb > progress) {
                            progress = bb;
                            handler.obtainMessage(HTTP_LOADING, progress).sendToTarget();
                        }
                        os.write(bs, 0, len);
                    }
                    // 完毕，关闭所有链接
                    os.close();
                    is.close();
                    con.disconnect();
                    handler.obtainMessage(HTTP_SUCCESE).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(HTTP_FAILD, e).sendToTarget();
                }
            }
        });
    }


}
