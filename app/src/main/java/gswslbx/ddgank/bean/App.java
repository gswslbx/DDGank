package gswslbx.ddgank.bean;

import android.app.Application;

import gswslbx.ddgank.R;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class App extends Application {
    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));
    }


}