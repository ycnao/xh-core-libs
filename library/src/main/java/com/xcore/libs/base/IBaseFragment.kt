package com.xcore.libs.base

import android.os.Bundle
import android.view.View
import android.app.Activity
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment


/**
 * 碎片顶层抽象
 *
 * @param <A> 持有该fragment的activity类型
 * @param <F> 其具体子类类型
 *
 * author: Created by 闹闹 on 2018-09-12
 * version: 1.0.0
 */
abstract class IBaseFragment<A : IBaseActivity<A>, F : Fragment> : Fragment() {

    val INDEX = "index"

    var mIndex: Int = -1

    lateinit var act: A

    lateinit var instance: F

    /**
     * 标志位，标志已经初始化完成
     */
    var isPrepared: Boolean = false

    /**
     * inflate布局文件 返回的view
     */
    var mView: View? = null

    /**
     * 获取当前碎片索引
     *
     * @return mIndex
     */
    fun getIndex() = mIndex

    /**
     * Fragment当前状态是否可见
     */
    private var isVisible: Boolean? = false

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    var mHasLoadedOnce: Boolean = false

    /**
     * 注入控件后调用。
     *
     * @param savedInstanceState
     */
    abstract fun afterInjectView(savedInstanceState: Bundle?)

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract fun lazyLoad()

    protected abstract fun initLayout(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * 得到初始化参数包中的数据
         */
        if (arguments != null) mIndex = requireArguments().getInt(INDEX)
        super.onCreate(savedInstanceState)
        instance = this as F
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        act = activity as A
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Create, or inflate the Fragment's UI, and return it.
        // If this Fragment has no UI then return null.
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(initLayout(), container, false)

            isPrepared = true
            // 实现懒加载
            lazyLoad()
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        if (mView != null) {
            val parent = mView?.parent as ViewGroup?
            parent?.removeView(mView)
        }

        return mView

//        return inflater.inflate(initLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!mHasLoadedOnce) afterInjectView(savedInstanceState)
    }

    /**
     * 可见时的回调方法
     */
    private fun onVisible() {
        lazyLoad()
    }

    /**
     * 不可见时的回调方法
     */
    private fun onInvisible() {

    }

    /**
     * setUserVisibleHint是在onCreateView之前调用的
     * 设置Fragment可见状态
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        /**
         * 判断是否可见
         */
        if (userVisibleHint) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    protected fun <Q> getAct(q: Class<Q>): Q = activity as Q

    /**
     * intent
     */
    protected fun <K> startActivity(class1: Class<K>) = startActivity(act.intent.setClass(act, class1))

    /**
     * 跳到另外一个activity，注意intent的必须是act.intent
     *
     * @param class1
     */
    protected fun <T> startActivityForResult(class1: Class<T>, requestCode: Int) {
        act.intent.setClass(act, class1)
        startActivityForResult(act.intent, requestCode)
    }
}
