//package com.yzh.myweb.config.bak;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//import com.yzh.myweb.filter.CustomAuthenticationEntryPoint;
//import com.yzh.myweb.service.CustomUserDetailsService;
//import com.yzh.myweb.service.UserService;
//import com.yzh.myweb.tokenstore.CustomTokenStoreDelegator;
//
//@Configuration
//public class OAuth2ServerConfig {
//
//    private static final String DEMO_RESOURCE_ID = "order";
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
//            resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            // @formatter:off
//            http
//                    // Since we want the protected resources to be accessible in the UI as well we need
//                    // session creation to be allowed (it's disabled by default in 2.0.6)
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .and()
//                    .requestMatchers().anyRequest()
//                    .and()
//                    .anonymous()
//                    .and()
//                    .authorizeRequests()
////                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
//                    .antMatchers("/rest/**").authenticated();//配置order访问控制，必须认证过后才可以访问
//            // @formatter:on
//        }
//    }
//
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        @Autowired
//        AuthenticationManager authenticationManager;
//        
//        @Autowired
//        RedisConnectionFactory redisConnectionFactory;
//        
//        @Autowired
//    	private UserService userService;
//        
//        @Autowired
//    	private DataSource dataSource;
//        
//        @Bean(name="tokenStore2")
//	    public TokenStore tokenStore() {
//	        return new CustomTokenStoreDelegator(new RedisTokenStore(redisConnectionFactory));
//	    }
//        
//        @Override
//        public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
//            //配置两个客户端,一个用于password认证一个用于client认证
//        	configurer.withClientDetails(new JdbcClientDetailsService(dataSource));
//        }
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints
//                    .tokenStore(tokenStore())
//                    .userDetailsService(new CustomUserDetailsService(userService))
//                    .authenticationManager(authenticationManager);
//        }
//
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//            //允许表单认证
//            oauthServer.allowFormAuthenticationForClients();
//        }
//
//    }
//
//}