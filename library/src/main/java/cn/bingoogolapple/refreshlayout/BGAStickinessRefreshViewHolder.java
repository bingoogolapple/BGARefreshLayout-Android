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
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 13:16
 * 描述:黏性下拉刷新风格、类似手机消息列表下拉刷新风格
 */
public class BGAStickinessRefreshViewHolder extends BGARefreshViewHolder {
    private BGAStickinessRefreshView mStickinessRefreshView;

    private Drawable mRotateDrawable;
    private int mStickinessColor = -1;

    public BGAStickinessRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_stickiness, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mStickinessRefreshView = (BGAStickinessRefreshView) mRefreshHeaderView.findViewById(R.id.stickinessRefreshView);
            mStickinessRefreshView.setStickinessRefreshViewHolder(this);
            if (mRotateDrawable != null) {
                mStickinessRefreshView.setRotateDrawable(mRotateDrawable);
            }
            if (mStickinessColor != -1) {
                mStickinessRefreshView.setStickinessColor(mStickinessColor);
            }
        }
        return mRefreshHeaderView;
    }

    public void setRotateDrawable(Drawable rotateDrawable) {
        mRotateDrawable = rotateDrawable;
    }

    public void setStickinessColor(int stickinessColor) {
        mStickinessColor = stickinessColor;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        mStickinessRefreshView.setMoveYDistance(moveYDistance);
    }

    @Override
    public void changeToIdle() {
        mStickinessRefreshView.smoothToIdle();
    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {
    }

    @Override
    public void changeToRefreshing() {
        mStickinessRefreshView.startRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mStickinessRefreshView.stopRefresh();
    }

    @Override
    public boolean canChangeToRefreshingStatus() {
        return mStickinessRefreshView.canChangeToRefreshing();
    }

}