package com.example.demo1.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.SingleChoiceQuestion;

import java.util.List;

public class SingleChoiceRecyclerAdapter extends RecyclerView.Adapter<SingleChoiceRecyclerAdapter.ViewHolder>{

    private List<SingleChoiceQuestion> mSingleChoiceList;

    public SingleChoiceRecyclerAdapter(List<SingleChoiceQuestion> list) {
        mSingleChoiceList =list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

         TextView content, choiceA, choiceB, choiceC, choiceD;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             content = (TextView)itemView.findViewById(R.id.single_choice_content);
             choiceA = (TextView)itemView.findViewById(R.id.single_choice_A);
             choiceB = (TextView)itemView.findViewById(R.id.single_choice_B);
             choiceC = (TextView)itemView.findViewById(R.id.single_choice_C);
             choiceD = (TextView)itemView.findViewById(R.id.single_choice_D);
         }
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_choice_item, parent, false);
        SingleChoiceRecyclerAdapter.ViewHolder viewHolder = new SingleChoiceRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SingleChoiceQuestion question = mSingleChoiceList.get(position);
        holder.content.setText((position + 1) + "." + question.getContent());
        holder.choiceA.setText("A." + question.getChoiceA());
        holder.choiceB.setText("B." + question.getChoiceB());
        holder.choiceC.setText("C." + question.getChoiceC());
        holder.choiceD.setText("D." + question.getChoiceD());
        String answer = question.getAnswer();
        switch (answer) {
            case "A" :
                holder.choiceA.setTextColor(Color.parseColor("#448AFF"));
                break;
            case "B":
                holder.choiceB.setTextColor(Color.parseColor("#448AFF"));
                break;
            case "C":
                holder.choiceC.setTextColor(Color.parseColor("#448AFF"));
                break;
            case "D":
                holder.choiceD.setTextColor(Color.parseColor("#448AFF"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSingleChoiceList.size();
    }
}
