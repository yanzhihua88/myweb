package com.yzh.myweb.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yzh.myweb.dto.UserDto;
import com.yzh.myweb.dto.UserPwdDto;
import com.yzh.myweb.exception.CommonException;
import com.yzh.myweb.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(description = "用户,权限,角色,部门等接口服务")
public class AuthorizationController extends BaseController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private UserService userService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    /**
     * 获取验证码
     * @return
     */
    @ApiOperation(value = "获取验证码", notes = "查验证码")
    @RequestMapping(value = "/imgCode", method = RequestMethod.POST)
    public Map<String, String> getCaptcha(HttpServletResponse response) throws Exception{
        String capText = defaultKaptcha.createText();
        Map<String, String> map = new HashMap<>();
        try {
            String uuid=UUID.randomUUID().toString().replaceAll("-","");
            redisTemplate.boundValueOps("captchaKey:" + uuid).set(capText,60*50000,TimeUnit.SECONDS);
            map.put("captchaKey", uuid);
            Cookie cookie = new Cookie("captchaCode",uuid);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage bi = defaultKaptcha.createImage(capText);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", out);
        Base64 encoder = new Base64();
        map.put("captcha", capText);
        map.put("img", "data:image/jpeg;base64," + encoder.encodeToString(out.toByteArray()));
        return map;
    }
    
    /**
	 * 用户注册
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "用户注册")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String, Integer> register(@RequestBody @Validated UserDto user) {
		Map<String, Integer> returnMap = new HashMap<>();
		boolean flag = checkCaptcha(user);
		if(flag){
			UserDto customUser =	userService.findByUserLoginName(user.getName());
			if(null==customUser){
				try{
					user.setPwd(passwordEncoder.encode(user.getPwd()));
					int num = userService.insertUser(user);
					returnMap.put("num", num);
				}catch (Exception e) {
					throw new CommonException("注册用户失败");
				}
			}else{
				throw new CommonException("用户名已经存在");
			}
		}
		return returnMap;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@ApiOperation(value = "修改密码", notes = "修改密码")
	@RequestMapping(value = "/rest/changePwd", method = RequestMethod.POST)
	public Map<String, String> changePwd(@RequestBody @Validated UserPwdDto user) {
		Map<String, String> returnMap = new HashMap<>();
		UserDto customUser =	userService.findByUserLoginName(user.getName());
		if(null==customUser){
			throw new CommonException("用户不存在");
		}
		
		String oldPwd = user.getPwd();
		String oldPwdInDB = customUser.getPwd();

		if(passwordEncoder.matches(oldPwd, oldPwdInDB)){
			String newPassword = passwordEncoder.encode(user.getNewPwd());
			userService.updatePassword(getCurrentUserId(), newPassword);
		}else{
			throw new CommonException("原密码错误");
		}
		returnMap.put("msg", "修改成功");
		return returnMap;
	}

	private boolean checkCaptcha(UserDto user){
		
		if(StringUtils.isEmpty(user.getCaptcha()) || StringUtils.isEmpty(user.getCaptchaKey())){
			throw new CommonException("captcha和captchaKey都不能为空");
		}
		
		Object code = redisTemplate.boundValueOps("captchaKey:" + user.getCaptchaKey()).get();

		if (code == null) {
			throw new CommonException("验证码已过期");
		}
		if (!user.getCaptcha().equals(code)) {
			throw new CommonException("验证码错误");
		}
		return true;
	}

}