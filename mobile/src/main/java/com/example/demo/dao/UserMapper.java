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

	/**
	 * 
	 * @param id should not be null or ''.
	 * @param password should not be null or ''.
	 * @param type should be one of 学生\教师\管理员.
	 * @param name
	 * @param sex should be one of 男\女 or null.
	 * @param iconPath
	 */
	public void register(@Param("id") String id, @Param("password") String password, @Param("type") String type,
			@Param("name") String name, @Param("sex") String sex, @Param("icon_path") String iconPath);

	/**
	 * 
	 * @param id
	 */
	public void deleteUser(@Param("id") String id);

	/**
	 * 
	 * @param id
	 * @param password
	 * @param type
	 * @param name
	 * @param sex
	 * @param iconPath
	 */
	public void updateUser(@Param("id") String id, @Param("password") String password, @Param("type") String type,
			@Param("name") String name, @Param("sex") String sex, @Param("icon_path") String iconPath);

	/**
	 * 
	 * @param id
	 * @param type
	 * @param name
	 * @param sex
	 * @return
	 */
	public List<User> queryUser(@Param("id") String id, @Param("type") String type, @Param("name") String name,
			@Param("sex") String sex);
	/**
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public User login(@Param("account")String account, @Param("password")String password);
}
