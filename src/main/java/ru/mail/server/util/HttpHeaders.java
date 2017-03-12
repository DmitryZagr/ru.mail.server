package ru.mail.server.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

	private final static Map<Integer, String> codeDescription = new HashMap<>();

	private final static String SERVER_NAME = "Server:";
	private final static String DATE = "Date:";
	private final static String CONNECTION = "Connection:";
	private final static String CONTENT_LENGHT = "Content-Length:";
	private final static String CONTENT_TYPE = "Content-Type:";

	private StringBuilder headers;

	static {
		codeDescription.put(200, "OK");
		codeDescription.put(404, "Not Found");
		codeDescription.put(405, "Method Not Allowed");
	}

	public static class HttpHeadersBuilder {

		private StringBuilder headers = new StringBuilder();
		private String codeStatus = "HTTP/1.1 {code} {message}\n\r";

		public HttpHeaders build() {

			HttpHeaders httpHeaders = new HttpHeaders(headers);

			headers.append("\n\r");

			return httpHeaders;
		}

		public HttpHeadersBuilder code(int codeStatus) {
			this.codeStatus = this.codeStatus.replaceAll("\\{code\\}", Integer.toString(codeStatus))
					.replaceAll("\\{message\\}", codeDescription.get(codeStatus));
			headers.append(this.codeStatus);
			setCommonHttpHeaders();
			return this;
		}

		public HttpHeadersBuilder connection(String connection) {
			setConnection(connection);
			return this;
		}

		public HttpHeadersBuilder contentType(String contentType) {
			headers.append(CONTENT_TYPE).append(" ").append(contentType).append("\n\r");
			return this;
		}

		public HttpHeadersBuilder contentLenght(String contentLenght) {
			headers.append(CONTENT_LENGHT).append(" ").append(contentLenght).append("\n\r");
			return this;
		}

		private void setCommonHttpHeaders() {
			headers.append(SERVER_NAME).append(" NettyServer\n\r").append(DATE).append(" ")
					.append(Calendar.getInstance().getTime().toString()).append("\n\r");
		}

		private void setConnection(String connection) {
			headers.append(CONNECTION).append(" " + connection + "\n\r");
		}

	}

	private HttpHeaders(StringBuilder headers) {
		this.headers = headers;
	}

	public StringBuilder getHttpHeaders() {
		return this.headers;
	}

}
