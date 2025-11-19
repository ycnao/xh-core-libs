package com.xcore.libs.base

/**
 *
 * author: Created by 闹闹 on 2018-09-12
 * version: 1.0.0
 */
interface IBasePresenter<in V : IBaseView> {

    fun attachView(view: V)

    fun detachView()
}
