package com.example.demo.domain;

import lombok.Data;

@Data
public class Score {
	private String scoreId;
	private String testId;
	private String studentId;
	private int score;
	private String everyScore;
}
