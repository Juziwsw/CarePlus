package cn.com.cjland.careplus.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.adapter.AllQABaseAdapter;
import cn.com.cjland.careplus.adapter.HosAdapter;
import cn.com.cjland.careplus.data.Question;

public class SelectHosActivity extends Activity{
    private ActionBar mActionBar;
    private ImageView mImgBack;
    private EditText mAutoComTxt,mEditNo;
    private Button mBtnNo;
    private ListView mListview;
    private List<Question> listData = new ArrayList<Question>();
    private HosAdapter mHosAdapter;
    private LinearLayout mLayoutNoData;
    private TextView mTextNoHos,mTxtnohoswar;
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hos);
        findview();
    }
    private void findview(){
        mActionBar = this.getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_layout_hos);
        mImgBack = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        mAutoComTxt = (EditText)mActionBar.getCustomView().findViewById(R.id.txt_auto_hos);
        mListview = (ListView)findViewById(R.id.list_hos_view);
        mLayoutNoData = (LinearLayout)findViewById(R.id.layout_nodata_war);
        mTextNoHos = (TextView)findViewById(R.id.txt_no_hos);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
            }
        });
//        mAutoComTxt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mAutoComTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    setData();
                    return true;
                }
                return false;
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = listData.get(position);
                colseActivity(question.title);
            }
        });
        mTextNoHos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    private void setData(){
        for (int i=0;i<10;i++){
            Question question = new Question();
            question.title="上海"+i+"医院";
            listData.add(question);
        }
        mHosAdapter = new HosAdapter(SelectHosActivity.this, listData, R.layout.activity_hosname_item);
        mListview.setAdapter(mHosAdapter);
        mLayoutNoData.setVisibility(View.GONE);
        mListview.setVisibility(View.VISIBLE);
    }
    //弹出框
    private void showDialog(){
        //消息按钮监听事件
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.select_hos_pop, null );
        //对话框
        mDialog = new AlertDialog.Builder(this).create();
        mDialog.show();
        //自定义dialog的宽度充满整个屏幕
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        mDialog.getWindow().setAttributes(lp);

        mDialog.getWindow().setContentView(layout);
        mDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditNo = (EditText)layout.findViewById(R.id.edit_no_hos);
        mBtnNo = (Button)layout.findViewById(R.id.btn_no_hos);
        mTxtnohoswar = (TextView)layout.findViewById(R.id.txt_nohos_war);
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditNo.getText().toString();
                if (name.equals("")) {
                    mTxtnohoswar.setVisibility(View.VISIBLE);
                } else {
                    colseActivity(name);
                }
            }
        });
    }
    private void colseActivity(String hosname){
        Intent intent = new Intent(SelectHosActivity.this, AuthenticationActivity.class);
        intent.putExtra("HosName", hosname);
        setResult(101, intent);
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
