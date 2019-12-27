package com.example.demo1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.SingleChoiceQuestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleChoiceRecyclerAdapter extends RecyclerView.Adapter<SingleChoiceRecyclerAdapter.ViewHolder>{

    private List<SingleChoiceQuestion> mSingleChoiceList;
    private Map<SingleChoiceQuestion, String> map  = new HashMap<>();

    public SingleChoiceRecyclerAdapter(List<SingleChoiceQuestion> list) {
        mSingleChoiceList =list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

         TextView content, choiceA, choiceB, choiceC, choiceD;
         RadioGroup choiceGroup;
         RadioButton a,b,c,d;
         LinearLayout choiceLayout;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             content = (TextView)itemView.findViewById(R.id.single_choice_content);
             choiceA = (TextView)itemView.findViewById(R.id.single_choice_A);
             choiceB = (TextView)itemView.findViewById(R.id.single_choice_B);
             choiceC = (TextView)itemView.findViewById(R.id.single_choice_C);
             choiceD = (TextView)itemView.findViewById(R.id.single_choice_D);
             choiceGroup = (RadioGroup)itemView.findViewById(R.id.single_choice_answer);
             a = (RadioButton)itemView.findViewById(R.id.single_answer_a);
             b = (RadioButton)itemView.findViewById(R.id.single_answer_b);
             c = (RadioButton)itemView.findViewById(R.id.single_answer_c);
             d = (RadioButton)itemView.findViewById(R.id.single_answer_d);
             choiceLayout = (LinearLayout)itemView.findViewById(R.id.single_teacher_view);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SingleChoiceQuestion question = mSingleChoiceList.get(position);
        map.put(question, "00");
        holder.content.setText((position + 1) + "." + question.getContent());
        String type = holder.itemView.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("type", null);
        switch (type) {
            case "教师":
                holder.choiceA.setText("A." + question.getChoiceA());
                holder.choiceB.setText("B." + question.getChoiceB());
                holder.choiceC.setText("C." + question.getChoiceC());
                holder.choiceD.setText("D." + question.getChoiceD());
                holder.choiceGroup.setVisibility(View.GONE);
                break;
            case "学生":
                holder.a.setText("A." + question.getChoiceA());
                holder.b.setText("B." + question.getChoiceB());
                holder.c.setText("C." + question.getChoiceC());
                holder.d.setText("D." + question.getChoiceD());
                holder.choiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.single_answer_a:
                                if(question.getAnswer().equals("A")) {
                                    map.put(question, "1A");
                                } else {
                                    map.put(question, "0A");
                                }
                                break;
                            case R.id.single_answer_b:
                                if(question.getAnswer().equals("B")) {
                                    map.put(question, "1B");
                                } else {
                                    map.put(question, "0B");
                                }
                                break;
                            case R.id.single_answer_c:
                                if(question.getAnswer().equals("C")) {
                                    map.put(question, "1C");
                                } else {
                                    map.put(question, "0C");
                                }
                                break;
                            case R.id.single_answer_d:
                                if(question.getAnswer().equals("D")) {
                                    map.put(question, "1D");
                                } else {
                                    map.put(question, "0D");
                                }
                                break;
                        }
                    }
                });
                holder.choiceLayout.setVisibility(View.GONE);
                break;
        }
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

    public String getScore(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < mSingleChoiceList.size(); i++) {
            str.append(map.get(mSingleChoiceList.get(i)) + "/");
        }
        str.deleteCharAt(str.length()-1);
        return str.toString();
    }
}
