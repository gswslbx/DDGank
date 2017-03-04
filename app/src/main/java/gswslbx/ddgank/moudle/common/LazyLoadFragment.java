package gswslbx.ddgank.moudle.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import gswslbx.ddgank.moudle.BaseFragment;


/**
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 * * Created by Gswslbx on 2017/3/4.
 */

public abstract class LazyLoadFragment extends BaseFragment {

    protected boolean isInit = false;//视图是否已经初始化
    protected boolean isLoad = false;//数据是否已经加载
    protected boolean isVisible = false;//视图是否当前可见

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     *
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isVisible = true;
            isCanLoadData();
        }
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {

        if (isInit && isVisible) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
        //
        isInit = false;
        isLoad = false;
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;

    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }
}
