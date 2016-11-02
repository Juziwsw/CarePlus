package cn.com.cjland.careplus.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.cjland.careplus.R;

public class FeekActivity extends BaseActivity {
    private ActionBar mActionBar;
    private TextView mActionTitle;
    private ImageView homeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feek);
        findview();
    }
    private void findview() {
        mActionBar = this.getActionBar();
        mActionTitle = (TextView) mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("意见反馈");
        homeView = (ImageView) mActionBar.getCustomView().findViewById(R.id.action_home);
        homeView.setVisibility(View.VISIBLE);
        homeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_home:
                colseActivity();
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
