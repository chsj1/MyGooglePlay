package com.abc.myappstore;

import android.test.AndroidTestCase;

import com.alibaba.fastjson.JSON;
import com.abc.myappstore.bean.CityBean;
import com.abc.myappstore.bean.Ip;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2016/7/8 14:02
 * 描述	      ${TODO}
 *
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-07-08 14:31:33 +0800 (星期五, 08 七月 2016) $
 * 更新描述   ${TODO}
 */
public class TestFastJson extends AndroidTestCase {
    public void testJsonStr2Obj() {
        String jsonStr = "{\"origin\":\"183.38.221.215\"}";
        Ip ip = JSON.parseObject(jsonStr, Ip.class);
        System.out.println("ip:" + ip.origin);
    }

    public void testJsonStr2JsonArr() {
        String jsonArrStr = "[{\"name\": \"莱芜\",\"type\": \"1\",\"cityId\": \"136\",\"belong\": \"21\",\"pinyin\": \"LaiWu\"},{\"name\": \"济宁\",\"type\": \"1\",\"cityId\": \"137\",\"belong\": \"21\",\"pinyin\": \"JiNing\"}]";
        List<CityBean> cityBeen = JSON.parseArray(jsonArrStr, CityBean.class);
        System.out.println("cityBeen.siez:" + cityBeen.size());
    }

    public void testObj2JsonStr() {
        Ip ip = new Ip();
        ip.origin = "192.168.1.100";
        String jsonString = JSON.toJSONString(ip);
        System.out.println("jsonString:" + jsonString);
    }
}
