//package com.yzh.myweb.config.bak;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
//import com.yzh.myweb.service.CustomUserDetailsService;
//import com.yzh.myweb.service.UserService;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//	@Autowired
//	@Qualifier("tokenStore2")
//	private TokenStore tokenStore2;
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private DataSource dataSource;
//
//	@Autowired
//	private UserService userService;
//	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
//		configurer.withClientDetails(new JdbcClientDetailsService(dataSource));
//	}
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints
//				.authenticationManager(authenticationManager)
//				.userDetailsService(new CustomUserDetailsService(userService))
//				.tokenStore(tokenStore2);
//	}
//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.allowFormAuthenticationForClients()
//				.tokenKeyAccess("permitAll()")
//				.checkTokenAccess("isAuthenticated()");
//	}
//}