package gswslbx.ddgank.moudle.gank.io.search;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.bean.ThemeManage;
import gswslbx.ddgank.utils.retrofit.DataRetrofit;
import gswslbx.ddgank.utils.retrofit.DataService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * SearchPresenter
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void search(final String searchText, final int page, final boolean isLoadMore) {
        if (TextUtils.isEmpty(searchText)) {
            mView.showTip("搜索内容不能为空");
            return;
        }
        if (!isLoadMore) {
            mView.showSwipLoading();
        }
        mSubscriptions.add(//添加订阅
                DataRetrofit.getRetrofitGank()
                        //创建被观察者
                        .create(DataService.class)
                        .getSearch(searchText, 10, page)
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
                                mView.showSearchFail("搜索出错了，请重试", searchText, page, isLoadMore);
                                mView.hideSwipLoading();
                            }

                            @Override
                            public void onNext(GanHuo searchResult) {
                                if (!isLoadMore) {
                                    if (searchResult == null || searchResult.getResults().size() == 0) {
                                        mView.showTip("没有搜索到结果");
                                        mView.hideSwipLoading();
                                        mView.setEmpty();
                                        return;
                                    }
                                    mView.setSearchItems(searchResult);
                                    mView.setLoading();
                                } else {
                                    mView.addSearchItems(searchResult);
                                }
                                boolean isLastPage = searchResult.getResults().size() < 10;
                                if (isLastPage) {
                                    mView.setLoadMoreIsLastPage();
                                }

                            }


                        }));
    }
}
