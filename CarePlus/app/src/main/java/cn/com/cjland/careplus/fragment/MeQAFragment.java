package cn.com.cjland.careplus.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import cn.com.cjland.careplus.adapter.AllQABaseAdapter;
import cn.com.cjland.careplus.adapter.MeQABaseAdapter;
import cn.com.cjland.careplus.data.Question;

public class MeQAFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View mRootView;
    private ListView mListView;
    private MeQABaseAdapter mMeQABaseAdapter;

    public MeQAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeQAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeQAFragment newInstance(String param1, String param2) {
        MeQAFragment fragment = new MeQAFragment();
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
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_me_qa, container, false);
        mListView = (ListView) mRootView.findViewById(R.id.list_view);
        callBack = new ItemCallBack();
        List<Question> listData = new ArrayList<Question>();
        for (int i=0;i<10;i++) {
            Question question = new Question();
            question.qustionId = i;
            question.title="花千骨"+i;
            if(i%3==0){
                question.isHasPhoto = true;
                question.photoNum = 4;
                question.summary="\u3000\u3000\u3000\u3000\u3000我感觉今天精神不好，总想睡觉，精神不集中，我感觉今天精神不好，总想睡觉，精神不集中我感觉今天精神不好，总想睡觉，精神不集中" +
                        "我感觉今天精神不好，总想睡觉，精神不集中我感觉今天精神不好，总想睡觉，精神不集中";
            }else{
                question.isHasPhoto = false;
                question.summary="\u3000\u3000\u3000\u3000\u3000我感觉今天精神不好，总想睡觉，精神不集中我感觉今天精神不好，总想睡觉，精神不集中";
            }
            question.date="2016/02/13 12:0"+i;
            question.heartNum="500"+i;
            question.respondNum = "100" + i;
            question.flag = true;
            listData.add(question);
        }
        mMeQABaseAdapter = new MeQABaseAdapter(mContext, listData, R.layout.fragment_me_qa_item,callBack);
        mListView.setAdapter(mMeQABaseAdapter);
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
        public void AnswerAction(int answerId);
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
        public void AnswerAction(int answerId) {
            startActivity(new Intent(mContext, QaDetailsActivity.class));
        }
    }
}
