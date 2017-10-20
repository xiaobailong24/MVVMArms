package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Fragment 生命周期代理接口
 */
public interface FragmentDelegate extends Parcelable {
    String FRAGMENT_DELEGATE = "fragment_delegate";


    /**
     * 代理 {@link android.support.v4.app.Fragment#onAttach(Context)}
     *
     * @param context Context
     */
    void onAttach(Context context);

    /**
     * 代理 {@link android.support.v4.app.Fragment#onCreate(Bundle)}
     *
     * @param savedInstanceState 数据恢复
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * 代理 {@link android.support.v4.app.Fragment#onViewCreated(View, Bundle)}
     *
     * @param view               View
     * @param savedInstanceState 数据恢复
     */
    void onCreateView(View view, Bundle savedInstanceState);

    /**
     * 代理 {@link android.support.v4.app.Fragment#onActivityCreated(Bundle)}
     *
     * @param savedInstanceState 数据恢复
     */
    void onActivityCreate(Bundle savedInstanceState);

    /**
     * 代理 {@link android.support.v4.app.Fragment#onStart()}
     */
    void onStart();

    /**
     * 代理 {@link android.support.v4.app.Fragment#onResume()}
     */
    void onResume();

    /**
     * 代理 {@link android.support.v4.app.Fragment#onPause()}
     */
    void onPause();

    /**
     * 代理 {@link android.support.v4.app.Fragment#onStop()}
     */
    void onStop();

    /**
     * 代理 {@link android.support.v4.app.Fragment#onSaveInstanceState(Bundle)}
     *
     * @param outState 数据保存
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * 代理 {@link android.support.v4.app.Fragment#onDestroyView()}
     */
    void onDestroyView();

    /**
     * 代理 {@link android.support.v4.app.Fragment#onDestroy()}
     */
    void onDestroy();

    /**
     * 代理 {@link android.support.v4.app.Fragment#onDetach()}
     */
    void onDetach();

    /**
     * Fragment 是否添加到 Activity
     *
     * @return true if the fragment is currently added to its activity.
     */
    boolean isAdded();
}
