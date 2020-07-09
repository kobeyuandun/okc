package com.free.business.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.free.base.R;
import com.free.base.mvp.BaseMvpActivity;
import com.free.base.utils.StateBarTranslucentUtils;
import com.free.business.viewpagerlazy.LazyExtendActivity;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity<SplashView, SplashPresent> implements SplashView{
    @BindView(R.id.ken_burns_images)
    KenBurnsView mKenBurns;
    @BindView(R.id.logo_splash)
    ImageView mLogo;
    @BindView(R.id.welcome_text)
    TextView mWelcomeText;

    Animation anim;
    ObjectAnimator alphaAnimation;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);

        Glide.with(this)
                .load(R.drawable.welcometoqbox)
                .into(mKenBurns);
        animation2();
        animation3();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LazyExtendActivity.class));
                finish();
            }
        }, 2000);

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }



    private void animation2() {
        mLogo.setAlpha(1.0F);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }



    private void animation3() {
        alphaAnimation = ObjectAnimator.ofFloat(mWelcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mKenBurns != null) {
            mKenBurns.pause();
        }
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
        if (anim != null) {
            anim.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mKenBurns != null) {
            mKenBurns.resume();
        }
    }
}
