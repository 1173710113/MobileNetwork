package com.example.demo1.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionContentSingleChoice {

    private String content;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;

    public QuestionContentSingleChoice(String content, String choiceA, String choiceB, String choiceC, String choiceD) {
        this.content = content;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
    }

    public QuestionContentSingleChoice(SingleChoiceQuestion question) {
        this.content = question.getContent();
        this.choiceA = question.getChoiceA();
        this.choiceB = question.getChoiceB();
        this.choiceC = question.getChoiceC();
        this.choiceD = question.getChoiceD();
    }

    public String getContent() {
        return content;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    @Override
    public String toString(){
        JSONObject object = new JSONObject();
        try {
            object.put("content", this.getContent());
            object.put("choiceA", this.getChoiceA());
            object.put("choiceB", this.getChoiceB());
            object.put("choiceC", this.getChoiceC());
            object.put("choiceD", this.getChoiceD());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
