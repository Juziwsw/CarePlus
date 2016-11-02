package cn.com.cjland.careplus.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.activity.LoginActivity;
import cn.com.cjland.careplus.activity.SettingActivity;
import cn.com.cjland.careplus.utils.SharePreService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View mRootView;
    private RelativeLayout mPersonalWallet,mSoftSetting,mReceiveOnline;
    private RelativeLayout mPersonalHeader;
    private ImageView imageView_ok,imageView_no,imageView_bg;
    private static  boolean is_receive = false;

    private OnFragmentInteractionListener mListener;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
    public void onStart() {
        super.onStart();
        is_receive = SharePreService.getIsReceive(mContext);
        if (is_receive){
            /*imageView_ok.setVisibility(View.VISIBLE);
            imageView_no.setVisibility(View.GONE);
            imageView_bg.setImageResource(R.drawable.ic_me_ok_bg);*/
            openReceive1();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_me, container, false);
        initialize();

        return mRootView;
    }
    private void  initialize(){
        imageView_ok = (ImageView) mRootView.findViewById(R.id.img_receive_ok);
        imageView_no = (ImageView) mRootView.findViewById(R.id.img_receive_no);
        imageView_bg = (ImageView) mRootView.findViewById(R.id.img_receive_bg);

        mPersonalWallet = (RelativeLayout)mRootView.findViewById(R.id.rel_personal_wallet);
        mSoftSetting = (RelativeLayout)mRootView.findViewById(R.id.rel_personal_setting);
        mReceiveOnline = (RelativeLayout) mRootView.findViewById(R.id.rel_personal_online);
        mPersonalWallet.setOnClickListener(this);
        mSoftSetting.setOnClickListener(this);
        mReceiveOnline.setOnClickListener(this);
        mPersonalHeader = (RelativeLayout) mRootView.findViewById(R.id.rel_personal_header);
        //InputStream is = this.getContext().getResources().openRawResource(R.drawable.ic_default_avatar);

        //Bitmap mBitmap = BitmapFactory.decodeStream(is);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.ic_login_header_bg);

        Drawable drawable = new BitmapDrawable(blurImageAmeliorate(bitmap));
        mPersonalHeader.setBackground(drawable);
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
            case R.id.rel_personal_wallet:
                startActivity(new Intent(mContext, LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
            case R.id.rel_personal_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
            case R.id.rel_personal_online:
                //imageView_bg.getWidth();
                /*imageView_ok.setVisibility(View.GONE);
                imageView_no.setVisibility(View.VISIBLE);*/
                if (is_receive){
                    closeReceive();
                }else{
                    openReceive();
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onStop();
    }

    private void openReceive(){
        final TranslateAnimation animation = new TranslateAnimation(0, imageView_bg.getWidth()-imageView_no.getWidth(),0, 0);
        animation.setDuration(1000);//
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        imageView_no.startAnimation(animation);
        //imageView.setAnimation(animation);
        animation.start();
        imageView_bg.setImageResource(R.drawable.ic_me_ok_bg);
        imageView_no.setImageResource(R.drawable.ic_me_ok);
        is_receive = true;
        SharePreService.setIsReceive(mContext,true);
    }
    private void openReceive1(){
        final TranslateAnimation animation = new TranslateAnimation(0, imageView_bg.getWidth()-imageView_no.getWidth(),0, 0);
        animation.setDuration(1000);//
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        imageView_no.startAnimation(animation);
        //imageView_no.setAnimation(animation);
        animation.start();
        imageView_bg.setImageResource(R.drawable.ic_me_ok_bg);
        imageView_no.setImageResource(R.drawable.ic_me_ok);
        //imageView_ok.setVisibility(View.VISIBLE);
        //imageView_no.setVisibility(View.GONE);
        is_receive = true;
        SharePreService.setIsReceive(mContext,true);
    }
    private void closeReceive(){
        final TranslateAnimation animation = new TranslateAnimation((imageView_bg.getWidth()-imageView_no.getWidth()),
                         -(imageView_bg.getWidth()-2*(imageView_no.getWidth())),0, 0);
        animation.setDuration(1000);//���ö�������ʱ��
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        imageView_no.startAnimation(animation);
        //imageView.setAnimation(animation);
        animation.start();
        imageView_bg.setImageResource(R.drawable.ic_me_no_bg);
        imageView_no.setImageResource(R.drawable.ic_me_no);
        is_receive = false;
        SharePreService.setIsReceive(mContext,false);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private Bitmap blurImageAmeliorate(Bitmap bmp)
    {
        long start = System.currentTimeMillis();
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 16; // ֵԽСͼƬ��Խ����Խ����Խ��

        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++)
        {
            for (int k = 1, len = width - 1; k < len; k++)
            {
                idx = 0;
                for (int m = -1; m <= 1; m++)
                {
                    for (int n = -1; n <= 1; n++)
                    {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        long end = System.currentTimeMillis();
        Log.d("may", "used time="+(end - start));
        return bitmap;
    }

    /**
     * ģ��Ч��
     * @param bmp
     * @return
     */
    private Bitmap blurImage(Bitmap bmp)
    {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int newColor = 0;

        int[][] colors = new int[9][3];
        for (int i = 1, length = width - 1; i < length; i++)
        {
            for (int k = 1, len = height - 1; k < len; k++)
            {
                for (int m = 0; m < 9; m++)
                {
                    int s = 0;
                    int p = 0;
                    switch(m)
                    {
                        case 0:
                            s = i - 1;
                            p = k - 1;
                            break;
                        case 1:
                            s = i;
                            p = k - 1;
                            break;
                        case 2:
                            s = i + 1;
                            p = k - 1;
                            break;
                        case 3:
                            s = i + 1;
                            p = k;
                            break;
                        case 4:
                            s = i + 1;
                            p = k + 1;
                            break;
                        case 5:
                            s = i;
                            p = k + 1;
                            break;
                        case 6:
                            s = i - 1;
                            p = k + 1;
                            break;
                        case 7:
                            s = i - 1;
                            p = k;
                            break;
                        case 8:
                            s = i;
                            p = k;
                    }
                    pixColor = bmp.getPixel(s, p);
                    colors[m][0] = Color.red(pixColor);
                    colors[m][1] = Color.green(pixColor);
                    colors[m][2] = Color.blue(pixColor);
                }

                for (int m = 0; m < 9; m++)
                {
                    newR += colors[m][0];
                    newG += colors[m][1];
                    newB += colors[m][2];
                }

                newR = (int) (newR / 9F);
                newG = (int) (newG / 9F);
                newB = (int) (newB / 9F);

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                newColor = Color.argb(255, newR, newG, newB);
                bitmap.setPixel(i, k, newColor);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        return bitmap;
    }

}
