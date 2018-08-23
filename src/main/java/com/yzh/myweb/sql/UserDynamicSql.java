package com.yzh.myweb.sql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.yzh.myweb.dto.UserDto;


public class UserDynamicSql {
    
	public String insertUser(UserDto user){
		return new SQL(){{
			INSERT_INTO(" mgr_user");
			if(!StringUtils.isEmpty(user.getName())){
				VALUES("user_name", "#{name}");
			}
			if(!StringUtils.isEmpty(user.getPwd())){
				VALUES("user_pwd", "#{pwd}");
			}
		}}.toString();
	}
	
	public String findByName(String name){
		return new SQL(){{
			SELECT( " id,user_name name,user_pwd pwd");
			FROM("mgr_user");
			WHERE(String.format("LOWER(user_name) = LOWER('%S')", name));
		}}.toString();
	}
	
	public String getResources(int id){
		return new SQL(){{
			SELECT( " a.* from mgr_resource a LEFT JOIN mgr_user_authorize b on a.id=b.res_id LEFT JOIN mgr_user c on b.user_id=c.id");
			WHERE(String.format("LOWER(c.id) = LOWER('%S')", id));
		}}.toString();
	}
	
	public String select(){
		return new SQL(){{
			
		}}.toString();
	}
	
	public String delete(){
		return new SQL(){{
			
		}}.toString();
	}
	
	public String update(int id,String newPwd){
		return new SQL(){{
			UPDATE("mgr_user");
			SET("user_pwd = '"+newPwd+"'");
			WHERE(String.format("LOWER(id) = LOWER('%S')", id));
		}}.toString();
	}
}
