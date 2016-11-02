package cn.com.cjland.careplus.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import org.json.JSONObject;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.utils.AppManager;
import cn.com.cjland.careplus.utils.HttpUtils;
import cn.com.cjland.careplus.utils.SharePreService;
import cn.com.cjland.careplus.utils.TimeUtils;

public class LoginActivity extends BaseActivity {
//    private EditText editTextname,editTextpsd;
    public  static  boolean progressShow;

    public Context mContext;
    EditText mEdtPhone;
    EditText mEdtPwd;
    TextView mTvForgetPwd;
    Button mBtnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        mContext = getBaseContext();
        TextView mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("登录");
        TextView mActionTask = (TextView)mActionBar.getCustomView().findViewById(R.id.action_task);
        mActionTask.setVisibility(View.VISIBLE);
        mActionTask.setText("注册");
        mActionTask.setOnClickListener(this);
        ImageView homeView = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        homeView.setVisibility(View.VISIBLE);
        homeView.setOnClickListener(this);
        //init view
        mEdtPhone = (EditText) findViewById(R.id.edt_phone);
        mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        mTvForgetPwd = (TextView) findViewById(R.id.edt_forget_pwd);
        mTvForgetPwd.setOnClickListener(this);
        mBtnLogin  = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_home:
                finish();
                break;
            case R.id.action_task://注册
                startActivity(new Intent(mContext, RegistActivity.class));
                break;
            case R.id.edt_forget_pwd://忘记密码
                startActivity(new Intent(mContext, ForgetPwdActivity.class));
                break;
            case R.id.btn_login://登录
                login();
//                Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
//                finish();
//                if (checkInput()) {
//                    new PostDataTask().execute();
//                }
                break;
            default:
                break;
        }
    }
    private boolean checkInput() {
        // phone
        if (mEdtPhone.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "手机号不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (0 < mEdtPhone.getText().toString().length()
                && mEdtPhone.getText().toString().length() < 11) {
            Toast.makeText(getApplicationContext(), "请输入完整的手机号",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        // password
        if (mEdtPwd.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (0 < mEdtPwd.getText().toString().length()
                && mEdtPwd.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "密码不得小于6位",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
    private class PostDataTask extends AsyncTask<Void, Void, String> {
        String resultdata = null;
        String url = getResources().getString(R.string.urlheader)+"";
        String param = "";
        //后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            // Simulates a background job.
            try {
                resultdata = new HttpUtils().PostString(mContext, url, param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultdata;
        }
        @Override
        protected void onPostExecute(String result) {
            if (null == result) {
                System.out.println("数据为空");
                return;
            }
            try {
                JSONObject dataRst = new JSONObject(result);
                if (dataRst.getString("event").equals("0")) {
                    // 获取SharedPreferences对象
                    SharedPreferences preferences;
                    preferences = getSharedPreferences("CarePlus", MODE_WORLD_READABLE);
                    JSONObject succcese = dataRst.getJSONObject("obj");
                    String UserId = String.valueOf(succcese.getInt("user_id"));
                    // 将控件中的数据在本机保存
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userId", UserId);
                    editor.putString("userPhone", mEdtPhone.getText().toString());
                    editor.putBoolean("onlineStatus", true);
                    editor.commit();
                    // 跳转到主页面
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                }
                Toast.makeText(mContext, dataRst.getString("msg"), Toast.LENGTH_SHORT).show();
            }  catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    public void login(){
        String currentUsername = mEdtPhone.getText().toString().trim();
        String currentPassword = mEdtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;

            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                if (!progressShow) {
                    return;
                }
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMGroupManager.getInstance().loadAllGroups();
                EMChatManager.getInstance().loadAllConversations();
                pd.dismiss();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onError(int i, final  String message) {
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Log.e("wu", "message==" + message);
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}
