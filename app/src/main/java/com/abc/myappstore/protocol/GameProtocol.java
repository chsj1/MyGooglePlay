package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;
import com.abc.myappstore.bean.ItemInfoBean;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 11:24
 * 描述	      ${TODO}
 *
 */
public class GameProtocol extends BaseProtocol<List<ItemInfoBean>>{
    @Override
    public String getInterfaceKey() {
        return "game";
    }

/*    @Override
    public List<ItemInfoBean> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString,new TypeToken<List<ItemInfoBean>>(){}.getType());
    }*/
}
