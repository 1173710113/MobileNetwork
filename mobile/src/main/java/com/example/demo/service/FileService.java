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
	 * 添加一个文件并返回文件的id
	 * @param file
	 * @return
	 */
	public String addFile(XFile file);

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
