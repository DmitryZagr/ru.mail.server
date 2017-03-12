package ru.mail.server.util;

import java.io.File;
import java.util.Calendar;

import ru.mail.server.util.HttpUtil.Method;

public class Http {

	private static final String HTTP_VERSION = "HTTP/1.1";
	private String date;
	private String serverName = "NettyServer";
	private String connection = "Closed";
	private String contentLength;
	private String contentType;
	private int responceCode;
	private String method;
	private File file;

	public static class HttpBuilder {
		private String http;

		public HttpBuilder httpString(String http) {
			this.http = http;
			return this;
		}

		public Http build() {
			return parseHttp();
		}

		private Http parseHttp() {

			String[] httpLines = this.http.split(System.getProperty("line.separator"));

			String firstHttpLine = httpLines[0];
			httpLines = firstHttpLine.split(" ");

			Http http = new Http();

			http.setDate(Calendar.getInstance().getTime().toString());

			http.setMethod(Method.getMethodName(httpLines[0]));

			if (http.getMethod() == null) {
				http.setResponceCode(HttpUtil.ResponceCode.NOT_ALLOWED);
				return http;
			}




			return http;

		}

	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getResponceCode() {
		return responceCode;
	}

	public void setResponceCode(int responceCode) {
		this.responceCode = responceCode;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public static String getHttpVersion() {
		return HTTP_VERSION;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}



//	public static String getServerName() {
//		return SERVER_NAME;
//	}

}
