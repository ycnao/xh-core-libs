package com.xcore.libs.model


/**
 *实体类
 * author: Created by 闹闹 on 2019/1/15
 * version: 1.0.0
 */
data class DataBean(
    val code: Int,
    val data: String,
    val description: String,
    val isSuccess: Boolean
)

data class DataIntBean(
    val code: Int,
    val data: Int,
    val description: String,
    val isSuccess: Boolean
)

data class DataBooleanBean(
    val code: Int,
    val data: Boolean,
    val description: String,
    val isSuccess: Boolean
)

data class VersionInfo(val name: String, val code: Int)




