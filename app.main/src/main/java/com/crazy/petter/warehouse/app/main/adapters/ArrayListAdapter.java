/* ArrayListAdapter.java
 *
 * (c) COPYRIGHT 2015 Guajii INC. All rights reserved.
 * Guajii CONFIDENTIAL PROPRIETARY
 * Guajii Advanced Technology and Software Operations
 *
 * REVISION HISTORY:
 * Author             Date                   Brief Description
 * -----------------  ----------     ---------------------------------------
 * chenliuliu            2015年4月7日下午1:32:07                init version
 * 
 */
package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class ArrayListAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mList;
    protected Context mContext;

    public ArrayListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);

    public void setList(ArrayList<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public ArrayList<T> getList() {
        return mList;
    }

    public void setList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }
}