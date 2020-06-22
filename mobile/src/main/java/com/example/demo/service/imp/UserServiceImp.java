package com.example.demo.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Service
@Transactional
public class UserServiceImp implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void register(User user){
		// TODO Auto-generated method stub
			userMapper.addUser(user);
	}

	@Override
	public User login(String id, String password) {
		// TODO Auto-generated method stub
		return userMapper.login(id, password);
		
	}

	@Override
	public void updateUserPassword(String id, String password) {
		userMapper.updateUserPassword(id, password);
	}



	@Override
	public String deleteUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return userMapper.queryUser(id);
	}
	
	@Override
	public void updateUserName(String id, String name) {
		userMapper.updateUserName(id, name);
	}

	@Override
	public List<User> queryStudentByCourse(String courseId) {
		return userMapper.queryStudentByCourse(courseId);
	}

}
