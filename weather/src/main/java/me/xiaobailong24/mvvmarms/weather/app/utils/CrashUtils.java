package me.xiaobailong24.mvvmarms.weather.app.utils;

import android.app.Application;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : 崩溃相关工具类
 *     {@see https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CrashUtils.java}
 *     Edit by xiaobailong24
 * </pre>
 */
public final class CrashUtils {

    //单例工具类持有Application的弱引用
    private static WeakReference<Application> mApplication;

    private static boolean mInitialized;
    private static String defaultDir;
    private static String dir;

    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH-mm-ss", Locale.getDefault());

    private static final String CRASH_HEAD;

    private static final UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    static {
        CRASH_HEAD = "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本
                "\n************* Crash Log Head ****************\n\n";

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

        UNCAUGHT_EXCEPTION_HANDLER = new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                if (e == null) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    return;
                }
                Date now = new Date(System.currentTimeMillis());
                String fileName = FORMAT.format(now) + ".txt";
                final String fullPath = (dir == null ? defaultDir : dir) + fileName;
                if (!createOrExistsFile(fullPath))
                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter pw = null;
                        try {
                            pw = new PrintWriter(new FileWriter(fullPath, false));
                            pw.write(CRASH_HEAD);
                            e.printStackTrace(pw);
                            Throwable cause = e.getCause();
                            while (cause != null) {
                                cause.printStackTrace(pw);
                                cause = cause.getCause();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (pw != null) {
                                pw.close();
                                System.exit(0);
                            }
                        }
                    }
                }).start();
                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean init(Application application) {
        if (mApplication == null)
            mApplication = new WeakReference<>(application);
        return init(application, "");
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @param crashDir 崩溃文件存储目录
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean init(Application application, @NonNull final File crashDir) {
        if (mApplication == null)
            mApplication = new WeakReference<>(application);
        return init(application, crashDir.getAbsolutePath() + FILE_SEP);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @param crashDir 崩溃文件存储目录
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean init(Application application, final String crashDir) {
        if (mApplication == null)
            mApplication = new WeakReference<>(application);
        if (isSpace(crashDir)) {
            dir = null;
        } else {
            dir = crashDir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
        }
        if (mInitialized)
            return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && mApplication.get().getExternalCacheDir() != null)
            defaultDir = mApplication.get().getExternalCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        else {
            defaultDir = mApplication.get().getCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        }
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
        return mInitialized = true;
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists())
            return file.isFile();
        if (!createOrExistsDir(file.getParentFile()))
            return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
