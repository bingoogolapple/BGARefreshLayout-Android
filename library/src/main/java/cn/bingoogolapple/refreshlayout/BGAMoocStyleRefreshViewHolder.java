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
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 13:16
 * 描述:慕课网下拉刷新风格
 */
public class BGAMoocStyleRefreshViewHolder extends BGARefreshViewHolder {
    private BGAMoocStyleRefreshView mMoocRefreshView;
    private int mUltimateColorResId = -1;
    private int mOriginalImageResId = -1;

    public BGAMoocStyleRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_mooc_style, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }

            mMoocRefreshView = (BGAMoocStyleRefreshView) mRefreshHeaderView.findViewById(R.id.moocView);
            if (mOriginalImageResId != -1) {
                mMoocRefreshView.setOriginalImage(mOriginalImageResId);
            } else {
                throw new RuntimeException("请调用" + BGAMoocStyleRefreshViewHolder.class.getSimpleName() + "的setOriginalImage方法设置原始图片资源");
            }
            if (mUltimateColorResId != -1) {
                mMoocRefreshView.setUltimateColor(mUltimateColorResId);
            } else {
                throw new RuntimeException("请调用" + BGAMoocStyleRefreshViewHolder.class.getSimpleName() + "的setUltimateColor方法设置最终生成图片的填充颜色资源");
            }
        }
        return mRefreshHeaderView;
    }

    /**
     * 设置原始的图片资源
     *
     * @param resId
     */
    public void setOriginalImage(@DrawableRes int resId) {
        mOriginalImageResId = resId;
    }

    /**
     * 设置最终生成图片的填充颜色资源
     *
     * @param resId
     */
    public void setUltimateColor(@ColorRes int resId) {
        mUltimateColorResId = resId;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        scale = 0.6f + 0.4f * scale;
        ViewCompat.setScaleX(mMoocRefreshView, scale);
        ViewCompat.setScaleY(mMoocRefreshView, scale);
    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {
    }

    @Override
    public void changeToRefreshing() {
        mMoocRefreshView.startRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mMoocRefreshView.stopRefreshing();
    }

}