package com.abc.myappstore.holder;

import android.view.View;
import android.widget.TextView;

import com.abc.myappstore.R;
import com.abc.myappstore.utils.UIUtils;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 16:11
 * 描述	      1.提供视图
 * 描述	      2.接收数据
 * 描述	      3.数据和视图的绑定
 * 描述	      针对的是HomeAdapter
 *
 */
public class HomeHolderBackUp {
    private TextView mTvTemp1;
    private TextView mTvTemp2;

    public View   mHolderView;//可以提供的视图-->view
    public String mData;//-->data

    public HomeHolderBackUp() {
        //初始化所能提供的视图
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);

        //找出孩子对象
        mTvTemp1 = (TextView) mHolderView.findViewById(R.id.tmp_tv_1);
        mTvTemp2 = (TextView) mHolderView.findViewById(R.id.tmp_tv_2);

        //mHolderView<找>一个对象,然后绑定在自己身上
        mHolderView.setTag(this);
    }

    /**
     * @param data
     * @des 1.接收数据
     * @des 2.进行数据和视图的绑定操作
     */
    public void setDataAndRefreshHolderView(String data) {
        //保存数据到成员变量
        mData = data;

        refreshHolderView(data);
    }

    /**
     * @param data
     * @des 进行数据和视图的绑定操作
     */
    private void refreshHolderView(String data) {
        //view-->成员变量
        //data-->局部变量,成员变量也有
        //data+view
        mTvTemp1.setText("我是头-" + data);
        mTvTemp2.setText("我是尾巴-" + data);
    }
}
