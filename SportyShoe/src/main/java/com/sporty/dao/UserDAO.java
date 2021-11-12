package com.sporty.dao;

import java.util.List;

import com.sporty.pojo.User;

public interface UserDAO {
	
	public List<User> getUsers();
	
	public User getUser(int user);
	
	public void saveUser(User user);
	
	public void deleteUser(int user);

}