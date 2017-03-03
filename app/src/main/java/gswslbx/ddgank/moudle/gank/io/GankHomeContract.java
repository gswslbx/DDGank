package gswslbx.ddgank.moudle.gank.io;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import gswslbx.ddgank.moudle.BasePresenter;
import gswslbx.ddgank.moudle.BaseView;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public interface GankHomeContract {
    interface View extends BaseView<Presenter> {

        //Banner加载失败
        void showBannerFail(String failMessage, boolean isRandom);

        void setBanner(String imgUrl);//设置Banner

        void setAppBarLayoutColor(int appBarLayoutColor);//设置Banner颜色

//        void showPermissionsTip();//获取权限提示

        Activity getContext();//获得当前上下文

//        void showMsgSaveSuccess();//保存成功提示
//
//        void showMsgSaveFail();//保存失败提示
//
//        void showSavingMsgTip();//保存中提示


    }

    interface Presenter extends BasePresenter {

        void getRandomBanner();//获得随机的Banner

        void setThemeColor(@Nullable Palette palette);//设置主题色

        void getBanner(final boolean isRandom);//获得Banner

//        void saveImg(Drawable drawable);//保存图片
    }
}
