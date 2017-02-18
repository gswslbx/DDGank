package gswslbx.ddgank.moudle;

/**
 * Created by Gswslbx on 2017/2/15.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);//使view能操作Presenter的方法
}
