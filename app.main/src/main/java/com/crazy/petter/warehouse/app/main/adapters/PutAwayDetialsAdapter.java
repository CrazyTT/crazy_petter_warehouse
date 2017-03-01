package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.GoodsPutAwayBean;

import java.util.ArrayList;
import java.util.HashMap;

public class PutAwayDetialsAdapter extends RecyclerView.Adapter<PutAwayDetialsAdapter.ViewHolder> {
    public ArrayList<GoodsPutAwayBean.DataEntity> datas = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;
    private HashMap<Integer, Boolean> isSelected;

    public PutAwayDetialsAdapter(Context context, PutAwayDetialsAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
        isSelected = new HashMap<>();
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void initIsSelected() {
        isSelected.clear();
    }

    @Override
    public PutAwayDetialsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_put_away_detial, viewGroup, false);
        return new PutAwayDetialsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PutAwayDetialsAdapter.ViewHolder viewHolder, final int position) {
        final GoodsPutAwayBean.DataEntity obj = datas.get(position);
        viewHolder.one.setText(obj.getSkuId() + "");
        viewHolder.two.setText(obj.getWaitPutAwayQty() + "");
        viewHolder.three.setText(obj.getSkuProperty() + "");
        viewHolder.four.setText(obj.getProduceDate() + "");
        viewHolder.mCheckBox.setChecked(false);
        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getIsSelected().put(position, isChecked);
                mOrderTodoAdapterCallBack.change();
            }
        });
        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.mCheckBox.toggle();
                mOrderTodoAdapterCallBack.change();
            }
        });
        if (position == 0 && datas.size() == 1) {
            viewHolder.mLinearLayout.performClick();
        }
        if (position == 0 && getList().size() == 1) {
            viewHolder.mCheckBox.setChecked(true);
        }
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
        CheckBox mCheckBox;

        public ViewHolder(View view) {
            super(view);
            one = (TextView) view.findViewById(R.id.one);
            two = (TextView) view.findViewById(R.id.two);
            three = (TextView) view.findViewById(R.id.three);
            four = (TextView) view.findViewById(R.id.four);
            mCheckBox = (CheckBox) view.findViewById(R.id.cb_check);
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

        void change();
    }
}