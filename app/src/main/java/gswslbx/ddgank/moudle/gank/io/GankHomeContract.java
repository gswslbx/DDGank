package gswslbx.ddgank.moudle.gank.io;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import gswslbx.ddgank.moudle.BasePresenter;
import gswslbx.ddgank.moudle.BaseView;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public interface GankHomeContract {
    interface View extends BaseView<Presenter> {
        //
        void showBannerFail(String failMessage, boolean isRandom);

        void setBanner(String imgUrl);//

        void setAppBarLayoutColor(int appBarLayoutColor);//

        void showPermissionsTip();//

        Activity getContext();//

        void showMsgSaveSuccess();//

        void showMsgSaveFail();//

        void showSavingMsgTip();//

    }

    interface Presenter extends BasePresenter {

        void getRandomBanner();//

        void setThemeColor(@Nullable Palette palette);//

        void getBanner(final boolean isRandom);//

        void saveImg(Drawable drawable);//
    }
}
