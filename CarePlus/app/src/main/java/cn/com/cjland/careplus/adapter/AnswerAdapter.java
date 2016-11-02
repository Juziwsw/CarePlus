package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.data.Answer;
import cn.com.cjland.careplus.data.Question;


public class AnswerAdapter extends ImagesBaseAdapter {

    public AnswerAdapter(Context mContext, List<?> mListData, int mItem) {
		super(mContext,mListData,mItem);

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
			viewHolder.address = (TextView)convertView.findViewById(R.id.tv_address);
            viewHolder.summary = (TextView) convertView.findViewById(R.id.tv_summary);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Answer answer = (Answer)mListData.get(position);
		viewHolder.title.setText(answer.title);
		viewHolder.date.setText(answer.date);
		viewHolder.summary.setText(answer.summary);
		viewHolder.address.setText(answer.address);

		return convertView;
	}
	private static class ViewHolder{
		public ImageView avatar;
		public TextView title,date,address,summary;
	}
}
