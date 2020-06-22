package com.example.demo1.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.Homework;
import com.example.demo1.util.TimeUtil;

import java.util.List;

public class HomeworkRecyclerAdapter extends RecyclerView.Adapter<HomeworkRecyclerAdapter.ViewHolder>{


    private List<Homework> mHomeworkList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, posterNameText, contentText, deadlineText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.homework_recycler_item_title);
            //posterNameText = (TextView)itemView.findViewById(R.id.homework_recycler_item_poster_name);
            contentText = (TextView)itemView.findViewById(R.id.homework_recycler_item_content);
            deadlineText = (TextView)itemView.findViewById(R.id.homework_recycler_item_deadline);
        }
    }

    public HomeworkRecyclerAdapter(List<Homework> homeworkList) {
        mHomeworkList = homeworkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Homework homework = mHomeworkList.get(position);
        holder.titleText.setText(homework.getTitle());
        //holder.posterNameText.setText(homework.getPosterName());
        holder.contentText.setText(homework.getContent());
        String deadline = TimeUtil.parseTime(homework.getDeadline());
        holder.deadlineText.setText(deadline);
        if(!TimeUtil.isAfter(deadline)) {
            holder.deadlineText.setTextColor(Color.parseColor("#757575"));
        }
    }

    @Override
    public int getItemCount() {
        return mHomeworkList.size();
    }

}
