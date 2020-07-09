package com.free.business.viewpagerlazy;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.base.R;

import androidx.annotation.Nullable;

/**
 * @author yuandunbin
 * @date 2020/7/8
 */
public class FragmentExtendLazy extends LazyFragment {
    private static final String TAG = "FragmentExtendLazy";
    private static final String INTENT_INT_INDEX = "index";
    private int tabIndex;
    private ImageView mImageView;
    private TextView mTextView;
    private CountDownTimer countDownTimer;

    public static FragmentExtendLazy newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(INTENT_INT_INDEX, position);

        FragmentExtendLazy fragment = new FragmentExtendLazy();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, tabIndex + " fragment " + "onCreate: ");
    }

    @Override
    protected void initView(View rootView) {
        mImageView = (ImageView) rootView.findViewById(R.id.iv_content);
        mTextView = (TextView) rootView.findViewById(R.id.tv_loading);
        tabIndex = getArguments().getInt(INTENT_INT_INDEX);
        Log.d(TAG, tabIndex + " fragment " + "initView: ");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_lazy_loading;
    }

    @Override
    protected void onFragmentFirstVisible() {
        Log.d(TAG, tabIndex + " fragment " + "real onFragmentFirstVisible");
    }

    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        getData();
        Log.d(TAG, tabIndex + " fragment " + "更新界面");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mTextView.setVisibility(View.GONE);
            int id;
            switch (tabIndex) {
                case 1:
                    id = R.drawable.a;
                    break;
                case 2:
                    id = R.drawable.b;
                    break;
                case 3:
                    id = R.drawable.c;
                    break;
                case 4:
                    id = R.drawable.d;
                    break;
                default:
                    id = R.drawable.b;
            }
            mImageView.setImageResource(id);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setVisibility(View.VISIBLE);
            Log.d(TAG, tabIndex + " handleMessage: ");
            //模拟耗时工作
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private void getData() {
        countDownTimer = new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                handler.sendEmptyMessage(0);
            }
        };
        countDownTimer.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, tabIndex + " fragment " + "onResume: ");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, tabIndex + " fragment " + "onPause: ");

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        tabIndex = getArguments().getInt(INTENT_INT_INDEX);
        Log.d(TAG, tabIndex + " fragment " + "setUserVisibleHint: " + isVisibleToUser);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, tabIndex + " fragment " + "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, tabIndex + " fragment " + "onDetach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
