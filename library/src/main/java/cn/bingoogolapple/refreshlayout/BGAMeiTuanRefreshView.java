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
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/11/14 上午12:16
 * 描述:
 */
public class BGAMeiTuanRefreshView extends RelativeLayout {
    private ImageView mPullDownView;
    private ImageView mReleaseRefreshingView;

    private AnimationDrawable mChangeToReleaseRefreshAd;
    private AnimationDrawable mRefreshingAd;

    private int mChangeToReleaseRefreshAnimResId;
    private int mRefreshingAnimResId;

    public BGAMeiTuanRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPullDownView = (ImageView) findViewById(R.id.iv_meituan_pull_down);
        mReleaseRefreshingView = (ImageView) findViewById(R.id.iv_meituan_release_refreshing);
    }

    public void setPullDownImageResource(@DrawableRes int resId) {
        mPullDownView.setImageResource(resId);
    }

    public void setChangeToReleaseRefreshAnimResId(@DrawableRes int resId) {
        mChangeToReleaseRefreshAnimResId = resId;
        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
    }

    public void setRefreshingAnimResId(@DrawableRes int resId) {
        mRefreshingAnimResId = resId;
    }

    public void handleScale(float scale) {
        scale = 0.1f + 0.9f * scale;
        ViewCompat.setScaleX(mPullDownView, scale);
        ViewCompat.setPivotY(mPullDownView, mPullDownView.getHeight());
        ViewCompat.setScaleY(mPullDownView, scale);
    }

    public void changeToIdle() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();

        mPullDownView.setVisibility(VISIBLE);
        mReleaseRefreshingView.setVisibility(INVISIBLE);
    }

    public void changeToPullDown() {
        mPullDownView.setVisibility(VISIBLE);
        mReleaseRefreshingView.setVisibility(INVISIBLE);
    }

    public void changeToReleaseRefresh() {
        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
        mChangeToReleaseRefreshAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(VISIBLE);
        mPullDownView.setVisibility(INVISIBLE);

        mChangeToReleaseRefreshAd.start();
    }

    public void changeToRefreshing() {
        stopChangeToReleaseRefreshAd();

        mReleaseRefreshingView.setImageResource(mRefreshingAnimResId);
        mRefreshingAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(VISIBLE);
        mPullDownView.setVisibility(INVISIBLE);

        mRefreshingAd.start();
    }

    public void onEndRefreshing() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();
    }

    private void stopRefreshingAd() {
        if (mRefreshingAd != null) {
            mRefreshingAd.stop();
            mRefreshingAd = null;
        }
    }

    private void stopChangeToReleaseRefreshAd() {
        if (mChangeToReleaseRefreshAd != null) {
            mChangeToReleaseRefreshAd.stop();
            mChangeToReleaseRefreshAd = null;
        }
    }
}