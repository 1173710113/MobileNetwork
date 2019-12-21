package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.XFile;

/**
 * 
 * @author msi-user
 *
 */
public interface FileService {

	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param fileSize
	 * @param posterId
	 * @param courseId
	 * @param postTime
	 */
	public void addFile(String path, String fileName, long fileSize, String posterId, String courseId, String postTime);

	/**
	 * 
	 * @param path
	 */
	public void deleteFile(String path);

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public List<XFile> queryFileByCourse(String courseId);
}
