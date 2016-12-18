package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 09:58
 * 描述	      ${TODO}
 *
 */
public class RecommendProtocol extends BaseProtocol<List<String>>{
    @Override
    public String getInterfaceKey() {
        return "recommend";
    }

 /*   @Override
    public List<String> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString,new TypeToken<List<String>>(){}.getType());
    }*/
}
