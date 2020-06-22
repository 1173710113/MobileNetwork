package com.example.demo.domain;

import lombok.Data;

@Data
public class Question {
	private String questionId;
	private String content;
	private String answer;
	private String testId;
	private String score;

}
