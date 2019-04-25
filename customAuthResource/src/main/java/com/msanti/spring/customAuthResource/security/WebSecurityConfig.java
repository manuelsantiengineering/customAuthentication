package com.msanti.spring.customAuthResource.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	// Here we basically declared certain paths to be accessible by all
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	   http
	   	.authorizeRequests()
	   	.antMatchers("/","/customAuth").permitAll()
		.anyRequest().authenticated();
	}    	
}
