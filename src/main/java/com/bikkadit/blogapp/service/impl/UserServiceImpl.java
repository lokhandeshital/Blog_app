package com.bikkadit.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.blogapp.exception.ResourceNotFoundException;
import com.bikkadit.blogapp.model.User;
import com.bikkadit.blogapp.payload.UserDto;
import com.bikkadit.blogapp.repository.UserRepository;
import com.bikkadit.blogapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepository;

	/**
	 * @author Shital
	 * @implNote This impl create user details
	 */
	@Override
	public UserDto createUser(UserDto userdto) {

		// Step 1 : Dto to Entity Convert
		log.info("Initiated request for save the user details");
		User newUsers = this.mapper.map(userdto, User.class);

		// Step 2 : Database save this User
		User saveUser = this.userRepository.save(newUsers);

		// Step 3 :Entity to Dto
		log.info("Completed request for save the user details");
		UserDto createUser = this.mapper.map(saveUser, UserDto.class);

		return createUser;
	}

	/**
	 * @implNote This impl update user details
	 */

	@Override
	public UserDto updateUser(UserDto userdto, Long userId) {

		log.info("Initiated request for the update user details with userId:{}", userId);
		User newUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		newUser.setId(userdto.getId());
		newUser.setName(userdto.getName());
		newUser.setEmail(userdto.getEmail());
		newUser.setPassword(userdto.getPassword());
		newUser.setAbout(userdto.getAbout());

		this.userRepository.save(newUser);

		log.info("Complted request for the update user details with userId:{}", userId);
		UserDto updatedUser = this.mapper.map(newUser, UserDto.class);

		return updatedUser;
	}

	/**
	 * @implNote This impl get single user details
	 */

	@Override
	public UserDto getUserById(Long userId) {

		log.info("Initiated request for get single user details with userId:{}", userId);
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		log.info("Completed request for get single user details with userId:{}", userId);
		return mapper.map(user, UserDto.class);
	}

	/**
	 * @implNote This impl get all user details
	 */
	@Override
	public List<UserDto> getAllUser() {

		log.info("Initiated request for get all the user details ");
		List<User> findAll = userRepository.findAll();

		List<UserDto> allList = findAll.stream().map((users) -> mapper.map(users, UserDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for get all the user details");

		return allList;
	}

	/**
	 * @implNote This impl use for delete user
	 */
	@Override
	public void deleteUser(Long userId) {

		log.info("Initiated request for delete user details with userId:{}", userId);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		log.info("Completed request for delete user details with userId:{}", userId);
		userRepository.delete(user);

	}

}
