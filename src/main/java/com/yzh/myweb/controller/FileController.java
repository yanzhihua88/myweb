package com.yzh.myweb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.util.IOUtils;
import com.yzh.myweb.annotation.AnnotationUtil;
import com.yzh.myweb.dto.ImportFileResponseDTO;
import com.yzh.myweb.dto.Stream;
import com.yzh.myweb.entity.CustomerFollowFile;
import com.yzh.myweb.exception.CustomException;
import com.yzh.myweb.exception.ServerErrorException;
import com.yzh.myweb.service.ICustomerFollowFileService;
import com.yzh.myweb.service.ceph.CephResponse;
import com.yzh.myweb.service.ceph.ICephFileService;
import com.yzh.myweb.utils.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
@Slf4j
@RestController
@Api(description = "文件上传相关接口")
public class FileController extends BaseController {

	@Autowired
	private ICustomerFollowFileService customerFollowFileService;

	@Autowired
	private ICephFileService cephFileService;

	@ApiOperation(value = "文件上传", notes = "文件上传")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public Map<String, Object> uploadTransferAttachment(MultipartHttpServletRequest multiReq)
			throws IOException, CustomException {
		List<MultipartFile> files = multiReq.getFiles("file");
		if (files == null || files.isEmpty()) {
			throw new ServerErrorException("文件不能为空");
		}
		MultipartFile file = files.get(0);
		// 处理上传的文件
		String fileName = getFileName(file);
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
		CephResponse cephResponse = cephFileService.uploadInputStream("bucketName", file.getInputStream());
		CustomerFollowFile customerFollowFile = new CustomerFollowFile();
		customerFollowFile.setFileName(fileName);
		customerFollowFile.setFileExt(fileExt);
		customerFollowFile.setBucketName(cephResponse.getCephBucket());
		customerFollowFile.setFileMd5(cephResponse.getFileMd5());
		customerFollowFile.setObjectName(cephResponse.getCephKey());
		customerFollowFileService.insert(customerFollowFile);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("fileId", customerFollowFile.getFileId());
		returnMap.put("fileName", fileName);
		return returnMap;
	}

	// 获取文件名称
	private String getFileName(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (fileName.contains("/")) {
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		} else {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		}
		return fileName;
	}

	@ApiOperation(value = "获取文件", notes = "获取文件")
	@RequestMapping(value = "/getFile/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable Integer id) throws CustomException {
		CustomerFollowFile customerFollowFile = customerFollowFileService.selectById(id);
		if (customerFollowFile == null) {
			throw new ServerErrorException("文件不存在");
		}
		InputStream inputStream = cephFileService.getInputStream(customerFollowFile.getBucketName(),
				customerFollowFile.getObjectName());
		if (inputStream == null) {
			throw new ServerErrorException("文件不存在");
		}

		try {
			String extension = customerFollowFile.getFileExt();
			String contentType = "application/" + extension.toLowerCase();

			if (!"pdf".equalsIgnoreCase(extension)) {
				contentType = "image/" + extension.toLowerCase();
			}

			if ("xlsx".equalsIgnoreCase(extension)) {
				contentType = "application/vnd.ms-excel";
			}

			if ("docx".equalsIgnoreCase(extension)) {
				contentType = "application/msword";
			}

			if ("ZIP".equalsIgnoreCase(extension)) {
				contentType = "application/x-zip-compressed";
			}

			if ("RAR".equalsIgnoreCase(extension)) {
				contentType = "application/octet-stream";
			}

			if ("exe".equalsIgnoreCase(extension)) {
				contentType = "application/x-msdownload";
			}
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.parseMediaType(contentType));
			return new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	@ApiOperation(value = "导入EXCEL", notes = "导入EXCEL")
	@RequestMapping(value = "/importFile", method = RequestMethod.POST)
	public ImportFileResponseDTO importFile(
			@RequestParam("file") MultipartFile file) {
		try {
			ImportFileResponseDTO dto = new ImportFileResponseDTO();
			List<Stream> list = new ArrayList<Stream>();
			if (file != null) {
				String fileName = file.getOriginalFilename();
				int dot = fileName.lastIndexOf('.');
				if ((dot > -1) && (dot < (fileName.length() - 1))) {
					String suffixName = fileName.substring(dot + 1);
					if (suffixName.equals("xls") || suffixName.equals("xlsx")) {
						Map<String, Object> rtMa = ExcelUtil.readXls(
								file.getInputStream(), "yyyy-MM-dd HH:mm:ss");
						List<Map<String,String>> mapList = null;
						Stream stream = null;
						for (Map.Entry<String, Object> entry : rtMa.entrySet()) {
							mapList = (List<Map<String, String>>) entry.getValue();
							int num=1;
							for (Map<String, String> map : mapList) {
								stream = new Stream();
								stream.setEnterprise(map.get("value2"));
								stream.setAddress(map.get("value3"));
								stream.setContact(map.get("value4"));
								stream.setPhone(map.get("value5"));
								stream.setEmail(map.get("value6"));
								list.add(stream);
								Map<String, Object> result = AnnotationUtil.validate(stream);
								if(result.get("result").equals(false)){
									log.info("文档第"+num+"行：" + result.get("message").toString());
									throw new ServerErrorException("第"+num+"行：" + result.get("message").toString());
								}
								num++;
							}
						}
						dto.setList(list);
						return dto;
					}else{
						throw new ServerErrorException("文件必须是excle格式");
					}
				}else{
					throw new ServerErrorException("请检查文件属性");
				}
			} else {
				throw new ServerErrorException("未获取到上传文件");
			}
		} catch (ServerErrorException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new ServerErrorException("系统异常");
		}
	}
}
