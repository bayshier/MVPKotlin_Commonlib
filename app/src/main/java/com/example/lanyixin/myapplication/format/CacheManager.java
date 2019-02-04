package com.example.lanyixin.myapplication.format;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;

import java.io.*;
import java.security.MessageDigest;

/**
 * 文件序列化缓存管理器<br>
 * 缓存路径：data/data/<package_name>/Cache_Serialization
 *
 * @author weige
 */
public class CacheManager {

    /**
     * 缓存数据目录
     */
    private static final String CACHE_SERIALIZATION_DIR = "Cache_Serialization";

    private static final String CACHE_VERSION = "CACHE_VERSION";

    private static final String TAG = "CacheManager";

    private static File cacheDir;

    private CacheManager() {
    }

    private static void deleteOldCacheDir(final File oldCacheDir) {
//        new MyAsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                deleteFile(oldCacheDir);
//                return null;
//            }
//        }.execute();
    }

    public static String md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuilder strBuf = new StringBuilder();
            for (byte b : encryption) {
                if (Integer.toHexString(0xff & b).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & b));
                } else {
                    strBuf.append(Integer.toHexString(0xff & b));
                }
            }
            return strBuf.toString();
        } catch (Exception e) {
            Logger.e(TAG, "makeKey# error=", e);
            return "";
        }
    }

    /**
     * 存储
     *
     * @param key  唯一key
     * @param data data
     */
    public static void save(String key, Object data) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        String md5 = md5(key);
        Logger.i(TAG, "save# key=" + key + ", md5=" + md5);
        serialization(md5, data);
    }

    /**
     * 读取
     *
     * @param key 唯一key
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String md5 = md5(key);
        Logger.i(TAG, "get# key=" + key + ", md5=" + md5);
        return (T) deserialization(md5);
    }

    /**
     * 删除
     *
     * @param key 唯一key
     */
    public static void delete(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        String md5 = md5(key);
        Logger.i(TAG, "delete# key=" + key + ", md5=" + md5);
        File file = new File(cacheDir, md5);
        deleteFile(file);
    }


    /**
     * serialize to file
     *
     * @param key 文件名
     * @param obj Object
     */
    private static void serialization(String key, Object obj) {
        File file = new File(cacheDir, key);
        if (file.exists()) {
            deleteFile(file);
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(obj);
        } catch (Throwable e) {
            deleteFile(file);
            Logger.e(TAG, "serialization#", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Throwable e) {
                    Logger.e(TAG, "serialization#", e);
                }
            }
        }
    }

    /**
     * deserialization from file
     *
     * @param key 文件名
     * @return Object
     */
    private static Object deserialization(String key) {
        File file = new File(cacheDir, key);
        if (!file.exists()) {
            return null;
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            return in.readObject();
        } catch (Throwable e) {
            deleteFile(file);
            Logger.e(TAG, "deserialization#", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable e) {
                    Logger.e(TAG, "deserialization#", e);
                }
            }
        }
        return null;
    }

    /**
     * delete file
     *
     * @param file file
     * @return true if delete success
     */
    private static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
        }
        return file.delete();
    }
}
