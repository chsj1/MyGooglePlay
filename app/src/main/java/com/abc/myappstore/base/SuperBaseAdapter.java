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


    private final Context mContext;

    public SuperBaseAdapter(Context context, List<ITEMBEANTYPE> dataSource) {
        super(dataSource);
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*--------------- 决定根视图长什么样子 ---------------*/
        BaseHolder baseHolder = null;

        if (convertView == null) {

            //创建一个BaseHolder的具体的子类对象
            baseHolder = getSpecialBaseHolder(position);
        } else {

            baseHolder = (BaseHolder) convertView.getTag();
        }

        /*--------------- 得到数据,绑定数据 ---------------*/
        //条目是普通类型
        //data
        ITEMBEANTYPE data = mDataSource.get(position);

        //data+view
        baseHolder.setDataAndRefreshHolderView(data);

        View holderView = baseHolder.mHolderView;

        return holderView;
    }


    /**
     * @param position
     * @return
     * @des 创建一个BaseHolder的具体的子类对象
     * @des 在SuperBaseAdapter中不知道如何创建一个BaseHolder的具体的子类对象, 只能交给子类
     * @des 子类必须实现, 定义成为抽象方法, 交给子类具体实现
     */
    public abstract BaseHolder getSpecialBaseHolder(int position);


}
