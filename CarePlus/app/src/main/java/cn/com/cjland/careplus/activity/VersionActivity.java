package cn.com.cjland.careplus.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.utils.HttpUtils;

public class VersionActivity extends BaseActivity {
    private ActionBar mActionBar;
    private TextView mActionTitle,mTxtVCanle,mTxtVFlash,mTxtVNo;
    private ImageView homeView;
    private RelativeLayout mRelVersionTest;
    private Dialog mDialog;
    /** 版本检测 **/
    private int mVersionNum = 0;
    private ExecutorService cachedThreadPool;
    private String mUrlVersion,mVersionApkUrl;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        mContext = VersionActivity.this;
        cachedThreadPool = Executors.newCachedThreadPool();
        mUrlVersion = getResources().getString(R.string.urlheader)+"/apk/apk";
        findview();
    }
    private void findview() {
        mActionBar = this.getActionBar();
        mActionTitle = (TextView) mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("关于陪伴家");
        homeView = (ImageView) mActionBar.getCustomView().findViewById(R.id.action_home);
        homeView.setVisibility(View.VISIBLE);
        homeView.setOnClickListener(this);
        mRelVersionTest = (RelativeLayout)findViewById(R.id.rel_version_test);
        mTxtVNo = (TextView)findViewById(R.id.txt_version_no);
        mRelVersionTest.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_home:
                colseActivity();
                break;
            case R.id.rel_version_test://检测是否有新版本
                checkUpdate();
                break;
            case R.id.txt_version_cancle:
                mDialog.dismiss();
                break;
            case R.id.txt_version_flash://更新版本
                mDialog.dismiss();
                showDownloadDialog();
                break;
        }
    }
    //弹出框
    private void showDialog(){
        //消息按钮监听事件
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.version_test_pop, null );
        //对话框
        mDialog = new AlertDialog.Builder(this).create();
        mDialog.show();
        mDialog.getWindow().setContentView(layout);
        mTxtVCanle = (TextView)layout.findViewById(R.id.txt_version_cancle);
        mTxtVFlash = (TextView)layout.findViewById(R.id.txt_version_flash);
        mTxtVCanle.setOnClickListener(this);
        mTxtVFlash.setOnClickListener(this);
    }
    /**
     * 检查软件是否有更新版本
     *
     * @return
     */

    private void checkUpdate(){
        //获取服务器的版本
        cachedThreadPool.execute(new Runnable() {
            String params = "version";
            @Override
            public void run() {
                try {
                    String data = HttpUtils.PostString(mContext, mUrlVersion, params);
                    Log.i("VERSION", "data=" + data);
                    if (data != null) {
                        JSONObject obj = new JSONObject(data);
                        Message msg = new Message();
                        msg.what = 0x123;
                        if (obj.getString("event").equals("0")) {
                            JSONObject version = obj.getJSONObject("objList");
                            mVersionNum = Integer.parseInt(version.getString("an_version"));
                            mVersionApkUrl = version.getString("url");
                        }
                        mMainHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    // 获取当前软件版本
                    int versionCode = getVersionCode(mContext);
                    // 版本判断
                    if (mVersionNum > versionCode) {
                        showDialog();
                    }else{
                        mTxtVNo.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };
    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("cn.com.cjland.careplus", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    private ProgressBar mProgress;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    /* 更新进度条 */
    private Dialog mDownloadDialog;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        };
    };
    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog()
    {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }
    /**
     * 下载apk文件
     */
    private void downloadApk()
    {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     *@date 2012-4-26
     *@blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(mVersionApkUrl);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "happymonkey");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do
                    {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0)
                        {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    };

    /**
     * 安装APK文件
     */
    private void installApk()
    {
        File apkfile = new File(mSavePath, "happymonkey");
        if (!apkfile.exists())
        {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
    private void colseActivity(){
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            colseActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
