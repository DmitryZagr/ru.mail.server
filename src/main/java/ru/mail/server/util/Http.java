package ru.mail.server.util;

import java.util.HashSet;
import java.util.Set;

public class Http {

	public static Set<String> ContentType = new HashSet<>();

	public static enum Methods {
		HEAD, GET;

		public boolean isHEAD(Methods m) {
			return m.equals(HEAD);
		}

		public boolean isGET(Methods m) {
			return m.equals(GET);
		}
	}

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
