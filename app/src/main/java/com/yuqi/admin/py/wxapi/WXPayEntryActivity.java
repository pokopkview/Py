//package com.yuqi.admin.py.wxapi;
//
//
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
//import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
//
//    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
//
//    private Button gotoBtn, regBtn, launchBtn, checkBtn, scanBtn;
//
//    // IWXAPI 是第三方app和微信通信的openapi接口
//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        //展示界面布局 可以自定义
////        setContentView(R.layout.entry);
//
//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
//        api.handleIntent(getIntent(),this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    // 微信发送请求到第三方应用时，会回调到该方法
//    @Override
//    public void onReq(BaseReq req) {
//        switch (req.getType()) {
//            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
//                break;
//            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
//                break;
//            default:
//                break;
//        }
//    }
//
//    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
//    @Override
//    public void onResp(BaseResp resp) {
//
//        Toast.makeText(this, "baseresp.getType = " + resp.getType(), Toast.LENGTH_SHORT).show();
//    }
//
//    private void goToGetMsg() {
//        Intent intent = new Intent(this, GetFromWXActivity.class);
//        intent.putExtras(getIntent());
//        startActivity(intent);
//        finish();
//    }
//
//    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
//        WXMediaMessage wxMsg = showReq.message;
//        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
//        msg.append("description: ");
//        msg.append(wxMsg.description);
//        msg.append("\n");
//        msg.append("extInfo: ");
//        msg.append(obj.extInfo);
//        msg.append("\n");
//        msg.append("filePath: ");
//        msg.append(obj.filePath);
//
//        Intent intent = new Intent(this, ShowFromWXActivity.class);
//        intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//        intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//        intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//        startActivity(intent);
//        finish();
//    }
//}