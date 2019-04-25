package com.msanti.spring.customAuthResource.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.msanti.spring.customAuthResource.constants.AuthorizationConstants;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
			.tokenStore(tokenStore())
			.resourceId(AuthorizationConstants.RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(AuthorizationConstants.TOKEN_KEY);
        return converter;
    }
 
}
