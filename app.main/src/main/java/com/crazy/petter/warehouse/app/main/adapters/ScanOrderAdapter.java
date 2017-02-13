package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.ScanStoreageBean;

import java.util.ArrayList;

public class ScanOrderAdapter extends RecyclerView.Adapter<ScanOrderAdapter.ViewHolder> {
    public ArrayList<ScanStoreageBean.DataEntity> datas = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;

    public ScanOrderAdapter(Context context, ScanOrderAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
    }


    @Override
    public ScanOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_scan_storeage, viewGroup, false);
        return new ScanOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ScanOrderAdapter.ViewHolder viewHolder, final int position) {
        final ScanStoreageBean.DataEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getInboundId());
        viewHolder.two.setText(obj.getExtDocId());
        viewHolder.three.setText(obj.getOwnerName());
        viewHolder.four.setText(obj.getOrderDate());
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

    public void setList(ArrayList<ScanStoreageBean.DataEntity> mList) {
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<ScanStoreageBean.DataEntity> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<ScanStoreageBean.DataEntity> getList() {
        return datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView one;
        TextView two;
        TextView three;
        TextView four;

        public ViewHolder(View view) {
            super(view);
            one = (TextView) view.findViewById(R.id.one);
            two = (TextView) view.findViewById(R.id.two);
            three = (TextView) view.findViewById(R.id.three);
            four = (TextView) view.findViewById(R.id.four);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        }
    }

    public void addItem(ScanStoreageBean.DataEntity content, int postion) {
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