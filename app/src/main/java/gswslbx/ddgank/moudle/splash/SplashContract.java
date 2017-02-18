package gswslbx.ddgank.moudle.splash;

import gswslbx.ddgank.moudle.BasePresenter;
import gswslbx.ddgank.moudle.BaseView;

/**
 * Created by Gswslbx on 2017/2/15.
 * Splash的协议类 是对BaseView和BasePresenter的进一步封装
 */

public interface SplashContract {

    interface Presenter extends BasePresenter{
        void getBing();//获取Bing图片
    }

    interface View extends BaseView<Presenter>{
        void toGankActivity();//跳转主页面
        void animateImage();//缩放图片
        void initImage(Object object);//加载图片
        void initText(Object object);//加载描述
    }

}
