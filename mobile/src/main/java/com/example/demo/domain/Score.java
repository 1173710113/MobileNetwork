package com.example.demo.domain;

public class Score {
	private String id;
	private String testId;
	private String studentId;
	private String score;
	public Score(String id, String testId, String studentId, String score) {
		super();
		this.id = id;
		this.testId = testId;
		this.studentId = studentId;
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
}