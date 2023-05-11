package com.bikkadit.blogapp.service;

import java.util.List;

import com.bikkadit.blogapp.payload.UserDto;

public interface UserService {

	// Create User
	public UserDto createUser(UserDto userdto);

	//Update User
	public UserDto updateUser(UserDto userdto, Long userId);

	//Get User By Id
	public UserDto getUserById(Long userId);

	//Get All User
	public List<UserDto> getAllUser();

	//Delete User
	public void deleteUser(Long userId);
}
