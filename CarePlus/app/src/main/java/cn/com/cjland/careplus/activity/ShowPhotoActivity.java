package cn.com.cjland.careplus.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import cn.com.cjland.careplus.R;

public class ShowPhotoActivity extends Activity {
    private ViewPager mViewager;
    private LinearLayout mLayoutTip;//加载点点
    private ImageView[] tips;
    private View[] mViews;
    private int mAnswerId;
    private ArrayList<View> views;
    private PagerAdapter mPagerAdapter;
    private Button mBtnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        mAnswerId = getIntent().getIntExtra("",0);
        findview();
        setViewpager();
    }
    /**
     * 获取所需控件
     */
    private void findview(){
        mViewager = (ViewPager)findViewById(R.id.viewpager_redpackge);
        mLayoutTip = (LinearLayout)findViewById(R.id.layout_viewpager_tip);
        mBtnClose = (Button)findViewById(R.id.btn_shoaphoto_close);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setViewpager(){
        mLayoutTip.removeAllViews();
        views = new ArrayList<View>();
//        int count = listred.size();
        int count = 4;
        //将点点加入到ViewGroup中
        tips = new ImageView[count];
        //将布局加入到View中
        mViews = new View[count];
        // 将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(ShowPhotoActivity.this);
        // 每个页面的view数据
        for (int i=0;i<count;i++){
            final ImageView imgphoto;
            ImageView imageView = new ImageView(this);
            // 为TextView添加长高设置
            LinearLayout.LayoutParams layoutParams_txt = new LinearLayout.LayoutParams(15, 15);
            layoutParams_txt.leftMargin = 10;
            imageView.setLayoutParams(layoutParams_txt);
            if(i == 0){
                imageView.setImageResource(R.drawable.ic_icon_span01);
            }else {
                imageView.setImageResource(R.drawable.ic_icon_crlrl);
            }
            tips[i] = imageView;
            mLayoutTip.addView(tips[i]);
            View view = mLi.inflate(R.layout.viewpager_photo, null);
            mViews[i] = view;
            views.add(view);
            imgphoto = (ImageView)mViews[i].findViewById(R.id.img_photo);
            switch (i){
                case 0:
                    imgphoto.setImageResource(R.drawable.ic_bg_all);
                    break;
                case 1:
                    imgphoto.setImageResource(R.drawable.ic_login_header_bg);
                    break;
                case 2:
                    imgphoto.setImageResource(R.drawable.ic_bg_all);
                    break;
                case 3:
                    imgphoto.setImageResource(R.drawable.ic_login_header_bg);
                    break;
            }
            imgphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ShowPhotoActivity.this,"当前位置----------",Toast.LENGTH_SHORT).show();
                }
            });

        }

        // 填充ViewPager的数据适配器
        mPagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }
        };
        mViewager.setAdapter(mPagerAdapter);//为viewpager配置适配器
        /**
         * 设置选中的tip的背景
         * @param selectItems
         */
        mViewager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < tips.length; i++) {
                    if (i == position) {
                        tips[i].setImageResource(R.drawable.ic_icon_span01);
                    } else {
                        tips[i].setImageResource(R.drawable.ic_icon_crlrl);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
