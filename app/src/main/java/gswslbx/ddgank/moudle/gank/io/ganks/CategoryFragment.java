package gswslbx.ddgank.moudle.gank.io.ganks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import gswslbx.ddgank.R;
import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.moudle.gank.io.meizi.MeiziListAdapter;
import gswslbx.ddgank.moudle.widget.OnLoadMoreListener;
import gswslbx.ddgank.moudle.widget.RecycleViewDivider;
import gswslbx.ddgank.moudle.widget.RecyclerViewWithFooter;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class CategoryFragment extends Fragment implements CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerViewWithFooter mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CategoryListAdapter mAndroidListAdapter;
    private MeiziListAdapter mMeiziListAdapter;

    private CategoryContract.Presenter mPresenter = new CategoryPresenter(this);

    private int mPage = 1;

    private String mCategoryName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCategoryName = bundle.getString("mCategoryName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ganks_frag, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (getCategoryName().equals("福利")) {
            mMeiziListAdapter = new MeiziListAdapter(getContext());

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
            mRecyclerView.setAdapter(mMeiziListAdapter);
            mRecyclerView.setOnLoadMoreListener(this);
            mRecyclerView.setEmpty();
        } else {
            mAndroidListAdapter = new CategoryListAdapter(getContext());

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
            mRecyclerView.setAdapter(mAndroidListAdapter);
            mRecyclerView.setOnLoadMoreListener(this);
            mRecyclerView.setEmpty();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(mPresenter);
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }


    public static CategoryFragment newInstance(String mCategoryName) {
        CategoryFragment categoryFragment = new CategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString("mCategoryName", mCategoryName);

        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public String getCategoryName() {
        return this.mCategoryName;
    }

    @Override
    public void showSwipLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.getItems(10, mPage, true);
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mPresenter.getItems(10, mPage, false);
    }

    @Override
    public void setLoading() {
        mRecyclerView.setLoading();
    }


    @Override
    public void getItemsFail(String failMessage, final int number, final int page, final boolean isRefresh) {
        if (getUserVisibleHint()) {
            Snackbar.make(mSwipeRefreshLayout, failMessage, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.getItems(number, page, isRefresh);
                }
            }).show();
        }
    }

    @Override
    public void setItems(GanHuo ganHuo) {
        if (getCategoryName().equals("福利")){
            mMeiziListAdapter.mData = ganHuo.getResults();
            mMeiziListAdapter.notifyDataSetChanged();
        }else{
            mAndroidListAdapter.mData = ganHuo.getResults();
            mAndroidListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addItems(GanHuo ganHuo) {
        if (getCategoryName().equals("福利")){
            mMeiziListAdapter.mData.addAll(ganHuo.getResults());
            mMeiziListAdapter.notifyDataSetChanged();
        }else{
            mAndroidListAdapter.mData.addAll(ganHuo.getResults());
            mAndroidListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

