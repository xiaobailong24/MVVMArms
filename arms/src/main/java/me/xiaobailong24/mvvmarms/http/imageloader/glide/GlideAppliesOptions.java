package me.xiaobailong24.mvvmarms.http.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

/**
 * @author xiaobailong24
 * @date 2017/8/17
 * Glide 配置接口
 */
public interface GlideAppliesOptions {

    /**
     * 配置 Glide 的自定义参数
     *
     * @param context Context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void applyGlideOptions(Context context, GlideBuilder builder);
}
