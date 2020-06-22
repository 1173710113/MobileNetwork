/**
 * 
 */
package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.User;

/**
 * @author msi-user
 *
 */
public interface UserService {

	/**
	 * 
	 * @param id
	 * @param password
	 * @param type
	 * @param name
	 * @param sex
	 * @return
	 */
	public void register(User user);
	
	/**
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public User login(String id, String password);
	
	/**
	 * 
	 * @param password should not be null or '' and should only contain valid character.
	 * @return
	 */
	public void updateUserPassword(String id, String password);
	
	/**
	 * 
	 * @param name should not be null or ''.
	 * @return
	 */
	public void updateUserName(String id, String name);
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String deleteUser(String id);
	
	public User getUserById(String userId);
	
	public List<User> queryStudentByCourse(String courseId);
	
}
