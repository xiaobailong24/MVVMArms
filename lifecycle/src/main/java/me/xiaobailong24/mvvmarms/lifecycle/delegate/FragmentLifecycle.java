package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/9/5
 * Fragment 生命周期回调。
 * 在 Fragment 对应生命周期方法执行完毕后进行的。
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
        if (f instanceof IFragment && f.getArguments() != null) {
            Timber.i("%s ---> onFragmentAttached", f.toString());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                fragmentDelegate = new FragmentDelegateImpl(fm, f);
                f.getArguments().putParcelable(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
            }
            fragmentDelegate.onAttach(context);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentCreated", f.toString());
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentViewCreated", f.toString());
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentActivityCreated", f.toString());
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentStarted", f.toString());
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentResumed", f.toString());
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentPaused", f.toString());
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentStopped", f.toString());
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentSaveInstanceState", f.toString());
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentViewDestroyed", f.toString());
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentDestroyed", f.toString());
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            Timber.i("%s ---> onFragmentDetached", f.toString());
            fragmentDelegate.onDetach();
            f.getArguments().clear();
        }
    }

    private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
        if (fragment instanceof IFragment && fragment.getArguments() != null) {
            fragment.getArguments().setClassLoader(getClass().getClassLoader());
            return fragment.getArguments().getParcelable(FragmentDelegate.FRAGMENT_DELEGATE);
        }
        return null;
    }
}