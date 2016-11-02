package cn.com.cjland.careplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.data.Answer;

public class ZXDesImgAdapter extends ImagesBaseAdapter {
    public ZXDesImgAdapter(Context context, List<Answer> mListData, int mItem) {
        super(context,mListData,mItem);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(mItem, null);
            viewHolder.header = (ImageView)convertView.findViewById(R.id.img_des_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Answer groupBean = (Answer)mListData.get(position);
        String url = groupBean.imagesUrl;
        if(!url.equals("")){
            //多线程加载图片
            viewHolder.header.setTag(url);//给imageview设置一个名为url的tag,就是相对应的进行绑定
            imageLoader.displayImage(url, viewHolder.header, options);
        }else{
            viewHolder.header.setImageResource(R.drawable.ic_login_header_bg);//设置默认图片
        }
        return convertView;
    }
    class ViewHolder{
        public ImageView header;
    }
}
