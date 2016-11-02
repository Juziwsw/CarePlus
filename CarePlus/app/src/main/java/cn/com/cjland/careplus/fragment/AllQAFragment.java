package cn.com.cjland.careplus.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.activity.QaDetailsActivity;
import cn.com.cjland.careplus.activity.ShowPhotoActivity;
import cn.com.cjland.careplus.adapter.AllQABaseAdapter;
import cn.com.cjland.careplus.data.Question;

public class AllQAFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View mRootView;
    private ListView mListView;
    private AllQABaseAdapter mAllQABaseAdapter;
    List<Question> listData = new ArrayList<Question>();
    public AllQAFragment() {
    }
    public static AllQAFragment newInstance(String param1, String param2) {
        AllQAFragment fragment = new AllQAFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_all_qa, container, false);
        mListView = (ListView) mRootView.findViewById(R.id.list_view);
        callBack = new ItemCallBack();
        for (int i=0;i<10;i++) {
            Question question = new Question();
            question.qustionId = i;
            question.title="花千骨"+i;
            if(i%3==0){
                question.isHeart = true;
                question.isHasPhoto = true;
                question.photoNum = 4;
                question.summary="\u3000\u3000\u3000\u3000\u3000我感觉今天精神不好，总想睡觉，精神不集中，我感觉今天精神不好，总想睡觉，精神不集中我感觉今天精神不好，总想睡觉，精神不集中" +
                        "我感觉今天精神不好，总想睡觉，精神不集中我感觉今天精神不好，总想睡觉，精神不集中";
            }else{
                question.isHeart = false;
                question.isHasPhoto = false;
                question.summary="\u3000\u3000\u3000\u3000\u3000我感觉今天精神不好，总想睡觉，精神不集中我感觉今天精神不好，总想睡觉，精神不集中";
            }
            question.date="2016/02/13 12:0"+i;
            question.heartNum="500"+i;
            question.respondNum = "100" + i;
            question.flag = true;
            listData.add(question);
        }
        mAllQABaseAdapter = new AllQABaseAdapter(mContext, listData, R.layout.fragment_qa_item,callBack);
        mListView.setAdapter(mAllQABaseAdapter);
        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private ItemCallBack callBack;
    // 回调函数
    public interface ListItemCallBack {
        public boolean TextViewShrink(View view,boolean flag);
        public boolean FollowAction(boolean isHeart,View imgview,View txtview);
        public void AnswerAction(int answerId);
        public void ShowPhotoAction(int answerId);
    }
    public class ItemCallBack implements ListItemCallBack {
        @Override
        public boolean TextViewShrink(View view, boolean flag) {
            TextView textview = (TextView) view;
            if (flag) {
                flag = false;
                textview.setEllipsize(null); // 展开
                textview.setSingleLine(flag);
                textview.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            } else {
                flag = true;
                textview.setMaxLines(3);
                textview.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                textview.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
            return flag;
        }

        @Override
        public boolean FollowAction(boolean isHeart,View imgview,View txtview) {
            ImageView img = (ImageView) imgview;
            TextView txt = (TextView) txtview;
            int num = Integer.parseInt(txt.getText().toString());
            if (isHeart) {
                isHeart = false;
                num -= 1;
                img.setImageResource(R.drawable.ic_heart_normal);
            }else{
                isHeart = true;
                num += 1;
                img.setImageResource(R.drawable.ic_heart_selected);
            }
            txt.setText(""+num);
            return isHeart;
        }
        @Override
        public void AnswerAction(int answerId) {
            startActivity(new Intent(mContext, QaDetailsActivity.class));
        }

        @Override
        public void ShowPhotoAction(int answerId) {
            Intent showIntent = new Intent(mContext, ShowPhotoActivity.class);
            showIntent.putExtra("answerId",answerId);
            startActivity(showIntent);
        }
    }
}
