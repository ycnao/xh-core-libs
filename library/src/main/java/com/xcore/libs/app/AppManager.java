package com.xcore.libs.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;

/**
 * 应用程序Activity管理类
 * <p>
 * author: Created by 闹闹 on 2018/6/26
 * version: 1.0.0
 */
public class AppManager {


    private static AppManager instance;

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    private static Stack<Activity> activityStack;

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.push(activity);
        System.out.println("act管理队列添加界面：" + activity);
    }

    /**
     * 获取管理act列表。
     */
    public Stack<Activity> getList() {
        return activityStack;
    }

    /**
     * 获取当前Activity（栈顶元素）
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 是否已经加载某个activity。加载过返回true，否则返回false。
     *
     * @param act
     */
    public boolean containActivity(Activity act) {
        return activityStack.contains(act);
    }

    /**
     * 移除栈顶的activity
     */
    public void removeCurrentActivity() {
        Activity activity = getCurrentActivity();
        System.out.println("移除栈顶界面：" + activity);
        activityStack.remove(activity);
    }

    /**
     * 获取堆栈中倒数第二个Activity
     */
    public Activity penultimateActivity() {
        Activity activity = activityStack.get(activityStack.size() - 2);
        System.out.println("移除栈顶下面界面：" + activity);
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        int size = activityStack.size();
        System.out.println("act管理队列长度：" + size);
        for (int i = 0; i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.removeAllElements();
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        finishAllActivity();
        System.exit(0);
    }

    /**
     * 判断某个activity是否是当前栈顶activity。
     *
     * @param <T>
     * @param activity
     * @return 是，返回true
     */
    public <T> boolean isTopActivity(Class<T> activity) {
        Activity currentActivity = getCurrentActivity();
        return currentActivity.getClass() == activity;
    }

    /**
     * 从一个Activity跳到另外一个Activity的intent
     *
     * @param currentContext
     * @param toActivityClass
     */
    public <T> Intent intentTo(Context currentContext, Class<T> toActivityClass) {
        Intent i = new Intent(currentContext, toActivityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    /**
     * 从当前Activity跳到另外一个Activity的intent
     *
     * @param toActivityClass
     * @param <T>
     */
    public <T> Intent intentTo(Class<T> toActivityClass) {
        Intent i = new Intent(getCurrentActivity(), toActivityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    /**
     * 从当前的Activity(栈顶的)跳转到另外一个Activity。并结束前一个activity。
     *
     * @param toActivityClass
     * @param <T>
     */
    public <T> void intentToAnOtherActivity(Class<T> toActivityClass) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Activity currentActivity = getCurrentActivity();
        intent.setClass(currentActivity, toActivityClass);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 从当前的Activity(栈顶的)跳转到另外一个Activity。
     * 传递数据过去。并结束前一个activity。
     *
     * @param toActivityClass
     * @param intent
     */
    public <T> void intentToAnOtherActivity(Class<T> toActivityClass, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Activity currentActivity = getCurrentActivity();
        intent.setClass(currentActivity, toActivityClass);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 从当前的Activity(栈顶的)跳转到另外一个Activity。
     * 并不结束前一个activity。
     *
     * @param toActivityClass
     * @param <T>
     */
    public <T> void intentToAnOtherActivity2(Class<T> toActivityClass) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Activity currentActivity = getCurrentActivity();
        intent.setClass(currentActivity, toActivityClass);
        currentActivity.startActivity(intent);
    }

    /**
     * 从当前的Activity(栈顶的)跳转到另外一个Activity。传递数据过去。并不结束前一个activity。
     *
     * @param toActivityClass
     * @param intent          可带有数据的intent
     */
    public <T> void intentToAnOtherActivity2(Class<T> toActivityClass, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Activity currentActivity = getCurrentActivity();
        intent.setClass(currentActivity, toActivityClass);
        currentActivity.startActivity(intent);
    }
}
