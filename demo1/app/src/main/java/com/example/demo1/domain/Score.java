package com.example.demo1.domain;

import org.litepal.crud.DataSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Score extends DataSupport {
	private String scoreId;
	private String testId;
	private String studentId;
	private int score;
	private String everyScore;
}
