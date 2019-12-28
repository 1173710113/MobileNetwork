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

	
	public void addFile(XFile file);

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
