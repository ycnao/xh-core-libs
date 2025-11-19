package com.xcore.libs.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.xcore.libs.event.UpdateTimeEvent
import org.greenrobot.eventbus.EventBus

/**
 *广播自动更新时间
 * author：created by 闹闹 on 2025/10/31
 * version：v1.0.0
 */
class UpdateTimeReceiver : BroadcastReceiver() {

    val TAG = "TimeStateReceiver"

    override fun onReceive(context: Context?, intent: Intent) {
        //onReceive会在广播来的时候自动调用
        val action = intent.action
        Log.d(TAG, "onReceive: *************************收到广播")
        Log.d(TAG, "onReceive: $action")
        when (action) {
            Intent.ACTION_TIME_TICK -> {
                //每一分钟更新时间
                EventBus.getDefault().post(UpdateTimeEvent("GX"))
                Log.d(TAG, "onReceive: 更新时间")
            }

            Intent.ACTION_TIME_CHANGED -> Log.d(TAG, "onReceive: 时间改变")
            else -> {}
        }
    }
}
