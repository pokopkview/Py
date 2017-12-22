package com.yuqi.admin.py.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.adapter.ShangPinzhAdapter;
import com.yuqi.admin.py.bean.APPCommodityBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ToastUtil;
import com.yuqi.admin.py.view.MultiGridView;


public class ZhFragment extends Fragment {
    private ShangPinzhAdapter spAdapter;
    private MultiGridView sjLv;
    APPCommodityBean page;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zonghe, null);
        sjLv = (MultiGridView) view.findViewById(R.id.list);
        initView();
        return view;
    }

    private void initView() {
//        //商品数据
//        APPHomePage();
//
//        spAdapter = new ShangPinzhAdapter(getActivity());
//        sjLv.setAdapter(spAdapter);
    }


    /**商品*/
    private void APPHomePage() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        params.addBodyParameter("Commodity_Type", "综合");//商品类型
        http.configCurrentHttpCacheExpiry(1000 * 10);

        http.send(HttpRequest.HttpMethod.GET,
                CommonData.URL + "APPqueryCommodityByType.action",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(getActivity());
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e("综合数据=", responseInfo.result);
                        String result = responseInfo.result;
                        Gson gson = new Gson();//初始化
                        page = gson.fromJson(result, APPCommodityBean.class);//存商品数据
                        String state = page.getState();
                        switch (state) {
                            case "200":
                                ToastUtil.show(getActivity(), "查询成功");
                                break;
                            case "210":
                                ToastUtil.show(getActivity(), "查询失败");
                                break;
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                        Log.e("请求异常后的回调方法", "失败=" + error.toString() + "/" + msg);
                    }
                });
    }
}
