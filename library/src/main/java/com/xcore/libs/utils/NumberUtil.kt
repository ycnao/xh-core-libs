package com.xcore.libs.utils

/**
 * 数字转中文数字
 * author：created by 闹闹 on 2025/10/31
 * version：v1.0.0
 */
object NumberUtil {

    @JvmStatic
    fun convert(number: Int): String {
        //数字对应的汉字
        val num = arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九")
        //单位
        val unit =
            arrayOf("", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", "万亿")
        //将输入数字转换为字符串
        val result = number.toString()
        //将该字符串分割为数组存放
        val ch = result.toCharArray()
        //结果 字符串
        var str = ""
        val length = ch.size
        for (i in 0 until length) {
            val c = ch[i].toInt() - 48
            if (c != 0) {
                str += num[c - 1] + unit[length - i - 1]
            }
        }
        return str
    }
}