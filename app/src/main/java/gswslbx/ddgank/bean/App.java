package gswslbx.ddgank.bean;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import gswslbx.ddgank.R;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class App extends Application {
    private static App INSTANCE;
    private RefWatcher refWatcher;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));

        //检测内存泄漏
        refWatcher = LeakCanary.install(this);
    }

    //观察引用什么时候应该被GC
    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

}