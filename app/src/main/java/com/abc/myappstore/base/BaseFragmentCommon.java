package com.abc.myappstore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 09:44
 * 描述	      针对Fragment常规的一些抽取方式
 *
 */
public abstract class BaseFragmentCommon extends Fragment {
    /*
      BaseFragmentCommon抽取了之后有什么好处?
      1.从java语言角度-->基类可以放置共有的方法,以及共有的属性-->保证代码量少一些(基类可以减少代码量)
      2.还可以控制哪些方法是必须实现,哪些方法是选择性实现
      3.针对BaseFragmentCommon,由于集成Fragment-->后期不用关心onCreate,onCreateView,onActivityCreated只需要关心
        init ,initView,initData,initListener
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initListener();
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * @des Fragment的初始化的时候接收一些参数
     * @des 在BaseFragmentCommon中, 不知道做什么样的初始化的操作, 交给子类覆写
     * @call
     */
    public void init() {

    }

    /**
     * @return
     * @des 决定Fragment所展示的ui视图是啥
     * @des 在BaseFragmentCommon中, 不知道Fragment具体展示什么视图, 交给子类覆写
     * @des 子类是必须要覆写
     * @call
     */
    public abstract View initView() ;

    /**
     * @des 加载Fragment里面所对应的一些数据
     * @des 在BaseFragmentCommon中, 不知道如何加载数据, 交给子类覆写
     * @call
     */
    public void initData() {

    }

    /**
     * @des 设置Fragment所对应的一些监听
     * @des 在BaseFragmentCommon中, 不知道做什么样的监听, 交给子类覆写
     * @call
     */
    public void initListener() {

    }
}
