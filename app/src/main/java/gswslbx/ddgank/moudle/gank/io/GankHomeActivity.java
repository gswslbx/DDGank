package gswslbx.ddgank.moudle.gank.io;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseActivity;
import gswslbx.ddgank.moudle.common.CommonViewPagerAdapter;
import gswslbx.ddgank.moudle.gank.io.ganks.GanksFragment;
import gswslbx.ddgank.moudle.gank.io.ganks.GanksPresenter;
import gswslbx.ddgank.moudle.gank.io.search.SearchActivity;
import gswslbx.ddgank.moudle.widget.SaveImgDialog;

public class GankHomeActivity extends BaseActivity implements GankHomeContract.View {

    @BindView(R.id.tl_home_ganks)
    TabLayout mTlHomeGanks;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.ll_home_search)
    LinearLayout mLlHomeSearch;
    @BindView(R.id.iv_home_banner)
    ImageView mIvHomeBanner;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.vp_home_ganks)
    ViewPager mVpHomeGanks;
    @BindView(R.id.root)
    CoordinatorLayout mRoot;

    public GankHomeContract.Presenter mHomePresenter = new GankHomePresenter(this);
    public int mCurrentPage = 1;

    String[] titles = {"福利", "Android", "iOS", "前端", "休息视频", "拓展资源"};
    CommonViewPagerAdapter infoPagerAdapter =
            new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

    // 妹子
    GanksFragment fuliFragment = GanksFragment.newInstance("福利");
    // Android
    GanksFragment androidFragment = GanksFragment.newInstance("Android");
    // iOS
    GanksFragment iOSFragment = GanksFragment.newInstance("iOS");
    // 前端
    GanksFragment frontFragment = GanksFragment.newInstance("前端");
    // 休息视频
    GanksFragment videoFragment = GanksFragment.newInstance("休息视频");
    // 拓展资源
    GanksFragment resFragment = GanksFragment.newInstance("拓展资源");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_home_act);
        ButterKnife.bind(this);
        initView();
        mHomePresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.unsubscribe();
    }

    private void initView() {

        //将fragment传递给presenter
        new GanksPresenter(fuliFragment);
        new GanksPresenter(androidFragment);
        new GanksPresenter(iOSFragment);
        new GanksPresenter(frontFragment);
        new GanksPresenter(videoFragment);
        new GanksPresenter(resFragment);

        infoPagerAdapter.addFragment(fuliFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(videoFragment);
        infoPagerAdapter.addFragment(resFragment);

        mVpHomeGanks.setAdapter(infoPagerAdapter);
        mTlHomeGanks.setupWithViewPager(mVpHomeGanks);
        mTlHomeGanks.setTabGravity(TabLayout.GRAVITY_FILL);
        mVpHomeGanks.setCurrentItem(mCurrentPage);//默认显示Android资源

        mVpHomeGanks.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                GanksFragment fragment = (GanksFragment) infoPagerAdapter.getItem(mCurrentPage);
                fragment.lazyLoad();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.ll_home_search)
    public void search(View view) {
        startActivity(new Intent(GankHomeActivity.this, SearchActivity.class));
    }

    @Override
    public void showBannerFail(String failMessage, final boolean isRandom) {
        Snackbar.make(mVpHomeGanks, failMessage, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomePresenter.getBanner(isRandom);
            }
        }).show();
    }

    @Override
    public void setBanner(String imgUrl) {
        Glide.with(this).load(imgUrl)
                .listener(GlidePalette.with(imgUrl)
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(@Nullable Palette palette) {
                                mHomePresenter.setThemeColor(palette);
                                //设置fragment下的下拉刷新主题色
                                GanksFragment fragment = (GanksFragment) infoPagerAdapter.getItem(mCurrentPage);
                                fragment.setWaveSwipeRefreshLayoutColor();

                            }
                        }))
                .into(mIvHomeBanner);
    }

    @Override
    public void setAppBarLayoutColor(int appBarLayoutColor) {
        mCollapsingToolbar.setContentScrimColor(appBarLayoutColor);
        mAppbar.setBackgroundColor(appBarLayoutColor);
    }

    @Override
    public void showPermissionsTip() {
        Snackbar.make(mVpHomeGanks, "需要权限才能保存妹子", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void showMsgSaveSuccess() {
        Snackbar.make(mVpHomeGanks, "图片保存成功", Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(i);
            }
        }).show();
    }

    @Override
    public void showMsgSaveFail() {
        Snackbar.make(mVpHomeGanks, "图片保存失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomePresenter.saveImg(mIvHomeBanner.getDrawable());
            }
        }).show();
    }

    @Override
    public void showSavingMsgTip() {
        Snackbar.make(mVpHomeGanks, "正在保存图片...", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.iv_home_banner)
    public void onClick() {
        mHomePresenter.getRandomBanner();
    }

    public void setGanksPresenter(GankHomeContract.Presenter ganksPresenter) {
        mHomePresenter = ganksPresenter;
    }

    @OnLongClick(R.id.iv_home_banner)
    public boolean onLongClick() {
        showSaveFuliDialog();
        return true;
    }

    private void showSaveFuliDialog() {
        SaveImgDialog saveImgDialog = new SaveImgDialog(this);
        saveImgDialog.setItemClick(new SaveImgDialog.OnItemClick() {
            @Override
            public void onItemClick() {
                mHomePresenter.saveImg(mIvHomeBanner.getDrawable());

            }
        });
        saveImgDialog.show();
    }

}
