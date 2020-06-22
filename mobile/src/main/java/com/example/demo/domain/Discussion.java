package com.example.demo.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Discussion {

	private String discussionId;
	private String posterId;
	private String posterName;
	private String courseId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;
	private String title;
	private String content;
	private int replyCount;
	
}
