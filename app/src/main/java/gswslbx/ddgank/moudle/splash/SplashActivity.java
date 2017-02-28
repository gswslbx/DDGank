package gswslbx.ddgank.moudle.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseActivity;

/**
 * Created by Gswslbx on 2017/2/14.
 * 启动页 加载必应每日图片
 */

public class SplashActivity extends BaseActivity {

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
        //实例化mSplashPresenter的同时传递mSplashFragment，使P得以操作V
        new SplashPresenter(mSplashFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
