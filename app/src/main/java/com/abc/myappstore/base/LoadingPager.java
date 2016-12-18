package com.abc.myappstore.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.abc.myappstore.R;
import com.abc.myappstore.factory.ThreadPoolProxyFactory;
import com.abc.myappstore.utils.LogUtils;
import com.abc.myappstore.utils.UIUtils;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 10:43
 * 描述	      普通类
 * 描述	      1.提供视图(是4种视图类型中的一种(加载中,成功,失败,空视图))
 * 描述	      2.接收/加载数据
 * 描述	      3.数据和视图的绑定
 *
 */
public abstract class LoadingPager extends FrameLayout {
    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_SUCCESS = 1;//成功
    public static final int STATE_ERROR   = 2;//失败
    public static final int STATE_EMPTY   = 3;//空视图
    public              int mCurState     = STATE_LOADING;//默认是加载中
    private View         mLoadingView;
    private View         mErrorView;
    private View         mEmptyView;
    private View         mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPager(Context context) {
        super(context);
        initCommonView();
    }

    /**
     * @des 初始化静态视图(加载中视图, 失败视图, 空视图)
     * @call LoadingPager一旦创建的时候
     */
    private void initCommonView() {
        //        加载中视图
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        //       失败视图
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //希望重新触发加载数据
                triggerLoadData();
            }
        });

        //        空视图
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        refreshUIByState();
    }

    /**
     * @des 根据不同的状态, 提供不同的视图
     * @call 1.LoadingPager一旦创建的时候-->loading视图(默认)
     * @call 2.外界触发加载了数据, 在数据加载之前-->loading视图
     * @call 3.外界触发加载了数据, 而且数据加载完成-->successView,errorView,emptyView
     */
    private void refreshUIByState() {
        //隐藏所有的视图
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        if (mSuccessView != null) {
            mSuccessView.setVisibility(View.GONE);
        }

        switch (mCurState) {
            case STATE_LOADING://应该显示  加载中视图
                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case STATE_EMPTY://应该显示  空视图
                mEmptyView.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR://应该显示  错误视图
                mErrorView.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCESS://应该显示  成功中视图
                //考虑是否创建成功视图
                if (mSuccessView == null) {
                    mSuccessView = initSuccessView();
                    this.addView(mSuccessView);
                }
                mSuccessView.setVisibility(View.VISIBLE);

                break;

            default:
                break;
        }
    }


    /**
     * @des 触发加载数据
     * @call 外界需要加载数据的时候调用
     */
    public void triggerLoadData() {
        if (mCurState != STATE_SUCCESS && mLoadDataTask == null) {
            LogUtils.sf("triggerLoadData");
            //重置当前的状态,然后刷新ui
            mCurState = STATE_LOADING;
            refreshUIByState();

            //异步加载
            mLoadDataTask = new LoadDataTask();
//            new Thread(mLoadDataTask).start();
            ThreadPoolProxyFactory.createNormalThreadPoolProxy().submit(mLoadDataTask);
        }
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //真正在子线程中开始加载具体的数据了
            LoadedResult tempState = initData();
            //得到数据-->tempState

            //处理数据-->处理curState的变化-->int
            mCurState = tempState.getState();

            UIUtils.getMainThreadHanlder().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->refreshByState
                    refreshUIByState();
                }
            });
            //run方法走到最后,置空任务
            mLoadDataTask = null;
        }
    }

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @des 在LoadingPager中, 不知道如何加载具体数据, 只能把自身变成基类, 交给子类覆写实现
     * @des 子类必须实现, 定义成为抽象方法, 交给子类具体实现
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    public abstract LoadedResult initData();

    /**
     * @return
     * @des 1.决定成功视图长什么样子-->xml
     * @des 2.数据和视图的绑定-->因为此时数据已经加载回来
     * @des 在LoadingPager中, 不知道成功视图长什么样子, 数据和视图如何绑定, 只能交给子类
     * @des 子类必须实现, 定义成为抽象方法, 交给子类具体实现
     * @call 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候,数据加载完成,数据加载成功
     */
    public abstract View initSuccessView();


    /**
     *  加载回来的结果，枚举
     */
    public enum LoadedResult {
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);
        public int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state = state;
        }
    }
}
