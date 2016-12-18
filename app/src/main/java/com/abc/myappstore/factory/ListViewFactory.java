package com.abc.myappstore.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.abc.myappstore.utils.UIUtils;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 11:16
 * 描述	      ${TODO}
 *
 */
public class ListViewFactory {
    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        //对listView做一些常规的属性设置
        listView.setDivider(null);
        listView.setCacheColorHint(Color.TRANSPARENT);//2.3

        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        return listView;
    }
}
