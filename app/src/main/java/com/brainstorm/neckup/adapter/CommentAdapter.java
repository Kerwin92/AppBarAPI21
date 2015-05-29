package com.brainstorm.neckup.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brainstorm.neckup.R;

import java.util.ArrayList;

/**
 * Created by kerwin on 15-5-18.
 */
public class CommentAdapter extends BaseAdapter {
    private final String TAG = "CommentAdapter";
    private ArrayList<CommentItem> commentLists = new ArrayList<>();
    private Context context;

    private CommentItem commentItem;

    public CommentAdapter(ArrayList<CommentItem> commentLists, Context context) {
        this.commentLists = commentLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentLists.size();
    }

    @Override
    public Object getItem(int i) {
        return commentLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolderState viewHolderState = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_comment, null);
            //            commentItem=(CommentItem)getItem(position);
            viewHolderState = new ViewHolderState();
            viewHolderState.ivHead = (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolderState.tvNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            viewHolderState.tvCount = (TextView) convertView.findViewById(R.id.tv_likeCount);
            viewHolderState.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolderState.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
            viewHolderState.ivLike = (ImageView) convertView.findViewById(R.id.iv_like);
            convertView.setTag(viewHolderState);
        } else {
            viewHolderState = (ViewHolderState) convertView.getTag();
        }
        commentItem = commentLists.get(position);
        viewHolderState.ivHead.setImageResource(R.drawable.head);
        viewHolderState.tvComment.setText(commentItem.getTvComment());
        viewHolderState.tvCount.setText(String.valueOf(commentItem.getTvCount()));
        viewHolderState.tvNickname.setText(commentItem.getTvNickname());
        viewHolderState.tvTime.setText(commentItem.getTvTime());
        if (commentItem.getIsShow() == 0) {
            viewHolderState.ivLike.setImageResource(R.drawable.like_unclick);
        } else {
            viewHolderState.ivLike.setImageResource(R.drawable.like_clicked);
        }
        viewHolderState.ivLike.setOnClickListener(new MyClickListener(viewHolderState, position) {
            void click(ViewHolderState viewHolderState, View view, int position) {
                Log.i(TAG, "position0: " + position);
                if (commentItem.getIsShow() == 1) {
                    viewHolderState.ivLike.setImageResource(R.drawable.like_unclick);
                    viewHolderState.tvCount.setText(String.valueOf(new Integer(viewHolderState.tvCount.getText()
                            .toString()) - 1));
                    commentItem.setIsShow(0);
                    Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                } else {
                    viewHolderState.ivLike.setImageResource(R.drawable.like_clicked);
                    viewHolderState.tvCount.setText(String.valueOf(new Integer(viewHolderState.tvCount.getText()
                            .toString()) + 1));
                    commentItem.setIsShow(1);
                    Toast.makeText(context, "点赞", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    abstract class MyClickListener implements View.OnClickListener {
        int position;
        ViewHolderState holder;

        public MyClickListener(ViewHolderState h, int p) {
            position = p;
            holder = h;
        }

        @Override
        public void onClick(View v) {
            click(holder, v, position);
        }

        abstract void click(ViewHolderState h, View v, int p);

    }

    private class ViewHolderState {
        private ImageView ivHead;
        private TextView tvNickname;
        private TextView tvComment;
        private TextView tvTime;
        private TextView tvCount;
        private ImageView ivLike;
    }
}
