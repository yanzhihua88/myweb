package com.yzh.myweb.service;

import java.util.List;

import com.yzh.myweb.dao.entity.Resource;
import com.yzh.myweb.dto.UserDto;

public interface UserService {


	UserDto findByUserLoginName(String userName);

	int updatePassword(int currentUserId, String newPassword);

	int insertUser(UserDto user);

	List<Resource> getResources(int id);

}