package ru.mail.server.util;

import org.apache.commons.lang3.StringUtils;

import ru.mail.server.util.HttpUtil.Method;

public class HttpRequest {
	private String methodName;
	private String path;
	private String httpVersion;
	private String fileExtension;
	private boolean isValid;

	public static class HttpRequestBuilder {

		private String methodName;
		private String path;
		private String httpVersion;
		private String fileExtension;
		private boolean isValid = true;

		public HttpRequest build(String http) {

			parseHttp(http);
			HttpRequest httpReq = new HttpRequest();
			httpReq.setFileExtension(fileExtension);
			httpReq.setHttpVersion(httpVersion);
			httpReq.setMethodName(methodName);
			httpReq.setPath(path);
			httpReq.setValid(isValid);

			return httpReq;
		}

		private void parseHttp(String httpRequest) {

			if (StringUtils.isEmpty(httpRequest)) {
				isValid = false;
				return;
			}

			httpRequest = httpRequest.split(System.getProperty("line.separator"))[0];

			this.methodName = httpRequest.substring(0, httpRequest.indexOf(" "));

			if (Method.getMethodName(this.methodName) == null) {
				isValid = false;
				return;
			}

			if (!httpRequest.contains("HTTP")) {
				isValid = false;
				return;
			}
			this.path = httpRequest.substring(methodName.length() + 1, httpRequest.indexOf("HTTP") - 1);
			if (path.contains("?"))
				this.path = path.substring(0, this.path.indexOf('?'));

			this.httpVersion = httpRequest.substring(path.length() + methodName.length() + 2, httpRequest.length() - 1);

			String[] split = httpVersion.split(" ");

			if(split.length > 1) {
				httpVersion = split[1];
			}

			if (!httpVersion.equals("HTTP/1.1")) {
				isValid = false;
				return;
			}

			getFileExtension();

		}

		private void getFileExtension() {
			String[] split = this.path.split("\\.");

			if (split == null || split.length == 0 || split.length == 1) {
				this.fileExtension = null;
				return;
			}

			this.fileExtension = split[split.length - 1];

			this.isValid = isValidExtention();
		}

		private boolean isValidExtention() {

			if (HttpUtil.getContentType(fileExtension) == null)
				return false;

			return true;
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
