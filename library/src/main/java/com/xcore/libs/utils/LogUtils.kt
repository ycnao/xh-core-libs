package com.xcore.libs.utils

import android.util.Log

object LogUtils {

    /**
     * 截断输出日志
     *
     * @param message String
     */
    @JvmStatic
    fun e(tag: String?, message: String?) {
        var msg = message
        if (tag == null || tag.isEmpty() || msg == null || msg.isEmpty()) return

        val segmentSize = 3 * 1024
        val length = msg.length.toLong()
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.e(tag, msg)
        } else {
            while (msg!!.length > segmentSize) {// 循环分段打印日志
                val logContent = msg.substring(0, segmentSize)
                msg = msg.replace(logContent, "")
                Log.e(tag, logContent)
            }
            Log.e(tag, msg)// 打印剩余日志
        }
    }

    //信息太长,分段打印
    @JvmStatic
    fun d(tag: String, msgs: String) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        var msg = msgs
        val max_str_length = 2001 - tag.length
        //大于4000时
        while (msg.length > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length))
            msg = msg.substring(max_str_length)
        }
        //剩余部分
        Log.i(tag, msg)
    }

}