package gswslbx.ddgank.moudle.gank.io.search;

import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.moudle.BasePresenter;
import gswslbx.ddgank.moudle.BaseView;

/**
 * SearchContract
 */
public interface SearchContract {
    interface View extends BaseView<Presenter> {

        void setToolbarBackgroundColor(int color);//

        //
        void showSearchFail(String failMsg, String searchText, int page, boolean isLoadMore);

        void setSearchItems(GanHuo searchResult);//

        void addSearchItems(GanHuo searchResult);//

        void showSwipLoading();//

        void hideSwipLoading();//

        void showTip(String msg);//

        void setLoadMoreIsLastPage();//

        void setEmpty();//

        void setLoading();//
    }

    interface Presenter extends BasePresenter {
        //
        void search(String searchText, int page, boolean isLoadMore);
    }
}
