package com.abc.myappstore.base;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abc.myappstore.adapter.MyBaseAdapter;
import com.abc.myappstore.factory.ThreadPoolProxyFactory;
import com.abc.myappstore.holder.LoadMoreHolder;
import com.abc.myappstore.utils.LogUtils;
import com.abc.myappstore.utils.UIUtils;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 16:42
 * 描述	      SuperBaseAdapter针对的就是MyBaseAdapter中未处理的getView方法
 *
 */
public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends MyBaseAdapter<ITEMBEANTYPE> implements AdapterView.OnItemClickListener {

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL = 1;
    private AbsListView mAbsListView;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private int mCurState;

    public SuperBaseAdapter(AbsListView absListView, List<ITEMBEANTYPE> dataSource) {
        super(dataSource);
        absListView.setOnItemClickListener(this);
        mAbsListView = absListView;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }


    /*
    listview中显示几种viewtype类型,需要在adapter中做如下操作
        1.覆写2个方法
            getViewTypeCount
            getItemViewType
        2.在getView方法分别处理
     */
    /*
    get(得到)ViewType(ViewType)Count(总数)
     */


    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//1(普通条目)+1(加载更多)=2
    }

    /*
    get(得到)Item(指定条目的)ViewType(ViewType类型)
    0-getViewTypeCount-1之间
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;//0
        } else {
            return getNorItemViewType(position);
        }
    }

    /**
     * @param position
     * @return
     * @des 得到普通条目的ViewType的类型, 默认情况是1
     * @des 子类可以覆写该方法, 返回更多普通条目的类型
     */
    public int getNorItemViewType(int position) {
        return VIEWTYPE_NORMAL;//1
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*--------------- 决定根视图长什么样子 ---------------*/
        BaseHolder baseHolder = null;
        int curItemViewType = getItemViewType(position);

        if (convertView == null) {
            if (curItemViewType == VIEWTYPE_LOADMORE) {//条目是加载更多
                //创建一个BaseHolder的具体的子类对象
                baseHolder = getLoadMoreHolder();

            } else {//条目是普通类型
                //创建一个BaseHolder的具体的子类对象
                baseHolder = getSpecialBaseHolder(position);
            }
        } else {
            baseHolder = (BaseHolder) convertView.getTag();
        }
        /*--------------- 得到数据,绑定数据 ---------------*/
        if (curItemViewType == VIEWTYPE_LOADMORE) {//条目是加载更多

            if (hasLoadMore()) {
                //ui-->应该显示正在加载更多
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_LOADING);

                //触发加载更多的数据
                triggerLoadMoreData();
            } else {
                //ui-->应该显示没有加载更多
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
            }
        } else {////条目是普通类型
            //data
            ITEMBEANTYPE data = mDataSource.get(position);

            //data+view
            baseHolder.setDataAndRefreshHolderView(data);
        }

        LogUtils.s(baseHolder.mHolderView.toString());
        View holderView = baseHolder.mHolderView;
        //针对holderView执行动画
        holderView.setScaleX(0.6f);
        holderView.setScaleY(0.5f);


        ViewCompat.animate(holderView).scaleX(1).scaleY(1).setDuration(400).
                setInterpolator(new OvershootInterpolator(4)).start();


        return holderView;
    }

    /**
     * 触发加载更多的数据
     */
    private void triggerLoadMoreData() {
        //正在执行任务之前,需要重置ui
        mCurState = LoadMoreHolder.LOADMORE_LOADING;
        mLoadMoreHolder.setDataAndRefreshHolderView(mCurState);

        if (mLoadMoreTask == null) {
            LogUtils.sf("triggerLoadMoreData");
            //异步加载
            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolProxyFactory.createNormalThreadPoolProxy().submit(mLoadMoreTask);
        }
    }


    class LoadMoreTask implements Runnable {
        private static final int PAGERSIZE = 20;

        @Override
        public void run() {
            /*--------------- 初始化决定ui的两个数据 ---------------*/
            List<ITEMBEANTYPE> loadMoreList = null;


            /*--------------- 真正的在子线程中开始加载更多的数据,得到数据,处理数据 ---------------*/
            try {
                loadMoreList = onLoadMore();
                //处理数据-->loadMoreList==>state
                if (loadMoreList == null) {//肯定是加载失败了
                    mCurState = LoadMoreHolder.LOADMORE_ERROR;//希望可以点击重试
                } else {
                    if (loadMoreList.size() == PAGERSIZE) {
                        mCurState = LoadMoreHolder.LOADMORE_LOADING;//还有可能加载更多
                    } else {
                        mCurState = LoadMoreHolder.LOADMORE_NONE;//没有加载更多
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mCurState = LoadMoreHolder.LOADMORE_ERROR;//希望可以点击重试
            }

            /*--------------- 定义两个临时变量 ---------------*/

            final List<ITEMBEANTYPE> finalLoadMoreList = loadMoreList;
            final int finalState = mCurState;

            /*--------------- 刷新对应的ui ---------------*/
            UIUtils.getMainThreadHanlder().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->ListView-->得到加载更多之后的list集合-->添加到dataSets-->adaper.notifyDataSetChanged();
                    if (finalLoadMoreList != null) {
                        mDataSource.addAll(finalLoadMoreList);
                        notifyDataSetChanged();
                    }
                    //刷新ui-->LoadMoreHolder-->得到state-->mLoadMoreHolder.setDataAndRefreshHolder()
                    mLoadMoreHolder.setDataAndRefreshHolderView(finalState);
                }
            });

            mLoadMoreTask = null;
        }
    }

    /**
     * @return
     * @des 真正的在子线程中加载更多
     * @des 在SuperBaseAdapter中, 不知道如何加载更多的数据, 所以交给子类
     * @des 子类是选择性的覆写该方法(如果子类有加载更多在覆写)
     */
    public List<ITEMBEANTYPE> onLoadMore() throws Exception {
        return null;
    }

    /**
     * @return
     * @des决定是否有加载更多
     * @des 默认是没有加载更多, 但是子类可以覆写该方法, 决定有加载更多
     */
    public boolean hasLoadMore() {
        return false;// 默认没有加载更多
    }

    /**
     * 创建一个BaseHolder的具体的子类对象-->加载更多的Holder
     *
     * @return
     */
    private LoadMoreHolder getLoadMoreHolder() {
        //因为加载更多的视图都是一样的,所以只需要一个loadmoreholder对象提供即可
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    /**
     * @param position
     * @return
     * @des 创建一个BaseHolder的具体的子类对象
     * @des 在SuperBaseAdapter中不知道如何创建一个BaseHolder的具体的子类对象, 只能交给子类
     * @des 子类必须实现, 定义成为抽象方法, 交给子类具体实现
     */
    public abstract BaseHolder getSpecialBaseHolder(int position);

    /*--------------- 处理条目的点击事件 ---------------*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mAbsListView instanceof ListView) {
            position = position - ((ListView) mAbsListView).getHeaderViewsCount();
        }

        int curItemViewType = getItemViewType(position);
        if (curItemViewType == VIEWTYPE_LOADMORE) {
            if (mCurState == LoadMoreHolder.LOADMORE_ERROR) {
                //重新触发加载更多的数据
                triggerLoadMoreData();
            }
        } else {
            onNormalItemClick(parent, view, position, id);
        }
    }

    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     * @des 普通条目点击事件的处理
     * @des 在SuperBaseAdapter中不知道如何处理普通条目的点击事件, 交给子类
     */
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
