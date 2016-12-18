package com.abc.myappstore.conf;

import com.abc.myappstore.utils.LogUtils;

/**
 * 创建者     Chris
 * 创建时间   2016/7/5 14:04
 * 描述	      ${TODO}
 *
 */
public class Constants {

    /*
      LogUtils.LEVEL_ALL;-->显示所有的日志输出
      LogUtils.LEVEL_OFF;-->关闭/屏蔽所有的日志输出
     */
    public static final int  DEBUGLEVEL      = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = Integer.MAX_VALUE;// 尽可能长，不用服务器了
//    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000;

    public static final class URLS {
        // genymotion 的网络地址 原生是 10.0.2.2
//        public static final String BASEURL         = "http://10.0.3.2:8080/GooglePlayServer/";
        // 这个地址真机没有办法访问
//        public static final String BASEURL         = "http://192.168.56.1:8080/GooglePlayServer/";
        // 服务器和 手机都接入同一个wifi，真机访问地址一定要是这个，不要使用以太网的ip,用无线网络的ip地址
        public static final String BASEURL         = "http://192.168.1.101:8080/GooglePlayServer/";
//        public static final String BASEURL         = "http://192.168.223.2:8080/GooglePlayServer/";
        public static final String IMGBASEURL      = BASEURL + "image?name=";
        public static final String DOWNLOADBASEURL = BASEURL + "download?";
    }

    public static final class REQ {

    }

    public static final class RES {

    }

    public static final class PAY {
        public static final int PAYTYPE_ZHIFUBAO = 1;
        public static final int PAYTYPE_WEIXIN   = 2;
        public static final int PAYTYPE_UUPAY    = 3;
    }
}
