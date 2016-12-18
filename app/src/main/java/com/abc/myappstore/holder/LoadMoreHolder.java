package com.abc.myappstore.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 09:49
 * 描述	      ${TODO}
 *
 */
public class LoadMoreHolder extends BaseHolder<Integer> {


    @InjectView(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;

    @InjectView(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    public static final int LOADMORE_LOADING = 0;//正在加载更多...
    public static final int LOADMORE_ERROR   = 1;//加载更多失败,点击重试
    public static final int LOADMORE_NONE    = 2;//没有加载更多


    /**
     * 决定所能提供的视图长什么样子
     * 找出孩子对象
     *
     * @return
     */
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        //找孩子
        ButterKnife.inject(this, holderView);
        return holderView;
    }

    /**
     * 数据和视图的绑定
     *
     * @param curState
     */
    @Override
    public void refreshHolderView(Integer curState) {
        //隐藏所有的视图
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);

        switch (curState) {
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:

                break;

            default:
                break;
        }
    }
}
