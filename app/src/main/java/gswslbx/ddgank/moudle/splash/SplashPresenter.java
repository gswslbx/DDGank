package gswslbx.ddgank.moudle.splash;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.regex.Pattern;

import gswslbx.ddgank.R;
import gswslbx.ddgank.bean.Bing;
import gswslbx.ddgank.utils.retrofit.DataRetrofit;
import gswslbx.ddgank.utils.retrofit.DataService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gswslbx on 2017/2/15.
 */

public class SplashPresenter implements SplashContract.Presenter {

    @NonNull
    private SplashContract.View mSplashView;//MVP中的View

    @NonNull
    private CompositeSubscription mSubscriptions;//CompositeSubscription持有所有的Subscriptions

    public SplashPresenter(@NonNull SplashContract.View splashView) {
        mSplashView = splashView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getBing();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getBing() {
        mSubscriptions.add(//添加订阅
                DataRetrofit.getRetrofitBing()
                        //创建被观察者
                        .create(DataService.class)
                        .getBing("js", 0, 1)
                        //指定被观察者（生产事件-网络请求）的线程环境 耗时I/O操作运行在子线程
                        .subscribeOn(Schedulers.io())
                        //切换后面（消费事件-响应请求）的线程环境 对UI的操作运行在主线程
                        .observeOn(AndroidSchedulers.mainThread())
                        //创建观察者，实现订阅
                        .subscribe(new Observer<Bing>() {

                            @Override
                            public void onCompleted() {//请求完成
                                Logger.d("请求完成");
                                mSplashView.animateImage();
                            }

                            @Override
                            public void onError(Throwable e) {//请求失败 加载预设图片
                                Logger.e(e, "请求失败");
                                mSplashView.initImage(R.drawable.wall_picture);
                                mSplashView.animateImage();
                            }

                            @Override
                            public void onNext(Bing bing) {//请求成功 加载请求到的图片
                                Logger.d("请求成功");
                                String bingUrl = "http://s.cn.bing.net";//返回的url不全 不知道为什么

                                mSplashView.initImage(bingUrl + bing.getImages().get(0).getUrl());

                                //正则表达式切割
                                String copyRight = bing.images.get(0).getCopyright();
                                Logger.d(copyRight);
                                Pattern pattern = Pattern.compile("\\(");
                                String[] strs = pattern.split(copyRight, 2);
                                Logger.d(strs[0] + "\n(" + strs[1]);

                                mSplashView.initText(strs[0] + "\n(" + strs[1]);
                            }
                        }));

    }

}
