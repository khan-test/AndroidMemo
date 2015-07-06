package com.memo.studygroup.app.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.memo.studygroup.app.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by KHAN on 2015-07-06.
 */
public abstract
class BaseListAdapter<T, V extends AbsListItemView<T>> extends BaseAdapter {

    private Context context;
    private List<T> items;

    public BaseListAdapter() { }

    public BaseListAdapter(Context context) {
        this.context = context;
    }

    public BaseListAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return getItems().size();
    }

    @Override
    public T getItem(int position) {
        if (position > -1 && getItems().size() > position) {
            return getItems().get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getItems() {
        if (items == null) {
            items = new ArrayList<T>();
        }
        return items;
    }

    public void clearItems() {
        getItems().clear();
    }

    public void addItem(T item) {
        addItem(-1, item);
    }

    public void addItem(int index, T item) {
        if (item != null) {
            if (getItems().size() > index && index > -1) {
                getItems().add(index, item);
            }
            else {
                getItems().add(item);
            }
        }
    }

    public void addItems(Collection<T> items) {
        addItems(false, -1, items);
    }

    public void addItems(boolean isRefresh, Collection<T> items) {
        addItems(isRefresh, -1, items);
    }

    public void addItems(int index, Collection<T> items) {
        addItems(false, index, items);
    }

    public void addItems(boolean isRefresh, int index, Collection<T> items) {
        if (isRefresh) {
            clearItems();
        }
        if (CollectionUtil.notEmpty(items)) {
            if (getItems().size() > index && index > -1) {
                getItems().addAll(index, items);
            }
            else {
                getItems().addAll(items);
            }
        }
    }

    public boolean replaceItem(T item) {
        if (getItems().contains(item)) {
            return replaceItem(getItems().indexOf(item), item);
        }
        return false;
    }

    public boolean replaceItem(int index, T item) {
        if (index < 0 || getItems().size() < index) {
            return false;
        }

        return getItems().set(index, item) != null;
    }

    public boolean removeItem(T item) {
        return getItems().remove(item);
    }

    public boolean removeItem(int index) {
        if (index < 0 || getItems().size() < index) {
            return false;
        }
        return getItems().remove(index) != null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V view;
        if (convertView == null || !(convertView instanceof AbsListItemView)) {
            view = generateView(position, parent);
        }
        else {
            view = (V) convertView;
        }
        T item = getItem(position);
        view.setPosition(position);
        if (item != null) {
            view.setItem(item);
        }

        return view;
    }

    public abstract V generateView(int position, ViewGroup parent);
}
