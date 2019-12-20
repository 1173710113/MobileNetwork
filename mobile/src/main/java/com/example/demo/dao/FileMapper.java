/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.XFile;

/**
 * @author msi-user
 *
 */
@Mapper
public interface FileMapper {

	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param fileSize
	 * @param posterId
	 * @param posterName
	 * @param courseId
	 * @param postTime
	 */
	public void addFile(@Param("path") String path, @Param("file_name") String fileName, @Param("size") long fileSize,
			@Param("poster_id") String posterId, @Param("poster_name") String posterName,
			@Param("course_id") String courseId, @Param("post_time") String postTime);

	/**
	 * 
	 * @param path
	 */
	public void deteFile(@Param("path") String path);

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public List<XFile> queryFileByCourse(@Param("course_id") String courseId);

}
