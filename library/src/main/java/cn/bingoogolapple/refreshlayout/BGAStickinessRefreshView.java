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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 22:34
 * 描述:黏性下拉刷新控件
 */
public class BGAStickinessRefreshView extends View {
    private static final String TAG = BGAStickinessRefreshView.class.getSimpleName();
    private BGAStickinessRefreshViewHolder mStickinessRefreshViewHolder;
    private RectF mTopBound;
    private RectF mBottomBound;
    private Rect mRotateDrawableBound;
    private Point mCenterPoint;

    private Paint mPaint;
    private Path mPath;

    private Drawable mRotateDrawable;
    /**
     * 旋转图片的大小
     */
    private int mRotateDrawableSize;

    private int mMaxBottomHeight;
    private int mCurrentBottomHeight = 0;

    /**
     * 是否正在旋转
     */
    private boolean mIsRotating = false;
    private boolean mIsRefreshing = false;
    /**
     * 当前旋转角度
     */
    private int mCurrentDegree = 0;

    private int mEdge = 0;
    private int mTopSize = 0;

    public BGAStickinessRefreshView(Context context) {
        this(context, null);
    }

    public BGAStickinessRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGAStickinessRefreshView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initBounds();
        initPaint();
        initSize();
    }

    private void initBounds() {
        mTopBound = new RectF();
        mBottomBound = new RectF();
        mRotateDrawableBound = new Rect();
        mCenterPoint = new Point();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
    }

    private void initSize() {
        mEdge = BGARefreshLayout.dp2px(getContext(), 5);
        mRotateDrawableSize = BGARefreshLayout.dp2px(getContext(), 30);
        mTopSize = mRotateDrawableSize + 2 * mEdge;

        mMaxBottomHeight = (int) (2.4f * mRotateDrawableSize);
    }

    public void setStickinessColor(@ColorRes int resId) {
        mPaint.setColor(getResources().getColor(resId));
    }

    public void setRotateImage(@DrawableRes int resId) {
        mRotateDrawable = getResources().getDrawable(resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mTopSize + getPaddingLeft() + getPaddingRight();
        int height = mTopSize + getPaddingTop() + getPaddingBottom() + mMaxBottomHeight;

        setMeasuredDimension(width, height);
        measureDraw();
    }

    private void measureDraw() {
        mCenterPoint.x = getMeasuredWidth() / 2;
        mCenterPoint.y = getMeasuredHeight() / 2;

        mTopBound.left = mCenterPoint.x - mTopSize / 2;
        mTopBound.right = mTopBound.left + mTopSize;
        mTopBound.bottom = getMeasuredHeight() - getPaddingBottom() - mCurrentBottomHeight;
        mTopBound.top = mTopBound.bottom - mTopSize;

        float scale = 1.0f - mCurrentBottomHeight * 1.0f / mMaxBottomHeight;
        scale = Math.min(Math.max(scale, 0.2f), 1.0f);
        int mBottomSize = (int) (mTopSize * scale);

        mBottomBound.left = mCenterPoint.x - mBottomSize / 2;
        mBottomBound.right = mBottomBound.left + mBottomSize;
        mBottomBound.bottom = mTopBound.bottom + mCurrentBottomHeight;
        mBottomBound.top = mBottomBound.bottom - mBottomSize;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mRotateDrawable == null) {
            return;
        }

        mPath.reset();

        mTopBound.round(mRotateDrawableBound);
        mRotateDrawable.setBounds(mRotateDrawableBound);
        if (mIsRotating) {
            mPath.addOval(mTopBound, Path.Direction.CW);
            canvas.drawPath(mPath, mPaint);
            canvas.save();
            canvas.rotate(mCurrentDegree, mRotateDrawable.getBounds().centerX(), mRotateDrawable.getBounds().centerY());
            mRotateDrawable.draw(canvas);
            canvas.restore();
        } else {
            // 移动到drawable左边缘的中间那个点
            mPath.moveTo(mTopBound.left, mTopBound.top + mTopSize / 2);
            // 从drawable左边缘的中间那个点开始画半圆
            mPath.arcTo(mTopBound, 180, 180);
            // 二阶贝塞尔曲线，第一个是控制点，第二个是终点
//            mPath.quadTo(mTopBound.right - mTopSize / 8, mTopBound.bottom, mBottomBound.right, mBottomBound.bottom - mBottomBound.height() / 2);

            // mCurrentBottomHeight   0 到 mMaxBottomHeight
            // scale                  0.2 到 1
            float scale = Math.max(mCurrentBottomHeight * 1.0f / mMaxBottomHeight, 0.2f);

            float bottomControlXOffset = mTopSize * ((3 + (float) Math.pow(scale, 7) * 16) / 32);
            float bottomControlY = mTopBound.bottom / 2 + mCenterPoint.y / 2;
            // 三阶贝塞尔曲线，前两个是控制点，最后一个点是终点
            mPath.cubicTo(mTopBound.right - mTopSize / 8, mTopBound.bottom, mTopBound.right - bottomControlXOffset, bottomControlY, mBottomBound.right, mBottomBound.bottom - mBottomBound.height() / 2);

            mPath.arcTo(mBottomBound, 0, 180);

//            mPath.quadTo(mTopBound.left + mTopSize / 8, mTopBound.bottom, mTopBound.left, mTopBound.bottom - mTopSize / 2);
            mPath.cubicTo(mTopBound.left + bottomControlXOffset, bottomControlY, mTopBound.left + mTopSize / 8, mTopBound.bottom, mTopBound.left, mTopBound.bottom - mTopSize / 2);

            canvas.drawPath(mPath, mPaint);

            mRotateDrawable.draw(canvas);
        }
    }

    public void setMoveYDistance(int moveYDistance) {
        int bottomHeight = moveYDistance - mTopSize - getPaddingBottom() - getPaddingTop();
        if (bottomHeight > 0) {
            mCurrentBottomHeight = bottomHeight;
        } else {
            mCurrentBottomHeight = 0;
        }
        postInvalidate();
    }

    /**
     * 是否能切换到正在刷新状态
     *
     * @return
     */
    public boolean canChangeToRefreshing() {
        return mCurrentBottomHeight >= mMaxBottomHeight * 0.98f;
    }

    public void startRefreshing() {
        ValueAnimator animator = ValueAnimator.ofInt(mCurrentBottomHeight, 0);
        animator.setDuration(mStickinessRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentBottomHeight = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsRefreshing = true;
                if (mCurrentBottomHeight != 0) {
                    mStickinessRefreshViewHolder.startChangeWholeHeaderViewPaddingTop(mCurrentBottomHeight);
                } else {
                    mStickinessRefreshViewHolder.startChangeWholeHeaderViewPaddingTop(-(mTopSize + getPaddingTop() + getPaddingBottom()));
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsRotating = true;
                startRotating();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    private void startRotating() {
        ViewCompat.postOnAnimation(this, new Runnable() {
            @Override
            public void run() {
                mCurrentDegree += 10;
                if (mCurrentDegree > 360) {
                    mCurrentDegree = 0;
                }
                if (mIsRefreshing) {
                    startRotating();
                }
                postInvalidate();
            }
        });
    }

    public void stopRefresh() {
        mIsRotating = true;
        mIsRefreshing = false;
        postInvalidate();
    }

    public void smoothToIdle() {
        ValueAnimator animator = ValueAnimator.ofInt(mCurrentBottomHeight, 0);
        animator.setDuration(mStickinessRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentBottomHeight = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsRotating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    public void setStickinessRefreshViewHolder(BGAStickinessRefreshViewHolder stickinessRefreshViewHolder) {
        mStickinessRefreshViewHolder = stickinessRefreshViewHolder;
    }

}