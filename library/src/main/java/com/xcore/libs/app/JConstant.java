package com.xcore.libs.app;

import android.os.Environment;

/**
 * 平台app共用一些常量
 * <p>
 * author: Created by 闹闹 on 2018/6/26
 * version: 1.0.0
 */
public class JConstant {

    /**
     * 公司所有app应用文件夹根目录。
     */
    public final static String SAVE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String SAVE_CAMERA_PATH = SAVE_FILE_PATH.concat("/DCIM/Camera/");

    //存储日志
    public static final String SAVE_LOG_PATH = SAVE_FILE_PATH.concat("/log");


    /************** 网络访问相关***************/
    public final static String NET_CODE = "code";
    public final static String NET_DATA = "data";
    public final static String NET_IS_SUCCESS = "isSuccess";
    public final static String NET_DESCRIPTION = "description";

    /**
     * 网络请求接口key
     */
    public static final class Net {
        public final static String DATA = "data";
        public final static String STATUS = "isSuccess";
        public final static String MESSAGE = "description";

        //查询列表相关。
        public final static String NET_TOTAL = "total";          //满足查询条件的总条数
        public final static String NET_PAGE_NO = "pageNo";       //页码
        public final static String NET_PAGE_SIZE = "pageSize";   //每页总条数
        public final static String NET_PAGE_COUNT = "pageCount"; //满足查询条件的总页数
        public final static String NET_DATA_LIST = "dataList";   //列表key

        public final static String NET_ERROR_HINT = "服务异常，稍后再试！";
    }

    public static class PERMISSIONS {
        public static final int ALL = 0;
        public static final int CAMERA = 1;
    }
}
