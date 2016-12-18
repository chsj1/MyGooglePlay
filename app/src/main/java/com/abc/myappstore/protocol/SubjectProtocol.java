package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;
import com.abc.myappstore.bean.SubjectBean;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2016/7/9 15:53
 * 描述	      ${TODO}
 *
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-07-12 16:42:46 +0800 (星期二, 12 七月 2016) $
 * 更新描述   ${TODO}
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    public String getInterfaceKey() {
        return "subject";
    }

  /*  @Override
    public List<SubjectBean> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, new TypeToken<List<SubjectBean>>() {
        }.getType());
    }*/
}
