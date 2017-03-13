package com.crazy.petter.warehouse.app.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 16/8/31.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    public ArrayList<String> datas = new ArrayList<>();
    AdapterCallBack mAdapterCallBack;
    int postion2 = 0;

    public ItemAdapter(ArrayList<String> datas, AdapterCallBack adapterCallBack, int postion3) {
        this.datas = datas;
        this.mAdapterCallBack = adapterCallBack;
        postion2 = postion3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String obj = datas.get(position);
        holder.mTextView.setText(obj);
        final ViewGroup.LayoutParams lp = holder.mTextView.getLayoutParams();
        lp.width = 120;
        holder.mTextView.setLayoutParams(lp);
        holder.mTextView.setPadding(3,0,3,0);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallBack.click(postion2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.txt_title);
        }
    }

    public interface AdapterCallBack {
        void click(int postion);
    }
}