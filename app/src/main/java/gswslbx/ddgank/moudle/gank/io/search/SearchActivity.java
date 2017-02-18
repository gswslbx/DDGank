package gswslbx.ddgank.moudle.gank.io.search;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.wayww.edittextfirework.FireworkView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseActivity;
import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.moudle.gank.io.search.SearchContract;
import gswslbx.ddgank.moudle.gank.io.search.SearchListAdapter;
import gswslbx.ddgank.moudle.gank.io.search.SearchPresenter;
import gswslbx.ddgank.utils.DisplayUtils;
import gswslbx.ddgank.utils.KeyboardUtils;
import gswslbx.ddgank.moudle.widget.OnLoadMoreListener;
import gswslbx.ddgank.moudle.widget.RecycleViewDivider;
import gswslbx.ddgank.moudle.widget.RecyclerViewWithFooter;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View, OnLoadMoreListener {

    @BindView(R.id.toolbar_search)
    Toolbar mToolbarSearch;
    @BindView(R.id.iv_edit_clear)
    AppCompatImageView mIvEditClear;
    @BindView(R.id.iv_search)
    AppCompatImageView mIvSearch;
    @BindView(R.id.appbar_search)
    AppBarLayout mAppbarSearch;
    @BindView(R.id.recycler_view_search)
    RecyclerViewWithFooter mRecyclerViewSearch;
    @BindView(R.id.swipe_refresh_layout_search)
    SwipeRefreshLayout mSwipeRefreshLayoutSearch;
    @BindView(R.id.ed_search)
    EditText mEdSearch;
    @BindView(R.id.fire_work)
    FireworkView mFireWork;

    private SearchContract.Presenter mSearchPresenter = new SearchPresenter(this);

    private int mPage = 1;
    private SearchListAdapter mSearchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        mFireWork.bindEditText(mEdSearch);
        setPresenter(mSearchPresenter);
        mSearchPresenter.subscribe();
    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbarSearch.setPadding(
                    mAppbarSearch.getPaddingLeft(),
                    mAppbarSearch.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbarSearch.getPaddingRight(),
                    mAppbarSearch.getPaddingBottom());
        }

        setSupportActionBar(mToolbarSearch);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSwipeRefreshLayoutSearch.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        mSwipeRefreshLayoutSearch.setRefreshing(false);
        mSwipeRefreshLayoutSearch.setEnabled(false);

        mSearchListAdapter = new SearchListAdapter(this);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSearch.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerViewSearch.setAdapter(mSearchListAdapter);
        mRecyclerViewSearch.setOnLoadMoreListener(this);
        mRecyclerViewSearch.setEmpty();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.unsubscribe();
    }

    //
    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbarSearch.setBackgroundColor(color);
    }

    //
    @Override
    public void showSearchFail(String failMsg, final String searchText, final int page, final boolean isLoadMore) {
        Snackbar.make(mSwipeRefreshLayoutSearch, failMsg, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchPresenter.search(searchText, page, isLoadMore);
            }
        }).show();
    }

    //
    @Override
    public void setSearchItems(GanHuo searchResult) {
        mSearchListAdapter.mData = searchResult.getResults();
        mSearchListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayoutSearch.setRefreshing(false);
    }

    /**
     * @param searchResult
     */
    @Override
    public void addSearchItems(GanHuo searchResult) {
        mSearchListAdapter.mData.addAll(searchResult.getResults());
        mSearchListAdapter.notifyDataSetChanged();
    }


    @Override
    public void showSwipLoading() {
        mSwipeRefreshLayoutSearch.setRefreshing(true);
    }

    @Override
    public void hideSwipLoading() {
        mSwipeRefreshLayoutSearch.setRefreshing(false);
    }

    @Override
    public void showTip(String msg) {
        Snackbar.make(mRecyclerViewSearch, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadMoreIsLastPage() {
        mRecyclerViewSearch.setEnd("没有更多数据了");
    }

    @Override
    public void setEmpty() {
        mRecyclerViewSearch.setEmpty();
    }

    @Override
    public void setLoading() {
        mRecyclerViewSearch.setLoading();
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), mPage, true);
    }

    @OnClick(R.id.iv_edit_clear)
    public void editClear() {
        mRecyclerViewSearch.setEmpty();
        mEdSearch.setText("");
        KeyboardUtils.showSoftInput(this, mEdSearch);
        hideSwipLoading();
        mSearchPresenter.unsubscribe();
    }

    @OnClick(R.id.iv_search)
    public void search() {
        KeyboardUtils.hideSoftInput(this);
        mPage = 1;
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), mPage, false);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mSearchPresenter = presenter;
    }

}

