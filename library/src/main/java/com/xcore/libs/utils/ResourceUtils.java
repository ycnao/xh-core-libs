package com.xcore.libs.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 资源获取相关方法
 */
public class ResourceUtils {

    private static Context appContext;

    protected static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    /**
     * 获取颜色
     */
    public static int getColor(int colorRes) {
        return ContextCompat.getColor(appContext, colorRes);
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(appContext, drawableId);
    }

    /**
     * 获取String资源
     */
    public static String getString(int stringId) {
        return appContext.getString(stringId);
    }


    /**
     * 从Assets获取String
     */
    public static String getStringFromAssetsFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = appContext.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

}
