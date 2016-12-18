package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 10:59
 * 描述	      ${TODO}
 *
 */
public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    public String getInterfaceKey() {
        return "hot";
    }

 /*   @Override
    public List<String> parseJsonString(String resultJsonString) {
        return new Gson().fromJson(resultJsonString, new TypeToken<List<String>>() {
        }.getType());
    }*/
}
