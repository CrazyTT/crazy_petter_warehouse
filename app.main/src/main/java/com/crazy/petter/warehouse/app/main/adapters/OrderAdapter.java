package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonUtil;
import com.crazy.petter.warehouse.app.main.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    public ArrayList<JSONObject> datas = new ArrayList<>();
    public ArrayList<OrderBean.CaptionEntity> titles = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;

    public OrderAdapter(Context context, OrderAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack, ArrayList<OrderBean.CaptionEntity> captionEntities) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
        this.titles = captionEntities;
    }


    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder viewHolder, final int position) {
        final JSONObject obj = datas.get(position);
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).getVISIBLE() != null && titles.get(i).getVISIBLE().equalsIgnoreCase("N")) {
                continue;
            }
            TextView temp = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setText(JsonUtil.getString(obj, titles.get(i).getFIELD_NAME()));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(210, LinearLayout.LayoutParams.WRAP_CONTENT);
            temp.setPadding(3, 0, 3, 0);
            temp.setGravity(Gravity.CENTER);
            viewHolder.mLinearLayout.addView(temp, layoutParams);
        }
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

    public void setList(ArrayList<JSONObject> mList) {
        if (mList.size() <= 0) {
            titles = new ArrayList<>();
        }
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<JSONObject> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<JSONObject> getList() {
        return datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;

        public ViewHolder(View view) {
            super(view);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        }
    }

    public void addItem(JSONObject content, int postion) {
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