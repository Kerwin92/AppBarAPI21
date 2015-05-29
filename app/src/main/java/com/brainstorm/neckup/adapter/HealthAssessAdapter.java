package com.brainstorm.neckup.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.brainstorm.neckup.R;

import java.util.HashMap;
import java.util.List;


public class HealthAssessAdapter extends BaseAdapter {

    private Context context;
    private List<HashMap<String, Object>> listData;
    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();

    public HealthAssessAdapter(Context context,
                               List<HashMap<String, Object>> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        LayoutInflater mInflater = LayoutInflater.from(context);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.checkbox_healthassess, null);
            holder.ckQuestion = (CheckBox) convertView
                    .findViewById(R.id.ck_question);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ckQuestion.setText((String) listData.get(position).get("qname"));
        holder.ckQuestion.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    state.put(position, isChecked);
                    // 点击后，中文加粗
                    TextPaint textPaint = holder.ckQuestion.getPaint();
                    textPaint.setFakeBoldText(true);
                    holder.ckQuestion.setTextColor(Color.BLACK);
                } else {
                    state.remove(position);
                    TextPaint textPaint = holder.ckQuestion.getPaint();
                    textPaint.setFakeBoldText(false);
                    holder.ckQuestion.setTextColor(Color.BLACK);
                }
            }
        });
        holder.ckQuestion.setChecked((state.get(position) == null ? false : true));
        return convertView;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return state;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        HealthAssessAdapter.state = isSelected;
    }

    private static class ViewHolder {
        private CheckBox ckQuestion;
    }
}
