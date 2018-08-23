package com.yzh.myweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test1 {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://192.168.2.176:3306/shengye_crm?useUnicode=true&characterEncoding=utf-8";
		String name = "root";//将要连接数据库的账户
		String password = "mysqlpwd";//将要连接数据库的密码
		Connection connection = DriverManager.getConnection(url,name,password);
		String sql = "SELECT t.user_name,t.real_name from t_user t LIMIT 1,2;";//定义一个要执行的SQL语句
		PreparedStatement ps = connection.prepareStatement(sql);
	    ResultSet row = ps.executeQuery(sql);//执行SQL语句
	    User user = null; //接收数据的实体类
	    List<User> list = new ArrayList<>(); //放实体类的List
	    while(row.next()){
	    	user = new User();
//	    	System.out.println(row.getString("user_name"));
//	    	System.out.println(row.getString("real_name"));
	    	user.setUserName(row.getString("user_name"));
	    	user.setRealName(row.getString("real_name"));
	    	list.add(user);
	    }
	    //看List里面的数据
	    for(User u : list){
	    	System.out.println("userName : " + u.getUserName());
	    	System.out.println("realName : " + u.getRealName());
	    	System.out.println("===========");
	    }
	}
	
	public static class User{
		private String userName;
		private String realName;
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		
	}

}
