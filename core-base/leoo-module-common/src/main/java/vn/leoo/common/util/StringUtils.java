package vn.leoo.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.http.HttpStatus;

import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.exception.ServiceException;

public class StringUtils {
	
	
	static final int BUFFER = 1024;
	
	public static String unzip(InputStream is) throws Exception {
        ZipInputStream zis=new ZipInputStream(is);
        zis.getNextEntry();
	    ByteArrayOutputStream baos=new ByteArrayOutputStream();
	    BufferedOutputStream  bos=new BufferedOutputStream(baos);
	    int len;
	    byte[] buf=new byte[1024];
	    while ((len=zis.read(buf))>0) {
	        bos.write(buf,0,len);
	    }
	    bos.flush();
	    bos.close();
	    zis.closeEntry();
	    zis.close();
	    return baos.toString("UTF-8");
	}
    
    public static byte[] zip(String source, String name) throws Exception {
    	byte data[] = new byte[BUFFER];
    	BufferedInputStream origin = null;
    	ByteArrayOutputStream baos=new ByteArrayOutputStream();
    	ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(baos));
    	try {
//    		out.setMethod(ZipOutputStream.DEFLATED);
 
    		ByteArrayInputStream sourceStream = new ByteArrayInputStream(source.getBytes());
    		origin = new BufferedInputStream(sourceStream, BUFFER);
    		
    		ZipEntry entry = new ZipEntry(name);
    		out.putNextEntry(entry);
    		int count;
    		while((count = origin.read(data, 0, BUFFER)) != -1) {
    			out.write(data, 0, count);
    		}    		
        } catch(Exception e) {
//        	e.printStackTrace();
        	throw e;
        }finally{
        	if(origin != null)
        		origin.close();             
    		out.close();
        }
        return baos.toByteArray();
    }
    
    public static byte[] compress(String source) throws Exception {
//        System.out.println("String length : " + str.length());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(source.getBytes());
        gzip.close();
//        String outStr = out.toString("UTF-8");
//        System.out.println("Output String lenght : " + outStr.length());
        return out.toByteArray();
    }
    
    public static String decompress(byte[] source) throws Exception {
    	ByteArrayInputStream bis = new ByteArrayInputStream(source);
        GZIPInputStream gis = new GZIPInputStream(bis);
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
          outStr += line;
        }
//        System.out.println("Output String lenght : " + outStr.length());
        return outStr;
    }
    
    public static boolean isNumeric(String str){  
    	try{  
    		double d = Double.parseDouble(str);
    		System.out.println("Number is: " + d);
    	}catch(NumberFormatException nfe){  
    		return false;  
    	}  
    	return true;  
    }
    
    public static boolean isEmpty(CharSequence cs) {
    	return cs == null || cs.length() == 0;
    }
    
    public static boolean isNotEmpty(CharSequence cs) {
    	return cs != null && cs.length() > 0;
    }
    
    //Kiem tra 1 chuoi la null or rong
  	public static boolean isNull(String str) {
  		return str == null || str.equals("") || str.trim().length() == 0 || str == "null";
  	}
    
    public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
    
    public static String substring(String source, int len){
    	String val = "";
    	try{
    		val = source.substring(len);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return val;
    }
    

    
    public static String clob2String(Clob clob) throws IOException, SQLException {
        StringBuilder sb = new StringBuilder();
        try (Reader reader = clob.getCharacterStream()) {
            char[] buffer = new char[2048];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, read);
            }
        }
        return sb.toString();
    }
	
	public static String format(String template, Map<String, Object> parameters) {
	    StringBuilder newTemplate = new StringBuilder(template);
	    List<Object> valueList = new ArrayList<>();

	    Matcher matcher = Pattern.compile("[$][{](\\w+)}").matcher(template);

	    while (matcher.find()) {
	        String key = matcher.group(1);

	        String paramName = "${" + key + "}";
	        int index = newTemplate.indexOf(paramName);
	        if (index != -1) {
	            newTemplate.replace(index, index + paramName.length(), "%s");
	            valueList.add(parameters.get(key));
	        }
	    }

	    return String.format(newTemplate.toString(), valueList.toArray());
	}
	
	
	public static String buildOrderByClause(String inputCol,String inputDirection, Map<String,String> sortColumnMap) {
	    StringBuilder orderBy = new StringBuilder();
	    
	    if(org.apache.commons.lang3.StringUtils.isBlank(inputCol)|| org.apache.commons.lang3.StringUtils.isBlank(inputDirection)) {
	    	throw new ServiceException(AppErrorCode.BAD_REQUEST, "Tham số sắp xếp dữ liệu không hợp lệ");
	    }
	    String [] cols = inputCol.split(",");
	    String [] directions = inputDirection.split(",");
	    
	    if(cols.length != directions.length) {
	    	throw new ServiceException(AppErrorCode.BAD_REQUEST, "Tham số sắp xếp dữ liệu không hợp lệ, số luợng hàng và kiểu dữ liệu sắp xếp không khớp");
	    }

	    for (int i = 0; i < cols.length && i <directions.length ; i++) {
	    	String col = cols[i];
	    	String direction = directions[i];
	    	
	    	
	        String column = sortColumnMap.get(col);

	        if (org.apache.commons.lang3.StringUtils.isBlank(column)) {
	            throw new ServiceException(AppErrorCode.BAD_REQUEST, "Trường sắp xếp dữ liệu không hợp lệ");
	        }

	        if (!"asc".equalsIgnoreCase(direction) && !"desc".equalsIgnoreCase(direction)) {
	        	 throw new ServiceException(AppErrorCode.BAD_REQUEST, "Kiểu sắp xếp dữ liệu không hợp lệ");
	        }

	        if (i > 0) {
	            orderBy.append(", ");
	        }

	        orderBy.append(column).append(" ").append(direction.toUpperCase());
	    }

	    return orderBy.toString();
	}
	
	
}
