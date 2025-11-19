package com.xcore.libs.file;

import android.webkit.MimeTypeMap;


import com.xcore.libs.model.FileType;
import com.xcore.libs.model.MimeType;

import java.io.File;

public class FileUtils {


    public static FileType getFileType(File file) {
        if (file.isFile())
            return FileType.FILE;
        else
            return FileType.DIRECTORY;
    }

    public static MimeType getMime(File file) {
        // 获取文件扩展名
        String extension = getFileExtension(file);
        // 通过扩展名获取MIME类型字符串
        MimeTypeMap mimeMap = MimeTypeMap.getSingleton();
        String mimeType = mimeMap.getMimeTypeFromExtension(extension);
        // 如果无法获取MIME类型，返回UNKNOWN
        if (mimeType == null) {
            return MimeType.UNKNOWN;
        }
        // 根据MIME字符串获取对应的枚举（假设存在该方法）
        return getMimeType(mimeType);
    }

    public static MimeType getMimeType(String type) {
        String[] split = type.split("/");
        String types = split[0];
        if (types.equals("audio")) {
            return MimeType.AUDIO;
        } else if (types.equals("image")) {
            return MimeType.IMAGE;
        } else if (types.equals("video")) {
            return MimeType.VIDEO;
        } else if (types.equals("text")) {
            if (type.endsWith("html")) {
                return MimeType.HTML;
            } else if (type.endsWith("xml")) {
                return MimeType.HTML;
            } else if (type.endsWith("plain")) {
                return MimeType.TEXT;
            } else {
                return MimeType.UNKNOWN;
            }
        } else if (types.equals("application")) {
            if (type.endsWith("pdf")) {
                return MimeType.PDF;
            } else if (type.endsWith("ogg")) {
                return MimeType.AUDIO;
            } else if (type.endsWith("vnd.android.package-archive")) {
                return MimeType.APPLICATION;
            } else {
                return MimeType.UNKNOWN;
            }
        } else {
            return MimeType.UNKNOWN;
        }
    }

    /**
     * 辅助方法：获取文件的扩展名（不包含点）
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            // 提取扩展名并转为小写（MimeTypeMap通常需要小写扩展名）
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
