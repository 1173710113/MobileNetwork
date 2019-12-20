/**
 * 
 */
package com.example.demo.domain;

/**
 * @author msi-user
 *
 */
public class XFile {

	private String filePath;
	private String fileName;
	private long fileSize;
	private String posterId;
	private String posterName;
	private String courseId;
	private String postTime;

	/**
	 * @param filePath
	 * @param fileName
	 * @param fileSize
	 * @param posterId
	 * @param posterName
	 * @param courseId
	 * @param postTime
	 */
	public XFile(String filePath, String fileName, long fileSize, String posterId, String posterName, String courseId,
			String postTime) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.posterId = posterId;
		this.posterName = posterName;
		this.courseId = courseId;
		this.postTime = postTime;
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

}
