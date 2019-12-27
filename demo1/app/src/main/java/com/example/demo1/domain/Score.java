package com.example.demo1.domain;

import org.litepal.crud.DataSupport;

public class Score extends DataSupport {
	private String scoreId;
	private String testId;
	private String studentId;
	private String score;
	private String everyScore;
	
	public String getEveryScore() {
		return everyScore;
	}
	public void setEveryScore(String everyScore) {
		this.everyScore = everyScore;
	}
	public Score(String id, String testId, String studentId, String score, String everyScore) {
		super();
		this.scoreId = id;
		this.testId = testId;
		this.studentId = studentId;
		this.score = score;
		this.everyScore = everyScore;
	}
	public String getId() {
		return scoreId;
	}
	public void setId(String id) {
		this.scoreId = id;
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
