package gswslbx.ddgank.moudle.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseActivity;

/**
 * Created by Gswslbx on 2017/2/14.
 * 启动页 加载必应每日图片
 * 注意 返回的url可能不全
 */

public class SplashActivity extends BaseActivity {
    private SplashContract.Presenter mSplashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);

        SplashFragment mSplashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.act_splash);
        if (mSplashFragment == null) {
            mSplashFragment = SplashFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    mSplashFragment, R.id.act_splash);
        }

        mSplashPresenter = new SplashPresenter(mSplashFragment);

        mSplashFragment.setPresenter(mSplashPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
