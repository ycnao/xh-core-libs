package com.xcore.libs.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ScrollView;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class BitmapUtils {

    public static boolean isEmptyBitmap(Bitmap bitmap) {
        return bitmap == null || bitmap.isRecycled() || bitmap.getWidth() == 0 || bitmap.getHeight() == 0;
    }


    /**
     * SurfaceView 所需要截图的组件
     *
     * @param surfaceView surfaceView
     * @param callback
     */
    public static void surfaceViewBitmap(SurfaceView surfaceView, Callback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int width = surfaceView.getWidth();
            int height = surfaceView.getHeight();
            final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            PixelCopy.request(surfaceView, bitmap, copyResult -> {
                if (copyResult == PixelCopy.SUCCESS) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // 此处为了flutter层使用，实际纯原生可以不做这个转换
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("bytes", imageInByte);
                    callback.onCallback("success", bitmap);
                    Log.d("jingluo", "success");
                } else {
                    callback.onCallback("failed", null);
                    Log.d("jingluo", "failed");
                }
            }, new Handler(Looper.getMainLooper()));
        }
    }


    /**
     * 当布局文件没有超出屏幕高度时：
     *
     * @param v
     * @return
     */
    public static Bitmap getBitmapByView(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    /**
     * 当布局超出了屏幕的高度，滑动采用Scrollview处理：
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 将YUV420格式数据转换为Bitmap
     */
    public static Bitmap convertYuvToBitmap(ByteBuffer yBuffer, ByteBuffer uBuffer, ByteBuffer vBuffer,
                                            int width, int height, int yStride, int uStride, int vStride) {
        try {
            // 计算YUV数据总大小
            int ySize = yStride * height;
            int uSize = uStride * (height / 2);
            int vSize = vStride * (height / 2);

            // 创建字节数组存储YUV数据
            byte[] yData = new byte[ySize];
            byte[] uData = new byte[uSize];
            byte[] vData = new byte[vSize];

            // 从缓冲区复制数据
            yBuffer.rewind();
            yBuffer.get(yData);
            uBuffer.rewind();
            uBuffer.get(uData);
            vBuffer.rewind();
            vBuffer.get(vData);

            // 转换为NV21格式（Android YuvImage支持的格式）
            byte[] nv21 = new byte[width * height * 3 / 2];
            System.arraycopy(yData, 0, nv21, 0, width * height);

            int uvIndex = width * height;
            for (int i = 0; i < height / 2; i++) {
                for (int j = 0; j < width / 2; j++) {
                    // 复制V分量
                    nv21[uvIndex++] = vData[i * uStride + j];
                    // 复制U分量
                    nv21[uvIndex++] = uData[i * uStride + j];
                }
            }

            // 将NV21数据转换为Bitmap
            YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, width, height), 90, outputStream);
            byte[] jpegData = outputStream.toByteArray();

            return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static interface Callback {
        void onCallback(String status, Bitmap bitmap);
    }
}
