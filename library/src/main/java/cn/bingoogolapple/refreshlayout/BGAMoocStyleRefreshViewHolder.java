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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 13:16
 * 描述:慕课网下拉刷新风格
 */
public class BGAMoocStyleRefreshViewHolder extends BGARefreshViewHolder {
    private BGAMoocStyleRefreshView mMoocRefreshView;
    /**
     * 原始的图片
     */
    private Bitmap mOriginalBitmap;
    /**
     * 最终生成图片的填充颜色
     */
    private int mUltimateColor = -1;

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
            if (mOriginalBitmap != null) {
                mMoocRefreshView.setOriginalBitmap(mOriginalBitmap);
            }
            if (mUltimateColor != -1) {
                mMoocRefreshView.setUltimateColor(mUltimateColor);
            }
        }
        return mRefreshHeaderView;
    }

    /**
     * 设置原始的图片
     *
     * @param originalBitmap
     */
    public void setOriginalBitmap(Bitmap originalBitmap) {
        mOriginalBitmap = originalBitmap;
    }

    /**
     * 设置最终生成图片的填充颜色
     *
     * @param ultimateColor
     */
    public void setUltimateColor(int ultimateColor) {
        mUltimateColor = ultimateColor;
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