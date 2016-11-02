package cn.com.cjland.careplus.imutils;

import android.content.Context;

import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
public class CareHelper {
    private static CareHelper instance = null;

    public synchronized static CareHelper getInstance() {
        if (instance == null) {
            instance = new CareHelper();
        }
        return instance;
    }

    public void init(Context context){
        swtAddContactsMsg();
    }

    public void swtAddContactsMsg(){
        EMContactManager.getInstance().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(List<String> list) {

            }
            @Override
            public void onContactDeleted(List<String> list) {

            }
            @Override
            public void onContactInvited(String s, String s1) {

            }
            @Override
            public void onContactAgreed(String s) {

            }
            @Override
            public void onContactRefused(String s) {

            }
        });
    }
}
