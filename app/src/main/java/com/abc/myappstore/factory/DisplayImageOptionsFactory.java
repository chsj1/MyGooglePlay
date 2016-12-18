package com.abc.myappstore.factory;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 创建者     Chris
 * 创建时间   2016/7/8 16:21
 * 描述	      ${TODO}
 *
 */
public class DisplayImageOptionsFactory {
    public static DisplayImageOptions createDefaultOptions() {
        DisplayImageOptions option = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        return option;
    }
}
