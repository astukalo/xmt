package xyz.a5s7.xmlt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Util {
	public static String readXML(String fileName) {
		InputStream is = null;
		StringBuilder buffer = new StringBuilder();
		try {
			is = Util.class.getClassLoader().getResourceAsStream(fileName);
			Reader in = new InputStreamReader(is, "UTF8");
			int c;
			while ((c = in.read()) != -1)
				buffer.append((char)c);
			return buffer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}

	public static File getFile(String name) {
		return new File(Util.class.getClassLoader().getResource(name).getPath());
	}

}
