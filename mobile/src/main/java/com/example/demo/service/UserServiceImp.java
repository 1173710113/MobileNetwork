package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;

@Service
@Transactional
public class UserServiceImp implements UserService{
	@Autowired
	private UserMapper mapper;
	@Override
	public void register(String id, String password, String type, String name, String sex) {
		// TODO Auto-generated method stub
		mapper.register(id, password, type, name, sex, "12");
	}

	@Override
	public User login(String id, String password) {
		// TODO Auto-generated method stub
		return mapper.login(id, password);
		
	}

	@Override
	public String updateUserPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserSex(String sex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return mapper.getUserById(userId);
	}

}
