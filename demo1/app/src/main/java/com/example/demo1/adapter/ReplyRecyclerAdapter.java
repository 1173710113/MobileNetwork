package com.example.demo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.Reply;

import java.util.List;

public class ReplyRecyclerAdapter extends RecyclerView.Adapter<ReplyRecyclerAdapter.ViewHolder> {

    private List<Reply> mReplyList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView posterNameText, contentText, postDateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterNameText = (TextView)itemView.findViewById(R.id.reply_recycler_item_poster_name);
            contentText = (TextView)itemView.findViewById(R.id.reply_recycler_item_content);
            postDateText = (TextView)itemView.findViewById(R.id.reply_recycler_item_post_date);
        }
    }

    public ReplyRecyclerAdapter(List<Reply> replyList) {
        mReplyList = replyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reply reply = mReplyList.get(position);
        holder.posterNameText.setText(reply.getPosterName());
        holder.contentText.setText(reply.getContent());
        holder.postDateText.setText(reply.getTime());
    }

    @Override
    public int getItemCount() {
        return mReplyList.size();
    }
}
