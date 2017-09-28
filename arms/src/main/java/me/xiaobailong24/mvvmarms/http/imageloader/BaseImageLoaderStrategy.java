package me.xiaobailong24.mvvmarms.http.imageloader;

import android.content.Context;

/**
 * Created by xiaobailong24 on 2017/8/17.
 * 策略模式，ImageLoader 抽象策略类
 */
public interface BaseImageLoaderStrategy<T extends BaseImageConfig> {
    void loadImage(Context context, T config);

    void clear(Context context, T config);
}
