package com.xcore.libs.file;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.File;

public class JVideoUtils {

    public static void convertFileToVideo(File videoFile) {
        // 确保文件存在且是视频文件
        if (videoFile != null && videoFile.exists() && videoFile.isFile()) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                // 设置数据源为该文件路径
                retriever.setDataSource(videoFile.getAbsolutePath());
                // 从这里可以获取视频的各种信息，例如宽度、高度、时长等
                String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                // 打印视频信息
                System.out.println("Width: " + width);
                System.out.println("Height: " + height);
                System.out.println("Duration: " + duration);

                // 在这里可以添加更多的处理逻辑

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 确保释放资源
                retriever.release();
            }
        }
    }

    //获取视频文件的第一帧
    public static Bitmap getFirstFrameFromVideo(File videoFile) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (videoFile.exists() && videoFile != null) {
                retriever.setDataSource(videoFile.getAbsolutePath());
                return retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } finally {
            retriever.release();
        }
        return null;
    }

    //获取视频文件的分辨率
    public static int[] getVideoResolution(String videoPath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int[] videoResolution = new int[2];

        try {
            retriever.setDataSource(videoPath);
            String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);

            if (width != null && height != null) {
                videoResolution[0] = Integer.parseInt(width);
                videoResolution[1] = Integer.parseInt(height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

        return videoResolution;
    }
}