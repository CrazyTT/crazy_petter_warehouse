package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjdv.lib.utils.entity.OrderBean;
import com.bjdv.lib.utils.util.JsonUtil;
import com.crazy.petter.warehouse.app.main.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderSelectAdapter extends RecyclerView.Adapter<OrderSelectAdapter.ViewHolder> {
    public ArrayList<JSONObject> datas = new ArrayList<>();
    public ArrayList<OrderBean.CaptionEntity> titles = new ArrayList<>();
    private Context mContext;
    OrderTodoAdapterCallBack mOrderTodoAdapterCallBack;

    private HashMap<Integer, Boolean> isSelected;

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void initIsSelected() {
        isSelected.clear();
    }

    public OrderSelectAdapter(Context context, OrderSelectAdapter.OrderTodoAdapterCallBack orderTodoAdapterCallBack, ArrayList<OrderBean.CaptionEntity> captionEntities) {
        this.mContext = context;
        this.mOrderTodoAdapterCallBack = orderTodoAdapterCallBack;
        isSelected = new HashMap<>();
        this.titles = captionEntities;
    }


    @Override
    public OrderSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_put_away_detial, viewGroup, false);
        return new OrderSelectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderSelectAdapter.ViewHolder viewHolder, final int position) {
        final JSONObject obj = datas.get(position);
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).getVISIBLE() != null && titles.get(i).getVISIBLE().equalsIgnoreCase("N")) {
                continue;
            }
            TextView temp = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
            temp.setText(JsonUtil.getString(obj, titles.get(i).getFIELD_NAME()));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        CheckBox mCheckBox;


        public ViewHolder(View view) {
            super(view);
            mCheckBox = (CheckBox) view.findViewById(R.id.cb_check);
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

        void change();
    }
}