package cn.com.cjland.careplus.activity;

import android.os.Bundle;

import com.easemob.easeui.ui.EaseBaseActivity;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ImBaseActivity extends EaseBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
        //MobclickAgent.onPause(this);
    }
}
