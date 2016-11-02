package cn.com.cjland.careplus.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.sdp.Info;

import cn.com.cjland.careplus.R;

public class AuthenticationActivity extends BaseActivity{
    private ActionBar mActionBar;
    private TextView mActionTitle,mTxtHosName,mTxtKsName,mTxtZcName;
    private ImageView homeView,mImgPhoto;
    private Button mBtnSubmit;
    private RelativeLayout mLayoutHos,mLayoutKs,mLayoutZc,mLayoutPhoto;
    private EditText mEditAuthName,mEditAuthQH,mEditAuthNumber,mEditAuthBH,mEditAuthDes;
    //选择头像
    private Dialog mDialog;
    private Button mBtnphonegraph,mBtnphonealbum,mBtnphotoCancle;
    /* 用来标识请求gallery的activity */
    private static final int UPLOAD_IMAGE = 2;
    private static final int PHOTO_REQUEST_CUT = 3;

    /* 拍照的照片存储位置 */
    public static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;
    private static File sdcardTempFile;
    private Bitmap bitmap;
    private String path;
    private String picPath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        File file = new File("/mnt/sdcard/companyfamily");
        if (!file.exists()) {
            try {
                //按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        sdcardTempFile = new File("/mnt/sdcard/companyfamily", "tmp_pic_" + SystemClock.currentThreadTimeMillis() + ".jpg");
        findview();
    }
    private void findview(){
        mActionBar = this.getActionBar();
        mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("认证");
        homeView = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        mBtnSubmit = (Button)mActionBar.getCustomView().findViewById(R.id.btn_auth_submit);
        mBtnSubmit.setVisibility(View.VISIBLE);
        mLayoutHos = (RelativeLayout)findViewById(R.id.layout_auth_hos);
        mTxtHosName = (TextView)findViewById(R.id.txt_auth_hos);
        mLayoutKs = (RelativeLayout)findViewById(R.id.layout_auth_keshi);
        mTxtKsName = (TextView)findViewById(R.id.txt_auth_ks);
        mLayoutZc = (RelativeLayout)findViewById(R.id.layout_auth_professional);
        mTxtZcName = (TextView)findViewById(R.id.txt_auth_zc);
        mLayoutPhoto = (RelativeLayout)findViewById(R.id.layout_auth_photo);
        mImgPhoto = (ImageView)findViewById(R.id.img_auth_phone);

        mEditAuthName = (EditText)findViewById(R.id.edit_auth_name);
        mEditAuthQH  = (EditText)findViewById(R.id.edit_auth_phone);
        mEditAuthNumber = (EditText)findViewById(R.id.edit_auth_number);
        mEditAuthBH = (EditText)findViewById(R.id.edit_auth_bianhao);
        mEditAuthDes = (EditText)findViewById(R.id.edit_auth_des);
        mBtnSubmit.setOnClickListener(this);
        mLayoutZc.setOnClickListener(this);
        mLayoutHos.setOnClickListener(this);
        mLayoutKs.setOnClickListener(this);
        homeView.setOnClickListener(this);
        mLayoutPhoto.setOnClickListener(this);
    }
    /**
     * 选择图片方式
     */
    private void Selectphotos(){
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.select_phone_dialog, null );
        mDialog = new AlertDialog.Builder(this).create();
        Window dialogWindow =mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        //添加动画
        Window window = mDialog.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);

        mDialog.show();

        //自定义dialog的宽度充满整个屏幕
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        mDialog.getWindow().setAttributes(lp);

        mDialog.getWindow().setContentView(layout);
        mBtnphonegraph = (Button)layout.findViewById(R.id.btn_phone_ghotograph);
        mBtnphonegraph.setOnClickListener(this);
        mBtnphonealbum = (Button)layout.findViewById(R.id.btn_photo_album);
        mBtnphonealbum.setOnClickListener(this);
        mBtnphotoCancle = (Button)layout.findViewById(R.id.btn_photo_cancle);
        mBtnphotoCancle.setOnClickListener(this);
    }
    //监听事件
    @Override
    public void onClick(View view) {
        Intent itgo;
        switch (view.getId()){
            case R.id.action_home://返回
                finish();
                break;
            case R.id.btn_auth_submit://提交
                submitData();
                break;
            case R.id.layout_auth_hos://选择医院
                itgo = new Intent(this,SelectHosActivity.class);
                startActivityForResult(itgo,1);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
            case R.id.layout_auth_keshi://选择科室
                itgo = new Intent(this,SelectKeshiActivity.class);
                startActivityForResult(itgo,1);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
            case R.id.layout_auth_professional://选择职称
                itgo = new Intent(this,SelectZcActivity.class);
                startActivityForResult(itgo,1);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
            case R.id.layout_auth_photo:
                Selectphotos();
                break;
            case R.id.btn_phone_ghotograph://拍照
                doTakePhoto();
                break;
            case R.id.btn_photo_album://选择照片
                doPickPhotoFromGallery();
                break;
            case R.id.btn_photo_cancle:
                mDialog.dismiss();
                break;
        }
    }

    /**
     * 提交按钮事件
     */
    public void submitData(){
        String AuthName = mEditAuthName.getText().toString();
        String AuthQH = mEditAuthQH.getText().toString();
        String AuthNumber = mEditAuthNumber.getText().toString();
        String AuthBH = mEditAuthBH.getText().toString();
        String AuthDes = mEditAuthDes.getText().toString();
        if(!AuthName.equals("")&&!AuthQH.equals("")&&!AuthNumber.equals("")&&!AuthBH.equals("")&&!picPath.equals("")){
            mBtnSubmit.setClickable(false);
            startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }else if(AuthName.equals("")){
            Toast.makeText(this,"真实姓名不能为空",Toast.LENGTH_SHORT).show();
        }else if(AuthQH.equals("")){
            Toast.makeText(this,"区号不能为空",Toast.LENGTH_SHORT).show();
        }else if(AuthNumber.equals("")){
            Toast.makeText(this,"电话号码不能为空",Toast.LENGTH_SHORT).show();
        }else if(AuthBH.equals("")){
            Toast.makeText(this,"编号不能为空",Toast.LENGTH_SHORT).show();
        }else if(picPath.equals("")){
            Toast.makeText(this,"请选择执业证或胸牌照片",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(data!=null) {
                    switch (resultCode) {
                        case 101:
                            mTxtHosName.setText(data.getStringExtra("HosName"));
                            break;
                        case 102:
                            mTxtKsName.setText(data.getStringExtra("KSName"));
                            break;
                        case 103:
                            mTxtZcName.setText(data.getStringExtra("ZCName"));
                            break;
                    }
                }
                break;
            case CAMERA_WITH_DATA:
                doCropPhoto(data);
                break;
            case UPLOAD_IMAGE:
                if (data != null) {
                    bitmap = data.getParcelableExtra("data");
                    mImgPhoto.setImageBitmap(bitmap);
                    mDialog.dismiss();
                    Uri url;
                    try {
                        url = Uri.parse(saveBitmapToFile(bitmap));
                        picPath = url.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_REQUEST_CUT:
                bitmap = BitmapFactory.decodeFile(sdcardTempFile.getAbsolutePath());
                mImgPhoto.setImageBitmap(bitmap);
                mDialog.dismiss();
                Uri url;
                try {
                    url = Uri.parse(saveBitmapToFile(bitmap));
                    picPath = url.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
    /**
     * 拍照获取图片
     *
     */
    private void doTakePhoto() {
        try {
            path = "/mnt/sdcard/companyfamily/zyphoto.jpg";
            final Intent intent = getTakePickIntent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(path)));
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "获取照片错误", Toast.LENGTH_LONG).show();
        }
    }

    private static Intent getTakePickIntent(String action) {
        Intent intent = new Intent();
        intent.putExtra("return-data", true);
        intent.setAction(action);
        return intent;
    }
    private void doCropPhoto(Intent data) {
        Uri currImageURI = null;
        if (data != null) {
            if (data.getExtras() != null) {
                File file = getFile(getBitmap(data));
                // 给新照的照片文件命名
                currImageURI = Uri.fromFile(file);
            } else {
                currImageURI = data.getData();
            }
        } else {
            currImageURI = Uri.fromFile(new File(path));
        }
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(currImageURI);
            startActivityForResult(intent, UPLOAD_IMAGE);
        } catch (Exception e) {
            Toast.makeText(this, "获取照片错误", Toast.LENGTH_LONG).show();
        }
    }
    private Bitmap getBitmap(Intent data) {

        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

        return bitmap;
    }

    private File getFile(Bitmap bitmap) {

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用

            return null;
        }
        String name = new DateFormat().format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";

        FileOutputStream b = null;

        if (!PHOTO_DIR.isDirectory()) {
            PHOTO_DIR.mkdirs();// 创建文件夹
        }
        File fileName = new File(PHOTO_DIR, name);

        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileName;
    }
    /**
     * Constructs an intent for image cropping. 调用图片剪辑程序
     */
    private static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        return intent;
    }
    /**
     * Save Bitmap to a file.保存图片到SD卡。
     */
    public static String saveBitmapToFile(Bitmap bitmap) throws IOException {
        BufferedOutputStream os = null;
        String path_file = getFilePath().trim() + "/zyphoto.png";
        try {
            File file = new File(path_file);
            int end = path_file.lastIndexOf(File.separator);
            String _filePath = path_file.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.fillInStackTrace();
                }
            }
        }
        return path_file;
    }

    public static String getFilePath() {
        File path = new File("/mnt/sdcard/companyfamily");;
        return path.toString();

    }
    // 请求Gallery程序
    private void doPickPhotoFromGallery() {
        try {
            final Intent intent = getPhotoPickIntent();
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "获取照片错误", Toast.LENGTH_LONG).show();
        }
    }
    // 封装请求Gallery的intent
    private static Intent getPhotoPickIntent() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("output", Uri.fromFile(sdcardTempFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);// 输出图片大小
        intent.putExtra("outputY", 300);
        return intent;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
