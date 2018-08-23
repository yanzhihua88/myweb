package com.yzh.myweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.yzh.myweb.utils.CephResponse;
import com.yzh.myweb.utils.CephStorageService;
import com.yzh.myweb.utils.CephFileService;
import com.yzh.myweb.utils.ICephFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MywebApplication.class)
public class CephTest {

	@Autowired
	private ICephFileService fileService;
	private String bucket = "mybucket";

	@Test
	public void createBucket() {
		try {
			Bucket b = fileService.createBucket(bucket);
			System.out.println("=====Bucket: " + b.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getBuckets() {
		try {
			List<Bucket> list = fileService.getBuckets();
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveDataInputStream() throws Exception {
		File file = new File("D:\\soft\\nginx-1.13.8.zip");
		InputStream is = new FileInputStream(file);
		try {
			long start = System.currentTimeMillis();
//			key 设计一套业务规则
			CephResponse cr = fileService.uploadInputStream(bucket, is);

			long end = System.currentTimeMillis();
			
			System.out.println(" ================耗时： " + (end - start));
			System.out.println(cr.getCephBucket());
			System.out.println(cr.getCephKey());
			System.out.println(cr.getVersionId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveDataFile() throws Exception {
		File file = new File("D:\\soft\\nginx-1.13.8.zip");
		try {
			long start = System.currentTimeMillis();

			CephResponse cr = fileService.uploadFile(bucket, file);

			long end = System.currentTimeMillis();

			System.out.println(" ================耗时： " + (end - start));
			System.out.println(cr.getCephBucket());
			System.out.println(cr.getCephKey());
			System.out.println(cr.getVersionId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getObject() throws Exception {
		String key ="mpsdevmybucket_1524474235912_0001";
		S3ObjectInputStream s3is =null;
		try {
			s3is = fileService.getObject(bucket, key);
		} catch (AmazonS3Exception e) {
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}
		File file = new File("D:\\"+key+".exe");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];

		int len;
	
		while ((len = s3is.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.flush();

		fos.close();
	}

	@Test
	public void getInputStream() throws Exception {
		InputStream is = fileService.getInputStream(bucket, "dd_3.4.9.exe");

		File file = new File("D:\\dd_3.4.9.exe");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];

		int len;
		// 读入需要下载的文件的内容，打包到zip文件
		while ((len = is.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.flush();

		fos.close();
	}

	@Test
	public void getFiles() {
		try {
			List<String> list = fileService.getFiles(bucket);
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteFile() {
		String key="mpsdevmybucket_1524474701543_0002";
		try {
			fileService.deleteFile(bucket, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteBucket() {
		try {
			fileService.deleteBucket(bucket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteBucketAndFiles() {
		try {
			fileService.deleteBucketAndFiles(bucket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}