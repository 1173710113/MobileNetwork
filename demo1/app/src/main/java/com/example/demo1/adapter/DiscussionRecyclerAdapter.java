package com.example.demo1.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.DiscussionDetailActivity;
import com.example.demo1.R;
import com.example.demo1.domain.Discussion;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.ValidateUtil;

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
            countText = (TextView)view.findViewById(R.id.discussion_recycler_item_count);
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
        final Discussion discussion = mDiscussionList.get(position);
        holder.titleText.setText(discussion.getTitle());
        holder.posterNameText.setText(discussion.getPosterName());
        String content = discussion.getContent();
        if(ValidateUtil.isEmpty(content)) {
            holder.contentText.setVisibility(View.GONE);
        } else {
            holder.contentText.setText(discussion.getContent());
        }
        holder.postDateText.setText(TimeUtil.parseTime(discussion.getPostTime()));
        holder.countText.setText(Integer.toString(discussion.getReplyCount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DiscussionDetailActivity.class);
                intent.putExtra("discussion", discussion);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiscussionList.size();
    }

}
