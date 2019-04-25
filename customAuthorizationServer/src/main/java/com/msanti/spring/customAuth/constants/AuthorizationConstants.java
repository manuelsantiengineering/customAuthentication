package com.msanti.spring.customAuth.constants;

public interface AuthorizationConstants {

	/* Constants specific to Roles  */
	String ROLE_USER 					= "USER";
	String ROLE_ADMIN 					= "ADMIN";
	
	/* In-Memory Client Details  */
	String CLIENT_ID 					= "c1";
	String GRANT_TYPE 					= "implicit";
	String SECRET 						= "123";
	String REDIRECT_URL					= "http://localhost:8080/privatePage";
	String RESOURCE_ID					= "oauth2-server";
	String[] SCOPES 					= {"read", "write", "trust"};
	
	/* In-Memory User Details  */
	String IN_MEM_USER					= "tomatito";
	String IN_MEM_PASS					= "password";
	
	/* Token Details  */
	String TOKEN_KEY					= "123";
	int TOKEN_TIMEOUT_SEC				= 180;
}
