package com.xcore.libs.data;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 应用程序全局配置类
 * author: Created by 闹闹 on 2018/6/26
 * version: 1.0.0
 */
public class DataUtils {

    private Context mContext;

    public DataUtils(Context context) {
        mContext = context;
    }

    // 缓存失效时间
    private static final int CACHE_TIME = 60 * 60000;

    /*
     * 下面实现的app的缓存机制 。 //使用方法 NewsList news = new NewsList(); news.setXXX();
     * //一系列的set之后 String key =
     * "根据当前读取网络时的id或其他神马规则总之获取一个不重名的key作为文件名保存到data/data/包名/files/下";
     * (BaseApplication)getApplication().saveObject(news ,key ); //读取的时候
     * BaseApplication app = (BaseApplication)getApplication(); NewsList list =
     * (NewsList )app .readObject(key); //一系列get方法获取到缓存的数据
     */

    /**
     * 保存磁盘缓存
     *
     * @param key
     * @param value
     * @throws IOException
     */
    public void setDiskCache(String key, String value) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput("cache_" + key + ".data", Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.flush();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取磁盘缓存数据
     *
     * @param key 不能包含分割符
     * @throws IOException
     */
    public String getDiskCache(String key) throws IOException {
        FileInputStream fis = null;
        try {
            fis = mContext.openFileInput("cache_" + key + ".data");
            byte[] datas = new byte[fis.available()];
            fis.read(datas);
            return new String(datas);
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     */
    public boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = mContext.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取对象
     *
     * @param file
     */
    public Serializable readObject(String file) {
        if (!isExistDataCache(file)) return null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = mContext.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = mContext.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断缓存数据是否可读
     *
     * @param cacheFile
     */
    public boolean isReadDataCache(String cacheFile) {
        return readObject(cacheFile) != null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cacheFile
     */
    public boolean isExistDataCache(String cacheFile) {
        boolean exist = false;
        File data = mContext.getFileStreamPath(cacheFile);
        if (data.exists()) exist = true;
        return exist;
    }


    /**
     * 判断缓存是否失效
     *
     * @param cacheFile
     */
    public boolean isCacheDataFailure(String cacheFile) {
        File data = mContext.getFileStreamPath(cacheFile);
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME) {
            return true;
        } else if (!data.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 清除app缓存 - 清除数据缓存
     */
    public void clearAppCache() {
        clearCacheFolder(mContext.getFilesDir(), System.currentTimeMillis());
        clearCacheFolder(mContext.getCacheDir(), System.currentTimeMillis());
    }
}

