/**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Homework;

/**
 * @author msi-user
 *
 */
@Mapper
public interface HomeworkMapper {

	public void addHomework(Homework homework);

	public void deleteHomework(String id);

	public List<Homework> queryHomework(String id);
	
	public List<Homework> queryHomeworkByCourse(String courseId);

}
