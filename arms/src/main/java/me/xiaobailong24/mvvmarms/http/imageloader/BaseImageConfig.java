package me.xiaobailong24.mvvmarms.http.imageloader;

import android.widget.ImageView;

/**
 * Created by xiaobailong24 on 2017/8/17.
 * 公共图片加载配置，可以自由扩展
 */

public class BaseImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;
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
