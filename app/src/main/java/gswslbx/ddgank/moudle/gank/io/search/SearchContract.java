package gswslbx.ddgank.moudle.gank.io.search;

import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.moudle.BasePresenter;
import gswslbx.ddgank.moudle.BaseView;

/**
 * SearchContract
 */
public interface SearchContract {
    interface View extends BaseView<Presenter> {
        void getKeyboardAction();//监听键盘回车事件

        void setToolbarBackgroundColor(int color);//设置Toolbar的背景色

        //搜索失败的提示
        void showSearchFail(String failMsg, String searchText, int page, boolean isLoadMore);

        void setSearchItems(GanHuo searchResult);//设置搜索结果

        void addSearchItems(GanHuo searchResult);//添加搜索结果

        void showSwipeLoading();//显示滑动加载动画

        void hideSwipeLoading();//隐藏滑动加载动画

        void showTip(String msg);//显示提示

        void setLoadMoreIsLastPage();//加载到最后一页了

        void setEmpty();//设置空页

        void setLoading();//设置加载ing
    }

    interface Presenter extends BasePresenter {
        //搜索
        void search(String searchText, int page, boolean isLoadMore);
    }
}
