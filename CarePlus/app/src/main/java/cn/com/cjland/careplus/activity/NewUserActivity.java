package cn.com.cjland.careplus.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.adapter.NewUserAdapter;
import cn.com.cjland.careplus.data.NewUser;
import cn.com.cjland.careplus.utils.AppManager;

/**
 * Created by Administrator on 2016/2/26.
 */
public class NewUserActivity extends BaseActivity {
    private ListView listView;
    private NewUserAdapter newUserAdapter;
    private List<NewUser>listData = new ArrayList<NewUser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        AppManager.getAppManager().addActivity(this);
        mContext = getBaseContext();
        TextView mActionTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.action_title);
        mActionTitle.setText("新的患者");
        getData();
        listView = (ListView) findViewById(R.id.listView);
        newUserAdapter = new NewUserAdapter(mContext, listData, R.layout.fragment_newuser_item);
        listView.setAdapter(newUserAdapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = menuInfo.position;
        listData.remove(position);
        newUserAdapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    private void getData(){
        for (int i = 0;i < 10;i++){
            NewUser newUser = new NewUser();
            newUser.newUserName = "花千骨"+i;
            newUser.putTime = "2016/2/26";
            if (i%2 == 0){
                newUser.received = true;
            }
            newUser.type = "普通咨询";
            newUser.newUserAge = 15+i+"";
            newUser.putNumber = 1+i+"";
            listData.add(newUser);
        }
    }
}
