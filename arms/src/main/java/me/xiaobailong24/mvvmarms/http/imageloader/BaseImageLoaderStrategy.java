package me.xiaobailong24.mvvmarms.http.imageloader;

import android.content.Context;

/**
 * @author xiaobailong24
 * @date 2017/8/17
 * 图片加载策略,实现 {@link BaseImageLoaderStrategy}
 * 并通过 {@link ImageLoader#setStrategy(BaseImageLoaderStrategy)} 配置后,才可进行图片请求
 */
public interface BaseImageLoaderStrategy<T extends BaseImageConfig> {
    /**
     * 加载图片
     *
     * @param context Context
     * @param config  ImageConfig
     */
    void loadImage(Context context, T config);

    /**
     * 停止加载
     *
     * @param context Context
     * @param config  ImageConfig
     */
    void clear(Context context, T config);
}
