package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.demo1.R;
import com.example.demo1.domain.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplyAdapter extends ArrayAdapter<Reply> {
    private int resourceId;

    public ReplyAdapter(Context context, int textViewResourceId, List<Reply> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reply reply = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        TextView ReplyContent = (TextView)view.findViewById(R.id.reply_content);
        TextView ReplyPostTime = (TextView)view.findViewById(R.id.reply_post_time);
        TextView ReplyPosterId = (TextView)view.findViewById(R.id.reply_poster_id);
        TextView ReplyPosterName = (TextView)view.findViewById(R.id.reply_poster_name);
        ReplyContent.setText(reply.getContent());
        ReplyPostTime.setText(reply.getTime());
        ReplyPosterId.setText(reply.getPosterId());
        ReplyPosterName.setText(reply.getPosterName());
        return view;
    }

}
