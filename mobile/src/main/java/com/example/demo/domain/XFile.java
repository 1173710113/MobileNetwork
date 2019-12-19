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
	private String posterId;
	private String posterName;
	private String postTime;

	/**
	 * @param filePath
	 * @param fileName
	 * @param posterId
	 * @param posterName
	 * @param postTime
	 */
	public XFile(String filePath, String fileName, String posterId, String posterName, String postTime) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
		this.posterId = posterId;
		this.posterName = posterName;
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
	 * @return the postTime
	 */
	public String getPostTime() {
		return postTime;
	}

}
