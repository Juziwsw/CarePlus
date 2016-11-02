package cn.com.cjland.careplus.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.adapter.HosAdapter;
import cn.com.cjland.careplus.data.Question;

public class SelectKeshiActivity extends BaseActivity {
    private ActionBar mActionBar;
    private ImageView mImgBack;
    private TextView mActionTitle;
    private ListView mListview,mListviewEr;
    private List<Question> listData = new ArrayList<Question>();
    private List<Question> listErData = new ArrayList<Question>();
    private HosAdapter mHosAdapter;
    private Dialog mDialog;
    private String KSname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keshi);
        findview();
    }
    private void findview(){
        mActionBar = this.getActionBar();
        mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("选择科室");
        mImgBack = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        mImgBack.setVisibility(View.VISIBLE);
        mListview = (ListView)findViewById(R.id.list_ks_view);
        setData();
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3 || position == 4 || position == 6 || position == 8
                        || position == 10 || position == 11 || position == 15) {
                    Question question = listData.get(position);
                    colseActivity(question.title);

                } else {
                    Question question = listData.get(position);
                    KSname = question.title;
                    showErDialog(position);
                }
            }
        });
        mImgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_home:
                finish();
                overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
                break;
        }
    }
    private void setData(){
        String data[] = getResources().getStringArray(R.array.keshi);
        for (int i =0 ;i<data.length;i++){
            Question question = new Question();
            question.title = data[i];
            listData.add(question);
        }
        mHosAdapter = new HosAdapter(SelectKeshiActivity.this, listData, R.layout.activity_hosname_item);
        mListview.setAdapter(mHosAdapter);
    }
    private void setErData(int position) {
        Resources res = getResources();
        String[] data = new String[0];
        listErData.clear();
        switch (position) {
            case 0:
                data = res.getStringArray(R.array.keshi1);
                break;
            case 1:
                data = res.getStringArray(R.array.keshi2);
                break;
            case 2:
                data = res.getStringArray(R.array.keshi3);
                break;
            case 5:
                data = res.getStringArray(R.array.keshi4);
                break;
            case 7:
                data = res.getStringArray(R.array.keshi5);
                break;
            case 9:
                data = res.getStringArray(R.array.keshi6);
                break;
            case 12:
                data = res.getStringArray(R.array.keshi7);
                break;
            case 13:
                data = res.getStringArray(R.array.keshi8);
                break;
            case 14:
                data = res.getStringArray(R.array.keshi9);
                break;
            case 16:
                data = res.getStringArray(R.array.keshi10);
                break;
        }
        for (int i = 0; i < data.length; i++) {
            Question question = new Question();
            question.title = data[i];
            listErData.add(question);
        }
        mHosAdapter = new HosAdapter(SelectKeshiActivity.this, listErData, R.layout.activity_hosname_item);
        mListviewEr.setAdapter(mHosAdapter);
    }
    //弹出框
    private void showErDialog(int position){
        //消息按钮监听事件
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.select_keshi_er, null );
        //对话框
        mDialog = new AlertDialog.Builder(this).create();
        Window window = mDialog.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);  //添加动画
        mDialog.show();
        //自定义dialog的宽度充满整个屏幕
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        lp.height = (int)(display.getHeight());
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().setContentView(layout);
        mListviewEr = (ListView)layout.findViewById(R.id.list_kser_view);
        setErData(position);
        Button mBtnCanle = (Button)layout.findViewById(R.id.btn_cancle);
        mBtnCanle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mListviewEr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDialog.dismiss();
                Question question = listErData.get(position);
                KSname = KSname+">"+question.title;
                colseActivity(KSname);
            }
        });
    }
    private void colseActivity(String hosname){
        Intent intent = new Intent(SelectKeshiActivity.this, AuthenticationActivity.class);
        intent.putExtra("KSName", hosname);
        setResult(102, intent);
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
