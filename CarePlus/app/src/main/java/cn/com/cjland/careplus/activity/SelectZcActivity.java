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

public class SelectZcActivity extends BaseActivity {
    private ActionBar mActionBar;
    private ImageView mImgBack;
    private TextView mActionTitle;
    private ListView mListview;
    private List<Question> listData = new ArrayList<Question>();
    private HosAdapter mHosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhicheng);
        findview();
    }
    private void findview(){
        mActionBar = this.getActionBar();
        mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("选择职称");
        mImgBack = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        mImgBack.setVisibility(View.VISIBLE);
        mListview = (ListView)findViewById(R.id.list_zc_view);
        setData();
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Question question = listData.get(position);
                    colseActivity(question.title);
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
        String data[] = getResources().getStringArray(R.array.zhicheng);
        for (int i =0 ;i<data.length;i++){
            Question question = new Question();
            question.title = data[i];
            listData.add(question);
        }
        mHosAdapter = new HosAdapter(SelectZcActivity.this, listData, R.layout.activity_hosname_item);
        mListview.setAdapter(mHosAdapter);
    }
    private void colseActivity(String hosname){
        Intent intent = new Intent(SelectZcActivity.this, AuthenticationActivity.class);
        intent.putExtra("ZCName", hosname);
        setResult(103, intent);
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
