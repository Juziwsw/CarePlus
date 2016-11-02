package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import cn.com.cjland.careplus.data.Question;


public class HosAdapter extends BaseAdapter {
	Context mContext;
	List<Question> mListData;
	int mItem;
	LayoutInflater mInflater;
    public HosAdapter(Context mContext, List<Question> mListData, int mItem) {
		this.mContext = mContext;
		this.mListData = mListData;
		this.mItem = mItem;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(mItem, null);
			viewHolder.txtName = (TextView)convertView.findViewById(R.id.txt_hos_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Question question = (Question) mListData.get(position);
		viewHolder.txtName.setText(question.title);
		return convertView;
	}
	private static class ViewHolder{
		public TextView txtName;
	}
}
