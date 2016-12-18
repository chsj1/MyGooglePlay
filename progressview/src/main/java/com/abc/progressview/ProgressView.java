package com.abc.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 创建者     伍碧林
 * 创建时间   2016/7/12 08:58
 * 描述	      ${TODO}
 *
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-07-12 09:18:49 +0800 (星期二, 12 七月 2016) $
 * 更新描述   ${TODO}
 */
public class ProgressView extends LinearLayout {

    private ImageView mIvIcon;
    private TextView  mTvNote;

    private boolean isProgressEnable = true;
    private int     mMax             = 100;
    private int   mProgress;
    private RectF mOval;
    private Paint mPaint;

    /**
     * 设置是否允许进度
     *
     * @param progressEnable
     */
    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public void setMax(int max) {
        mMax = max;
    }

    /**
     * 设置进度的当前值
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mProgress = progress;
        //重绘
        invalidate();
    }

    /**
     * 修改图标的内容
     */
    public void setIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 修改文本的内容
     */
    public void setNote(String content) {
        mTvNote.setText(content);
    }

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //挂载布局
        View view = View.inflate(context, R.layout.inflate_progressview, this);
        mIvIcon = (ImageView) view.findViewById(R.id.ivIcon);
        mTvNote = (TextView) view.findViewById(R.id.tvNote);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//绘制背景(透明)
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);//绘制图标和文字
        if (isProgressEnable) {
            if (mOval == null) {

                mOval = new RectF(mIvIcon.getLeft(), mIvIcon.getTop(), mIvIcon.getRight(), mIvIcon.getBottom());
            }
            float startAngle = -90;
            float sweepAngle = mProgress * 1.0f / mMax * 360;
            boolean useCenter = false;
            if (mPaint == null) {
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
                mPaint.setAntiAlias(true);
                mPaint.setColor(Color.BLUE);
            }
            canvas.drawArc(mOval, startAngle, sweepAngle, useCenter, mPaint);
        }

    }
}
