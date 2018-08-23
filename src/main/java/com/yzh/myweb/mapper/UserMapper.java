package com.yzh.myweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.yzh.myweb.dao.entity.Resource;
import com.yzh.myweb.dto.UserDto;
import com.yzh.myweb.sql.UserDynamicSql;

@Mapper
public interface UserMapper {

	@InsertProvider(method = "insertUser", type = UserDynamicSql.class)
	int insertUser(UserDto user);

	@SelectProvider(method = "findByName", type = UserDynamicSql.class)
	List<UserDto> findByName(String name);
	
	@SelectProvider(method = "select", type = UserDynamicSql.class)
	List<UserDto> select(String sellerCode);

	@DeleteProvider(method = "delete", type = UserDynamicSql.class)
	int delete(int id);

	// 修改密码
	@UpdateProvider(method = "update", type = UserDynamicSql.class)
	int update(int id,String newPwd);

	@SelectProvider(method = "getResources", type = UserDynamicSql.class)
	List<Resource> getResources(int id);
}