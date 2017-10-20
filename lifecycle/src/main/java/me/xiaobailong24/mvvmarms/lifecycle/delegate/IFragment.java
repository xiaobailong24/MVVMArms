package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Fragment 公用接口
 */
public interface IFragment {

    /**
     * UI 初始化
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * Activity 与 Fragment 通信接口
     * 此方法是让外部调用使 Fragment 做一些操作的,比如说外部的 Fragment 想让 Fragment 对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传 {@link android.os.Message},通过what字段,来区分不同的方法,
     * 在此方法中就可以 switch 做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 新姿势：可以通过 Activity 的 ViewModel 共享数据给包含的 Fragment，配合 LiveData 好用到爆。
     *
     * @param data Object
     * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments">Sharing Data Between Fragments</a>
     */
    void setData(Object data);

    /**
     * Fragment 是否依赖注入
     *
     * @return true: 进行依赖注入；false:不进行依赖注入
     */
    boolean injectable();


    /**
     * 是否使用EventBus.
     * 默认使用(true)
     *
     * @return boolean
     */
    boolean useEventBus();
}
