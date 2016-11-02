package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.activity.ShowPhotoActivity;
import cn.com.cjland.careplus.data.Question;
import cn.com.cjland.careplus.fragment.AllQAFragment.ListItemCallBack;


public class AllQABaseAdapter extends ImagesBaseAdapter {
	private ListItemCallBack callBack;
    public AllQABaseAdapter(Context mContext, List<?> mListData, int mItem,ListItemCallBack callBack) {
		super(mContext,mListData,mItem);
		this.callBack = callBack;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(mItem, null);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.summary = (TextView) convertView.findViewById(R.id.tv_summary);
            viewHolder.respondImg = (ImageView) convertView.findViewById(R.id.iv_respond);
            viewHolder.heartImg = (ImageView) convertView.findViewById(R.id.iv_heart);
            viewHolder.respondNum = (TextView) convertView.findViewById(R.id.tv_respond_num);
            viewHolder.heartNum = (TextView) convertView.findViewById(R.id.tv_heart_num);
			viewHolder.photoNum = (TextView)convertView.findViewById(R.id.tv_phone_num);
			viewHolder.RelFollow = (RelativeLayout)convertView.findViewById(R.id.rel_zixun_follow);
			viewHolder.RelPhoto = (RelativeLayout)convertView.findViewById(R.id.rel_zixun_photo);
			viewHolder.RelAnswer = (RelativeLayout)convertView.findViewById(R.id.rel_zixun_answer);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Question question = (Question)mListData.get(position);
		viewHolder.title.setText(question.title);
		viewHolder.date.setText(question.date);
		viewHolder.summary.setText(question.summary);
		//是否有图片
		if (question.isHasPhoto) {
			viewHolder.RelPhoto.setVisibility(View.VISIBLE);
			viewHolder.photoNum.setText(""+question.photoNum+"张");
		}else{
			viewHolder.RelPhoto.setVisibility(View.GONE);
		}
		//获取行数 目测是因为在TextView创建完毕之后才会Post这条消息
		viewHolder.summary.post(new Runnable() {
			@Override
			public void run() {
			}
		});
		viewHolder.respondNum.setText(question.respondNum);
		viewHolder.heartNum.setText(question.heartNum);
		if(question.isHeart){
			viewHolder.heartImg.setImageResource(R.drawable.ic_heart_selected);
		}else{
			viewHolder.heartImg.setImageResource(R.drawable.ic_heart_normal);
		}
		//实现TextView的展开与收缩
		viewHolder.summary.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				question.flag = callBack.TextViewShrink(v,question.flag);
			}
		});
		viewHolder.RelFollow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				question.isHeart = callBack.FollowAction(question.isHeart, viewHolder.heartImg, viewHolder.heartNum);
			}
		});
		viewHolder.RelAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callBack.AnswerAction(question.qustionId);
			}
		});
		viewHolder.RelPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				callBack.ShowPhotoAction(question.qustionId);
				Intent showIntent = new Intent(mContext, ShowPhotoActivity.class);
				showIntent.putExtra("answerId",question.qustionId);
				mContext.startActivity(showIntent);
			}
		});
		return convertView;
	}
	private static class ViewHolder{
		public TextView title,date,summary,respondNum,heartNum,photoNum;
		public ImageView avatar,respondImg,heartImg;
		public RelativeLayout RelFollow,RelPhoto,RelAnswer;
	}
}
