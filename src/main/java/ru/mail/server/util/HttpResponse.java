package ru.mail.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.io.FilenameUtils;

import ru.mail.file.IOUtil;
import ru.mail.server.NettyServer;

public class HttpResponse {
	private HttpHeaders httpHeaders;
	private HttpRequest request;
	private static final String INDEX = "index.html";
	private FileInputStream fis;
	private long fileLenght;

	public static class HttpResponseBuilder {
		private HttpRequest request;

		public HttpResponseBuilder httpRequest(HttpRequest request) {
			this.request = request;
			return this;
		}

		public HttpResponse build() {
			HttpResponse httpResp = new HttpResponse();
			httpResp.request = request;
			httpResp.build();
			return httpResp;
		}
	}

	private void build() {

		if (!request.isValid()) {
			invalidRequest();
			return;
		}

		openFileInputStream();

	}

	private FileInputStream openFileInputStream() {

		String childPath = request.getPath();

		if (request.getFileExtension() == null) {
			if (!(childPath.charAt(childPath.length() - 1) == '/'))
				childPath += "/";
			childPath += INDEX;
			request.setFileExtension(FilenameUtils.getExtension(childPath));
		}

		try {
			childPath = URLDecoder.decode(childPath, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		IOUtil ioutil = new IOUtil(NettyServer.getServerConfigByName("NettyServer").getRootDir(), childPath);

		File file = ioutil.getFile();


		if (!file.exists()) {
			notFound();
			return null;
		}

		try {
			fis = new FileInputStream(file);
			fileLenght = file.length();
			OK();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fis = null;
		}

		return fis;

	}

	public FileInputStream getFileInputStream() {
		return this.fis;
	}

	private void OK() {
		httpHeaders = new HttpHeaders.HttpHeadersBuilder().code(ResponceCode.OK)
				.contentType(HttpUtil.getContentType(request.getFileExtension()))
				.contentLenght(Long.toString(fileLenght)).connection(HttpHeaders.CONNECTION_CLOSE).build();
	}

	private void invalidRequest() {
		httpHeaders = new HttpHeaders.HttpHeadersBuilder().code(ResponceCode.NOT_ALLOWED)
				.connection(HttpHeaders.CONNECTION_CLOSE).build();
	}

	private void notFound() {
		httpHeaders = new HttpHeaders.HttpHeadersBuilder().code(ResponceCode.NOT_FOUND)
				.connection(HttpHeaders.CONNECTION_CLOSE).build();
	}

	private static class ResponceCode {
		static final int OK = 200;
		static final int NOT_FOUND = 404;
		static final int NOT_ALLOWED = 405;
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public long getFileLenght() {
		return fileLenght;
	}

}
