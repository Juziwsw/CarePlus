package cn.com.cjland.careplus.activity;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.ui.EaseContactListFragment;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.easemob.exceptions.EaseMobException;

import java.util.ArrayList;
import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.fragment.ContactsFragment;
import cn.com.cjland.careplus.fragment.HomeFragment;
import cn.com.cjland.careplus.fragment.MeFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener,
        HomeFragment.OnFragmentInteractionListener, ContactsFragment.OnFragmentInteractionListener,
        MeFragment.OnFragmentInteractionListener {
    public String TAG = "MainActivity";
    Context mContext;
    Resources mResources;
    public ViewPager mViewPager;
    FrageAdapter mFrageAdapter;
    List<Fragment> fragments = new ArrayList<Fragment>();
    LinearLayout mLinHome;
    LinearLayout mLinContacts;
    LinearLayout mLinChat;
    LinearLayout mLinMe;
    TextView mTvHome;
    TextView mTvContacts;
    TextView mTvChat;
    TextView mTvMe;
    ImageView mIvHome;
    ImageView mIvContacts;
    ImageView mIvChat;
    ImageView mIvMe;

    ImageView mNewUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swtAddContactsMsg();
        setContentView(R.layout.activity_main);
        EaseUI.getInstance().init(this);
        mContext = this.getApplicationContext();
        mResources = mContext.getResources();

        mLinHome = (LinearLayout) findViewById(R.id.tab_home);
        mLinHome.setOnClickListener(this);
        mLinContacts = (LinearLayout) findViewById(R.id.tab_contacts);
        mLinContacts.setOnClickListener(this);
        mLinChat = (LinearLayout) findViewById(R.id.tab_chat);
        mLinChat.setOnClickListener(this);
        mLinMe = (LinearLayout) findViewById(R.id.tab_me);
        mLinMe.setOnClickListener(this);

        mTvHome = (TextView)findViewById(R.id.tv_home);
        mTvContacts = (TextView)findViewById(R.id.tv_contacts);
        mTvChat = (TextView)findViewById(R.id.tv_chat);
        mTvMe = (TextView)findViewById(R.id.tv_me);

        mIvHome = (ImageView)findViewById(R.id.iv_home);
        mIvContacts = (ImageView)findViewById(R.id.iv_contacts);
        mIvChat = (ImageView)findViewById(R.id.iv_chat);
        mIvMe = (ImageView)findViewById(R.id.iv_me);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        fragments.add(HomeFragment.newInstance("",""));
        fragments.add(new EaseContactListFragment());
        fragments.add(new EaseConversationListFragment());
        fragments.add(MeFragment.newInstance("", ""));
        mFrageAdapter = new FrageAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mFrageAdapter);
        mViewPager.setOnPageChangeListener(mFrageAdapter);
        mViewPager.setCurrentItem(0);
        mFrageAdapter.onPageSelected(0);
    }
    class FrageAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{

        List<Fragment> fragments;

        public FrageAdapter(FragmentManager fragmentManager,
                            List<Fragment> fragments) {
            super(fragmentManager);
            // TODO Auto-generated constructor stub
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            switch (position) {
                case 0:
                    mTvHome.setTextColor(mResources.getColor(R.color.color52c6cc));
                    mTvContacts.setTextColor(mResources.getColor(R.color.gray));
                    mTvChat.setTextColor(mResources.getColor(R.color.gray));
                    mTvMe.setTextColor(mResources.getColor(R.color.gray));
                    mIvHome.setImageResource(R.drawable.ic_home_selected);
                    mIvContacts.setImageResource(R.drawable.ic_contacts_normal);
                    mIvChat.setImageResource(R.drawable.ic_chat_normal);
                    mIvMe.setImageResource(R.drawable.ic_me_normal);
//                    mActionTitle.setText(mResources.getString(R.string.home_text));
                    break;
                case 1:
                    mTvHome.setTextColor(mResources.getColor(R.color.gray));
                    mTvContacts.setTextColor(mResources.getColor(R.color.color52c6cc));
                    mTvChat.setTextColor(mResources.getColor(R.color.gray));
                    mTvMe.setTextColor(mResources.getColor(R.color.gray));
                    mIvHome.setImageResource(R.drawable.ic_home_normal);
                    mIvContacts.setImageResource(R.drawable.ic_contacts_selected);
                    mIvChat.setImageResource(R.drawable.ic_chat_normal);
                    mIvMe.setImageResource(R.drawable.ic_me_normal);
                    break;
                case 2:
                    mTvHome.setTextColor(mResources.getColor(R.color.gray));
                    mTvContacts.setTextColor(mResources.getColor(R.color.gray));
                    mTvChat.setTextColor(mResources.getColor(R.color.color52c6cc));
                    mTvMe.setTextColor(mResources.getColor(R.color.gray));
                    mIvHome.setImageResource(R.drawable.ic_home_normal);
                    mIvContacts.setImageResource(R.drawable.ic_contacts_normal);
                    mIvChat.setImageResource(R.drawable.ic_chat_selected);
                    mIvMe.setImageResource(R.drawable.ic_me_normal);
                    break;
                case 3:
                    mTvHome.setTextColor(mResources.getColor(R.color.gray));
                    mTvContacts.setTextColor(mResources.getColor(R.color.gray));
                    mTvChat.setTextColor(mResources.getColor(R.color.gray));
                    mTvMe.setTextColor(mResources.getColor(R.color.color52c6cc));
                    mIvHome.setImageResource(R.drawable.ic_home_normal);
                    mIvContacts.setImageResource(R.drawable.ic_contacts_normal);
                    mIvChat.setImageResource(R.drawable.ic_chat_normal);
                    mIvMe.setImageResource(R.drawable.ic_me_selected);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.tab_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_contacts:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_chat:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab_me:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.img_new_user:
                try {
                    EMContactManager.getInstance().addContact("18639737039","11111");
                   // EMContactManager.getInstance().addContact("18721899184","11111");
                    Log.e("wu", "onClick: " );
                } catch (EaseMobException e) {
                    e.printStackTrace();
                }
                //EMClient.getInstance().contactManager().addContact(toAddUsername, reason);
              /*  Intent intent = new Intent(MainActivity.this,NewUserActivity.class);
                startActivity(intent);*/
                break;
            default:
                break;
        }
    }

    public void setCurrentPage(int index) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(index);
        }
    }

    public int getCurrentPage() {
        if (mViewPager != null) {
            mViewPager.getCurrentItem();
        }
        return 0;
    }
    public void onFragmentInteraction(Uri uri){

    }
    /* 返回按钮 */
    private long mExitTime = 0L;// 控制关闭程序的变量

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        // 拦截MENU按钮点击事件，让他无任何操作
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void swtAddContactsMsg(){
        EMContactManager.getInstance().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(List<String> list) {
                //增加了联系人时回调此方法

            }
            @Override
            public void onContactDeleted(List<String> list) {
                //被删除时回调此方法

            }
            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
                cn.com.cjland.careplus.utils.Log.e("wu", "username==" + username + "&&" + "reason==" + reason);

            }
            @Override
            public void onContactAgreed(String s) {
                //好友请求被同意

            }
            @Override
            public void onContactRefused(String s) {
                //好友请求被拒绝

            }
        });
        EMChat.getInstance().setAppInited();
    }
}
