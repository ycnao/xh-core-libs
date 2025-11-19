package com.xcore.libs.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xcore.libs.utils.DateUtil;
import com.xcore.libs.model.FileData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 操作文件的方法
 */
public class JFileUtils {

    /**
     * 根据path获取子文件文件列表
     *
     * @param directoryPath
     * @return
     */
    public static List<FileData> getFileList(String directoryPath) {
        List<FileData> fileDataList = new ArrayList<>();
        File directory = new File(directoryPath);
        // 获取目录下所有文件和目录
        File[] files = directory.listFiles();
        List<File> fileList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                // 这里可以添加过滤条件，例如只获取文件或者特定类型的文件
                fileList.add(file);
            }
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            String name = file.getName();
            String path = file.getPath();
            String[] type = name.split("_");
            long createTime = file.lastModified();
            String size = FileUtil.toSizeInKB(path);
            String time;
            if (type[1].contains(".")) {
                String[] split = type[1].split("\\.");
                long timer = DateUtil.stringToLong(split[0], DateUtil.TIME_STRING_NUMBER);
                time = DateUtil.longToString(timer, DateUtil.DEFAULT_TIME_STRING_CN);
            } else {
                time = DateUtil.longToString(createTime, DateUtil.DEFAULT_TIME_STRING_CN);
            }


            FileData fileData = new FileData(name, path, time, size, type[0], false, FileUtils.getMime(fileList.get(i)));
            fileDataList.add(fileData);
        }

        return fileDataList;
    }

    /**
     * 获取文件最后修改日期
     *
     * @param file
     * @return
     */
    public static String getFileModifiedDate(File file) {
        String formattedDate = "";
        if (file.exists()) {
            long lastModified = file.lastModified();
            Date date = new Date(lastModified);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = sdf.format(date);
            System.out.println("文件最后修改日期: " + formattedDate);
        } else {
            System.out.println("文件不存在");
        }
        return formattedDate;
    }


    /**
     * 修改文件名
     *
     * @param data
     * @param newFileName
     * @return
     */
    public static String renameTo(Context context, FileData data, String newFileName) {
        String filePath = data.getPath();
        String fileName = data.getName();
        String newPath = "";
        if (filePath.contains(fileName)) {
            newPath = filePath.replace(fileName, newFileName);
        } else {
            Toast.makeText(context, "内容不存在", Toast.LENGTH_SHORT).show();
        }
        // 原始文件路径
        File oldFile = new File(data.getPath());
        // 新文件路径
        File newFile = new File(newPath);
        // 尝试重命名文件
        boolean success = oldFile.renameTo(newFile);
        if (success) {
            Toast.makeText(context, "重命名成功", Toast.LENGTH_SHORT).show();
            return newFile.getAbsolutePath();
        } else {
            Toast.makeText(context, "重命名失败，确保有足够的权限和文件系统空间", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public static boolean createFile(File file, boolean isDeleteOldFile) {
        if (file == null) return false;
        if (file.exists()) {
            if (isDeleteOldFile) {
                if (!file.delete()) return false;
            } else {
                return file.isFile();
            }
        }
        if (!createDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean createDir(File file) {
        if (file == null) return false;
        if (file.exists()) {
            return file.isDirectory();
        } else {
            return file.mkdirs();
        }
    }

    /**
     * 新建文件夹
     *
     * @param filePath
     * @param newFileName
     */
    public static void createDirectory(Context context, String filePath, String newFileName) {
//        String filePath = data.getPath();
//        String fileName = data.getName();
        String newPath = "";
        if (!TextUtils.isEmpty(filePath)) {
            newPath = filePath + "/" + newFileName;
        } else {
            Toast.makeText(context, "文件夹不存在", Toast.LENGTH_SHORT).show();
        }
        File dir = new File(newPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                Toast.makeText(context, "文件夹创建成功", Toast.LENGTH_SHORT).show();
                // 成功创建文件夹
            } else {
                Toast.makeText(context, "文件夹创建失败", Toast.LENGTH_SHORT).show();
                // 创建文件夹失败
            }
        }
    }

    /**
     * 复制
     *
     * @param fromFile
     * @param toFile
     */
    public static int copyFile(String fromFile, String toFile) {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists()) {
            return -1;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory()) {
                //如果当前项为子目录 进行递归
                copyFile(currentFiles[i].getPath() + "/", toFile + "/" + currentFiles[i].getName() + "/");
            } else {
                //如果当前项为文件则进行文件拷贝
                CopySdcardFile(currentFiles[i].getPath(), toFile + "/" + currentFiles[i].getName());
            }
        }
        return 0;
    }


    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * 移动
     *
     * @param sourceFile
     * @param destFile
     */
    public static boolean moveFile(File sourceFile, File destFile) {
        if (!sourceFile.exists()) {
            Log.e("JFileUtils", "Source file does not exist");
        }
        if (destFile.exists()) {
            Log.e("JFileUtils", "Destination file already exists");
        }
        // 确保目标文件的父目录存在
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        // 使用renameTo方法移动文件
        if (sourceFile.renameTo(destFile)) {
            Log.e("JFileUtils", "File moved successfully");
            return true;
        } else {
            Log.e("JFileUtils", "Failed to move the file");
            return false;
        }
    }
}