package com.yuqi.admin.py.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yuqi.admin.py.BaseActivity;
import com.yuqi.admin.py.R;
import com.yuqi.admin.py.bean.APPHomePageBean;
import com.yuqi.admin.py.bean.APPqueryCommodityBean;
import com.yuqi.admin.py.bean.DingDanBean;
import com.yuqi.admin.py.data.CommonData;
import com.yuqi.admin.py.utils.DialogUtil;
import com.yuqi.admin.py.utils.ImageUtil;
import com.yuqi.admin.py.utils.ToastUtil;
import com.yuqi.admin.py.view.ResizableImageView;
import com.yuqi.admin.py.view.lib.CycleViewPager1;
import com.yuqi.admin.py.view.lib.utils.ViewFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 * 商品详情
 */
public class SCommodityDetailsActivity extends BaseActivity{
    private TextView spxq_yuexiao,spxq_kuaidi,spxq_jiage,spxq_miaoshu,submit;
    private EditText spxq_shuliang;
    Intent intent;
    /**轮播图片*/
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<APPqueryCommodityBean.ObjectBean.CommoditypicturesBean> infos = new ArrayList<APPqueryCommodityBean.ObjectBean.CommoditypicturesBean>();
    private CycleViewPager1 cycleViewPager;

    private Context mContext;
    private LinearLayout container;
    //商品详情返回数据存储
    APPqueryCommodityBean queryCommodityBean;
    private List<DingDanBean.DingdanBean> dingDan;
    private int orderNumber = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_spxq_shangpinxiangqing);
        mContext = this;
        init();
        //获取首页传来的图片 商品id
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        String Commodity_id1 = bundle.getString("Commodity_id");

        int Commodity_id = Integer.parseInt(Commodity_id1);
        //商品详情
        APPqueryCommodityHttp(Commodity_id);
    }


    private void init() {
        container = (LinearLayout) findViewById(R.id.ll_showpictrue_container);
        spxq_miaoshu = (TextView)findViewById(R.id.spxq_miaoshu);
        spxq_jiage = (TextView)findViewById(R.id.spxq_jiage);
        spxq_kuaidi = (TextView)findViewById(R.id.spxq_kuaidi);
        spxq_yuexiao = (TextView)findViewById(R.id.spxq_yuexiao);
        spxq_shuliang = (EditText)findViewById(R.id.spxq_shuliang);

        submit= (TextView)findViewById(R.id.submit);
        submit.setVisibility(View.VISIBLE);
        submit.setText("购物车");

    }

    @Override
    public String title_text() {
        return "商品详情";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Jrgwc://添加购物车
                int user_id = CommonData.user_id;
                int commodity_id = queryCommodityBean.getObject().getCommodity().getId();
                if (spxq_shuliang.getText().toString().trim().equals("")){
                    orderNumber = 1;
                }else {
                    orderNumber = Integer.parseInt(spxq_shuliang.getText().toString().trim());
                }
                addShoppingtrolleyHttp(user_id,commodity_id,orderNumber);
//                ObserverManager manager = ObserverManager.getInstance();
//                manager.addGoods(commodity_id,orderNumber);//添加购物车
                break;
            case R.id.Ljgm://立即购买
                int commodity_id1 = queryCommodityBean.getObject().getCommodity().getId();
                if (spxq_shuliang.getText().toString().trim().equals("")){//购买数量
                    orderNumber = 1;
                }else {
                    orderNumber = Integer.parseInt(spxq_shuliang.getText().toString().trim());
                }
                dingDan = new ArrayList<DingDanBean.DingdanBean>();
                for (int i = 0; i <1; i++) {
                    DingDanBean.DingdanBean info = new DingDanBean.DingdanBean();
                    info.setCommodity_id(commodity_id1);
                    info.setOrderNumber(orderNumber);
                    info.setPicture(queryCommodityBean.getObject().getCommoditypictures().get(0).getPicture());
                    info.setSales(queryCommodityBean.getObject().getCommodity().getSales());
                    info.setExpress(queryCommodityBean.getObject().getCommodity().getExpress()+"");
                    info.setCommodityPrice(queryCommodityBean.getObject().getCommodity().getCommodityPrice());
                    info.setCommodityName(queryCommodityBean.getObject().getCommodity().getCommodityName());
                    dingDan.add(info);
                }
                intent = new Intent(SCommodityDetailsActivity.this,SConfirmationSingleActivity.class);
                intent.putExtra("dingDan", (Serializable) dingDan);
                startActivity(intent);
                break;

            case R.id.submit://跳购物车界面
                intent = new Intent(SCommodityDetailsActivity.this,SShoppingCartActivity.class);
                startActivity(intent);
                break;

            case R.id.spxq_jian://商品数据减
                int jian = Integer.parseInt(spxq_shuliang.getText().toString());
                if (jian > 1 ){
                     jian = Integer.parseInt(spxq_shuliang.getText().toString()) - 1;
                    spxq_shuliang.setText(jian+"");
                }else if (spxq_shuliang.getText().toString().length()==0){
                    spxq_shuliang.setText(1+"");
                }
                break;
            case R.id.spxq_jia://商品数据加
                int jia = Integer.parseInt(spxq_shuliang.getText().toString()) + 1;
                if (jia == 9999)return;
                spxq_shuliang.setText(jia+"");
                break;

        }
    }
    //添加购物车接口
    private void addShoppingtrolleyHttp(int user_id, int commodity_id, int commodityNumber) {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("user_id", user_id+"");
        params1.addQueryStringParameter("commodity_id", commodity_id+"");
        params1.addQueryStringParameter("commodityNumber", commodityNumber+"");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        Log.e("请求数据=", user_id+"、"+commodity_id+"、"+commodityNumber+"、");

        http.send(HttpRequest.HttpMethod.GET,
                CommonData.URL + "addShoppingtrolley.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(SCommodityDetailsActivity.this);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        DialogUtil.finish();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DialogUtil.finish();
                        Log.e("添加购物车=", responseInfo.result);

                        String state = queryCommodityBean.getState();
                        switch (state) {
                            case "200":
                                ToastUtil.show(SCommodityDetailsActivity.this,"添加成功");
                                 break;
                            case "210":
                                ToastUtil.show(SCommodityDetailsActivity.this,"添加失败");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
//                        ToastUtil.show(SCommodityDetailsActivity.this,"网络异常");
                    }
                });

    }

    // 12.22 详细介绍图片设置    底部根据图片张数加载
    private void initImag( APPqueryCommodityBean queryCommodityBean) {
        List<APPqueryCommodityBean.ObjectBean.CommodityparticularsBean> bean = queryCommodityBean.getObject().getCommodityparticulars();
        for(APPqueryCommodityBean.ObjectBean.CommodityparticularsBean bean1 : bean) {
            ResizableImageView imageView = new ResizableImageView(mContext);
//            BitmapUtils bitmapUtils = new BitmapUtils(mContext);
//            bitmapUtils.display(imageView,bean1.getPicture());

            ImageUtil.loadImg(imageView,bean1.getPicture());

            container.addView(imageView);
        }
    }

    //商品详情数据请求
    private void APPqueryCommodityHttp(int Commodity_id) {
        RequestParams params1 = new RequestParams();
        params1.addQueryStringParameter("Commodity_id", Commodity_id+"");

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        Log.e(CommonData.REQUEST_PARAMETER, Commodity_id+"");
        http.send(HttpRequest.HttpMethod.GET,
                CommonData.URL + "APPqueryCommodity.action",
                params1,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtil.start(SCommodityDetailsActivity.this);
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
                        queryCommodityBean = gson.fromJson(result, APPqueryCommodityBean.class);//存商品数据
                        String state = queryCommodityBean.getState();
                        switch (state) {
                            case "200":
                                spxq_yuexiao.setText(queryCommodityBean.getObject().getCommodity().getSales()+"");
                                spxq_kuaidi.setText("￥"+queryCommodityBean.getObject().getCommodity().getExpress()+"");
                                spxq_jiage.setText("￥"+queryCommodityBean.getObject().getCommodity().getCommodityPrice()+"");
                                spxq_miaoshu.setText(queryCommodityBean.getObject().getCommodity().getCommodityName()+"");

                                //轮播图片设置
                                configImageLoader();
                                initialize(queryCommodityBean);
                                //详细介绍图片设置
                                initImag(queryCommodityBean);
                                break;
                            case "210":
                                break;
                        }

                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        DialogUtil.finish();
                    }
                });
    }
    //配置ImageLoder
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(SCommodityDetailsActivity.this).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    @SuppressLint("NewApi")
    private void initialize( APPqueryCommodityBean queryCommodityBean) {
        cycleViewPager = (CycleViewPager1) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content1);

        for(int i = 0; i <  queryCommodityBean.getObject().getCommoditypictures().size(); i ++) {
            APPqueryCommodityBean.ObjectBean.CommoditypicturesBean info = new APPqueryCommodityBean.ObjectBean.CommoditypicturesBean();
            info.setPicture(queryCommodityBean.getObject().getCommoditypictures().get(i).getPicture());
//            switch (i) {
//                case 0:
//                    info.setPicture_url("http://www.yuqibest.com/yuqibest/index.php");
//                    break;
//                case 1:
//                    info.setPicture_url("http://www.yuqibest.com/yuqibest/index.php/Home/Index/cpy_des/sort/1.html");
//                    break;
//                case 2:
//                    info.setPicture_url("http://www.yuqibest.com/yuqibest/index.php/Home/Index/blueprint/sort/3.html");
//                    break;
//            }
            infos.add(info);
        }


        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(SCommodityDetailsActivity.this, infos.get(infos.size()-1).getPicture()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(SCommodityDetailsActivity.this, infos.get(i).getPicture()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(SCommodityDetailsActivity.this, infos.get(0).getPicture()));
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }
    private CycleViewPager1.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager1.ImageCycleViewListener() {
        @Override
        public void onImageClick(APPHomePageBean.ObjectBean.PicturesBean info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
//                Intent it = new Intent(SCommodityDetailsActivity.this,WebActivity.class);
//                it.putExtra("url",info.getFirst());
//                startActivity(it);
            }
        }
    };
}
