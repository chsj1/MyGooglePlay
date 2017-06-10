package com.abc.myappstore.base;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.abc.myappstore.activity.DetailActivity;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.holder.ItemHolder;
import com.abc.myappstore.manager.DownLoadManager;
import com.abc.myappstore.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 14:07
 * 描述	     1.创建一个BaseHolder 2.实现普通条目的点击事件
 */
public class ItemAdapter extends SuperBaseAdapter<ItemInfoBean> {

    public List<ItemHolder> mItemHolders = new ArrayList<>();

    public ItemAdapter(AbsListView absListView, List<ItemInfoBean> dataSource) {
        super(absListView, dataSource);
    }

    /**
     * @param position
     * @return
     * @des 创建一个BaseHolder的具体的子类对象
     */
    @Override
    public BaseHolder getSpecialBaseHolder(int position) {
        ItemHolder itemHolder = new ItemHolder();
        //添加观察者到观察者集合中
        DownLoadManager.getInstance().addObserver(itemHolder);

        //保存itemHolder到集合中
        mItemHolders.add(itemHolder);
        return itemHolder;
    }

    /**
     * 覆写hasLoadMore方法,决定有加载更多
     *
     * @return
     */
    @Override
    public boolean hasLoadMore() {
        return true;
    }


    /**
     * 实现普通条目的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        //data
        ItemInfoBean itemInfoBean = mDataSource.get(position);

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String packageName = itemInfoBean.packageName;
        String name = itemInfoBean.name;
        intent.putExtra("packageName", packageName);
        intent.putExtra("name", name);

        UIUtils.getContext().startActivity(intent);
    }
}
