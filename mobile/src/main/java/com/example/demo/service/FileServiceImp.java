/**
 * 
 */
package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.FileMapper;
import com.example.demo.domain.XFile;

/**
 * @author msi-user
 *
 */
@Service
public class FileServiceImp implements FileService {

	@Autowired
	FileMapper fileMapper;

	@Override
	public void addFile(String path, String fileName, long fileSize, String posterId, String courseId,
			String postTime) {
		fileMapper.addFile(path, fileName, fileSize, posterId, courseId, postTime);

	}

	@Override
	public void deleteFile(String path) {
		fileMapper.deteFile(path);

	}

	@Override
	public List<XFile> queryFileByCourse(String courseId) {
		List<XFile> fileList = new ArrayList<>();
		fileList.addAll(fileMapper.queryFileByCourse(courseId));
		System.out.println(fileList.size());
		return fileList;
	}

}
