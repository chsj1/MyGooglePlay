package com.abc.myappstore.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 创建者     Chris
 * 创建时间   2016/7/12 08:40
 * 描述	      ${TODO}
 *
 */
public class ProgressBtn extends Button {

    private boolean isProgressEnable = true;
    private int     max              = 100;
    private int      progress;
    private Drawable mDrawable;

    /**
     * 是否允许有进度
     */
    public void setProgressEnable(boolean progressEnable) {
        isProgressEnable = progressEnable;
    }

    /**
     * 设置进度的最大值
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 设置进度的当前值
     */
    public void setProgress(int progress) {
        this.progress = progress;
        //重绘进度
        invalidate();
    }

    public ProgressBtn(Context context) {
        super(context);
    }

    public ProgressBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //之前
        //        canvas.drawText("haha",20,20,getPaint());
        if (isProgressEnable) {
            if (mDrawable == null) {
                mDrawable = new ColorDrawable(Color.BLUE);
            }
            int left = 0;
            int top = 0;
            int right = (int) (progress * 1.0f / max * getMeasuredWidth()+.5f);
            int bottom = getBottom();
            mDrawable.setBounds(left, top, right, bottom);

            mDrawable.draw(canvas);

        }
        super.onDraw(canvas);
    }
}
