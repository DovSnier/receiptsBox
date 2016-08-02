package com.dvsnier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.dvsnier.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractBaseAdapter
 * Created by lizw on 2016/1/19.
 *
 * @version 1.0.0
 * @since JDK 1.7
 */
public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> dataSet = new ArrayList<>();
    //    protected List<T> dataSet = new LinkedList<>();

    public AbstractBaseAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AbstractBaseAdapter(Context context, List<T> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void initializedContainer() {
        if (StringUtils.isNotEmpty(dataSet)) {
            dataSet.clear();
        }
    }

    @Override
    public int getCount() {
        return StringUtils.isNotEmpty(dataSet) ? dataSet.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return StringUtils.isNotEmpty(dataSet) ? dataSet.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<T> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<T> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }
}
