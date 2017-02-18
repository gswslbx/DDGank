package gswslbx.ddgank.moudle.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseFragment;
import gswslbx.ddgank.moudle.gank.io.GankHomeActivity;

/**
 * Created by Gswslbx on 2017/2/18.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {

    @BindView(R.id.iv_splash_image)
    ImageView mIvSplashImage;
    @BindView(R.id.tv_splash_name)
    TextView mTvSplashName;
    private SplashContract.Presenter mSplashPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置全屏显示
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.splash_frag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSplashPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSplashPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void toGankActivity() {
        startActivity(new Intent(getActivity(), GankHomeActivity.class));
    }

    @Override
    public void animateImage() {
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2500);
        mIvSplashImage.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toGankActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void initImage(Object object) {
        Glide.with(getActivity())
                .load(object)
                .into(mIvSplashImage);
    }

    @Override
    public void initText(Object object) {
        mTvSplashName.setText((CharSequence) object);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mSplashPresenter = presenter;
    }
}
