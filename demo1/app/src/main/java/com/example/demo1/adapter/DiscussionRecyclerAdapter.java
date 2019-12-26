package com.example.demo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.Discussion;

import java.util.List;

public class DiscussionRecyclerAdapter extends RecyclerView.Adapter<DiscussionRecyclerAdapter.ViewHolder> {

    private List<Discussion> mDiscussionList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, posterNameText, contentText, postDateText, countText;

        public ViewHolder(@NonNull View view) {
            super(view);
            titleText = (TextView)view.findViewById(R.id.discussion_recycler_item_title);
            posterNameText = (TextView)view.findViewById(R.id.discussion_recycler_item_poster_name);
            contentText = (TextView)view.findViewById(R.id.discussion_recycler_item_content);
            postDateText = (TextView)view.findViewById(R.id.discussion_recycler_item_post_date);
            contentText = (TextView)view.findViewById(R.id.discussion_recycler_item_count);
        }
    }

    public DiscussionRecyclerAdapter(List<Discussion> discussionList) {
        mDiscussionList = discussionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discussion discussion = mDiscussionList.get(position);
        holder.titleText.setText(discussion.getTitle());
        holder.posterNameText.setText(discussion.getPosterName());
        holder.contentText.setText(discussion.getContent());
        holder.postDateText.setText(discussion.getPostTime());
        holder.contentText.setText(Integer.toString(discussion.getReplyCount()));
    }

    @Override
    public int getItemCount() {
        return mDiscussionList.size();
    }
}
