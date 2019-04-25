package com.msanti.spring.customAuth.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.msanti.spring.customAuth.constants.AuthorizationConstants;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	AuthenticationManager authenticationManager = super.authenticationManagerBean(); 
    	return authenticationManager;
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) 
      throws Exception {
        auth.inMemoryAuthentication()
          .withUser(AuthorizationConstants.IN_MEM_USER)
          .password(new BCryptPasswordEncoder().encode(AuthorizationConstants.IN_MEM_PASS))
          .authorities(AuthorizationConstants.ROLE_USER, AuthorizationConstants.ROLE_ADMIN);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http
         .authorizeRequests()
         .antMatchers("/oauth/authorize","/").permitAll()
         .and()
         .formLogin().loginPage("/login").permitAll();
    }

    @Bean("encoder")
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
   
}
