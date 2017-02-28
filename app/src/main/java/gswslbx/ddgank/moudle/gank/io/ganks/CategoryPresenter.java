package gswslbx.ddgank.moudle.gank.io.ganks;

import android.support.annotation.NonNull;

import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.utils.retrofit.DataRetrofit;
import gswslbx.ddgank.utils.retrofit.DataService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mCategoryView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public CategoryPresenter(CategoryContract.View gankView) {
        mCategoryView = gankView;
        mCategoryView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getItems(10, 1, true);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getItems(final int number, final int page, final boolean isRefresh) {

        if (isRefresh) {
            mCategoryView.showSwipLoading();
        }
        mSubscriptions.add(//添加订阅
                DataRetrofit.getRetrofitGank()
                        //创建被观察者
                        .create(DataService.class)
                        .getGanHuo(mCategoryView.getCategoryName(), number, page)
                        //指定被观察者（生产事件-网络请求）的线程环境 耗时I/O操作运行在子线程
                        .subscribeOn(Schedulers.io())
                        //切换后面（消费事件-响应请求）的线程环境 对UI的操作运行在主线程
                        .observeOn(AndroidSchedulers.mainThread())
                        //创建观察者，实现订阅
                        .subscribe(new Observer<GanHuo>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                mCategoryView.hideSwipLoading();
                                mCategoryView.getItemsFail(mCategoryView.getCategoryName() + " 数据获取失败，请重试"
                                        , number, page, isRefresh);
                            }

                            @Override
                            public void onNext(GanHuo androidResult) {
                                if (isRefresh) {
                                    mCategoryView.setItems(androidResult);
                                    mCategoryView.hideSwipLoading();
                                    mCategoryView.setLoading();
                                } else {
                                    mCategoryView.addItems(androidResult);
                                }
                            }
                        }));
    }
}
