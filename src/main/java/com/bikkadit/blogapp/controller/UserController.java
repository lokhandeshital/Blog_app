package com.bikkadit.blogapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blogapp.helper.AppConstant;
import com.bikkadit.blogapp.payload.UserDto;
import com.bikkadit.blogapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @author Shital
	 * @apiNote This Api is used to create user data
	 * @param userdto
	 * @return
	 */

	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto) {
		log.info("Initiated request for create user details");
		UserDto createUser = this.userService.createUser(userdto);
		log.info("Completed request for create user details");
		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);

	}

	/**
	 * @apiNote This Api is used for update user details
	 * @param userdto
	 * @param userid
	 * @return
	 */

	@PutMapping("/user/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto, @PathVariable Long userid) {
		log.info("Initiated request for update user details with userId:{}", userid);
		UserDto updateUser = this.userService.updateUser(userdto, userid);
		log.info("Completed request for update user details with userId:{}", userid);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to get single user
	 * @param userid
	 * @return
	 */

	@GetMapping("/user/{userid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long userid) {
		log.info("Initiated request for get single user with userId:{}", userid);
		UserDto userById = this.userService.getUserById(userid);
		log.info("Completed request for get single user with userId:{}", userid);
		return new ResponseEntity<UserDto>(userById, HttpStatus.FOUND);

	}

	/**
	 * @apiNote This Api is used to get all users
	 * @return
	 */

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUser() {
		log.info("Initiated request to get all user details");
		List<UserDto> allUser = this.userService.getAllUser();
		log.info("Completed request to get all user details");
		return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.FOUND);

	}

	/**
	 * @apiNote This Api is used to delete user details
	 * @param userid
	 * @return
	 */

	// ADMIN can delete the user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userid) {
		log.info("Initiated request for delete user with userId:{}", userid);
		this.userService.deleteUser(userid);
		log.info("Completed request for delete user with userId:{}", userid);
		return new ResponseEntity<String>(AppConstant.USER_DELETE, HttpStatus.OK);

	}

}
