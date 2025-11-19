package com.xcore.libs.bitmap;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.xcore.libs.file.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Bitmap Util
 */
public class BitmapUtil {

    public static String saveBitmap(Context context, Bitmap bitmap, String path, String name) {
        String filePath = FileUtil.toFilePath(path);
        String fileName = FileUtil.fileName(name, ".jpg");

//        return viewShot(context, bitmap, name + filename + ".jpg", 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return saveQUp(context, bitmap, filePath, fileName);
        } else {
            return saveQNext(context, bitmap, filePath, fileName);
        }
    }

    // Android Q 以下
    private static String saveQNext(Context context, Bitmap image, String path, String fileName) {
        Log.i("BitmapUtils", "saveQNext: >>> " + path);
        // 创建文件夹
        FileUtil.mkdir(path);
        // 文件名称
        Log.i("BitmapUtils", "saveQNext: " + fileName);
        File file = new File(path, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            // 通过io流的方式来压缩保存图片
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            // 保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Android Q 以上
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String saveQUp(Context context, Bitmap image, String path, String fileName) {
        // 文件夹路径
        String imagePath = path + fileName;
        Log.i("BitmapUtils", "文件夹目录 >>> " + path);
        FileUtil.mkdir(path);
        // 文件名字
        Log.d("BitmapUtils", "文件名字 >>> " + imagePath);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.TITLE, fileName);
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        Uri uri = null;
        OutputStream outputStream = null;
        ContentResolver localContentResolver = context.getContentResolver();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                //该媒体项在存储设备中的相对路径，该媒体项将在其中保留
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, path);
                Uri externalUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                uri = localContentResolver.insert(externalUri, contentValues);
            } else {
                contentValues.put(MediaStore.Images.Media.DATA, path);
                uri = localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }

//            Uri url = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//            Log.e("BitmapUtils", "e:" + url);
//            uri = localContentResolver.insert(url, contentValues);
            outputStream = localContentResolver.openOutputStream(uri);
            // Bitmap图片保存
            // 1、宽高比例压缩
            // 2、压缩参数
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return imagePath;
        } catch (Exception e) {
            e.printStackTrace();
            if (uri != null) {
                localContentResolver.delete(uri, null, null);
            }
        } finally {
            image.recycle();
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 99, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}
