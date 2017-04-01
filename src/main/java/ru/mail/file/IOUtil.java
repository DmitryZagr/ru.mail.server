package ru.mail.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IOUtil {

	public static  File getFile(String documentRoot, String childPath) {
		return new File(documentRoot + childPath);
	}

}
