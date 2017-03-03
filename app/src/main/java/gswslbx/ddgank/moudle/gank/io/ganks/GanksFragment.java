package gswslbx.ddgank.moudle.gank.io.ganks;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import gswslbx.ddgank.R;
import gswslbx.ddgank.bean.GanHuo;
import gswslbx.ddgank.bean.ThemeManage;
import gswslbx.ddgank.moudle.gank.io.meizi.MeiziListAdapter;
import gswslbx.ddgank.moudle.widget.OnLoadMoreListener;
import gswslbx.ddgank.moudle.widget.RecycleViewDivider;
import gswslbx.ddgank.moudle.widget.RecyclerViewWithFooter;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by Gswslbx on 2017/2/16.
 */

public class GanksFragment extends Fragment implements GanksContract.View,
        WaveSwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerViewWithFooter mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    WaveSwipeRefreshLayout mSwipeRefreshLayout;

    private GanksListAdapter mGankListAdapter;
    private MeiziListAdapter mMeiziListAdapter;
    private GanksContract.Presenter mGanksPresenter;

    private int mPage = 1;
    private String mGanksName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mGanksName = bundle.getString("mGanksName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ganks_frag, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setWaveColor(Color.argb(255, 63, 81, 181));//默认主题色

//        mSwipeRefreshLayout.setWaveColor(0xFF000000 + new Random().nextInt(0xFFFFFF));//随机变色

        //// TODO: 2017/3/2 视频的处理
        if (getGanksName().equals("福利")) {
            mMeiziListAdapter = new MeiziListAdapter(getContext());

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
            mRecyclerView.setAdapter(mMeiziListAdapter);
            mRecyclerView.setOnLoadMoreListener(this);
            mRecyclerView.setEmpty();
        } else {
            mGankListAdapter = new GanksListAdapter(getContext());

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
            mRecyclerView.setAdapter(mGankListAdapter);
            mRecyclerView.setOnLoadMoreListener(this);
            mRecyclerView.setEmpty();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGanksPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGanksPresenter.unsubscribe();
    }

    public static GanksFragment newInstance(String mGanksName) {
        GanksFragment ganksFragment = new GanksFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mGanksName", mGanksName);

        ganksFragment.setArguments(bundle);
        return ganksFragment;
    }

    @Override
    public String getGanksName() {
        return this.mGanksName;
    }

    @Override
    public void showSwipeLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                mPage = 1;
                mGanksPresenter.getItems(10, mPage, true);
            }
        }, 3000);

    }

    @Override
    public void setWaveSwipeRefreshLayoutColor(){
        mSwipeRefreshLayout.setWaveColor(ThemeManage.INSTANCE.getColorPrimary());
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mGanksPresenter.getItems(10, mPage, false);
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
                    mGanksPresenter.getItems(number, page, isRefresh);
                }
            }).show();
        }
    }

    @Override
    public void setItems(GanHuo ganHuo) {
        if (getGanksName().equals("福利")) {
            mMeiziListAdapter.mData = ganHuo.getResults();
            mMeiziListAdapter.notifyDataSetChanged();
        } else {
            mGankListAdapter.mData = ganHuo.getResults();
            mGankListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addItems(GanHuo ganHuo) {
        if (getGanksName().equals("福利")) {
            mMeiziListAdapter.mData.addAll(ganHuo.getResults());
            mMeiziListAdapter.notifyDataSetChanged();
        } else {
            mGankListAdapter.mData.addAll(ganHuo.getResults());
            mGankListAdapter.notifyDataSetChanged();
        }
    }

    public void setGanksPresenter(GanksContract.Presenter ganksPresenter) {
        mGanksPresenter = ganksPresenter;
    }
}

