package cn.bingoogolapple.refreshlayout.demo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import cn.bingoogolapple.bgaindicator.BGAFixedIndicator;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.demo.R;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavListViewFragment;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavRecyclerViewFragment;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavScrollViewFragment;
import cn.bingoogolapple.refreshlayout.demo.ui.fragment.StickyNavWebViewFragment;

public class ViewPagerActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ViewPager mContentVp;
    private BGAFixedIndicator mIndicator;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_viewpager);
        mRefreshLayout = getViewById(R.id.refreshLayout);
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

        mContentVp.setAdapter(new ContentViewPagerAdapter(getSupportFragmentManager(), this));
        mIndicator.initData(0, mContentVp);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    private static class ContentViewPagerAdapter extends FragmentPagerAdapter {
        private Class[] mFragments;
        private String[] mTitles;
        private Context mContext;

        public ContentViewPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
            mFragments = new Class[4];
            mFragments[0] = StickyNavRecyclerViewFragment.class;
            mFragments[1] = StickyNavListViewFragment.class;
            mFragments[2] = StickyNavScrollViewFragment.class;
            mFragments[3] = StickyNavWebViewFragment.class;

            mTitles = new String[4];
            mTitles[0] = "RV";
            mTitles[1] = "LV";
            mTitles[2] = "SV";
            mTitles[3] = "WV";
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(mContext, mFragments[position].getName());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}