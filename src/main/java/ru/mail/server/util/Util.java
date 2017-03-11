package ru.mail.server.util;

import java.util.HashSet;
import java.util.Set;

public class Util {

	private static Set<String> ContentType = new HashSet<>();

	static {
		ContentType.add("text/html");
		ContentType.add("text/css");
		ContentType.add("text/javascript");
		ContentType.add("image/jpg");
		ContentType.add("image/jpeg");
		ContentType.add("image/png");
		ContentType.add("image/gif");
		ContentType.add("application/x-shockwave-flash");
	}
}
