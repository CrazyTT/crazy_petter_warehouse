package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.PickWaveDetialsBean;

import java.util.ArrayList;

public class RemarkWaveAdapter extends RecyclerView.Adapter<RemarkWaveAdapter.ViewHolder> {
    public ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> datas = new ArrayList<>();
    private Context mContext;

    public RemarkWaveAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public RemarkWaveAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_remark, viewGroup, false);
        return new RemarkWaveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemarkWaveAdapter.ViewHolder viewHolder, final int position) {
        final PickWaveDetialsBean.DataEntity.LotPropertyEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getLotValue());
        viewHolder.two.setText(obj.getLotCode());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setList(ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> mList) {
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<PickWaveDetialsBean.DataEntity.LotPropertyEntity> getList() {
        return datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView one;
        TextView two;

        public ViewHolder(View view) {
            super(view);
            one = (TextView) view.findViewById(R.id.one);
            two = (TextView) view.findViewById(R.id.two);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        }
    }

    public void addItem(PickWaveDetialsBean.DataEntity.LotPropertyEntity content, int postion) {
        datas.add(postion, content);
        notifyItemInserted(postion + 1);
    }

    public void removeItem(int postion) {
        datas.remove(postion);
        notifyItemRemoved(postion + 1);
    }
}