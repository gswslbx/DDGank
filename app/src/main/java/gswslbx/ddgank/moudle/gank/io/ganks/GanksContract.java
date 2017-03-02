package gswslbx.ddgank.moudle.gank.io.ganks;


import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.moudle.BasePresenter;
import gswslbx.ddgank.moudle.BaseView;

/**
 * GanksContract
 */
public interface GanksContract {

    interface View extends BaseView<Presenter> {

        void setItems(GanHuo ganHuo);//将获得的数据传入item

        void addItems(GanHuo ganHuo);//增加item数

        //列表item数据获取失败
        void getItemsFail(String failMessage, final int number, final int page, final boolean isRefresh);

        String getCategoryName();//获得所选类别名称

        void showSwipLoading();//显示刷新效果

        void hideSwipLoading();//停止显示刷新效果

        void setLoading();//设置加载
    }

    interface Presenter extends BasePresenter {

        //得到数据
        void getItems(int number, int page, boolean isRefresh);
    }
}
