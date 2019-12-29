package com.example.demo1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
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
import com.example.demo1.domain.Score;
import com.example.demo1.domain.SingleChoiceQuestion;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
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
         Map<String, TextView> holderMap;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             content = (TextView)itemView.findViewById(R.id.single_choice_content);
             holderMap = new HashMap<>();
             choiceA = (TextView)itemView.findViewById(R.id.single_choice_A);
             holderMap.put("A", choiceA);
             choiceB = (TextView)itemView.findViewById(R.id.single_choice_B);
             holderMap.put("B", choiceB);
             choiceC = (TextView)itemView.findViewById(R.id.single_choice_C);
             holderMap.put("C", choiceC);
             choiceD = (TextView)itemView.findViewById(R.id.single_choice_D);
             holderMap.put("D", choiceD);
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
                String answer = question.getAnswer();
                holder.holderMap.get(answer).setTextColor(Color.parseColor("#448AFF"));
                break;
            case "学生":
                holder.a.setText("A." + question.getChoiceA());
                holder.b.setText("B." + question.getChoiceB());
                holder.c.setText("C." + question.getChoiceC());
                holder.d.setText("D." + question.getChoiceD());
                String id = holder.itemView.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("id", null);
                if(id != null) {
                    List<Score> result = DataSupport.where("testId = ? and studentId = ?", question.getTestId(), id).find(Score.class);
                    if(result != null && result.size() != 0) {
                        holder.choiceGroup.setVisibility(View.GONE);
                        holder.choiceA.setText("A." + question.getChoiceA());
                        holder.choiceB.setText("B." + question.getChoiceB());
                        holder.choiceC.setText("C." + question.getChoiceC());
                        holder.choiceD.setText("D." + question.getChoiceD());
                        List<String> scores =Arrays.asList(result.get(0).getEveryScore().split("/"));
                        String score = scores.get(position);
                        String first = score.substring(0,1);
                        holder.holderMap.get(question.getAnswer()).setTextColor(Color.parseColor("#448AFF"));
                        if(first.equals("1")){

                        } else {
                            String second = score.substring(1);
                            if(second.equals("0")) {
                                holder.holderMap.get(question.getAnswer()).setTextColor(Color.parseColor("#BDBDBD"));
                            } else {
                                holder.holderMap.get(second).setTextColor(Color.parseColor("#FF0000"));
                            }
                        }
                    } else {
                        holder.choiceLayout.setVisibility(View.GONE);
                    }
                }
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
