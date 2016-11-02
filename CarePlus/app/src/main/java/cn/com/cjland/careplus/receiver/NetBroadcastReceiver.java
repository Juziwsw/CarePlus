package cn.com.cjland.careplus.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.bean.Toast;

/**
 * Created by Administrator on 2016/2/2.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.show(context, context.getResources().getString(R.string.txt_network_warning), Toast.LENGTH_SHORT);
    }
}
