package com.abc.myappstore.protocol;

import com.abc.myappstore.base.BaseProtocol;
import com.abc.myappstore.bean.CategoryInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 08:30
 * 描述	      ${TODO}
 *
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {
    @Override
    public String getInterfaceKey() {
        return "category";
    }
    @Override
    public List<CategoryInfoBean> parseJsonString(String resultJsonString) {
        /*--------------- Bean的结果和jsonString的结构不统一的时候,只能用结点解析 ---------------*/

        List<CategoryInfoBean> result = new ArrayList<>();
        try {
            JSONArray rootJsonArray = new JSONArray(resultJsonString);
            //遍历集合
            for (int i = 0; i < rootJsonArray.length(); i++) {
                JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);
                //取出title
                String title = itemJsonObject.getString("title");

                CategoryInfoBean titleCategoryInfoBean = new CategoryInfoBean();
                titleCategoryInfoBean.title = title;
                titleCategoryInfoBean.isTitle = true;
                //加入集合中
                result.add(titleCategoryInfoBean);

                //取出infos
                JSONArray infosJsonArray = itemJsonObject.getJSONArray("infos");
                for (int j = 0; j < infosJsonArray.length(); j++) {
                    JSONObject infoJsonObject = infosJsonArray.getJSONObject(j);

                    String name1 = infoJsonObject.getString("name1");
                    String name2 = infoJsonObject.getString("name2");
                    String name3 = infoJsonObject.getString("name3");
                    String url1 = infoJsonObject.getString("url1");
                    String url2 = infoJsonObject.getString("url2");
                    String url3 = infoJsonObject.getString("url3");

                    CategoryInfoBean infoBean = new CategoryInfoBean();
                    infoBean.name1 = name1;
                    infoBean.name2 = name2;
                    infoBean.name3 = name3;
                    infoBean.url1 = url1;
                    infoBean.url2 = url2;
                    infoBean.url3 = url3;
                    //加入集合
                    result.add(infoBean);
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
