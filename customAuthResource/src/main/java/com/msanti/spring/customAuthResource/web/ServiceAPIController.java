package com.msanti.spring.customAuthResource.web;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.msanti.spring.customAuthResource.model.SampleUser;;

@RestController
@RequestMapping("/api")
public class ServiceAPIController {
	
	private Logger logger =  LoggerFactory.getLogger(ServiceAPIController.class);

	@RequestMapping("/currentUser")
	public Principal getUser(Principal user) {
		return user;
	}
	
	@RequestMapping("/adminresource")
	@PreAuthorize("hasAuthority('ADMIN') and #oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_ADMIN'))")
	public String adminResource(Principal user) {
		return "{\"id\":\"" + user.getName() + "\",\"content\":\"Hello World from Admin Resource\"}";
	}
	
	@RequestMapping(value="/usergreeting", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAuthority('USER') and #oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
	public String userResource(Principal user) {
		return "{\"id\":\"" + user.getName() + "\",\"content\":\"Hello World from User Greeting\"}";
	}
	
	@PreAuthorize("hasAuthority('USER') or #oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasAuthority('USER'))")
    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    public ResponseEntity<List<SampleUser>> listAllSampleUsers() {
    	logger.info("Listing all users...");
    	SampleUser s1 = new SampleUser();
    	SampleUser s2 = new SampleUser();
    	
    	s1.setFirstName("ListUser01");
    	s1.setLastName("01LastName");
    	
    	s2.setFirstName("ListUser02");
    	s2.setLastName("02LastName");
    	
        List<SampleUser> users = new ArrayList<SampleUser>();
        
        users.add(s1);
        users.add(s2);
        
        return new ResponseEntity<List<SampleUser>>(users, HttpStatus.OK);
    }
  
}
