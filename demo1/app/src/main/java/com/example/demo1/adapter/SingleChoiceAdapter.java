package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo1.R;
import com.example.demo1.domain.SingleChoiceQuestion;

import org.w3c.dom.Text;

import java.util.List;

public class SingleChoiceAdapter extends ArrayAdapter<SingleChoiceQuestion> {
    private int resourceId;

    public SingleChoiceAdapter(@NonNull Context context, int textViewResourceId, List<SingleChoiceQuestion> list) {
        super(context, textViewResourceId, list);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SingleChoiceQuestion question = getItem(position);
        View v = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        TextView content = (TextView)v.findViewById(R.id.single_choice_content);
        TextView choiceA = (TextView)v.findViewById(R.id.single_choice_A);
        TextView choiceB = (TextView)v.findViewById(R.id.single_choice_B);
        TextView choiceC = (TextView)v.findViewById(R.id.single_choice_C);
        TextView choiceD = (TextView)v.findViewById(R.id.single_choice_D);
        content.setText((position+1)+ "." + question.getContent());
        choiceA.setText("A." + question.getChoiceA());
        choiceB.setText("B." + question.getChoiceB());
        choiceC.setText("C." + question.getChoiceC());
        choiceD.setText("D." + question.getChoiceD());
        return v;
    }
}
