/**
 * 
 */
package com.example.demo.domain;

/**
 * @author msi-user
 *
 */
public class File {

	private String filePath;
	private String fileName;
	private User poster;
	private String postTime;
	/**
	 * @param filePath
	 * @param fileName
	 * @param poster
	 * @param postTime
	 */
	public File(String filePath, String fileName, User poster, String postTime) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
		this.poster = poster;
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
	 * @return the poster
	 */
	public User getPoster() {
		return poster;
	}
	/**
	 * @return the postTime
	 */
	public String getPostTime() {
		return postTime;
	}
}
