package com.yzh.myweb.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yzh.myweb.dao.entity.Resource;
import com.yzh.myweb.dto.UserDto;

public class CustomUserDetailsService implements UserDetailsService{

	private UserService userService;

    public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    	UserDto userDto = userService.findByUserLoginName(name);
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<Resource> resources = userService.getResources(userDto.getId());
        GrantedAuthority grantedAuthority;
        for(Resource r:resources){
        	grantedAuthority = new SimpleGrantedAuthority(r.getResAuthorizeKey());
        	authorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(userDto.getName(), userDto.getPwd(), authorities);
    }

}
