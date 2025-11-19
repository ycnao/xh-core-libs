package com.xcore.libs.file;

import android.os.Build;
import android.os.Environment;
import android.text.format.DateFormat;

import com.xcore.libs.utils.DateUtils;

import java.io.File;
import java.util.Date;

public class FileUtil {

    //创建
    public static void mkdir(String name) {
        File file = new File(name);
        file.mkdir();
    }


    public static String toFilePath(String imagePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return Environment.DIRECTORY_DCIM + imagePath;
        } else {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String path = externalStoragePublicDirectory.getPath();
            return path + imagePath + "";
        }
    }

    public static String fileName(String name, String format) {
        String filename = DateUtils.dateToString(new Date(), DateUtils.TIME_STRING_NUMBER);
        return name + filename + format + "";
    }

    public static String toSizeInKB(String filePath) {
        File file = new File(filePath);
        long sizeInBytes = file.length();
        long sizeLong = sizeInBytes / 1024;
        double sizeInKB = Double.parseDouble(sizeLong + "");
        return sizeInKB + "KB";
    }

    public static int toProgress(int totalSize, int freeSize) {
        int size = totalSize - freeSize;
        int freeVal = (size / totalSize) * 100;
        return freeVal;
    }

    public static String toSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return size + "B";
        } else {
            size = size / 1024;
        }

        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推

        if (size < 1024) {
            return size + "KB";
        } else {
            size = size / 1024;
        }

        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return size / 100 + "." + size % 100 + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return size / 100 + "." + size % 100 + "GB";
        }
    }

    public static String getVideoPath() {
        long millis = System.currentTimeMillis();
        String time = DateFormat.format("yyyyMMddHHmmss", millis).toString();
        return "video_" + time + ".mp4";
    }
}
