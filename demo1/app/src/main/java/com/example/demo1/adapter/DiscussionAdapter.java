package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.demo1.R;
import com.example.demo1.domain.Discussion;

import java.util.List;

public class DiscussionAdapter extends ArrayAdapter<Discussion> {
    private int resourceId;

    public DiscussionAdapter(Context context, int textViewResourceId, List<Discussion> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Discussion discussion = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        TextView discussionTitle = (TextView)view.findViewById(R.id.discussion_title);
        TextView discussionPostDate = (TextView)view.findViewById(R.id.discussion_post_date);
        TextView discussionPosterId = (TextView)view.findViewById(R.id.discussion_poster_id);
        TextView discussionPosterName = (TextView)view.findViewById(R.id.discussion_poster_name);
        TextView discussionReplyCount = (TextView)view.findViewById(R.id.discussion_reply_count);
        discussionTitle.setText(discussion.getTitle());
        discussionPostDate.setText(discussion.getPostTime());
        discussionPosterId.setText(discussion.getPosterId());
        discussionPosterName.setText(discussion.getPosterName());
        discussionReplyCount.setText(Integer.toString(discussion.getReplyCount()));
        return view;
    }
}
