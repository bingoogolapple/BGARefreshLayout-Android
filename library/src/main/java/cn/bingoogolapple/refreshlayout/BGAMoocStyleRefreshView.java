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
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 10:43
 * 描述:慕课网下拉刷新风格控件
 */
public class BGAMoocStyleRefreshView extends View {
    private PorterDuffXfermode mXfermode;
    /**
     * 用来画临时图像的画笔
     */
    private Paint mPaint;
    /**
     * 用来画临时图像的画布
     */
    private Canvas mCanvas;
    /**
     * 原始的图片
     */
    private Bitmap mOriginalBitmap;
    /**
     * 原始的图片宽度
     */
    private int mOriginalBitmapWidth;
    /**
     * 原始的图片高度
     */
    private int mOriginalBitmapHeight;
    /**
     * 最终生成的图片
     */
    private Bitmap mUltimateBitmap;
    /**
     * 贝塞尔曲线路径
     */
    private Path mBezierPath;
    /**
     * 贝塞尔曲线控制点x
     */
    private float mBezierControlX;
    /**
     * 贝塞尔曲线控制点y
     */
    private float mBezierControlY;
    /**
     * 贝塞尔曲线原始的控制点y
     */
    private float mBezierControlOriginalY;
    /**
     * 当前波纹的y值
     */
    private float mWaveY;
    /**
     * 波纹原始的y值
     */
    private float mWaveOriginalY;
    /**
     * 贝塞尔曲线控制点x是否增加
     */
    private boolean mIsBezierControlXIncrease;
    /**
     * 是否正在刷新
     */
    private boolean mIsRefreshing = false;

    public BGAMoocStyleRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initCanvas() {
        mOriginalBitmapWidth = mOriginalBitmap.getWidth();
        mOriginalBitmapHeight = mOriginalBitmap.getHeight();

        // 初始状态值
        mWaveOriginalY = mOriginalBitmapHeight;
        mWaveY = 1.2f * mWaveOriginalY;
        mBezierControlOriginalY = 1.25f * mWaveOriginalY;
        mBezierControlY = mBezierControlOriginalY;

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mBezierPath = new Path();

        mCanvas = new Canvas();
        mUltimateBitmap = Bitmap.createBitmap(mOriginalBitmapWidth, mOriginalBitmapHeight, Config.ARGB_8888);
        mCanvas.setBitmap(mUltimateBitmap);
    }

    /**
     * 设置最终生成图片的填充颜色资源
     *
     * @param resId
     */
    public void setUltimateColor(@ColorRes int resId) {
        mPaint.setColor(getResources().getColor(resId));
    }

    /**
     * 设置原始图片资源
     *
     * @param resId
     */
    public void setOriginalImage(@DrawableRes int resId) {
        mOriginalBitmap = BitmapFactory.decodeResource(getResources(), resId);
        initCanvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mUltimateBitmap == null) {
            return;
        }

        drawUltimateBitmap();
        // 将目标图绘制在当前画布上，起点为左边距，上边距的交点
        canvas.drawBitmap(mUltimateBitmap, getPaddingLeft(), getPaddingTop(), null);
        if (mIsRefreshing) {
            invalidate();
        }
    }

    /**
     * 绘制最终的图片
     */
    private void drawUltimateBitmap() {
        mBezierPath.reset();
        mUltimateBitmap.eraseColor(Color.parseColor("#00ffffff"));

        if (mBezierControlX >= mOriginalBitmapWidth + 1 / 2 * mOriginalBitmapWidth) {
            mIsBezierControlXIncrease = false;
        } else if (mBezierControlX <= -1 / 2 * mOriginalBitmapWidth) {
            mIsBezierControlXIncrease = true;
        }

        mBezierControlX = mIsBezierControlXIncrease ? mBezierControlX + 10 : mBezierControlX - 10;
        if (mBezierControlY >= 0) {
            mBezierControlY -= 2;
            mWaveY -= 2;
        } else {
            mWaveY = mWaveOriginalY;
            mBezierControlY = mBezierControlOriginalY;
        }

        mBezierPath.moveTo(0, mWaveY);
        mBezierPath.cubicTo(mBezierControlX / 2, mWaveY - (mBezierControlY - mWaveY), (mBezierControlX + mOriginalBitmapWidth) / 2, mBezierControlY, mOriginalBitmapWidth, mWaveY);
        mBezierPath.lineTo(mOriginalBitmapWidth, mOriginalBitmapHeight);
        mBezierPath.lineTo(0, mOriginalBitmapHeight);
        mBezierPath.close();

        mCanvas.drawBitmap(mOriginalBitmap, 0, 0, mPaint);
        mPaint.setXfermode(mXfermode);
        mCanvas.drawPath(mBezierPath, mPaint);
        mPaint.setXfermode(null);
    }

    public void startRefreshing() {
        mIsRefreshing = true;
        reset();
    }

    public void stopRefreshing() {
        mIsRefreshing = false;
        reset();
    }

    private void reset() {
        mWaveY = mWaveOriginalY;
        mBezierControlY = mBezierControlOriginalY;
        mBezierControlX = 0;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize + getPaddingLeft() + getPaddingRight();
        } else {
            width = mOriginalBitmapWidth + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize + getPaddingTop() + getPaddingBottom();
        } else {
            height = mOriginalBitmapHeight + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        setMeasuredDimension(width, height);
    }

}