package com.xcore.libs.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View

object Utils {

    /**
     * Adapt to 33 partition album permission application
     */
    @JvmStatic
    fun permissions(): Array<String?> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permission31()
        } else {
            permission26()
        }
    }


//    public static String[] permission33() {
//        return new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_MEDIA_IMAGES,
//                Manifest.permission.READ_MEDIA_VIDEO};
//    }

    //    public static String[] permission33() {
    //        return new String[]{
    //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
    //                Manifest.permission.READ_MEDIA_IMAGES,
    //                Manifest.permission.READ_MEDIA_VIDEO};
    //    }
    fun permission26(): Array<String?> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    fun permission31(): Array<String?> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    @JvmStatic
    fun hideNav(activity: Activity) {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    @JvmStatic
    fun getResStr(context: Context, id: Int): String {
        return context.resources.getString(id)
    }

    @JvmStatic
    fun getResList(context: Context, id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }

    /**
     * 获取宽
     *
     * @return
     */
    @JvmStatic
    fun getScreenWidth(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * 获取高
     *
     * @return
     */
    @JvmStatic
    fun getScreenHeight(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
}