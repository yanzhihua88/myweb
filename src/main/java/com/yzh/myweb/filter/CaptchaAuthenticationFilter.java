package com.yzh.myweb.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzh.myweb.common.CustomResponseBody;
import com.yzh.myweb.service.UserService;


//filter进行表单提交的验证码的验证
public class CaptchaAuthenticationFilter extends GenericFilterBean {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    //验证码拦截路径
    private static final String CODE_ANT_URL = "/oauth/token";
    private static final String PARAMETER_CAPTCHA_CODE = "captchaCode";
    private static final String PARAMETER_CAPTCHA_KEY = "captchaKey";
    private static final String PARAMETER_USER_NAME = "username";
    private static final String PARAMETER_USER_PASSWORD = "password";
    private boolean postOnly = true;
    private UserService userService;

    //请求路径匹配
    private RequestMatcher requiresAuthenticationRequestMatcher;

    private RedisTemplate redisTemplate;

    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler(CODE_ANT_URL); //设置验证失败重定向路径

    public CaptchaAuthenticationFilter() {
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(CODE_ANT_URL, "POST");
    }

    public CaptchaAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, RedisTemplate redisTemplate, UserService userService) {
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 不是需要过滤的路径，执行下一个过滤器
        if (!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }

        Authentication authResult;
        try {
            authResult = this.attemptAuthentication(request, response);
            if (authResult == null) {
                logger.error("Authentication is null!");
                // return immediately as subclass has indicated that it hasn't completed
                // authentication
                return;
            }

        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            return;
        } catch (AuthenticationException failed) {
            logger.error("Authentication failed.", failed);
            //Authentication failed
            unsuccessfulAuthentication(request, response, failed);
            return;
        }

        //认证成功，执行下个过滤器
        filterChain.doFilter(request, response);
    }

    private Authentication attemptAuthentication(HttpServletRequest request,
                                                 HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        //用户名
        String userName = request.getParameter(PARAMETER_USER_NAME);
        //密码
        String password = request.getParameter(PARAMETER_USER_PASSWORD);
        //获取验证码
        String captcha = request.getParameter(PARAMETER_CAPTCHA_CODE);
        //redis中存储的验证码的key
        String captchaKey = request.getParameter(PARAMETER_CAPTCHA_KEY);

        if (captcha == null) {
            captcha = "";
        }

        captcha = captcha.trim();

        CodeAuthenticationToken authRequest = new CodeAuthenticationToken(captcha, captchaKey, userName, password);

        //这里可以直接省略掉，用provider直接验证
        CodeAuthenticationManager manager = new CodeAuthenticationManager(new CodeAuthenticationProvider(redisTemplate, userService));
        return manager.authenticate(authRequest);
    }

    /**
     * 比较需要过滤的请求路径
     *
     * @param request
     * @param response
     * @return
     */
    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    /**
     * 处理验证码认证失败
     * </ol>
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication request failed: " + failed.toString(), failed);
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
        }
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        CustomResponseBody responseBody = new CustomResponseBody("911", failed.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}