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
import gswslbx.ddgank.R;
import gswslbx.ddgank.moudle.BaseActivity;
import gswslbx.ddgank.moudle.common.CommonViewPagerAdapter;
import gswslbx.ddgank.moudle.gank.io.ganks.CategoryFragment;
import gswslbx.ddgank.moudle.gank.io.search.SearchActivity;

public class GankHomeActivity extends BaseActivity implements GankHomeContract.View {

    @BindView(R.id.tl_home_category)
    TabLayout tlHomeCategory;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_home_search)
    LinearLayout llHomeSearch;
    @BindView(R.id.iv_home_banner)
    ImageView ivHomeBanner;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.vp_home_category)
    ViewPager vpHomeCategory;
    @BindView(R.id.root)
    CoordinatorLayout root;

    public GankHomeContract.Presenter mHomePresenter = new GankHomePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_home);
        ButterKnife.bind(this);
        initView();
        setPresenter(mHomePresenter);
        mHomePresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.unsubscribe();
    }

    private void initView() {

        String[] titles = {"福利", "Android", "iOS", "前端", "休息视频", "拓展资源"};
        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        // 妹子
        CategoryFragment appFragment = CategoryFragment.newInstance("福利");
        // Android
        CategoryFragment androidFragment = CategoryFragment.newInstance("Android");
        // iOS
        CategoryFragment iOSFragment = CategoryFragment.newInstance("iOS");
        // 前端
        CategoryFragment frontFragment = CategoryFragment.newInstance("前端");
        // 休息视频
        CategoryFragment videoFragment = CategoryFragment.newInstance("休息视频");
        // 拓展资源
        CategoryFragment resFragment = CategoryFragment.newInstance("拓展资源");

        infoPagerAdapter.addFragment(appFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(videoFragment);
        infoPagerAdapter.addFragment(resFragment);

        vpHomeCategory.setAdapter(infoPagerAdapter);
        tlHomeCategory.setupWithViewPager(vpHomeCategory);
        tlHomeCategory.setTabGravity(TabLayout.GRAVITY_FILL);
        vpHomeCategory.setCurrentItem(1);
    }

    /**
     * @param view
     */

    @OnClick(R.id.ll_home_search)
    public void search(View view) {
        startActivity(new Intent(GankHomeActivity.this, SearchActivity.class));
    }

    @Override
    public void showBannerFail(String failMessage, final boolean isRandom) {
        Snackbar.make(vpHomeCategory, failMessage, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
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
                            }
                        }))
                .into(ivHomeBanner);
    }

    @Override
    public void setAppBarLayoutColor(int appBarLayoutColor) {
        collapsingToolbar.setContentScrimColor(appBarLayoutColor);
        appbar.setBackgroundColor(appBarLayoutColor);
    }

    @Override
    public void showPermissionsTip() {
        Snackbar.make(vpHomeCategory, "需要权限才能保存妹子", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void showMsgSaveSuccess() {
        Snackbar.make(vpHomeCategory, "图片保存成功", Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(i);
            }
        }).show();
    }

    @Override
    public void showMsgSaveFail() {
        Snackbar.make(vpHomeCategory, "图片保存失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomePresenter.saveImg(ivHomeBanner.getDrawable());
            }
        }).show();
    }

    @Override
    public void showSavingMsgTip() {
        Snackbar.make(vpHomeCategory, "正在保存图片...", Snackbar.LENGTH_LONG).show();
    }

    /**
     *
     */
    @OnClick(R.id.iv_home_banner)
    public void onClick() {
        mHomePresenter.getRandomBanner();
    }

    @Override
    public void setPresenter(GankHomeContract.Presenter presenter) {
        mHomePresenter = presenter;
    }
}
