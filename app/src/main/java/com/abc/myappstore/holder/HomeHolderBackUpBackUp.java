package com.abc.myappstore.holder;

import android.view.View;
import android.widget.TextView;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 16:11
 * 描述	      1.提供视图
 * 描述	      2.接收数据
 * 描述	      3.数据和视图的绑定
 * 描述	      针对的是HomeAdapter
 *
 */
public class HomeHolderBackUpBackUp extends BaseHolder<String> {
    @InjectView(R.id.tmp_tv_1)
    TextView mTmpTv1;
    @InjectView(R.id.tmp_tv_2)
    TextView mTmpTv2;

    /**
     * @return
     * @des 决定所能提供的视图长什么样子
     * @des 找出所能提供视图里面的孩子对象
     */
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
        //找出所能提供视图里面的孩子对象
        ButterKnife.inject(this, holderView);
        return holderView;
    }

    /**
     * @param data
     * @des 进行数据和视图的绑定
     */
    @Override
    public void refreshHolderView(String data) {
        //view-->成员变量

        //data-->局部变量,基类里面也有
        //data+view
        mTmpTv1.setText("我是头-" + data);
        mTmpTv2.setText("我是尾巴-" + data);

    }
}
