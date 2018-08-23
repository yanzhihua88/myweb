//package com.yzh.myweb.config.bak;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import com.yzh.myweb.filter.CaptchaAuthenticationFilter;
//import com.yzh.myweb.filter.CustomAuthenticationEntryPoint;
//import com.yzh.myweb.service.CustomUserDetailsService;
//import com.yzh.myweb.service.UserService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private UserService userService;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//    
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //Ignore, public
//        web.ignoring().antMatchers("/public/**", "/static/**");
//    }
//
//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(new CustomUserDetailsService(userService))
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Autowired
//    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//
//    @Bean
//    public CaptchaAuthenticationFilter captchaAuthenticationFilter(){
//        return new CaptchaAuthenticationFilter(new AntPathRequestMatcher("/oauth/token**"), redisTemplate, userService);
//    }
//    
//	 /**
//     * BCrypt  加密
//     * @return PasswordEncoder
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//            	.requestMatchers().anyRequest()
//            	.and()
//                .authorizeRequests()
//                .antMatchers("/oauth/*","/imgCode","/register").permitAll()
//                .and()
////                .csrf().disable()
//                .exceptionHandling()
//                //出现认证异常,访问异常时通过该入口返回给客户端统一的信息
//               .authenticationEntryPoint(customAuthenticationEntryPoint)
//               .accessDeniedHandler(new OAuth2AccessDeniedHandler());
//        // @formatter:on
//    }
//}