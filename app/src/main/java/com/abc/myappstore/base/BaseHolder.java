package com.abc.myappstore.base;

import android.view.View;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 16:25
 * 描述	      1.提供视图
 * 描述	      2.接收数据
 * 描述	      3.数据和视图的绑定
 *
 */
public abstract class BaseHolder<HOLDERBEANTYPE> {
    public View           mHolderView;//可以提供的视图-->view
    public HOLDERBEANTYPE mData;//-->data

    public BaseHolder() {
        //初始化所能提供的视图
        mHolderView = initHolderViewAndFindViews();

        //mHolderView<找>一个对象,然后绑定在自己身上
        mHolderView.setTag(this);
    }


    /**
     * @param data
     * @des 1.接收数据
     * @des 2.进行数据和视图的绑定操作
     */
    public void setDataAndRefreshHolderView(HOLDERBEANTYPE data) {
        //保存数据到成员变量
        mData = data;

        // 进行数据和视图的绑定操作
        refreshHolderView(data);
    }


    /**
     * @return
     * @des 决定根视图长什么样子
     */
    public abstract View initHolderViewAndFindViews();

    /**
     * @param data
     * @des 进行数据和视图的绑定操作
     * @des 子类是必须实现, 所以定义成为抽象方法, 交给子类具体实现
     */
    public abstract void refreshHolderView(HOLDERBEANTYPE data);
}
