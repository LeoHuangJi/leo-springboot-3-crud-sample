package vn.leoo.common.util.file;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfo {
	private String absolutePath;
	private String name;
	private String parent;
	private String path;
	
    private byte[] data;
    private String type;
    private String extension;
    private String base64;
    private Object file;
    private int size;

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public Object getFile() {
		return file;
	}

	public void setFile(Object file) {
		this.file = file;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int compareDateTo(FileInfo info){
		int compare = 0;
		try {
			int idx = name.indexOf("_");
			String strDate1 = this.name.substring(idx+1, this.name.indexOf("."));
			String strDate2 = info.getName().substring(idx+1, info.getName().indexOf("."));
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = format.parse(strDate1);
			Date date2 = format.parse(strDate2);
			compare = date1.compareTo(date2);
			
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		return compare;
	}
	
	public int compareDateTo(Date compareDate){
		int compare = 0;
		try {
			int idx = name.indexOf("_");
			String strDate1 = this.name.substring(idx+1, this.name.indexOf("."));
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = format.parse(strDate1);
			compare = date1.compareTo(compareDate);
			
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		return compare;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
