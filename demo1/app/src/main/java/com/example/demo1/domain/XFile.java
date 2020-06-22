/**
 * 
 */
package com.example.demo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.litepal.crud.DataSupport;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author msi-user
 *
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class XFile extends DataSupport {

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
