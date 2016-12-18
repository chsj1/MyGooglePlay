package com.abc.myappstore.holder;

import android.view.View;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.utils.UIUtils;

/**
 * 创建者     Chris
 * 创建时间   2016/7/12 16:44
 * 描述	      ${TODO}
 *
 */
public class MenuHolder extends BaseHolder<Object> {
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
        return holderView;
    }

    @Override
    public void refreshHolderView(Object data) {

    }
}
