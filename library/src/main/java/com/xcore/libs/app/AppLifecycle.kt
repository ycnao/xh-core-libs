package com.xcore.libs.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.xcore.libs.event.AppEvent
import com.xcore.libs.utils.LogUtils
import org.greenrobot.eventbus.EventBus

/**
 * APP 监听
 */
class AppLifecycle : Application.ActivityLifecycleCallbacks {

    private val tag = javaClass.simpleName
    private var mActivityCount = 0
    private var isAppForeground = true

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        LogUtils.d(tag, "onActivityCreated ${activity.javaClass.simpleName}")
    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.d(tag, "onActivityStarted ${activity.javaClass.simpleName}")
        mActivityCount++
        if (mActivityCount > 0 && !isAppForeground) {
            LogUtils.d(tag, "应用回到前台")
            isAppForeground = true
            EventBus.getDefault().post(AppEvent(0))
        }
    }

    override fun onActivityResumed(activity: Activity) {
        LogUtils.d(tag, "onActivityResumed ${activity.javaClass.simpleName}")
    }

    override fun onActivityPaused(activity: Activity) {
        LogUtils.d(tag, "onActivityPaused ${activity.javaClass.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        LogUtils.d(tag, "onActivityStopped ${activity.javaClass.simpleName}")
        mActivityCount--
        if (mActivityCount == 0) {
            isAppForeground = false
            LogUtils.d(tag, "应用退到后台")
            EventBus.getDefault().post(AppEvent(1))
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        LogUtils.d(tag, "onActivitySaveInstanceState ${activity.javaClass.simpleName}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        LogUtils.d(tag, "onActivityDestroyed ${activity.javaClass.simpleName}")
    }

}