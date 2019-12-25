package com.example.demo.domain;

public class Question {
	private String id;
	private String content;
	private String answer;
	private String testId;
	private String score;
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Question(String id, String content, String answer, String testId, String score) {
		super();
		this.id = id;
		this.content = content;
		this.answer = answer;
		this.testId = testId;
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", content=" + content + ", answer=" + answer + ", testId=" + testId + "]";
	}
	
	
}
