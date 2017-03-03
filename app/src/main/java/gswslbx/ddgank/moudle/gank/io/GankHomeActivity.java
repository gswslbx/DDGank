package gswslbx.ddgank.moudle.gank.io;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import gswslbx.ddgank.moudle.gank.io.ganks.GanksFragment;
import gswslbx.ddgank.moudle.gank.io.ganks.GanksPresenter;
import gswslbx.ddgank.moudle.gank.io.search.SearchActivity;

public class GankHomeActivity extends BaseActivity implements GankHomeContract.View {

    @BindView(R.id.tl_home_ganks)
    TabLayout tlHomeGanks;
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
    @BindView(R.id.vp_home_ganks)
    ViewPager vpHomeGanks;
    @BindView(R.id.root)
    CoordinatorLayout root;

    public GankHomeContract.Presenter mHomePresenter = new GankHomePresenter(this);
    public int mColorPrimary = R.color.colorPrimary;

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

        vpHomeGanks.setAdapter(infoPagerAdapter);
        tlHomeGanks.setupWithViewPager(vpHomeGanks);
        tlHomeGanks.setTabGravity(TabLayout.GRAVITY_FILL);
        vpHomeGanks.setCurrentItem(1);//默认显示Android资源
    }

    @OnClick(R.id.ll_home_search)
    public void search(View view) {
        startActivity(new Intent(GankHomeActivity.this, SearchActivity.class));
    }

    @Override
    public void showBannerFail(String failMessage, final boolean isRandom) {
        Snackbar.make(vpHomeGanks, failMessage, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
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
    public int getColorPrimary(int colorPrimary) {
        mColorPrimary = colorPrimary;
        return mColorPrimary;
    }

    //    @Override
//    public void showPermissionsTip() {
//        Snackbar.make(vpHomeGanks, "需要权限才能保存妹子", Snackbar.LENGTH_LONG).show();
//    }

    @Override
    public Activity getContext() {
        return this;
    }

//    @Override
//    public void showMsgSaveSuccess() {
//        Snackbar.make(vpHomeGanks, "图片保存成功", Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivity(i);
//            }
//        }).show();
//    }
//
//    @Override
//    public void showMsgSaveFail() {
//        Snackbar.make(vpHomeGanks, "图片保存失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mHomePresenter.saveImg(ivHomeBanner.getDrawable());
//            }
//        }).show();
//    }
//
//    @Override
//    public void showSavingMsgTip() {
//        Snackbar.make(vpHomeGanks, "正在保存图片...", Snackbar.LENGTH_LONG).show();
//    }

    @OnClick(R.id.iv_home_banner)
    public void onClick() {
        mHomePresenter.getRandomBanner();
    }

    public void setGanksPresenter(GankHomeContract.Presenter ganksPresenter) {
        mHomePresenter = ganksPresenter;
    }
}
