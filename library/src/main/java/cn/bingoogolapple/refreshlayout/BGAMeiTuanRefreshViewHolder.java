/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.bingoogolapple.refreshlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/11/13 下午11:03
 * 描述:
 */
public class BGAMeiTuanRefreshViewHolder extends BGARefreshViewHolder {
    private BGAMeiTuanRefreshView mMeiTuanRefreshView;
    private int mPullDownImageResId = -1;
    private int mChangeToReleaseRefreshAnimResId = -1;
    private int mRefreshingAnimResId = -1;

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGAMeiTuanRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    /**
     * 设置下拉过程中的图片资源
     *
     * @param resId
     */
    public void setPullDownImageResource(@DrawableRes int resId) {
        mPullDownImageResId = resId;
    }

    /**
     * 设置进入释放刷新状态时的动画资源
     *
     * @param resId
     */
    public void setChangeToReleaseRefreshAnimResId(@DrawableRes int resId) {
        mChangeToReleaseRefreshAnimResId = resId;
    }

    /**
     * 设置正在刷新时的动画资源
     *
     * @param resId
     */
    public void setRefreshingAnimResId(@DrawableRes int resId) {
        mRefreshingAnimResId = resId;
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_meituan, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }

            mMeiTuanRefreshView = (BGAMeiTuanRefreshView) mRefreshHeaderView.findViewById(R.id.meiTuanView);
            if (mPullDownImageResId != -1) {
                mMeiTuanRefreshView.setPullDownImageResource(mPullDownImageResId);
            } else {
                throw new RuntimeException("请调用" + BGAMeiTuanRefreshViewHolder.class.getSimpleName() + "的setPullDownImageResource方法设置下拉过程中的图片资源");
            }
            if (mChangeToReleaseRefreshAnimResId != -1) {
                mMeiTuanRefreshView.setChangeToReleaseRefreshAnimResId(mChangeToReleaseRefreshAnimResId);
            } else {
                throw new RuntimeException("请调用" + BGAMeiTuanRefreshViewHolder.class.getSimpleName() + "的setChangeToReleaseRefreshAnimResId方法设置进入释放刷新状态时的动画资源");
            }
            if (mRefreshingAnimResId != -1) {
                mMeiTuanRefreshView.setRefreshingAnimResId(mRefreshingAnimResId);
            } else {
                throw new RuntimeException("请调用" + BGAMeiTuanRefreshViewHolder.class.getSimpleName() + "的setRefreshingAnimResId方法设置正在刷新时的动画资源");
            }
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        if (scale <= 1.0f) {
            mMeiTuanRefreshView.handleScale(scale);
        }
    }

    @Override
    public void changeToIdle() {
        mMeiTuanRefreshView.changeToIdle();
    }

    @Override
    public void changeToPullDown() {
        mMeiTuanRefreshView.changeToPullDown();
    }

    @Override
    public void changeToReleaseRefresh() {
        mMeiTuanRefreshView.changeToReleaseRefresh();
    }

    @Override
    public void changeToRefreshing() {
        mMeiTuanRefreshView.changeToRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mMeiTuanRefreshView.onEndRefreshing();
    }
}