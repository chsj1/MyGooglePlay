package com.abc.myappstore.manager;


/**
 * 创建者     chris
 * 创建时间   2016/7/12 09:42
 * 描述	     放置和下载相关的一些信息
 *
 */
public class DownLoadInfo {
    public String downLoadUrl;//下载的downLoadUrl
    public String savePath;//下载apk的存放的位置
    public int curState = DownLoadManager.STATE_UNDOWNLOAD;
    public String   packageName;
    public int      max;
    public int      progress;
    public Runnable task;
}
