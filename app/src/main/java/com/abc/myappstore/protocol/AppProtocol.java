package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;
import com.abc.myappstore.bean.ItemInfoBean;

import java.util.List;

/**
 * 创建者     chris
 * 创建时间   2016/7/9 11:11
 * 描述	      ${TODO}
 *
 */
public class AppProtocol extends BaseProtocol<List<ItemInfoBean>>{
    @Override
    public String getInterfaceKey() {
        return "app";
    }

}
