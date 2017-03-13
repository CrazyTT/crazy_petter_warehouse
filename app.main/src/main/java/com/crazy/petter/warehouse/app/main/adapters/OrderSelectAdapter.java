package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

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
    public ArrayList<ArrayList<String>> mListOrder = new ArrayList<>();
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
        final ArrayList<String> obj = mListOrder.get(position);
//        for (int i = 0; i < obj.size(); i++) {
//            TextView temp = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_title, null).findViewById(R.id.txt_title);
//            temp.setText(obj.get(i));
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
//            temp.setPadding(3, 0, 3, 0);
//            temp.setGravity(Gravity.CENTER);
//            viewHolder.mLinearLayout.addView(temp, layoutParams);
//        }
        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getIsSelected().put(position, isChecked);
                mOrderTodoAdapterCallBack.change();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.mHorizontalListView.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(obj, new ItemAdapter.AdapterCallBack() {
            @Override
            public void click(final int postion2) {
                viewHolder.mCheckBox.toggle();
                mOrderTodoAdapterCallBack.change();
            }
        }, position);
        viewHolder.mHorizontalListView.setAdapter(itemAdapter);
        viewHolder.mCheckBox.setChecked(false);
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
        //初始化列表
        for (int i = 0; i < mList.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < titles.size(); j++) {
                if (titles.get(j).getVISIBLE() != null && titles.get(j).getVISIBLE().equalsIgnoreCase("N")) {
                    continue;
                }
                temp.add(JsonUtil.getString(mList.get(i), titles.get(j).getFIELD_NAME()));
            }
            mListOrder.add(temp);
        }
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
        RecyclerView mHorizontalListView;

        public ViewHolder(View view) {
            super(view);
            mCheckBox = (CheckBox) view.findViewById(R.id.cb_check);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_content);
            mHorizontalListView = (RecyclerView) view.findViewById(R.id.list_item);
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