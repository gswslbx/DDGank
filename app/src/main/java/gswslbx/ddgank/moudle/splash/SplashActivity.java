package gswslbx.ddgank.moudle.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseActivity;
import gswslbx.ddgank.moudle.gank.io.GankHomeActivity;

/**
 * Created by Gswslbx on 2017/2/14.
 * 启动页 加载必应每日图片
 * 注意 返回的url可能不全
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @BindView(R.id.iv_splash_image)
    ImageView mIvSplashImage;
    @BindView(R.id.tv_splash_name)
    TextView mTvSplashName;
    private SplashContract.Presenter mSplashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mSplashPresenter = new SplashPresenter(this);
        setPresenter(mSplashPresenter);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
        mSplashPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");
        mSplashPresenter.unsubscribe();
    }

    @Override
    public void toGankActivity() {
        startActivity(new Intent(SplashActivity.this, GankHomeActivity.class));

        overridePendingTransition(0, 0);

        SplashActivity.this.finish();
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
        Glide.with(SplashActivity.this)
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
