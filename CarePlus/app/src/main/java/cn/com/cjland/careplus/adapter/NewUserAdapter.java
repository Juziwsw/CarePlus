package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.data.NewUser;

/**
 * Created by Administrator on 2016/2/26.
 */
public class NewUserAdapter extends ImagesBaseAdapter{

    public NewUserAdapter(Context mContext, List<?> mListData, int mItem) {
        super(mContext, mListData, mItem);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(mItem, null);
            viewHolder.newUserAvatar = (ImageView) convertView.findViewById(R.id.img_user_avatar);
            viewHolder.newUserName = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.putTime = (TextView) convertView.findViewById(R.id.txt_time);
            viewHolder.btnAccept = (ImageView) convertView.findViewById(R.id.btn_accept);
            viewHolder.txtAccept = (TextView) convertView.findViewById(R.id.txt_accepted);
            viewHolder.newUserAge = (TextView) convertView.findViewById(R.id.txt_age);
            viewHolder.putNumber = (TextView) convertView.findViewById(R.id.txt_num);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewUser newUser = (NewUser)mListData.get(position);
        viewHolder.newUserName.setText(newUser.newUserName);
        viewHolder.putTime.setText(newUser.putTime);
        viewHolder.newUserAge.setText(newUser.newUserAge);
        viewHolder.putNumber.setText(newUser.putNumber);
        if (newUser.received){
            viewHolder.btnAccept.setVisibility(View.GONE);
            viewHolder.txtAccept.setVisibility(View.VISIBLE);
        }else {
            viewHolder.btnAccept.setVisibility(View.VISIBLE);
            viewHolder.txtAccept.setVisibility(View.GONE);
        }
        viewHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.btnAccept.setVisibility(View.GONE);
                viewHolder.txtAccept.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        public ImageView newUserAvatar;
        public TextView newUserName;
        public TextView newUserSex;
        public TextView newUserAge;
        public TextView putTime;
        public TextView putNumber;
        public ImageView btnAccept;
        public TextView txtAccept;

    }
}
