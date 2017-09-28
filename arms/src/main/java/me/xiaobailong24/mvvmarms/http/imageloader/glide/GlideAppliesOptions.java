package me.xiaobailong24.mvvmarms.http.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;

/**
 * Created by xiaobailong24 on 2017/8/17.
 * Glide 配置接口
 */
public interface GlideAppliesOptions {

    /**
     * Description: 配置 Glide 的自定义参数
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void applyGlideOptions(Context context, GlideBuilder builder);
}
