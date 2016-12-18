package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;
import com.abc.myappstore.bean.HomeBean;

/**
 * 创建者     Chris
 * 创建时间   2016/7/8 16:25
 * 描述	      针对的是HomeFragment里面的网络请求
 * 描述	      把HomeFragment里面相关的网络请求的代码,移动过来了而已
 *
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {
    /**
     * 决定协议的关键字
     *
     * @return
     */
    @Override
    public String getInterfaceKey() {
        return "home";
    }

    /**
     * 完成具体请求结果的解析过程
     *
     * @param resultJsonString
     * @return
     */
/*    @Override
    public HomeBean parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        HomeBean homeBean = gson.fromJson(resultJsonString, HomeBean.class);
        return homeBean;
    }*/
}
