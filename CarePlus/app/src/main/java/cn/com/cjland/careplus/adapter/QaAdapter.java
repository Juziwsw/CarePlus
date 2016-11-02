package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.data.QA;
import cn.com.cjland.careplus.data.Question;


public class QaAdapter extends ImagesBaseAdapter {

    public QaAdapter(Context mContext, List<?> mListData, int mItem) {
		super(mContext,mListData,mItem);

	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub

		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(mItem, null);

			viewHolder.titleA = (TextView) convertView.findViewById(R.id.tv_ta);
            viewHolder.titleB = (TextView) convertView.findViewById(R.id.tv_tb);
            viewHolder.summary = (TextView) convertView.findViewById(R.id.tv_summary);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		QA qa = (QA)mListData.get(position);
		viewHolder.titleA.setText(qa.titleA);
		viewHolder.titleB.setText(qa.titleB);
		viewHolder.summary.setText(qa.summary);

		return convertView;
	}
	private static class ViewHolder{
		public TextView titleA;
		public TextView titleB;
		public TextView summary;
	}
}
