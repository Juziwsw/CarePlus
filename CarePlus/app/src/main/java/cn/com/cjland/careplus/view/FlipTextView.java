package cn.com.cjland.careplus.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;

import java.util.ArrayList;
import java.util.List;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.bean.JournalismBean;

/**
 * Created by Administrator on 2015/11/9.
 */
public class FlipTextView extends TextSwitcher implements TextSwitcher.ViewFactory {


    private List<JournalismBean> demoBeans = new ArrayList<>();
    private int mIndex;
    private ItemDataListener itemDataListener;
    private View view ; //click view
    private static final int AUTO_RUN_FLIP_TEXT = 11;
    private static final int WAIT_TIME = 5000;

    public FlipTextView(Context context) {
        super(context);
        init();
    }

    public FlipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.trans_bottom_to_top_in_fast));
        setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.trans_bottom_to_top_out_fast));
    }

    @Override
    public View makeView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.flip_item_text_view,null);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUTO_RUN_FLIP_TEXT:

                    if(demoBeans.size()>0){
                        mHandler.sendEmptyMessageDelayed(AUTO_RUN_FLIP_TEXT,WAIT_TIME);
                        setText(Html.fromHtml(demoBeans.get(mIndex).getTitle()));
                    }
                    mIndex++;
                    if(mIndex>demoBeans.size()-1){
                        mIndex=0;
                    }
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mIndex==0){
                                itemDataListener.onItemClick(demoBeans.size()-1);
                            }else{
                                itemDataListener.onItemClick(mIndex-1);
                            }
                        }
                    });
                    break;
            }
        }
    };

    public void setData(List<JournalismBean> datas,ItemDataListener listener,View v){
        view = v;
        itemDataListener = listener;
        if(demoBeans.size()>0){
            demoBeans.clear();
        }
        demoBeans.addAll(datas);
        mIndex = 0;
        mHandler.removeMessages(AUTO_RUN_FLIP_TEXT);
        mHandler.sendEmptyMessage(AUTO_RUN_FLIP_TEXT);
    }


    public abstract interface ItemDataListener{
        public void onItemClick(int position);
    }
}
