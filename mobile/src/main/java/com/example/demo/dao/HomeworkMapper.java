/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Homework;

/**
 * @author msi-user
 *
 */
@Mapper
public interface HomeworkMapper {
	/**
	 * 
	 * @param id
	 * @param poster
	 * @param title
	 * @param content
	 * @param deadline
	 * @param postTime
	 * @param course
	 */
	public void addHomework(@Param("id") String id, @Param("user") String user, @Param("title") String title,
			@Param("content") String content, @Param("deadline") String deadline, @Param("time") String postTime,
			@Param("course") String course);

	/**
	 * 
	 * @param id
	 */
	public void deleteHomework(@Param("id") String id);

	/**
	 * 
	 * @param id
	 * @param title
	 * @param content
	 * @param deadline
	 */
	public void updateCourse(@Param("id") String id, @Param("title") String title, @Param("content") String content,
			@Param("deadline") String deadline);

	/**
	 * 
	 * @param id
	 * @param title
	 * @return
	 */
	public List<Homework> queryHomework(@Param("id") String id, @Param("title") String title);

}
