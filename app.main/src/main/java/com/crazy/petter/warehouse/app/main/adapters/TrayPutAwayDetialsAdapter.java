package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.GoodsPutAwayBean;

import java.util.ArrayList;

public class TrayPutAwayDetialsAdapter extends RecyclerView.Adapter<TrayPutAwayDetialsAdapter.ViewHolder> {
    public ArrayList<GoodsPutAwayBean.DataEntity> datas = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;

    public TrayPutAwayDetialsAdapter(Context context, TrayPutAwayDetialsAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
    }

    @Override
    public TrayPutAwayDetialsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tray_put_away_detial, viewGroup, false);
        return new TrayPutAwayDetialsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrayPutAwayDetialsAdapter.ViewHolder viewHolder, final int position) {
        final GoodsPutAwayBean.DataEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getSkuId() + "");
        viewHolder.two.setText(obj.getQty() + "");
        viewHolder.three.setText(obj.getSkuProperty() + "");
        viewHolder.four.setText(obj.getExtLot() + "");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setList(ArrayList<GoodsPutAwayBean.DataEntity> mList) {
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<GoodsPutAwayBean.DataEntity> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<GoodsPutAwayBean.DataEntity> getList() {
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

    public void addItem(GoodsPutAwayBean.DataEntity content, int postion) {
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