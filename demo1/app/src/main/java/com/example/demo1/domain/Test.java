package com.example.demo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Test extends DataSupport implements Serializable {
	private String testId;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	private String courseId;
	private int testCount;
	private double avgScore;
}
