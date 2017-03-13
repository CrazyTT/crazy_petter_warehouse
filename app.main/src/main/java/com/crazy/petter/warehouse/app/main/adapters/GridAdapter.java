package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjdv.lib.utils.entity.GridViewItem;
import com.crazy.petter.warehouse.app.main.R;

/**
 * Created by liuliuchen on 16/8/31.
 */
public class GridAdapter extends ArrayListAdapter<GridViewItem> {
    private LayoutInflater layout;
    private int firstIndex;

    public GridAdapter(Context context, int firstIndex2) {
        super(context);
        this.layout = LayoutInflater.from(context);
        firstIndex = firstIndex2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layout.inflate(R.layout.item_function, null);
            holder.itmeImage = (ImageView) convertView.findViewById(R.id.ItemImage);
            holder.itemText = (TextView) convertView.findViewById(R.id.ItemText);
            holder.itemIndex = (TextView) convertView.findViewById(R.id.ItemIndex);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itmeImage.setImageResource(mList.get(position).getItmeImage());
        holder.itemText.setText(mList.get(position).getItemText());
        holder.itemIndex.setText((position + firstIndex + 1) + ", ");
        return convertView;
    }

    private class ViewHolder {
        private ImageView itmeImage;
        private TextView itemText;
        private TextView itemIndex;
    }

}