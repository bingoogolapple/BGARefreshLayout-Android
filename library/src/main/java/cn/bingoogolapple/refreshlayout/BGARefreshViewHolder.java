/**
 * Copyright 2015 bingoogolapple
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.refreshlayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 12:56
 * 描述:继承该抽象类实现响应的抽象方法，做出各种下拉刷新效果。参考BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMoocStyleRefreshViewHolder、BGAMeiTuanRefreshViewHolder
 */
public abstract class BGARefreshViewHolder {
    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值
     */
    private static final float PULL_DISTANCE_SCALE = 1.8f;
    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值，默认1.8f
     */
    private float mPullDistanceScale = PULL_DISTANCE_SCALE;
    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值
     */
    private static final float SPRING_DISTANCE_SCALE = 0.4f;
    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值，默认0.4f
     */
    private float mSpringDistanceScale = SPRING_DISTANCE_SCALE;

    protected Context mContext;
    /**
     * 下拉刷新上拉加载更多控件
     */
    protected BGARefreshLayout mRefreshLayout;
    /**
     * 下拉刷新控件
     */
    protected View mRefreshHeaderView;
    /**
     * 上拉加载更多控件
     */
    protected View mLoadMoreFooterView;
    /**
     * 底部加载更多提示控件
     */
    protected TextView mFooterStatusTv;
    /**
     * 底部加载更多菊花控件
     */
    protected ImageView mFooterChrysanthemumIv;
    /**
     * 底部加载更多菊花drawable
     */
    protected AnimationDrawable mFooterChrysanthemumAd;
    /**
     * 正在加载更多时的文本
     */
    protected String mLodingMoreText = "加载中...";
    /**
     * 是否开启加载更多功能
     */
    private boolean mIsLoadingMoreEnabled = true;
    /**
     * 整个加载更多控件的背景颜色资源id
     */
    private int mLoadMoreBackgroundColorRes = -1;
    /**
     * 整个加载更多控件的背景drawable资源id
     */
    private int mLoadMoreBackgroundDrawableRes = -1;
    /**
     * 下拉刷新控件的背景颜色资源id
     */
    protected int mRefreshViewBackgroundColorRes = -1;
    /**
     * 下拉刷新控件的背景drawable资源id
     */
    protected int mRefreshViewBackgroundDrawableRes = -1;
    /**
     * 头部控件移动动画时常
     */
    private int mTopAnimDuration = 500;

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGARefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        mContext = context;
        mIsLoadingMoreEnabled = isLoadingMoreEnabled;
    }

    /**
     * 设置正在加载更多时的文本
     *
     * @param loadingMoreText
     */
    public void setLoadingMoreText(String loadingMoreText) {
        mLodingMoreText = loadingMoreText;
    }

    /**
     * 设置整个加载更多控件的背景颜色资源id
     *
     * @param loadMoreBackgroundColorRes
     */
    public void setLoadMoreBackgroundColorRes(@ColorRes int loadMoreBackgroundColorRes) {
        mLoadMoreBackgroundColorRes = loadMoreBackgroundColorRes;
    }

    /**
     * 设置整个加载更多控件的背景drawable资源id
     *
     * @param loadMoreBackgroundDrawableRes
     */
    public void setLoadMoreBackgroundDrawableRes(@DrawableRes int loadMoreBackgroundDrawableRes) {
        mLoadMoreBackgroundDrawableRes = loadMoreBackgroundDrawableRes;
    }

    /**
     * 设置下拉刷新控件的背景颜色资源id
     *
     * @param refreshViewBackgroundColorRes
     */
    public void setRefreshViewBackgroundColorRes(@ColorRes int refreshViewBackgroundColorRes) {
        mRefreshViewBackgroundColorRes = refreshViewBackgroundColorRes;
    }

    /**
     * 设置下拉刷新控件的背景drawable资源id
     *
     * @param refreshViewBackgroundDrawableRes
     */
    public void setRefreshViewBackgroundDrawableRes(@DrawableRes int refreshViewBackgroundDrawableRes) {
        mRefreshViewBackgroundDrawableRes = refreshViewBackgroundDrawableRes;
    }

    /**
     * 获取顶部未满足下拉刷新条件时回弹到初始状态、满足刷新条件时回弹到正在刷新状态、刷新完毕后回弹到初始状态的动画时间，默认为500毫秒
     *
     * @return
     */
    public int getTopAnimDuration() {
        return mTopAnimDuration;
    }

    /**
     * 设置顶部未满足下拉刷新条件时回弹到初始状态、满足刷新条件时回弹到正在刷新状态、刷新完毕后回弹到初始状态的动画时间，默认为300毫秒
     *
     * @param topAnimDuration
     */
    public void setTopAnimDuration(int topAnimDuration) {
        mTopAnimDuration = topAnimDuration;
    }

    /**
     * 获取上拉加载更多控件，如果不喜欢这种上拉刷新风格可重写该方法实现自定义LoadMoreFooterView
     *
     * @return
     */
    public View getLoadMoreFooterView() {
        if (!mIsLoadingMoreEnabled) {
            return null;
        }
        if (mLoadMoreFooterView == null) {
            mLoadMoreFooterView = View.inflate(mContext, R.layout.view_normal_refresh_footer, null);
            mLoadMoreFooterView.setBackgroundColor(Color.TRANSPARENT);
            if (mLoadMoreBackgroundColorRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundColorRes);
            }
            if (mLoadMoreBackgroundDrawableRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundDrawableRes);
            }
            mFooterStatusTv = (TextView) mLoadMoreFooterView.findViewById(R.id.tv_normal_refresh_footer_status);
            mFooterChrysanthemumIv = (ImageView) mLoadMoreFooterView.findViewById(R.id.iv_normal_refresh_footer_chrysanthemum);
            mFooterChrysanthemumAd = (AnimationDrawable) mFooterChrysanthemumIv.getDrawable();
            mFooterStatusTv.setText(mLodingMoreText);
        }
        return mLoadMoreFooterView;
    }

    /**
     * 获取头部下拉刷新控件
     *
     * @return
     */
    public abstract View getRefreshHeaderView();

    /**
     * 下拉刷新控件可见时，处理上下拉进度
     *
     * @param scale         下拉过程0 到 1，回弹过程1 到 0，没有加上弹簧距离移动时的比例
     * @param moveYDistance 整个下拉刷新控件paddingTop变化的值，如果有弹簧距离，会大于整个下拉刷新控件的高度
     */
    public abstract void handleScale(float scale, int moveYDistance);

    /**
     * 进入到未处理下拉刷新状态
     */
    public abstract void changeToIdle();

    /**
     * 进入下拉状态
     */
    public abstract void changeToPullDown();

    /**
     * 进入释放刷新状态
     */
    public abstract void changeToReleaseRefresh();

    /**
     * 进入正在刷新状态
     */
    public abstract void changeToRefreshing();

    /**
     * 结束下拉刷新
     */
    public abstract void onEndRefreshing();

    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值
     *
     * @return
     */
    public float getPaddingTopScale() {
        return mPullDistanceScale;
    }

    /**
     * 设置手指移动距离与下拉刷新控件paddingTop移动距离的比值
     *
     * @param pullDistanceScale
     */
    public void setPullDistanceScale(float pullDistanceScale) {
        mPullDistanceScale = pullDistanceScale;
    }

    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值
     *
     * @return
     */
    public float getSpringDistanceScale() {
        return mSpringDistanceScale;
    }

    /**
     * 设置下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值，不能小于0，如果刷新控件比较高，建议将该值设置小一些
     *
     * @param springDistanceScale
     */
    public void setSpringDistanceScale(float springDistanceScale) {
        if (springDistanceScale < 0) {
            throw new RuntimeException("下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值springDistanceScale不能小于0");
        }
        mSpringDistanceScale = springDistanceScale;
    }

    /**
     * 是处于能够进入刷新状态
     *
     * @return
     */
    public boolean canChangeToRefreshingStatus() {
        return false;
    }

    /**
     * 进入加载更多状态
     */
    public void changeToLoadingMore() {
        if (mIsLoadingMoreEnabled && mFooterChrysanthemumAd != null) {
            mFooterChrysanthemumAd.start();
        }
    }

    /**
     * 结束上拉加载更多
     */
    public void onEndLoadingMore() {
        if (mIsLoadingMoreEnabled && mFooterChrysanthemumAd != null) {
            mFooterChrysanthemumAd.stop();
        }
    }

    /**
     * 获取下拉刷新控件的高度，如果初始化时的高度和最后展开的最大高度不一致，需重写该方法返回最大高度
     *
     * @return
     */
    public int getRefreshHeaderViewHeight() {
        if (mRefreshHeaderView != null) {
            // 测量下拉刷新控件的高度
            mRefreshHeaderView.measure(0, 0);
            return mRefreshHeaderView.getMeasuredHeight();
        }
        return 0;
    }

    /**
     * 改变整个下拉刷新头部控件移动一定的距离（带动画），自定义刷新控件进入刷新状态前后的高度有变化时可以使用该方法（参考BGAStickinessRefreshView）
     *
     * @param distance
     */
    public void startChangeWholeHeaderViewPaddingTop(int distance) {
        mRefreshLayout.startChangeWholeHeaderViewPaddingTop(distance);
    }

    /**
     * 设置下拉刷新上拉加载更多控件，该方法是设置BGARefreshViewHolder给BGARefreshLayout时由BGARefreshLayout调用
     *
     * @param refreshLayout
     */
    public void setRefreshLayout(BGARefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

}