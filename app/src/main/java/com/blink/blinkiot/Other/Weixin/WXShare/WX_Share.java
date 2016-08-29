package com.blink.blinkiot.Other.Weixin.WXShare;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/1/5.
 */
public class WX_Share {

    private IWXAPI api = null;

    private int THUMB_SIZE = 120;

    public static int sceneWXSceneSession = 0;
    public static int sceneWXSceneTimeline = 1;
    public static int sceneWXSceneFavorite = 2;

//    分享或收藏的目标场景，通过修改scene场景值实现。
//    发送到聊天界面——WXSceneSession
//    发送到朋友圈——WXSceneTimeline
//    添加到微信收藏——WXSceneFavorite

    public WX_Share(Context context, String APP_ID) {
        // (微信)实例化对象
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    /**
     * 分享文字模块
     *
     * @param content
     * @param description
     * @param scene
     */
    public void share_text(String content, String description, int scene) {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = description;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); //
        // transaction字段用于唯一标识一个请求
        req.message = msg;
        // req.scene = isTimelineCb.isChecked() ?
        req.scene = WXShareState(scene);
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    /**
     * 分享图片的模块
     *
     * @param bitmap
     * @param scene
     */
    public void share_image(Bitmap bitmap, int scene) {
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = WXShareState(scene);
        api.sendReq(req);
    }

    /**
     * 分享音乐的模块
     *
     * @param music_url
     * @param title
     * @param description
     * @param bitmap
     * @param scene
     */
    public void share_music(String music_url, String title, String description, Bitmap bitmap, int scene) {
        WXMusicObject musicobj = new WXMusicObject();
        musicobj.musicUrl = music_url;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = musicobj;
        //音乐标题
        msg.title = title;
        //音乐的描述
        msg.description = description;

        //设置缩略图
        Bitmap thumbitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = Util.bmpToByteArray(thumbitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = WXShareState(scene);
        api.sendReq(req);
    }

    /**
     * 分享视频模块
     *
     * @param video_url
     * @param title
     * @param description
     * @param bitmap
     * @param scene
     */
    public void share_video(String video_url, String title, String description, Bitmap bitmap, int scene) {
        WXVideoObject videoObj = new WXVideoObject();
        videoObj.videoUrl = video_url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.description = description;
        msg.mediaObject = videoObj;

        //设置缩略图
        Bitmap thumbitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = Util.bmpToByteArray(thumbitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;

        req.scene = WXShareState(scene);
        api.sendReq(req);

    }

    /**
     * 分享网址的模块
     *
     * @param web_url
     * @param title
     * @param description
     * @param bitmap
     * @param scene
     */
    public void share_web(String web_url, String title, String description, Bitmap bitmap, int scene) {
        WXWebpageObject webObj = new WXWebpageObject();
        webObj.webpageUrl = web_url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.description = description;
        msg.mediaObject = webObj;

        //设置缩略图
        Bitmap thumbitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = Util.bmpToByteArray(thumbitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;

        req.scene = WXShareState(scene);
        api.sendReq(req);

    }

    /**
     * 判断分享的的地方
     *
     * @param scene
     * @return
     */
    private int WXShareState(int scene) {
        //如果传入参数是0则视为分享聊天界面
        //如果传入参数是1则视为分享朋友圈
        if (scene == 1) {
            return SendMessageToWX.Req.WXSceneTimeline;
        } else if (scene == 0) {
            return SendMessageToWX.Req.WXSceneSession;
        }
        return 0;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
