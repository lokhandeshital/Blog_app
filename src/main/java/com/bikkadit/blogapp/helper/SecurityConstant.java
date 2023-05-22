package com.bikkadit.blogapp.helper;

public class SecurityConstant {

	public static final String INVALID_USERNAME_PASSWORD = "Invalid Username Password...";

	public static final String UNAUTHORIZED_MESSAGE = "Access Denied !!!!";

	// For Security

	public static final String SECRET = "jwtTokenKey";

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public static final String HEADER_STRING = "Authorization";

	public static final String TOKEN_PREFIX = "Bearer";

	public static final Integer TOKEN_INDEX = 7;
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
}
