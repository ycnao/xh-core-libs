package com.xcore.libs.utils

import android.content.Context
import android.content.pm.PackageManager
import com.xcore.libs.model.VersionInfo

/**
 * 工具类
 * author：created by 闹闹 on 2025/10/27
 * version：v1.0.0
 */
object AppUtils {

    /**
     * 获取版本信息
     */
    @JvmStatic
    fun versionInfo(context: Context): VersionInfo {
        return VersionInfo(versionName(context), versionCode(context))
    }


    /**
     * 获取当前本地apk的版本
     * - 获取软件版本号，对应AndroidManifest.xml下android:versionCode
     */
    @JvmStatic
    fun versionCode(context: Context): Int {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取版本号名称
     */
    @JvmStatic
    fun versionName(context: Context): String {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

}
