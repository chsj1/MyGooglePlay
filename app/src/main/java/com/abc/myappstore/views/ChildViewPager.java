package com.abc.myappstore.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 14:56
 * 描述	      ${TODO}
 *
 */
public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1.是否派发
     * 原则:一般不做处理
     * return true-->不派发
     * return false-->不派发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /*
      2.是否拦截
      return true-->走到自己的onTouchEvent中
                            return true-->消费事件
                            return false-->不消费-->传递给父控件
      reutrn false-->传递给孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /*
      3.是否消费
        return true-->消费事件
        return false-->不消费-->传递给父控件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();

                int diffX = (int) (moveX - mDownX + .5f);
                int diffY = (int) (moveY - mDownY + .5f);
                if (Math.abs(diffX) > Math.abs(diffY)) {//左右拖动
                    //希望ChildViewPager处理事件-->自身-->请求父容器不拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {//上下拖动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
