package com.msanti.spring.customAuth.config;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.msanti.spring.customAuth.constants.AuthorizationConstants;

@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationConfig extends AuthorizationServerConfigurerAdapter{
	
//	private Logger logger =  LoggerFactory.getLogger(CustomAuthorizationConfig.class);
	
	@Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
 
	// The very first step in this configuration is to define a client, 
	// which basically talks to an authorization server to get an access token.
	// The type of client that we have used here is in-memory, which is appropriate for developmental purposes.
	// We can associate it with the user account hold in Google, 
	// which is being asked when we initiate authorization with Google
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) 
      throws Exception {
        clients.inMemory()
          .withClient(AuthorizationConstants.CLIENT_ID)
          .authorizedGrantTypes(AuthorizationConstants.GRANT_TYPE)
          .scopes(AuthorizationConstants.SCOPES)
          .secret(passwordEncoder.encode(AuthorizationConstants.SECRET))
          .redirectUris(AuthorizationConstants.REDIRECT_URI)
          .resourceIds(AuthorizationConstants.RESOURCE_ID);
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(AuthorizationConstants.TOKEN_KEY);
        return converter;
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
 
    // This method is a glue point to group together the things that we have configured so far.
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
          .authenticationManager(authenticationManager)
          .tokenServices(tokenServices())
          .tokenStore(tokenStore())
          .accessTokenConverter(accessTokenConverter());
    }
    
    @Bean("resourceServerTokenServices")
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        // Since the grant type is implicit, the refresh token is not allowed
        defaultTokenServices.setSupportRefreshToken(false);
        defaultTokenServices.setAccessTokenValiditySeconds(AuthorizationConstants.TOKEN_TIMEOUT_SEC);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        return defaultTokenServices;
    }

}
