//package com.yzh.myweb.config.bak;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//import com.yzh.myweb.filter.CustomAuthenticationEntryPoint;
//import com.yzh.myweb.tokenstore.CustomTokenStoreDelegator;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//	@Autowired
//	private RedisConnectionFactory redisConnection;
//	
//    @Bean(name="tokenStore2")
//    public TokenStore tokenStore() {
//        return new CustomTokenStoreDelegator(new RedisTokenStore(redisConnection));
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//        resources.tokenStore(tokenStore());
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                // Spring Security Oauth2 permitAll() url 去除 Authorization Bearer xxxx
//                /*.apply(permitAllSecurityConfig)
//                .and()*/
//                .authorizeRequests()
//                .antMatchers("/oauth/token/**", "/imgCode","/register").permitAll()
//                .antMatchers("/changePwd").hasAuthority("USER")
//                .anyRequest().authenticated();
//    }
//
//}
