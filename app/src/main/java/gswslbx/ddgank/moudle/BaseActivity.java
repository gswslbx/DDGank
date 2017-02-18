package gswslbx.ddgank.moudle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.orhanobut.logger.LogLevel;

import gswslbx.ddgank.BuildConfig;

import static com.orhanobut.logger.Logger.init;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏半透明
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置log是否打印
        if (BuildConfig.LOG_DEBUG) {
            init().logLevel(LogLevel.FULL);
        } else {
            init().logLevel(LogLevel.NONE);
        }
    }
}
