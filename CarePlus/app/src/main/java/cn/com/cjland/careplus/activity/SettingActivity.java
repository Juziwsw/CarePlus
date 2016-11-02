package cn.com.cjland.careplus.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.cjland.careplus.R;

public class SettingActivity extends BaseActivity {
    private ActionBar mActionBar;
    private TextView mActionTitle;
    private ImageView homeView;
    private RelativeLayout mRelpbjFeek,mRelPbjhos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findview();
    }
    private void findview() {
        mActionBar = this.getActionBar();
        mActionTitle = (TextView) mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("设置");
        homeView = (ImageView) mActionBar.getCustomView().findViewById(R.id.action_home);
        homeView.setVisibility(View.VISIBLE);
        mRelpbjFeek = (RelativeLayout)findViewById(R.id.rel_setting_feek);
        mRelPbjhos = (RelativeLayout)findViewById(R.id.rel_setting_pbj);
        homeView.setOnClickListener(this);
        mRelpbjFeek.setOnClickListener(this);
        mRelPbjhos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_home:
                colseActivity();
                break;
            case R.id.rel_setting_feek:
                startActivity(new Intent(mContext, FeekActivity.class));
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
            case R.id.rel_setting_pbj:
                startActivity(new Intent(mContext, VersionActivity.class));
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
        }
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
