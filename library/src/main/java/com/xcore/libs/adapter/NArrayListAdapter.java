package com.xcore.libs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

/**
 * 类描述：数据源为list的适配器。抽象，简化了适配器的创建。
 * 适用item只有一种类型。方便，不用每次建适配器都要写一大堆相同的东西了，哈哈
 * author：created by 闹闹 on 2025/10/30
 * version：v1.0.0
 */
public abstract class NArrayListAdapter<T> extends BaseAdapter {

    /**
     * 数据源
     */
    protected List<T> mData;

    protected Context mContext;

    /**
     * item布局view持有者接口
     */
    public static class BaseViewHolder {
    }

    /**
     * list item 布局资源
     */
    private int mItemLayoutResId;

    public NArrayListAdapter(Context context, List<T> data, int itemLayoutResId) {
        mData = data;
        mContext = context;
        mItemLayoutResId = itemLayoutResId;
    }

    /**
     * 获取item布局view持有者实例。
     */
    public abstract NArrayListAdapter.BaseViewHolder getViewHolder();

    /**
     * 实例化控件
     *
     * @param viewHolder  BaseViewHolder
     * @param convertView View
     * @param position    position
     */
    public abstract void initView(NArrayListAdapter.BaseViewHolder viewHolder, View convertView, int position);

    /**
     * 填充控件数据。
     *
     * @param viewHolder  viewHolder
     * @param convertView View
     * @param position    position
     */
    public abstract void setData(NArrayListAdapter.BaseViewHolder viewHolder, View convertView, int position);

    /**
     * 清空数据源
     */
    public void clean() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 更改数据源，刷新列表数据
     *
     * @param newData List
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void notifyDataSetChanged(List newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    /**
     * 获取其对应的fragment。记得转型回来。
     *
     * @param <T> Class
     * @param t
     */
    public <T> Fragment getItFragmentInstance(Class<T> t) {
        FragmentActivity act = (FragmentActivity) mContext;
        List<Fragment> fragments = act.getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getClass() == t) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mData.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NArrayListAdapter.BaseViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemLayoutResId, null, false);
            viewHolder = getViewHolder();

            // 实例化控件
            initView(viewHolder, convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NArrayListAdapter.BaseViewHolder) convertView.getTag();
        }
        setData(viewHolder, convertView, position);
        return convertView;
    }
}
