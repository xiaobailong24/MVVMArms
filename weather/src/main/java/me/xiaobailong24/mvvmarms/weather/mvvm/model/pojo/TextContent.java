package me.xiaobailong24.mvvmarms.weather.mvvm.model.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * @author xiaobailong24
 * @date 2017/5/15
 * 主要封装标题、内容项，配合 DataBinding，自动更新 UI
 */
public class TextContent extends BaseObservable {
    private String title;
    private String content;


    public TextContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
