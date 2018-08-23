package com.yzh.myweb.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service("fileService")
public class CephFileService implements ICephFileService {
	@Autowired
	private CephStorageService cephStorageService;

	@Value("${ceph.env}")
	private String cephEnv;
	
	@Value("${ceph.projectName}")
	private String cephProjectName;

	private static Logger logger = LoggerFactory.getLogger(CephFileService.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private String getBucketName(String bucketName){
		return  cephProjectName + "" + cephEnv + "" + bucketName;
	}
	@Override
	public Bucket createBucket(String bucketName) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.createBucket(bucketName);
	}

	@Override
	public List<Bucket> getBuckets() throws AmazonS3Exception {
		return cephStorageService.getBuckets();
	}

	@Override
	public boolean isBucketExists(String bucketName) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.isBucketExists(bucketName);
	}

	@Override
	public boolean deleteBucket(String bucketName) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.deleteBucket(bucketName);
	}

	@Override
	public boolean deleteBucketAndFiles(String bucketName) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.deleteBucketAndFiles(bucketName);
	}

	@Override
	public List<String> getFiles(String bucketName) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.getFiles(bucketName);
	}

	@Override
	public boolean isFileExists(String bucketName, String cephKey) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.isFileExists(bucketName, cephKey);
	}

	@Override
	public void deleteFile(String bucketName, String cephKey) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		cephStorageService.deleteFile(bucketName, cephKey);
	}

	@Override
	public S3ObjectInputStream getObject(String bucketName, String cephKey) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.getObject(bucketName, cephKey);
	}

	@Override
	public InputStream getInputStream(String bucketName, String cephKey) throws AmazonS3Exception {
		bucketName = getBucketName(bucketName);
		return cephStorageService.getInputStream(bucketName, cephKey);
	}

	@Override
	public CephResponse uploadInputStream(String bucketName, String cephKey, InputStream is) throws AmazonS3Exception {
		CephResponse response = new CephResponse();
		response.setCephBucket(bucketName);
		bucketName = getBucketName(bucketName);
		if (!cephStorageService.isBucketExists(bucketName)) {
			cephStorageService.createBucket(bucketName);
		}
		PutObjectResult result = cephStorageService.saveInputStream(bucketName, cephKey, is);

		response.setCephKey(cephKey);
		response.setVersionId(result.getVersionId());
		return response;
	}

	@Override
	public CephResponse uploadInputStream(String bucketName, InputStream is) throws AmazonS3Exception, IOException {

		CephResponse response = new CephResponse();
		response.setCephBucket(bucketName);
		bucketName = getBucketName(bucketName);

		if (!cephStorageService.isBucketExists(bucketName)) {
			cephStorageService.createBucket(bucketName);
		}
		// redis自增长+1，从1开始自增
		RedisAtomicLong entityIdCounter = new RedisAtomicLong("cephKey", redisTemplate.getConnectionFactory());
		entityIdCounter.expire(1, TimeUnit.DAYS);
		Long incrementCount = entityIdCounter.getAndIncrement();
		String cephKey = bucketName + "_" + System.currentTimeMillis() + "_"
				+ String.format("%08d", incrementCount + 1);

		PutObjectResult result = cephStorageService.saveInputStream(bucketName, cephKey, is);

		response.setCephKey(cephKey);
		response.setVersionId(result.getVersionId());
		return response;
	}

	@Override
	public CephResponse uploadFile(String bucketName, String cephKey, File file) throws AmazonS3Exception {
		CephResponse response = new CephResponse();
		response.setCephBucket(bucketName);
		bucketName = getBucketName(bucketName);

		if (!cephStorageService.isBucketExists(bucketName)) {
			cephStorageService.createBucket(bucketName);
		}
		PutObjectResult result = cephStorageService.saveFile(bucketName, cephKey, file);

		response.setCephKey(cephKey);
		response.setVersionId(result.getVersionId());
		return response;
	}

	@Override
	public CephResponse uploadFile(String bucketName, File file) throws AmazonS3Exception, IOException {
		CephResponse response = new CephResponse();
		response.setCephBucket(bucketName);
		bucketName = getBucketName(bucketName);

		if (!cephStorageService.isBucketExists(bucketName)) {
			cephStorageService.createBucket(bucketName);
		}
		// redis自增长+1，从1开始自增
		RedisAtomicLong entityIdCounter = new RedisAtomicLong("contractNo", redisTemplate.getConnectionFactory());
		entityIdCounter.expire(1, TimeUnit.DAYS);
		Long incrementCount = entityIdCounter.getAndIncrement();
		String cephKey = bucketName + "_" + System.currentTimeMillis() + "_"
				+ String.format("%08d", incrementCount + 1);

		PutObjectResult result = cephStorageService.saveFile(bucketName, cephKey, file);

		response.setCephKey(cephKey);
		response.setVersionId(result.getVersionId());
		return response;
	}
}
