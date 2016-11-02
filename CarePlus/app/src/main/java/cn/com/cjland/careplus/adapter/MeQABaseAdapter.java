package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.data.Question;
import cn.com.cjland.careplus.fragment.MeQAFragment.ListItemCallBack;


public class MeQABaseAdapter extends ImagesBaseAdapter {
	private ListItemCallBack callBack;
    public MeQABaseAdapter(Context mContext, List<?> mListData, int mItem,ListItemCallBack callBack) {
		super(mContext,mListData,mItem);
		this.callBack = callBack;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub

		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(mItem, null);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.summary = (TextView) convertView.findViewById(R.id.tv_summary);
            viewHolder.respondNum = (TextView) convertView.findViewById(R.id.tv_respond_num);
			viewHolder.RelAnswer = (LinearLayout)convertView.findViewById(R.id.rel_me_answer);
			viewHolder.RelPhoto = (RelativeLayout)convertView.findViewById(R.id.rel_zixun_photo);
			viewHolder.photoNum = (TextView)convertView.findViewById(R.id.tv_phone_num);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Question question = (Question)mListData.get(position);
		viewHolder.title.setText(question.title);
		viewHolder.date.setText(question.date);
		viewHolder.summary.setText(question.summary);
		viewHolder.respondNum.setText(question.respondNum);
		//是否有图片
		if (question.isHasPhoto) {
			viewHolder.RelPhoto.setVisibility(View.VISIBLE);
			viewHolder.photoNum.setText("" + question.photoNum + "张");
		}else{
			viewHolder.RelPhoto.setVisibility(View.GONE);
		}
		//实现TextView的展开与收缩
		viewHolder.summary.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				question.flag = callBack.TextViewShrink(v, question.flag);
			}
		});
		viewHolder.RelAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callBack.AnswerAction(question.qustionId);
			}
		});
		return convertView;
	}
	private static class ViewHolder{
		public TextView title,date,summary,respondNum,photoNum;
		public ImageView avatar;
		public RelativeLayout RelPhoto;
		public LinearLayout RelAnswer;
	}
}
