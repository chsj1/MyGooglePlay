package com.abc.myappstore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.abc.myappstore.utils.LogUtils;
import com.abc.myappstore.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 09:58
 * 描述	      针对谷歌市场里面的Fragment做一些特有封装
 *
 */
public abstract class BaseFragment extends Fragment {

    public LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LogUtils.s("--onCreateView--");
        //返回一个具体的View(是经过了数据绑定的视图)
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                /**
                 * @return
                 * @des 真正在子线程中开始加载具体的数据
                 * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
                 */
                @Override
                public LoadedResult initData() {
                    //方法的具体实现过程,通过调用相应同名方法实现
                    return BaseFragment.this.initData();
                }

                /**
                 * @return
                 * @des 1.决定成功视图长什么样子-->xml
                 * @des 2.数据和视图的绑定-->因为此时数据已经加载回来
                 * @call 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候,数据加载完成,数据加载成功
                 */
                @Override
                public View initSuccessView() {
                    //方法的具体实现过程,通过调用相应同名方法实现
                    return BaseFragment.this.initSuccessView();
                }
            };
        } else {
            //低版本会出现一个问题,需要加上如下代码-->在2.3的系统
            ViewParent parent = mLoadingPager.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }
        return mLoadingPager;
    }


    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @des 在BaseFragment不知道如何真正的在子线程中开始加载具体的数据, 交给子类
     * @des 子类必须实现, 定义成为抽象方法, 交给子类具体实现
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    public abstract LoadingPager.LoadedResult initData();

    /**
     * @return
     * @des 1.决定成功视图长什么样子-->xml
     * @des 2.数据和视图的绑定-->因为此时数据已经加载回来
     * @des 在BaseFragment里面不知道成功视图长什么样子, 以及数据和视图如何绑定,交给子类
     * @des 子类必须实现, 定义成为抽象方法, 交给子类具体实现
     * @call 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候,数据加载完成,数据加载成功
     */
    public abstract View initSuccessView();

    /*
    分析,歌市场里面的Fragment所对应的View(视图),有些什么特点?
    1.视图有4种视图情况
        ①加载中视图
        ②加载失败视图
        ③空视图
        ④成功视图
    2.针对4种视图情况做分析
        1.加载中视图,加载失败视图,空视图属于静态视图,而成功视图是动态视图
        2.静态视图最开始就可以决定长什么样子,但是动态视图,没有决定-->只有在数据加载完成了才可以决定
    3.一个视图有多种展示情况,但是同一时刻只能展示一种的时候,我们思考思路是怎样
    多种-->2种
        boolean flag==>true/false
        int   ===>0/1
        判断某一个对象是否为null===>要么为null/要么不为null
     多种-->2种以上
        常量1  ①加载中视图
        常量2  ②加载失败视图
        常量3  ③空视图
        常量4  ④成功视图
        当前状态-->mCurState-->默认是①加载中视图-->
     */
    /*
    分析,歌市场里面的Fragment还需要进行数据的加载-->数据加载了curState才会切换-->视图展示情况才会进行切换
    基本步骤
    0.触发加载 -->①加载中视图
        进入页面开始加载数据
        下拉刷新
        上滑加载更多
        点击重试
    1.异步加载 -->①加载中视图
    2.得到数据
    3.处理数据-->更新状态
    4.刷新ui
        -->加载错误,加载异常的-->②加载失败视图
        -->加载成功,但是数据长度为0-->③空视图
        -->加载成功,数据长度不为0-->④成功视图
    */

    /**
     * 根据请求回来的数据,返回具体的LoadedResult类型值
     *
     * @param resResult
     * @return
     */
    public LoadingPager.LoadedResult checkResResult(Object resResult) {
        if (resResult == null) {
            return LoadingPager.LoadedResult.EMPTY;
        }
        //list
        if (resResult instanceof List) {
            if (((List) resResult).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        //map
        if (resResult instanceof Map) {
            if (((Map) resResult).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }
}
