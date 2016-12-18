package com.abc.myappstore.bean;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 08:31
 * 描述	      ${TODO}
 *
 */
public class CategoryInfoBean {
    public String  name1;//经营
    public String  name2;//竞速
    public String  name3;//
    public String  url1;//image/category_game_9.jpg
    public String  url2;//image/category_game_10.jpg
    public String  url3;//image/category_game_10.jpg
    public String  title;
    public boolean isTitle;//额外添加一个属性

    @Override
    public String toString() {
        return "CategoryInfoBean{" +
                "name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                ", title='" + title + '\'' +
                ", isTitle=" + isTitle +
                '}';
    }
}
