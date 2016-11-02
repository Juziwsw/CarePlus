package cn.com.cjland.careplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.cjland.careplus.R;

public class AnswerActivity extends BaseActivity {
    EditText mEdtSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mContext = getBaseContext();
        TextView mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("回答");
        TextView mActionTask = (TextView)mActionBar.getCustomView().findViewById(R.id.action_task);
        mActionTask.setVisibility(View.VISIBLE);
        mActionTask.setText("发送");
        mActionTask.setOnClickListener(this);
        ImageView homeView = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        homeView.setVisibility(View.VISIBLE);
        homeView.setOnClickListener(this);
        mEdtSummary = (EditText) findViewById(R.id.edt_summary);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_home:
                finish();
                break;
            case R.id.action_task://注册
                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
