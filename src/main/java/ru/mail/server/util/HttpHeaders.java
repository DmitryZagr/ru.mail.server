package ru.mail.server.util;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

	private final static Map<Integer, String> codeDescription = new HashMap<>();

	static {
		codeDescription.put(200, "OK");
		codeDescription.put(404, "Not Found");
		codeDescription.put(405, "Method Not Allowed");
	}

}
