package com.xcore.libs.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 拷贝文件到sd卡,Android复制assets文件到SD卡
 * author: created by 闹闹 on 2023/3/1
 * version: v1.0.0
 */
public class CopyAssetsToSd {


    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(100));

    private Context mContext;

    /**
     * assets的文件夹 js
     */

    private String assetDir;

    /**
     * 目标文件夹
     */

    private String dir;

    public CopyAssetsToSd(Context context, String assetDir, String dir) {
        mContext = context;
        this.assetDir = assetDir;
        this.dir = dir;
        new MyAsyncTask().execute();
    }

    /**
     * 监听复制完成
     */

    public interface onCopyListener {

        void success();

    }

    private onCopyListener mOnCopyListener;

    public void setOnCopyListener(onCopyListener onCopyListener) {
        this.mOnCopyListener = onCopyListener;
    }

    public void copyAssets(final String assetDir, final String dir) {

        //下面是线程池的方法

        threadPoolExecutor.execute(new Runnable() {

            @Override

            public void run() {
                String[] files;
                AssetManager assetManager = mContext.getResources().getAssets();
                try {
                    // 获得Assets文件夹下指定文件夹一共有多少文件
                    files = assetManager.list(assetDir);
                } catch (IOException e1) {
                    return;
                }
                final File mWorkingPath = new File(dir);
                // 如果文件路径不存在
                if (!mWorkingPath.exists()) {
                    mWorkingPath.mkdirs();
                }

                for (int i = 0; i < files.length; i++) {
                    int finalI = i;
                    try {

                        // 获得每个文件的名字
                        String fileName = files[finalI];
                        File outFile = new File(mWorkingPath + "/" + fileName);
                        // 判断文件是否存在
                        if (!outFile.exists()) {
                            outFile.createNewFile();
                            FileOutputStream out = new FileOutputStream(outFile);
                            InputStream in = null;
                            if (0 != assetDir.length()) {
                                in = assetManager.open(assetDir + "/" + fileName);
                            } else {
                                in = assetManager.open(fileName);
                            }

                            // Transfer bytes from in to out

                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            in.close();
                            out.close();
                        } else {

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//下面是线程的方法

// new Thread(new Runnable() {

// @Override

// public void run() {

// String[] files;

// AssetManager assetManager = mContext.getResources().getAssets();

// try {

// // 获得Assets一共有几多文件

// files = assetManager.list(assetDir);

// } catch (IOException e1) {

// return;

// }

// final File mWorkingPath = new File(dir);

// // 如果文件路径不存在

// if (!mWorkingPath.exists()) {

// mWorkingPath.mkdirs();

// }

//

// for (int i = 0; i < files.length; i++) {

// int finalI = i;

//

// try {

// // 获得每个文件的名字

// String fileName = files[finalI];

// File outFile = new File(mWorkingPath + "/" + fileName);

// // 判断文件是否存在

// if (!outFile.exists()) {

// outFile.createNewFile();

// FileOutputStream out = new FileOutputStream(outFile);

// InputStream in = null;

// if (0 != assetDir.length())

// in = assetManager.open(assetDir + "/" + fileName);

// else

// in = assetManager.open(fileName);

// // Transfer bytes from in to out

// byte[] buf = new byte[1024];

// int len;

// while ((len = in.read(buf)) > 0) {

// out.write(buf, 0, len);

// }

// in.close();

// out.close();

// } else {

//

//

// }

// } catch (FileNotFoundException e) {

// e.printStackTrace();

// } catch (IOException e) {

// e.printStackTrace();

// }

// }

//

// }

//

// }).start();

    }

    class MyAsyncTask extends AsyncTask {

//onPreExecute用于异步处理前的操作

        //在doInBackground方法中进行异步任务的处理.
        @Override
        protected Object doInBackground(Object[] objects) {
            copyAssets(assetDir, dir);
            return null;
        }

        //onPostExecute用于UI的更新.此方法的参数为doInBackground方法返回的值.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mOnCopyListener != null) {
                //复制完成的监听
                mOnCopyListener.success();
            }
        }
    }
}
