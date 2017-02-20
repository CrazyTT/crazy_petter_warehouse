package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;

import java.util.ArrayList;

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    public ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> datas = new ArrayList<>();
    private Context mContext;

    public RemarkAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public RemarkAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_remark, viewGroup, false);
        return new RemarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemarkAdapter.ViewHolder viewHolder, final int position) {
        final PickDetialsBean.DataEntity.LotPropertyEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getLotValue());
        viewHolder.two.setText(obj.getLotCode());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setList(ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> mList) {
        datas = mList;
        notifyDataSetChanged();

    }

    public void addList(ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> mList) {
        datas.addAll(mList);
        notifyDataSetChanged();
    }

    public ArrayList<PickDetialsBean.DataEntity.LotPropertyEntity> getList() {
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

    public void addItem(PickDetialsBean.DataEntity.LotPropertyEntity content, int postion) {
        datas.add(postion, content);
        notifyItemInserted(postion + 1);
    }

    public void removeItem(int postion) {
        datas.remove(postion);
        notifyItemRemoved(postion + 1);
    }
}