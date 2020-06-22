package com.example.demo1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class Question {
	private String questionId;
	private String content;
	private String answer;
	private String testId;
	private String score;

}
