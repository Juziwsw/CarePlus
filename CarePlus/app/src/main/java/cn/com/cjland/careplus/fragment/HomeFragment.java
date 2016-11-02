package cn.com.cjland.careplus.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.cjland.careplus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View mRootView;
    private RelativeLayout mAllQa;
    private RelativeLayout mMeQa;
    private TextView mTitleAllQa;
    private TextView mTitleMeQa;
    private Fragment mFragment;
    private AllQAFragment mAllQAFragment;
    private MeQAFragment mMeQAFragment;
    private OnFragmentInteractionListener mListener;
    FragmentTransaction mFragmentTransaction;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        mAllQa = (RelativeLayout) mRootView.findViewById(R.id.rel_all_qa);
        mAllQa.setOnClickListener(this);
        mAllQa.setBackgroundResource(R.drawable.ic_tabs);
        mMeQa = (RelativeLayout) mRootView.findViewById(R.id.rel_me_qa);
        mMeQa.setOnClickListener(this);
        mMeQa.setBackgroundResource(R.drawable.transparent);
        mTitleAllQa = (TextView) mRootView.findViewById(R.id.all_qa_title);
        mTitleAllQa.setTextColor(mContext.getResources().getColor(R.color.color52c6cc));
        mTitleMeQa = (TextView) mRootView.findViewById(R.id.me_qa_title);
        mTitleMeQa.setTextColor(mContext.getResources().getColor(R.color.gray));
        mAllQAFragment = AllQAFragment.newInstance("", "");
        mMeQAFragment = MeQAFragment.newInstance("", "");
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.tabcontent, mAllQAFragment).add(R.id.tabcontent, mMeQAFragment).show(mAllQAFragment).hide(mMeQAFragment);
        mFragmentTransaction.commit();
        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rel_all_qa:
                if (null == mAllQAFragment) {
                    mAllQAFragment = AllQAFragment.newInstance("", "");
                }
                mTitleAllQa.setTextColor(mContext.getResources().getColor(R.color.color52c6cc));
                mTitleMeQa.setTextColor(mContext.getResources().getColor(R.color.gray));
                mAllQa.setBackgroundResource(R.drawable.ic_tabs);
                mMeQa.setBackgroundResource(R.drawable.transparent);
                mFragmentTransaction = getFragmentManager().beginTransaction();
                mFragmentTransaction.show(mAllQAFragment).hide(mMeQAFragment).commit();
                break;
            case R.id.rel_me_qa:
                if (null == mMeQAFragment) {
                    mMeQAFragment = MeQAFragment.newInstance("", "");
                }
                mTitleAllQa.setTextColor(mContext.getResources().getColor(R.color.gray));
                mTitleMeQa.setTextColor(mContext.getResources().getColor(R.color.color52c6cc));
                mAllQa.setBackgroundResource(R.drawable.transparent);
                mMeQa.setBackgroundResource(R.drawable.ic_tabs);
                mFragmentTransaction = getFragmentManager().beginTransaction();
                mFragmentTransaction.show(mMeQAFragment).hide(mAllQAFragment).commit();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
