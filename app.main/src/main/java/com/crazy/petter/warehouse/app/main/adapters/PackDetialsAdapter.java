package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.PackDetialsBean;

import java.util.ArrayList;

public class PackDetialsAdapter extends RecyclerView.Adapter<PackDetialsAdapter.ViewHolder> {
    public ArrayList<PackDetialsBean.DataEntity> datas = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;

    public PackDetialsAdapter(Context context, PackDetialsAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
    }


    @Override
    public PackDetialsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_scan_storeage, viewGroup, false);
        return new PackDetialsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PackDetialsAdapter.ViewHolder viewHolder, final int position) {
        final PackDetialsBean.DataEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getSkuId());
        viewHolder.two.setText(obj.getPackageQty() + "");
        viewHolder.three.setText(obj.getSkuName());
        viewHolder.four.setText(obj.getRemark());
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

    public void setList(ArrayList<PackDetialsBean.DataEntity> mList) {
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<PackDetialsBean.DataEntity> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<PackDetialsBean.DataEntity> getList() {
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

    public void addItem(PackDetialsBean.DataEntity content, int postion) {
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