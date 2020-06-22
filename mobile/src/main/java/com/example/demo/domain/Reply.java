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
public class Reply {

	private String replyId;
	private String discussionId;
	private String posterId;
	private String posterName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date time;
	private String content;
}
