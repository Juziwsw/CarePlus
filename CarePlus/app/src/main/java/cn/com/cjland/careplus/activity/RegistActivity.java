package cn.com.cjland.careplus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.utils.AppManager;
import cn.com.cjland.careplus.utils.HttpUtils;
import cn.com.cjland.careplus.utils.Log;

public class RegistActivity extends BaseActivity {
    EditText mEdtPhone;
    EditText mEdtCode;
    EditText mEdtPwd;
    EditText mEdtInviteCode;
    Button mBtnGetCode;
    Button mBtnSubmit;
    CheckBox mCBBox;
    private TimeCount mTime;
    public boolean mIsGetCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        mContext = getBaseContext();
        mTime = new TimeCount(60000, 1000);//构造CountDownTimer对象
        mIsGetCode = false;

        TextView mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("注册");
        ImageView homeView = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        homeView.setVisibility(View.VISIBLE);
        homeView.setOnClickListener(this);
        //init view
        mEdtPhone = (EditText) findViewById(R.id.edt_phone);
        mEdtCode = (EditText) findViewById(R.id.edt_code);
        mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        mEdtInviteCode = (EditText) findViewById(R.id.edt_invite_code);
        mBtnGetCode = (Button) findViewById(R.id.btn_get_code);
        mBtnGetCode.setOnClickListener(this);
        mBtnSubmit  = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
        mCBBox = (CheckBox) findViewById(R.id.check_box);
        mCBBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean state) {
                // TODO Auto-generated method stub
                if (state) {
                    mBtnGetCode.setEnabled(true);
                    mBtnSubmit.setEnabled(true);
                } else {
                    mBtnGetCode.setEnabled(false);
                    mBtnSubmit.setEnabled(false);
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_home:
                finish();
                break;
            case R.id.btn_get_code:
                if (checkInputPhone()) {
                    mTime.start();//开始计时
                    mIsGetCode = true;
                    Toast.makeText(mContext, "发送成功！", Toast.LENGTH_SHORT).show();
//                    new CodeDataTask().execute();
                }
                break;
            case R.id.btn_submit:
                if (checkInputPhone() && checkInputPassword() && checkSecruityCode()) {
                    mTime.cancel();
                    mIsGetCode = false;
                    Toast.makeText(mContext, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
//                    new PostDataTask().execute();
                }
                break;
            default:
                break;
        }
    }

    private boolean checkInputPhone() {
        // phone
        if (mEdtPhone.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (0 < mEdtPhone.getText().toString().length() && mEdtPhone.getText().toString().length() < 11) {
            Toast.makeText(getApplicationContext(), "请输入完整的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        //
        return true;

    }
    private boolean checkInputPassword() {
        //password
        if (mEdtPwd.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (0 < mEdtPwd.getText().toString().length() && mEdtPwd.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "密码不得小于6位", Toast.LENGTH_SHORT).show();
            return false;
        }
        //
        return true;
    }
    private boolean checkSecruityCode() {
        //password
        if (!mIsGetCode) {
            Toast.makeText(getApplicationContext(), "请先获取验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mEdtCode.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (0 < mEdtCode.getText().toString().length() && mEdtCode.getText().toString().length() < 4) {
            Toast.makeText(getApplicationContext(), "请输入完整的验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        //
        return true;
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            mBtnGetCode.setText("获取");
            mBtnGetCode.setEnabled(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            mBtnGetCode.setEnabled(false);
            mBtnGetCode.setText(millisUntilFinished /1000+"秒");
        }
    }
    private class CodeDataTask extends AsyncTask<Void, Void, String> {
        String resultdata = null;
        String url = getResources().getString(R.string.urlheader)+"/User/code";
        String param = "user_number=" + mEdtPhone.getText().toString();
        //后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            // Simulates a background job.
            try {
                resultdata = new HttpUtils().CodePostString(mContext, url, param);
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
                if (!dataRst.getString("event").equals("0")) {
                    mTime.cancel();
                    mIsGetCode = false;
                }
                Toast.makeText(mContext, dataRst.getString("msg"), Toast.LENGTH_SHORT).show();
            }  catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }
    private class PostDataTask extends AsyncTask<Void, Void, String> {
        String resultdata = null;
        String url = getResources().getString(R.string.urlheader)+"/User/password";
        String param = "user_number=" + mEdtPhone.getText().toString() + "&new_password="
                + mEdtPwd.getText().toString() + "&security_code=" + mEdtCode.getText().toString();;
        //后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            // Simulates a background job.
            try {
                resultdata = new HttpUtils().RegistPostString(mContext, url, param);
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

//    public Handler baseHander = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
//                    break;
//                case 2:
//                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
//                    break;
//                case 3:
//                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
//                    break;
//                case 4:
//                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
//                    break;
//                case 5:
//                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
//                    break;
//
//
//            }
//        }
//
//    };
//    private  class registerThread extends  Thread{
//        String username,pwd;
//        public  registerThread(String name,String passWord){
//            username = name;
//            pwd = passWord;
//        }
//        @Override
//        public void run() {
//            try{
//                // 调用sdk注册方法
//                Log.e("wushiwei", "name=" + username);
//                EMChatManager.getInstance().createAccountOnServer(username, pwd);
//                Message m = new Message();
//                m.what = 1;
//                baseHander.sendMessage(m);
//            }catch (final EaseMobException e){
//                //注册失败
//                Message m = new Message();
//
//                int errorCode=e.getErrorCode();
//                if(errorCode== EMError.NONETWORK_ERROR){
//                    m.what = 2;
//                    m.obj =e.getMessage();
//                    baseHander.sendMessage(m);;
//                }else if(errorCode==EMError.USER_ALREADY_EXISTS){
//                    m.what = 3;
//                    m.obj =e.getMessage();
//                    baseHander.sendMessage(m);
//                }else if(errorCode==EMError.UNAUTHORIZED){
//                    m.what = 4;
//                    m.obj =e.getMessage();
//                    baseHander.sendMessage(m);
//                }else{
//                    m.what = 5;
//                    m.obj =e.getMessage();
//                    baseHander.sendMessage(m);
//                }
//
//            }
//        }
//    }
}
