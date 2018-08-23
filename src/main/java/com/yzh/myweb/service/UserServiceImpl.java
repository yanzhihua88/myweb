package com.yzh.myweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yzh.myweb.dao.entity.Resource;
import com.yzh.myweb.dto.UserDto;
import com.yzh.myweb.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDto findByUserLoginName(String name) {
		List<UserDto> list = userMapper.findByName(name);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int updatePassword(int currentUserId, String newPassword) {
		return userMapper.update(currentUserId, newPassword);
	}

	@Override
	public int insertUser(UserDto user) {
		return userMapper.insertUser(user);
	}

	@Override
	public List<Resource> getResources(int id) {
		return userMapper.getResources(id);
	}
}