package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {
	public void addTest(@Param("name") String name,
			@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("courseId") String courseId);
}
