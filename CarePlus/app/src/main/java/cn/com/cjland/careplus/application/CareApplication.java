package cn.com.cjland.careplus.application;

import android.app.Application;

import com.easemob.chat.EMChat;
import com.easemob.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2016/2/25.
 */
public class CareApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //init demo helper
        EaseUI.getInstance().init(this);
        EMChat.getInstance().setDebugMode(true);//测试是为ture,发包时为false
        //CareHelper.getInstance().init(this);
    }
}
