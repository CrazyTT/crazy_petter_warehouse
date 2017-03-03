package com.crazy.petter.warehouse.app.main.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.petter.warehouse.app.main.R;
import com.crazy.petter.warehouse.app.main.beans.PickDetialsBean;

public class RemarkAdapter2 extends ArrayListAdapter<PickDetialsBean.DataEntity.LotPropertyEntity> {

    public RemarkAdapter2(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_remark, parent, false);
            vh = new ViewHolder();
            vh.one = (TextView) convertView.findViewById(R.id.one2);
            vh.two = (TextView) convertView.findViewById(R.id.two2);
            vh.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_content);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(mList.get(position).getLotLabel()) && TextUtils.isEmpty(mList.get(position).getLotCode())) {
            vh.one.setText("栏位" + "    :");
        } else if (TextUtils.isEmpty(mList.get(position).getLotLabel())) {
            vh.one.setText(mList.get(position).getLotCode() + "    :");
        } else {
            vh.one.setText(mList.get(position).getLotLabel() + "    :");
        }
        vh.two.setText(mList.get(position).getLotValue());
        return convertView;
    }

    public class ViewHolder {
        LinearLayout mLinearLayout;
        TextView one;
        TextView two;
    }
}