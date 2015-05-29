package com.brainstorm.neckup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brainstorm.neckup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerwin on 15-5-19.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<HlvRecordItem> hlvRecordItems = new ArrayList<>();
    private HlvRecordItem hlvRecordItem;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, List<HlvRecordItem> hlvRecordItems) {
        this.context = context;
        this.hlvRecordItems = hlvRecordItems;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return hlvRecordItems.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // TODO Auto-generated method stub
        hlvRecordItem = hlvRecordItems.get(position);
        viewHolder.tvDate.setText(hlvRecordItems.get(position).getDate());
        if (hlvRecordItem.getAmDone() == 1) {
            viewHolder.ivAm.setImageResource(R.drawable.hlv_done);
        } else {
            viewHolder.ivAm.setImageResource(R.drawable.hlv_pm);
        }
        if (hlvRecordItem.getPmDone() == 1) {
            viewHolder.ivPm.setImageResource(R.drawable.hlv_done);
        } else {
            viewHolder.ivPm.setImageResource(R.drawable.hlv_pm);
        }
       /* viewHolder.view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewHolder.llytRecord.setBackgroundColor(context.getResources().getColor(R.color.darker_blue));
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.hlistview_record_item, null);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvDate;
        private ImageView ivAm;
        private ImageView ivPm;
        private LinearLayout llytRecord;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            // TODO Auto-generated constructor stub
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            ivAm = (ImageView) view.findViewById(R.id.iv_am);
            ivPm = (ImageView) view.findViewById(R.id.iv_pm);
            llytRecord = (LinearLayout) view.findViewById(R.id.llyt_record);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ViewHolder", "onClick--> position = " + getPosition());
                    llytRecord.setBackgroundColor(context.getResources().getColor(R.color.darker_blue));

                }
            });
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View parent, int position);

    }

}