package com.example.demo1.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleChoiceQuestion {
    private String questionId;
    private String content;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String answer;
    private String testId;
    private String score;

    public SingleChoiceQuestion(String questionId, String content, String choiceA, String choiceB, String choiceC, String choiceD, String answer, String testId, String score) {
        this.questionId = questionId;
        this.content = content;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.answer = answer;
        this.testId = testId;
        this.score = score;
    }

    public SingleChoiceQuestion(Question question) {
        this.questionId = question.getId();
        this.answer = question.getAnswer();
        this.testId = question.getTestId();
        this.score = question.getScore();
        String data = question.getContent();
        try {
            JSONObject object = new JSONObject(data);
            System.out.println(data);
            this.content = object.getString("content");
            this.choiceA = object.getString("choiceA");
            this.choiceB = object.getString("choiceB");
            this.choiceC = object.getString("choiceC");
            this.choiceD = object.getString("choiceD");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getQuestionId() {
        return questionId;
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

    public String getAnswer() {
        return answer;
    }

    public String getTestId() {
        return testId;
    }

    public String getScore() {
        return score;
    }
}
