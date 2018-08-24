package com.yzh.myweb.ftp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aspose.words.BreakType;
import com.aspose.words.ConvertUtil;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.PageSetup;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.WrapType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("FTPHandler")
public class FTPHandler implements IFTPHandler {

	@Value("${sy.erp.ftp.ftpHost}")
	private String ftpHost;
	@Value("${sy.erp.ftp.ftpPort}")
	private String ftpPort;
	@Value("${sy.erp.ftp.ftpUserName}")
	private String ftpUserName;
	@Value("${sy.erp.ftp.ftpPassword}")
	private String ftpPassword;
	@Value("${sy.erp.ftp.tempfilePath}")
	private String tempfilePath;

	public boolean uploadFileToFTP(InputStream inputStream, String ftpPath, String fileName, String ftpFileName) {

		FTPConfigProperties config = new FTPConfigProperties(ftpHost, ftpPort, ftpUserName, ftpPassword);

		if (!config.canFTP()) {
			log.error(">>>>>>>>>>>>>>>>>>>ftp配置异常!");
			return false;
		}

		FTPClient ftpClient = null;
		File file = null;
		try {
			if (!fileName.toLowerCase().contains(".pdf")) {
				log.info("不是PDF文件，开始转换为PDF");
				String outputFileName = tempfilePath + fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
				file = new File(outputFileName);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				convertImageToPdf(inputStream, outputFileName);
				inputStream = new FileInputStream(new File(outputFileName));
			}

			log.info("开始上传文件到FTP.");
			ftpClient = FTPClientUtil.getFTPClient(config);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// ftpClient.makeDirectory(ftpPath);
			createDir(ftpPath, ftpClient);
			ftpClient.changeWorkingDirectory(ftpPath);// "/Register/2017-09-28"
			boolean uprs = ftpClient.storeFile(ftpFileName, inputStream);
			log.info("上传文件结果:" + uprs);

		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>>ftp上传异常:::", e);
			return false;
		} finally {
			try {
				if (ftpClient != null) {
					ftpClient.disconnect();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				//是否需要删除本地文件 file.delete();
				if(null!=file && file.exists()){
					file.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * 创建目录(没有则创建目录)
	 *
	 * @param dir
	 * @return
	 */
	public void createDir(String dir, FTPClient ftpClient) throws Exception{
		try {
			// 目录编码，解决中文路径问题
			String d = new String(dir.toString().getBytes("GBK"), "iso-8859-1");
			String[] arr = d.split("/");
			StringBuffer sbfDir = new StringBuffer();
			// 循环生成子目录
			for (String s : arr) {
				if (StringUtils.isEmpty(s)) {
					continue;
				}
				sbfDir.append("/");
				sbfDir.append(s);
				// 尝试切入目录
				if (ftpClient.changeWorkingDirectory(sbfDir.toString()))
					continue;
				if (!ftpClient.makeDirectory(sbfDir.toString())) {
					log.info("[失败]ftp创建目录：" + sbfDir.toString());
				}
				log.info("[成功]创建ftp目录：" + sbfDir.toString());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public boolean uploadFile2FTP(InputStream inputStream, String ftpPath, String fileName) {
		FTPConfigProperties config = new FTPConfigProperties(ftpHost, ftpPort, ftpUserName, ftpPassword);

		if (!config.canFTP()) {
			log.error(">>>>>>>>>>>>>>>>>>>ftp配置异常!");
			return false;
		}

		FTPClient ftpClient = null;
		log.info("开始上传文件到FTP.");
		try {
			ftpClient = FTPClientUtil.getFTPClient(config);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			createDir(ftpPath, ftpClient);
			ftpClient.changeWorkingDirectory(ftpPath);// "/Register/2017-09-28"
			boolean uprs = ftpClient.storeFile(fileName, inputStream);
			log.info("上传文件结果:" + uprs);
			inputStream.close();
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>>ftp上传异常:::", e);
			return false;
		} finally {
			try {
				if (ftpClient != null) {
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public void convertImageToPdf(InputStream ins, String outputFileName) throws Exception {
		// Create Aspose.Words.Document and DocumentBuilder.
		// The builder makes it simple to add content to the document.
		Document doc = new Document();
		DocumentBuilder builder = new DocumentBuilder(doc);

		// Load images from the disk using the approriate reader.
		// The file formats that can be loaded depends on the image readers
		// available on the machine.
		ImageInputStream iis = ImageIO.createImageInputStream(ins);
		ImageReader reader = ImageIO.getImageReaders(iis).next();
		reader.setInput(iis, false);

		try {
			// Get the number of frames in the image.
			int framesCount = reader.getNumImages(true);

			// Loop through all frames.
			for (int frameIdx = 0; frameIdx < framesCount; frameIdx++) {
				// Insert a section break before each new page, in case of a
				// multi-frame image.
				if (frameIdx != 0)
					builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

				// Select active frame.
				BufferedImage image = reader.read(frameIdx);

				// We want the size of the page to be the same as the size of
				// the image.
				// Convert pixels to points to size the page to the actual image
				// size.
				PageSetup ps = builder.getPageSetup();

				ps.setPageWidth(ConvertUtil.pixelToPoint(image.getWidth()));
				ps.setPageHeight(ConvertUtil.pixelToPoint(image.getHeight()));

				// Insert the image into the document and position it at the top
				// left corner of the page.
				builder.insertImage(image, RelativeHorizontalPosition.PAGE, 0, RelativeVerticalPosition.PAGE, 0,
						ps.getPageWidth(), ps.getPageHeight(), WrapType.NONE);
			}
		} finally {
			if (iis != null) {
				iis.close();
				reader.dispose();
			}
		}

		// Save the document to PDF.
		doc.save(outputFileName);
	}
	
	public boolean uploadBigFileToFTP(InputStream inputStream, String ftpPath, String fileName, String ftpFileName) {
		FTPConfigProperties config = new FTPConfigProperties(ftpHost, ftpPort, ftpUserName, ftpPassword);
		if (!config.canFTP()) {
			log.error(">>>>>>>>>>>>>>>>>>>ftp配置异常!");
			return false;
		}
		FTPClient ftpClient = null;
		File file = null;
		try {
			log.info("开始上传文件到FTP.");
			ftpClient = FTPClientUtil.getFTPClient(config);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			createDir(ftpPath, ftpClient);
			ftpClient.changeWorkingDirectory(ftpPath);
			boolean uprs = ftpClient.storeFile(ftpFileName, inputStream);
			log.info("上传文件结果:" + uprs);
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>>ftp上传异常:::", e);
			return false;
		} finally {
			try {
				if (ftpClient != null) {
					ftpClient.disconnect();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				//是否需要删除本地文件 file.delete();
				if(null!=file && file.exists()){
					file.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
