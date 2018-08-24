package com.yzh.myweb.ftp;

import java.io.InputStream;

/**
 * @author pero.yan
 */
public interface IFTPHandler {
	public boolean uploadFileToFTP(InputStream inputStream, String ftpPath, String fileName, String ftpFileName);
	
	public boolean uploadBigFileToFTP(InputStream inputStream, String ftpPath, String fileName, String ftpFileName);

	boolean uploadFile2FTP(InputStream inputStream, String ftpPath, String fileName);
}
