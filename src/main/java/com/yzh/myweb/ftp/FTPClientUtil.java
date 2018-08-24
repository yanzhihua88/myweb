package com.yzh.myweb.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FTPClientUtil {

	/**
	 * 根据FTP配置获取FTP客户端
	 * 
	 * @param config
	 * @return
	 * @author david.li 2017年9月27日
	 */
	public static FTPClient getFTPClient(FTPConfigProperties config) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(config.getFtpHost(), Integer.valueOf(config.getFtpPort()));// 连接FTP服务器
			ftpClient.login(config.getFtpUserName(), config.getFtpPassword());// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				log.info("未连接到FTP，用户名或密码错误");
				ftpClient.disconnect();
			} else {
				log.info("FTP连接成功");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e);
		}
		return ftpClient;
	}

}
