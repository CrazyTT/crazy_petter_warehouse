package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.PickWaveBean;

import java.util.ArrayList;

public class PickWaveAdapter extends RecyclerView.Adapter<PickWaveAdapter.ViewHolder> {
    public ArrayList<PickWaveBean.DataEntity> datas = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;

    public PickWaveAdapter(Context context, PickWaveAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
    }


    @Override
    public PickWaveAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_scan_storeage_five, viewGroup, false);
        return new PickWaveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PickWaveAdapter.ViewHolder viewHolder, final int position) {
        final PickWaveBean.DataEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getWaveDocId());
        viewHolder.two.setText(obj.getStatusDesc());//状态还不确定
        viewHolder.three.setText(obj.getStatus() + "");
        viewHolder.four.setText(obj.getTotalCount() + "");
        viewHolder.five.setText(obj.getTotalCount() + "");
        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderTodoAdapterCallBack.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setList(ArrayList<PickWaveBean.DataEntity> mList) {
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<PickWaveBean.DataEntity> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<PickWaveBean.DataEntity> getList() {
        return datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView one;
        TextView two;
        TextView three;
        TextView four;
        TextView five;

        public ViewHolder(View view) {
            super(view);
            one = (TextView) view.findViewById(R.id.one);
            two = (TextView) view.findViewById(R.id.two);
            three = (TextView) view.findViewById(R.id.three);
            four = (TextView) view.findViewById(R.id.four);
            five = (TextView) view.findViewById(R.id.five);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        }
    }

    public void addItem(PickWaveBean.DataEntity content, int postion) {
        datas.add(postion, content);
        notifyItemInserted(postion + 1);
    }

    public void removeItem(int postion) {
        datas.remove(postion);
        notifyItemRemoved(postion + 1);
    }

    public interface OrderTodoAdapterCallBack {
        void click(int postion);
    }
}