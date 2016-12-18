package com.abc.myappstore.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.abc.myappstore.R;
import com.abc.myappstore.utils.LogUtils;
import com.abc.myappstore.utils.UIUtils;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 16:13
 * 描述	     1.已知宽度,可以动态计算高度
 * 描述	     2.已知高度,可以动态计算宽度
 * 描述 公式:图片的宽高比===控件的宽高比
 */
public class RatioLayout extends FrameLayout {
    public static final String TAG = "RatioLayout";


    private float mPicRatio = 1f;
    public  int   mRelative = RELATIVE_WIDTH;


    public static final int RELATIVE_WIDTH  = 0;//已知宽度,动态计算高度
    public static final int RELATIVE_HEIGHT = 1;//已知高度,动态计算宽度

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    public void setRelative(int relative) {
        mRelative = relative;
    }

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


        //取出自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, mPicRatio);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, mRelative);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
        MeasureSpec.AT_MOST 至多
        MeasureSpec.EXACTLY 确定的 match_prent 100dp 100px
        MeasureSpec.UNSPECIFIED 不确定的 wrap_content
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_WIDTH) {
            //得到自身的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);

            //动态计算高度
            //图片的宽高比===控件的宽/控件的宽高
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);

            setMeasuredDimension(selfWidth, selfHeight);

            /*
            int childWidthMeasureSpec= MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED)
            measure(childWidthMeasureSpec,childHeightMeasureSpec)

            measure(0,0);
            getMeasureWidth();
            getMeasureHeight();
             */
            //让孩子测绘自身
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            int childHeightDp = UIUtils.px2Dip(childHeight);
            LogUtils.i(TAG, "childHeightDp:" + childHeightDp);
        } else if (heightMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_HEIGHT) {
            //得到自身的高度了
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

            //动态计算自身的宽度
            //图片的宽高比===控件的宽/控件的高
            int selfWidth = (int) (mPicRatio * selfHeight + .5f);

            setMeasuredDimension(selfWidth, selfHeight);

            //让孩子测绘自身
            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingBottom() - getPaddingTop();
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            int childWidthDp = UIUtils.px2Dip(childWidth);
            LogUtils.i(TAG, "childWidthDp:" + childWidthDp);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
