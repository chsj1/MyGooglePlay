package com.abc.myappstore.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     Chris
 * 创建时间   2016/7/5 14:05
 * 描述	      全局可访问,单例(android系统给我们维护的一个单例)
 * 描述       该类放置的变量或者方法都是全局可访问
 *
 */
public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static long    mMainThreadId;
    // 网络协议内存缓存
    public Map<String, String> mProtocolCacheMap = new HashMap<>();

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程的Handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 得到主线程的线程id
     */
    public long getMainThreadId() {
        return mMainThreadId;
    }


    @Override
    public void onCreate() {//程序的入口方法
        super.onCreate();
        /*---------------程序启动的时候,就创建一些应用里面常用的对象 ---------------*/

        //上下文
        mContext = getApplicationContext();

        //创建一个主线程里面的Handler
        mHandler = new Handler();

        //得到主线程的线程id
        mMainThreadId = android.os.Process.myTid();

        initImageLoader(this);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)//
                .threadPriority(Thread.NORM_PRIORITY - 2)//
                .denyCacheImageMultipleSizesInMemory()//
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024)).tasksProcessingOrder(QueueProcessingType.LIFO)//
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
