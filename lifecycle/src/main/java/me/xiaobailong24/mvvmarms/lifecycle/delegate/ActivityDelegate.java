package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * Activity 生命周期代理接口
 */
interface ActivityDelegate extends Parcelable {
    String ACTIVITY_DELEGATE = "activity_delegate";


    /**
     * 代理 {@link Activity#onCreate(Bundle)}
     *
     * @param savedInstanceState 数据恢复
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * 代理 {@link Activity#onStart()}
     */
    void onStart();

    /**
     * 代理 {@link Activity#onResume()}
     */
    void onResume();

    /**
     * 代理 {@link Activity#onPause()}
     */
    void onPause();

    /**
     * 代理 {@link Activity#onStop()}
     */
    void onStop();

    /**
     * 代理 {@link Activity#onSaveInstanceState(Bundle)}
     *
     * @param outState 数据保存
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * 代理 {@link Activity#onDestroy()}
     */
    void onDestroy();
}
