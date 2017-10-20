package me.xiaobailong24.mvvmarms.repository.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author xiaobailong24
 * @date 2017/9/7
 * SharedPreferences 数据管理工具类
 */
public class DataHelper {
    private static SharedPreferences mSharedPreferences;
    public static final String SP_NAME = "config";


    private DataHelper() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 存储重要信息到 SharedPreference
     *
     * @param context Context
     * @param key     Key
     * @param value   Value
     */
    public static void setStringSF(Context context, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * 返回存在 SharedPreference 的信息
     *
     * @param context Context
     * @param key     Key
     * @return Value
     */
    public static String getStringSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, null);
    }

    /**
     * 存储重要信息到 SharedPreference
     *
     * @param context Context
     * @param key     Key
     * @param value   Value
     */
    public static void setIntegerSF(Context context, String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * 返回存在 SharedPreference 的信息
     *
     * @param key Key
     * @return Value，默认值 -1.
     */
    public static int getIntegerSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt(key, -1);
    }

    /**
     * 清除某个内容
     *
     * @param context Context
     * @param key     Key
     */
    public static void removeSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().remove(key).apply();
    }

    /**
     * 清除 SharedPreference
     *
     * @param context Context
     */
    public static void clearSharedPreference(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().clear().apply();
    }

    /**
     * 将对象储存到 SharedPreference
     *
     * @param context Context
     * @param key     Key
     * @param device  自定义类对象
     */
    public static <T> boolean saveDeviceData(Context context, String key, T device) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            // 将对象写入字节流
            oos.writeObject(device);
            // 将字节流编码成base64的字符串
            String oauthBase64 = new String(Base64.encode(stream
                    .toByteArray(), Base64.DEFAULT));
            mSharedPreferences.edit().putString(key, oauthBase64).apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将对象从 SharedPreference 中取出来
     *
     * @param context Context
     * @param key     Key
     * @return T 自定义类对象
     */
    public static <T> T getDeviceData(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        T device = null;
        String productBase64 = mSharedPreferences.getString(key, null);

        if (productBase64 == null) {
            return null;
        }
        //读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);

        //封装到字节流
        ByteArrayInputStream stream = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(stream);

            // 读取对象
            device = (T) bis.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }

    /**
     * 返回缓存文件夹
     *
     * @param context Context
     * @return 缓存文件夹
     */
    public static File getCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file;
            //获取系统管理的sd卡缓存文件
            file = context.getExternalCacheDir();
            //如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
            if (file == null) {
                file = new File(getCacheFilePath(context));
                makeDirs(file);
            }
            return file;
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 获取自定义缓存文件地址
     *
     * @param context Context
     * @return 自定义缓存文件地址
     */
    @SuppressLint("SdCardPath")
    public static String getCacheFilePath(Context context) {
        String packageName = context.getPackageName();
        return String.format("/mnt/sdcard/%s", packageName);
    }


    /**
     * 创建未存在的文件夹
     *
     * @param file 目标文件
     * @return 新文件夹
     */
    public static File makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 使用递归获取目录文件大小
     *
     * @param dir 目录文件
     * @return 目录文件大小
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                //递归调用继续统计
                dirSize += getDirSize(file);
            }
        }
        return dirSize;
    }

    /**
     * 使用递归删除文件夹
     *
     * @param dir 文件夹
     * @return 删除结果
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                //递归调用继续删除
                deleteDir(file);
            }
        }
        return true;
    }


    /**
     * 输入流转为 String
     *
     * @param in InputStream
     * @return String
     */
    public static String byteToString(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = in.read(buf)) != -1) {
            out.write(buf, 0, buf.length);
        }
        String result = out.toString();
        out.close();
        return result;
    }

}
