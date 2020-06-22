/**
 * 
 */
package com.example.demo.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author msi-user
 *
 */
@Data
public class Course {

	private String courseId;
	private String name;
	private String teacherId;
	private String teacherName;
	private int maxVol;
	private String destination;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	private int realVol;
	private String code;


}
