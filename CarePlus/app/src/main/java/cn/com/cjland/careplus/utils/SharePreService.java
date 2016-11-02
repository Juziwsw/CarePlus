package cn.com.cjland.careplus.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/11/27.
 */
public class SharePreService {
    //
    private static SharedPreferences preferences;

    public static  String getShaedPrerence(Context context,String preferencesName){
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(preferencesName,"");
    }
    public static  boolean getBoolShaedPrerence(Context context,String preferencesName){
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(preferencesName,false);
    }
    public static String getUserId(Context context) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(SumConstants.USERID,"");
    }
    public static void setUserId(Context context, String userId) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SumConstants.USERID, userId);
        editor.commit();
    }

    public static String getUserAvatar(Context context) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString("userAvatarUrl","");
    }
    public static void setUserAvatar(Context context, String avatarUrl) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userAvatarUrl", avatarUrl);
        editor.commit();
    }

    public static Boolean getUserStatus(Context context) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(SumConstants.ISLOADSTATUS, false);
    }

    public static void setUserStatus(Context context, Boolean status) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SumConstants.ISLOADSTATUS, status);
        editor.commit();
    }
    public static void clearData(Context context) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public static void clearSomeData(Context context) {
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SumConstants.ISLOADSTATUS, false);
        editor.putString(SumConstants.USERID, "");
        editor.commit();
    }
    public static void setAddress(Context context, String strAddress){
        preferences = context.getSharedPreferences(
                SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SumConstants.ADDRESS, strAddress);
        editor.commit();
    }
    public static String getAddress(Context context){
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(SumConstants.ADDRESS,"");
    }
    public static void setIsReceive(Context context, Boolean IsReceive){
        preferences = context.getSharedPreferences(
                SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SumConstants.ISRECEIVE, IsReceive);
        editor.commit();
    }
    public static boolean getIsReceive(Context context){
        preferences = context.getSharedPreferences(SumConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(SumConstants.ISRECEIVE,false);
    }
}
