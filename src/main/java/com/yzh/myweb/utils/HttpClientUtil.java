package com.yzh.myweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 */
public class HttpClientUtil {

	public static final String DEFAULT_CHARSET = "UTF-8";
	private static int connectTimeout = 30000;
	private static int readTimeout = 600000;

	public static String reqAndRes(String urlString, String parameterData) throws Exception {
		return reqAndRes(urlString, parameterData, connectTimeout, readTimeout);
	}

	private static HttpURLConnection getConnection(String urlString, String method, int connectTimeout, int readTimeout)
			throws Exception {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Connection", "Close");
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeout));
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(readTimeout));
		} catch (Exception e) {
			throw e;
		}
		return conn;
	}

	public static String reqAndRes(String urlString, String parameterData, int connectTimeout, int readTimeout)
			throws Exception {
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			conn = getConnection(urlString, "POST", connectTimeout, readTimeout);
			outputStream = conn.getOutputStream();

			outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			outputStreamWriter.write(parameterData);
			outputStreamWriter.flush();

			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} catch (MalformedURLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return resultBuffer.toString();
	}

}
