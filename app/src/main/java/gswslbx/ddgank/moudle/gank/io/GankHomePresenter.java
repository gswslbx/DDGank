package gswslbx.ddgank.moudle.gank.io;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.orhanobut.logger.Logger;

import gswslbx.ddgank.R;
import gswslbx.ddgank.bean.App;
import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.bean.ThemeManage;
import gswslbx.ddgank.utils.retrofit.DataRetrofit;
import gswslbx.ddgank.utils.retrofit.DataService;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class GankHomePresenter implements GankHomeContract.Presenter {

    private GankHomeContract.View mHomeView;
    private Activity mContext;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public GankHomePresenter(GankHomeContract.View homeView) {
        mHomeView = homeView;
        mHomeView.setGanksPresenter(this);
        mContext = homeView.getContext();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getBanner(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        mContext = null;
    }

    @Override
    public void getRandomBanner() {
        getBanner(true);
    }

    @Override
    public void setThemeColor(@Nullable Palette palette) {
        if (palette != null) {
            int colorPrimary = App.getInstance().getResources().getColor(R.color.colorPrimary);
            // 把从调色板上获取的主题色保存在内存中
//            ThemeManage.INSTANCE.setColorPrimary(palette.getVibrantColor(colorPrimary));
//            ThemeManage.INSTANCE.setColorPrimary(palette.getDarkVibrantColor(colorPrimary));
//            ThemeManage.INSTANCE.setColorPrimary(palette.getLightVibrantColor(colorPrimary));

            ThemeManage.INSTANCE.setColorPrimary(palette.getMutedColor(colorPrimary));
//            ThemeManage.INSTANCE.setColorPrimary(palette.getDarkMutedColor(colorPrimary));
//            ThemeManage.INSTANCE.setColorPrimary(palette.getLightMutedColor(colorPrimary));

            // 设置 AppBarLayout 的背景色
            mHomeView.setAppBarLayoutColor(ThemeManage.INSTANCE.getColorPrimary());

            mHomeView.getColorPrimary(ThemeManage.INSTANCE.getColorPrimary());
        }
    }

    /**
     * Banner
     * @param isRandom true：随机  false：获取最新
     */
    @Override
    public void getBanner(final boolean isRandom) {

        Observable<GanHuo> observable;
        if (isRandom) {
            observable = DataRetrofit.getRetrofitGank()
                    //创建被观察者
                    .create(DataService.class)
                    .getRandomFuli(1);
        } else {
            observable = DataRetrofit.getRetrofitGank()
                    //创建被观察者
                    .create(DataService.class)
                    .getGanHuo("福利", 1, 1);
        }
        mSubscriptions.add(//添加订阅
                observable
                        //指定被观察者（生产事件-网络请求）的线程环境 耗时I/O操作运行在子线程
                        .subscribeOn(Schedulers.io())
                        //切换后面（消费事件-响应请求）的线程环境 对UI的操作运行在主线程
                        .observeOn(AndroidSchedulers.mainThread())
                        //创建观察者，实现订阅
                        .subscribe(new Observer<GanHuo>() {

                            @Override
                            public void onCompleted() {//请求完成
                                Logger.d("请求完成");
                            }

                            @Override
                            public void onError(Throwable e) {//请求失败
                                Logger.e(e, "请求失败");
                                mHomeView.showBannerFail("Banner 图加载失败，请重试", isRandom);
                            }

                            @Override
                            public void onNext(GanHuo ganHuo) {//请求成功 加载请求到的图片
                                Logger.d("请求成功");
                                if (ganHuo != null && ganHuo.getResults() != null && ganHuo.getResults().size() > 0
                                        && ganHuo.getResults().get(0).getUrl() != null) {
                                    mHomeView.setBanner(ganHuo.getResults().get(0).getUrl());
                                } else {
                                    mHomeView.showBannerFail("Banner 图加载失败，请重试", isRandom);
                                }
                            }
                        }));

    }

//    @Override
//    public void saveImg(final Drawable drawable) {
//        if (drawable == null) {
//            mHomeView.showMsgSaveFail();
//            return;
//        }
//        mHomeView.showSavingMsgTip();
//        RxPermissions rxPermissions = new RxPermissions(mContext);
//        mSubscriptions.add(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        if (aBoolean) {
//                            saveImageToGallery(ImageUtil.drawableToBitmap(drawable));
//                        } else {
//                            mHomeView.showPermissionsTip();
//                        }
//                    }
//                }));
//    }

//    private void saveImageToGallery(final Bitmap bitmap) {
//        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                boolean isSaveSuccess = ImageUtil.saveImageToGallery(mContext, bitmap);
//                subscriber.onNext(isSaveSuccess);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Boolean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Boolean isSaveSuccess) {
//                        if (isSaveSuccess) {
//                            mHomeView.showMsgSaveSuccess();
//                        } else {
//                            mHomeView.showMsgSaveFail();
//                        }
//                    }
//                });
//        mSubscriptions.add(subscription);
//    }

}
