package vn.leoo.common.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public class IOUtils {
	private static final int BUFFER_SIZE = 4096;
	
	public static void outputData(OutputStream os, byte[] data) throws Exception{
		os.write(data);
		os.flush();
		os.close();
	}
	
	public static void outputData(OutputStream os, InputStream in) throws Exception{
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		// write bytes read from the input stream into the output stream
		while ((bytesRead = in.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		in.close();
		os.close();
	}
	
	public static void writeByteArrayToFile(String filePathName, byte[] data) throws Exception{
		FileUtils.writeByteArrayToFile(new File(filePathName), data);
	}	

//	public static void main(String[] args) {
//		
//	}

}
