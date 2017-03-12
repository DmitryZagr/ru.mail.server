package ru.mail.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IOUtil {

	private File file;
	private boolean valid;

	public IOUtil(String documentRoot, String childPath) {
		file = new File(documentRoot + childPath);
	}

	public FileInputStream getFileStream() {

		FileInputStream fis;

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}

		return fis;
	}

	public boolean isValid() {
		return valid;
	}

	public File getFile() {
		return file;
	}

}
