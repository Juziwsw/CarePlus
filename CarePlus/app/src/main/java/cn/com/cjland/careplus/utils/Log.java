package cn.com.cjland.careplus.utils;

/**
 * Log信息 
 */
public class Log {
	private static final boolean LOG = true;
	
	public static void d(String tag, String msg) {
		if(LOG) {
			android.util.Log.d(tag, msg);
		}
	}
	
	public static void d(String msg) {
		if(LOG) {
			android.util.Log.d("jcop", msg);
		}
	}
	
	public static void v(String tag, String msg) {
		if(LOG) {
			android.util.Log.v(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Exception e) {
		if(LOG) {
			android.util.Log.e(tag, msg, e);
		}
	}
	
	public static void e(String tag, String msg) {
		if(LOG) {
			android.util.Log.e(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if(LOG) {
			android.util.Log.i(tag, msg);
		}
	}
}
