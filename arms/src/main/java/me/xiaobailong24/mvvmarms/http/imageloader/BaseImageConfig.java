package me.xiaobailong24.mvvmarms.http.imageloader;

import android.widget.ImageView;

/**
 * @author xiaobailong24
 * @date 2017/8/17
 * 公共图片加载配置，可以自由扩展
 */
public class BaseImageConfig {
    /**
     * 图片 Url
     */
    protected String url;
    /**
     * 要显示的 ImageView
     */
    protected ImageView imageView;
    /**
     * 图片占位符
     */
    protected int placeholder;
    /**
     * 错误图片占位符
     */
    protected int errorPic;

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
