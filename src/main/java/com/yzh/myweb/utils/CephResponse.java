package com.yzh.myweb.utils;

public class CephResponse {
	
	private String cephBucket;
	private String cephKey;
	private String versionId;
	private String fileMd5;
	
	public String getCephBucket() {
		return cephBucket;
	}
	public void setCephBucket(String cephBucket) {
		this.cephBucket = cephBucket;
	}
	public String getCephKey() {
		return cephKey;
	}
	public void setCephKey(String cephKey) {
		this.cephKey = cephKey;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getFileMd5() {
		return fileMd5;
	}
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
	
}
