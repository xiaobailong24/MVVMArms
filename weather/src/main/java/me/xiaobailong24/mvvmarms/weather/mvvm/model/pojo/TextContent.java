package me.xiaobailong24.mvvmarms.weather.mvvm.model.pojo;

import android.databinding.BaseObservable;

/**
 * @author xiaobailong24
 * @date 2017/5/15
 * 主要封装标题、内容项，配合 DataBinding，自动更新 UI
 */
public class TextContent extends BaseObservable {
    public final String title;
    public final String content;


    public TextContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
