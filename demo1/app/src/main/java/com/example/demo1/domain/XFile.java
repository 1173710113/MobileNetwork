/**
 * 
 */
package com.example.demo1.domain;

import org.litepal.crud.DataSupport;

/**
 * @author msi-user
 *
 */
public class XFile extends DataSupport {

	private String fileId;
	private String filePath;
	private String fileName;
	private long fileSize;
	private String posterId;
	private String posterName;
	private String courseId;
	private String postTime;

	/**
	 * @param fileId
	 * @param filePath
	 * @param fileName
	 * @param fileSize
	 * @param posterId
	 * @param posterName
	 * @param courseId
	 * @param postTime
	 */
	public XFile(String fileId, String filePath, String fileName, long fileSize, String posterId, String posterName,
			String courseId, String postTime) {
		super();
		this.fileId = fileId;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.posterId = posterId;
		this.posterName = posterName;
		this.courseId = courseId;
		this.postTime = postTime;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @return the posterId
	 */
	public String getPosterId() {
		return posterId;
	}

	/**
	 * @return the posterName
	 */
	public String getPosterName() {
		return posterName;
	}

	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * @return the postTime
	 */
	public String getPostTime() {
		return postTime;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @param posterId the posterId to set
	 */
	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	/**
	 * @param posterName the posterName to set
	 */
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * @param postTime the postTime to set
	 */
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

}
