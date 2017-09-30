package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * Activity 生命周期代理接口
 */

public interface ActivityDelegate extends Parcelable {
    String ACTIVITY_DELEGATE = "activity_delegate";


    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
