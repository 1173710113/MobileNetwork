 /**
 * 
 */
package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.User;

/**
 * @author msi-user
 *
 */
@Mapper
public interface UserMapper {
	
	public void addUser(User user);

	public void deleteUser(String id);
	
	public void updateUserName(String id, String name);
	
	public void updateUserPassword(String id, String password);
	
	public User queryUser(String id);
	
	public List<User> queryStudentByCourse(String courseId);
	
	public List<User> queryStudentByTest(String testId);
	/**
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public User login(@Param("account")String account, @Param("password")String password);
	
	
	
	
	
	
	
}
