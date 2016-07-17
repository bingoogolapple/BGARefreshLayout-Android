package cn.bingoogolapple.refreshlayout.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgaindicator.BGAFixedIndicator;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.demo.App;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.model.BannerModel;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavListViewFragment;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavRecyclerViewFragment;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavScrollViewFragment;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavWebViewFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPagerActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private BGABanner mBanner;
    private BGAFixedIndicator mIndicator;
    private ViewPager mContentVp;

    private Fragment[] mFragments;
    private String[] mTitles;
    private StickyNavRecyclerViewFragment mRecyclerViewFragment;
    private StickyNavListViewFragment mListViewFragment;
    private StickyNavScrollViewFragment mScrollViewFragment;
    private StickyNavWebViewFragment mWebViewFragment;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_viewpager);
        mRefreshLayout = getViewById(R.id.refreshLayout);
        mBanner = getViewById(R.id.banner);
        mIndicator = getViewById(R.id.indicator);
        mContentVp = getViewById(R.id.vp_viewpager_content);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mApp, true));

        initBanner();

        mFragments = new Fragment[4];
        mFragments[0] = mRecyclerViewFragment = new StickyNavRecyclerViewFragment();
        mFragments[1] = mListViewFragment = new StickyNavListViewFragment();
        mFragments[2] = mScrollViewFragment = new StickyNavScrollViewFragment();
        mFragments[3] = mWebViewFragment = new StickyNavWebViewFragment();

        mTitles = new String[4];
        mTitles[0] = "RecyclerView";
        mTitles[1] = "ListView";
        mTitles[2] = "ScrollView";
        mTitles[3] = "WebView";
        mContentVp.setAdapter(new ContentViewPagerAdapter(getSupportFragmentManager()));
        mIndicator.initData(0, mContentVp);
    }

    private void initBanner() {
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.mipmap.holder).error(R.mipmap.holder).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });

        App.getInstance().getEngine().getBannerModel().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mBanner.setData(R.layout.view_image, bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        switch (mContentVp.getCurrentItem()) {
            case 0:
                mRecyclerViewFragment.onBGARefreshLayoutBeginRefreshing(refreshLayout);
                break;
            case 1:
                mListViewFragment.onBGARefreshLayoutBeginRefreshing(refreshLayout);
                break;
            case 2:
                mScrollViewFragment.onBGARefreshLayoutBeginRefreshing(refreshLayout);
                break;
            case 3:
                mWebViewFragment.onBGARefreshLayoutBeginRefreshing(refreshLayout);
                break;
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        switch (mContentVp.getCurrentItem()) {
            case 0:
                return mRecyclerViewFragment.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            case 1:
                return mListViewFragment.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            case 2:
                return mScrollViewFragment.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            case 3:
                return mWebViewFragment.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            default:
                return false;
        }
    }

    public void endRefreshing() {
        mRefreshLayout.endRefreshing();
    }

    public void endLoadingMore() {
        mRefreshLayout.endLoadingMore();
    }

    class ContentViewPagerAdapter extends FragmentPagerAdapter {

        public ContentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}