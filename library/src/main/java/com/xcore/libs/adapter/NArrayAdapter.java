package com.xcore.libs.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * 简单数组适配器。 数据源：ArrayList<String>
 * author：created by 闹闹 on 2025/10/30
 * version：v1.1.0
 */
public abstract class NArrayAdapter extends NAbstractAdapter {

    /**
     * 构造函数
     *
     * @param context
     * @param data
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public NArrayAdapter(Context context, ArrayList<String> data) {
        super(context, (List) data);
    }
}
