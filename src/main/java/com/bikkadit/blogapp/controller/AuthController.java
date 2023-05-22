package com.bikkadit.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blogapp.exception.ApiException;
import com.bikkadit.blogapp.helper.ApiConstant;
import com.bikkadit.blogapp.payload.JwtAuthRequest;
import com.bikkadit.blogapp.payload.JwtAuthResponse;
import com.bikkadit.blogapp.payload.UserDto;
import com.bikkadit.blogapp.security.JwtTokenHelper;
import com.bikkadit.blogapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = ApiConstant.BASE_URL_AUTH)
@Slf4j
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	/**
	 * @author Shital
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = ApiConstant.LOGIN_URL)
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		log.info("Initiated request for create token");
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		log.info("Completed request for create token");

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details..");
			throw new ApiException("Invalid Username or Password !!!!");
		}
	}

	// Register New User Api

	@PostMapping(value = ApiConstant.REGISTER_NEW_USER)
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userdto) {

		log.info("Initiated request for register new user");
		UserDto registerNewUser = this.userService.registerNewUser(userdto);

		log.info("Completed request for register new user");
		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);
	}

}
