package com.example.demo1.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo1.R;

public class AddQuestionDialog extends Dialog implements View.OnClickListener{
    private EditText contentEdit, choiceAEdit, choiceBEdit, choiceCEdit, choiceDEdit;
    private RadioGroup choiceGroup;
    private TextView cancel, confirm;
    private String answer;
    private IOnCancelListener onCancelListener;
    private IOnConfirmListener onConfirmListener;

    public AddQuestionDialog(@NonNull Context context) {
        super(context);
    }

    public AddQuestionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public String getContent(){
        return contentEdit.getText().toString();
    }

    public String getChoiceA(){
        return choiceAEdit.getText().toString();
    }

    public String getChoiceB(){
        return choiceBEdit.getText().toString();
    }

    public String getChoiceC(){
        return choiceCEdit.getText().toString();
    }

    public String getChoiceD(){
        return choiceDEdit.getText().toString();
    }

    public String getAnswer(){
        return answer;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        contentEdit = (EditText)findViewById(R.id.add_question_content);
        choiceAEdit = (EditText)findViewById(R.id.add_question_choice_a);
        choiceBEdit = (EditText)findViewById(R.id.add_question_choice_b);
        choiceCEdit = (EditText)findViewById(R.id.add_question_choice_c);
        choiceDEdit = (EditText)findViewById(R.id.add_question_choice_d);
        choiceGroup = (RadioGroup)findViewById(R.id.add_question_answer);
        cancel = (TextView)findViewById(R.id.add_question_cancel);
        confirm = (TextView)findViewById(R.id.add_question_confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        choiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.add_question_answer_a:
                        answer = "A";
                        break;
                    case R.id.add_question_answer_b:
                        answer = "B";
                        break;
                    case R.id.add_question_answer_c:
                        answer = "C";
                        break;
                    case R.id.add_question_answer_d:
                        answer = "D";
                        break;
                }
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_question_cancel:
                if(onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                break;
            case R.id.add_question_confirm:
                if(onConfirmListener != null){
                    onConfirmListener.onConfirm();
                }
                break;
        }
    }

    public AddQuestionDialog setOnCancelListener(IOnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return this;
    }

    public AddQuestionDialog setOnConfirmListener(IOnConfirmListener onConfirmListener){
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public interface IOnCancelListener{
        void onCancel();
    }

    public interface IOnConfirmListener{
        void onConfirm();
    }
}
