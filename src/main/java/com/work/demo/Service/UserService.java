package com.work.demo.Service;



import java.util.List;

import com.work.demo.model.UserInfo;

public interface UserService {
	public void addUser(UserInfo userinfo);
	
	
	public List<UserInfo> getAllUser();

}
