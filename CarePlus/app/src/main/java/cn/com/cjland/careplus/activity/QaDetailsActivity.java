package cn.com.cjland.careplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.adapter.AnswerAdapter;
import cn.com.cjland.careplus.adapter.ZXDesImgAdapter;
import cn.com.cjland.careplus.data.Answer;
import cn.com.cjland.careplus.data.QA;
import cn.com.cjland.careplus.utils.AppManager;
import cn.com.cjland.careplus.view.ListViewForScrollView;

public class QaDetailsActivity extends BaseActivity {
    private ImageView mImgBack,mImgFollow;
    private TextView mTxtTitle,mTxtAnswerNum;
    private ScrollView mScrollView;
    private ListViewForScrollView mListView,mListImg;
    private AnswerAdapter mAnswerAdapter;
    private ZXDesImgAdapter mImgAdapter;
    private List<Answer> listData = new ArrayList<Answer>();
    private List<Answer> beanImgList = new ArrayList<Answer>();
    private RelativeLayout mRelFollow;
    private Boolean isHeart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa_details);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        findview();
    }
    private void findview(){
        mTxtTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mTxtTitle.setText("详情");
        mImgBack = (ImageView)mActionBar.getCustomView().findViewById(R.id.action_home);
        mImgBack.setVisibility(View.VISIBLE);
        mScrollView = (ScrollView)findViewById(R.id.scrollview_des_wordmat);
        mScrollView.smoothScrollTo(0, 0);
        mListView = (ListViewForScrollView) findViewById(R.id.list_view);
        mListImg = (ListViewForScrollView)findViewById(R.id.list_zixun_img);
        mRelFollow = (RelativeLayout)findViewById(R.id.rel_zixun_follow);
        mTxtAnswerNum = (TextView)findViewById(R.id.tv_heart_num);
        mImgFollow = (ImageView)findViewById(R.id.iv_heart);
        mRelFollow.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
        for(int i=0;i<2;i++){
            Answer answer = new Answer();
            answer.imagesUrl = "";
            beanImgList.add(answer);
        }
        mImgAdapter = new ZXDesImgAdapter(mContext, beanImgList, R.layout.activity_zx_img_item);
        mListImg.setAdapter(mImgAdapter);
        for (int i=0;i<10;i++) {
            Answer answer = new Answer();
            answer.title="佚名"+i;
            answer.summary="这次专家讲的也不错……";
            answer.date="2015-03-13 10:0"+i;
            answer.address = "上海人民医院";
            listData.add(answer);
        }
        mAnswerAdapter = new AnswerAdapter(mContext, listData, R.layout.answer_item);
        mListView.setAdapter(mAnswerAdapter);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_home:
                finish();
                break;
            case R.id.rel_zixun_follow:
                int num = Integer.parseInt(mTxtAnswerNum.getText().toString());
                if (isHeart) {
                    isHeart = false;
                    num -= 1;
                    mImgFollow.setImageResource(R.drawable.ic_heart_normal);
                }else{
                    isHeart = true;
                    num += 1;
                    mImgFollow.setImageResource(R.drawable.ic_heart_selected);
                }
                mTxtAnswerNum.setText(""+num);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }
}
