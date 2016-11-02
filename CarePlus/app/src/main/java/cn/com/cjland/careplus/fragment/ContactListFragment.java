package cn.com.cjland.careplus.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.easeui.ui.EaseContactListFragment;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.widget.ContactItemView;

/**
 * Created by Administrator on 2016/2/29.
 */
public class ContactListFragment extends EaseContactListFragment{
    private ContactItemView applicationItem;

    @Override
    protected void initView() {
        super.initView();
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.em_contacts_header, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        applicationItem = (ContactItemView) headerView.findViewById(R.id.application_item);
        applicationItem.setOnClickListener(clickListener);
       /* headerView.findViewById(R.id.group_item).setOnClickListener(clickListener);
        headerView.findViewById(R.id.chat_room_item).setOnClickListener(clickListener);
        headerView.findViewById(R.id.robot_item).setOnClickListener(clickListener);*/
        //添加headerview
        listView.addHeaderView(headerView);
        //添加正在加载数据提示的loading view
      /*  loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);*/
        //注册上下文菜单
        //registerForContextMenu(listView);

    }


    protected class HeaderItemClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.application_item:
                    // 进入申请与通知页面
                    //startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));
                    break;
              /*  case R.id.group_item:
                    // 进入群聊列表页面
                    startActivity(new Intent(getActivity(), GroupsActivity.class));
                    break;
                case R.id.chat_room_item:
                    //进入聊天室列表页面
                    startActivity(new Intent(getActivity(), PublicChatRoomsActivity.class));
                    break;
                case R.id.robot_item:
                    //进入Robot列表页面
                    startActivity(new Intent(getActivity(), RobotsActivity.class));
                    break;
*/
                default:
                    break;
            }
        }
    }

}
