package com.yzh.myweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yzh.myweb.service.ceph.CephFileService;


@Configuration
public class CephServiceConfiguration {
	
	@Value("${spring.profiles.active}")
	private String cephEnv;
	
	@Value("${ceph.projectName}")
	private String cephProjectName;
	
	@Value("${ceph.accessKey}")
	private String accessKey;
	
	@Value("${ceph.secretKey}")
	private String secretKey;
	
	@Value("${ceph.gateway}")
	private String host;

	@Bean(name = "cephFileService")
    public CephFileService getCephFileService() {
        return  new CephFileService(cephEnv,cephProjectName,accessKey,secretKey,host);
    }

}
