package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.List;

import cn.com.cjland.careplus.R;


public class TestBaseAdapter extends ImagesBaseAdapter {

	static boolean flag = true;
    public TestBaseAdapter(Context mContext, List<?> mListData, int mItem) {
		super(mContext,mListData,mItem);

	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub

		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(mItem, null);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_des);
			viewHolder.btnFold = (Button) convertView.findViewById(R.id.btn_fold);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(mListData.get(position).toString());
//		//获取行数 目测是因为在TextView创建完毕之后才会Post这条消息
//		viewHolder.textView.post(new Runnable() {
//			@Override
//			public void run() {
//				Log.i("tv.getLineCount()", "" + viewHolder.textView.getLineCount());
//			}
//		});
		//实现TextView的展开与收缩
		viewHolder.btnFold.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("tv.getLineCount()", viewHolder.textView.getHeight() + "");
				if (flag) {
					flag = false;
					viewHolder.textView.setEllipsize(null); // 展开
					viewHolder.textView.setSingleLine(flag);
					viewHolder.textView.post(new Runnable() {
						@Override
						public void run() {
							Log.i("tv.getLineCount()", "" + viewHolder.textView.getLineCount());
						}
					});
					viewHolder.btnFold.setText("收起");
				} else {
					flag = true;
					viewHolder.textView.setLines(3);
					viewHolder.textView.setEllipsize(TextUtils.TruncateAt.END); // 收缩
					viewHolder.textView.post(new Runnable() {
						@Override
						public void run() {
							Log.i("tv.getLineCount()", "" + viewHolder.textView.getLineCount());
						}
					});
					viewHolder.btnFold.setText("展开");
				}
			}
		});
		return convertView;
	}
	private static class ViewHolder{
		public TextView textView;
		public Button btnFold;
	}
}
