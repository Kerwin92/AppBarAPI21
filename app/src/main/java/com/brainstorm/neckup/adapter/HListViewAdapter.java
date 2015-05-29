package com.brainstorm.neckup.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brainstorm.neckup.R;

import java.util.ArrayList;
import java.util.List;

public class HListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<HlvRecordItem> horizontalListViewItems = new ArrayList<>();
    private int selectIndex = -1;
    private HlvRecordItem hlvRecordItem;

    public HListViewAdapter(Context context, List<HlvRecordItem> horizontalListViewItems) {
        this.mContext = context;
        this.horizontalListViewItems = horizontalListViewItems;
    }

    public int getCount() {
        return horizontalListViewItems.size();
    }

    public Object getItem(int position) {
        return horizontalListViewItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.hlistview_record_item, null);
            holder.tvDate = (TextView) convertView
                    .findViewById(R.id.tv_date);
            holder.ivAm = (ImageView) convertView
                    .findViewById(R.id.iv_am);
            holder.ivPm = (ImageView) convertView.findViewById(R.id.iv_pm);
            holder.llytRecord = (LinearLayout) convertView.findViewById(R.id.llyt_record);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectIndex) {
            convertView.setSelected(true);
            holder.llytRecord.setBackgroundColor(mContext.getResources().getColor(R.color.darker_blue));
        } else {
            convertView.setSelected(false);
            holder.llytRecord.setBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
        }
        hlvRecordItem = (HlvRecordItem) getItem(position);
        holder.tvDate.setText(hlvRecordItem.getDate());
        //        iconBitmap = getPropThumnail(mIconIDs[position]);
        if (hlvRecordItem.getAmDone() == 1) {
            holder.ivAm.setImageResource(R.drawable.hlv_done);
        } else {
            holder.ivAm.setImageResource(R.drawable.hlv_pm);
        }
        if (hlvRecordItem.getPmDone() == 1) {
            holder.ivPm.setImageResource(R.drawable.hlv_done);
        } else {
            holder.ivPm.setImageResource(R.drawable.hlv_pm);
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvDate;
        private ImageView ivAm;
        private ImageView ivPm;
        private LinearLayout llytRecord;
    }


    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}