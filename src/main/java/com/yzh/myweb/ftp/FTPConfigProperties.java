package com.yzh.myweb.ftp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(FTPConfigProperties.FTPCONFIG_PREFIX)
public class FTPConfigProperties {
	
	public static final String FTPCONFIG_PREFIX = "com.web.ftp";
	
	private String ftpHost;
	
	
	private String ftpPort;
	
	
	private String ftpUserName;
	
	
	private String ftpPassword;
	
	
	public FTPConfigProperties(String ftpHost, String ftpPort, String ftpUserName, String ftpPassword) {
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.ftpUserName = ftpUserName;
		this.ftpPassword = ftpPassword;
	}
	
	
	
	public boolean canFTP() {
		 
		return !StringUtils.isEmpty(ftpHost) && !StringUtils.isEmpty(ftpPort)  && !StringUtils.isEmpty(ftpUserName) && !StringUtils.isEmpty(ftpPassword);
		
	}
	
	public FTPConfigProperties() {
		
	}

	
	

}
