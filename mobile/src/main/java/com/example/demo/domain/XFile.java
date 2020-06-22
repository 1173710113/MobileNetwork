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
public class XFile {

	private String fileId;
	private String filePath;
	private String fileName;
	private long fileSize;
	private String posterId;
	private String posterName;
	private String courseId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;

}
