package com.work.demo.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.work.demo.Repository.UserRepository;
import com.work.demo.Service.UserService;
import com.work.demo.model.UserInfo;


@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository UR;
	
	@Override
	public void addUser(UserInfo userinfo) {
		UR.save(userinfo);
		
	}

	@Override
	public List<UserInfo> getAllUser() {
		
		return UR.findAll();
	}

}
