package vn.leoo.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import vn.leoo.common.util.formatter.DateFormatter;

public class BeanUtilities {
	public static void copyProperties(Object source, Object destination) throws Exception{
        try {
			BeanUtils.copyProperties(source, destination);
		} catch (BeansException e) {
			e.printStackTrace();
			throw e;
		}
    }

	public static boolean setField(Object object, String fieldName, Object fieldValue) throws Exception{
	    Class<?> clazz = object.getClass();
	    if (clazz != null) {
	        try {
	            Field f = clazz.getDeclaredField(fieldName);
	            String type = f.getType().getTypeName();
//	        	String name = f.getName();
	            
	            f.setAccessible(true);
	            
	            if(type.contains("java.lang.String")){			        		
	        		f.set(object, fieldValue);
	        	}else if(type.contains("java.util.Date") || type.contains("java.sql.Timestamp")){
	        		if(fieldValue!=null && !"".equals(fieldValue)){
	        			String date_source = fieldValue.toString().trim();
	        			System.out.println(date_source);
	        			if(date_source.length()==14 && !date_source.contains("-") 
	        										&& !date_source.contains("/") 
	        										&& !date_source.contains(":")){
	        				int len = 0;
	        				String y = date_source.substring(0, 4);
	        				len = y.length();
	        				String m = date_source.substring(len, 6);
	        				len += m.length();
	        				String d = date_source.substring(len, 8);
	        				len += d.length();
	        				String hh = date_source.substring(len, 10);
	        				len += hh.length();
	        				String mm = date_source.substring(len, 12);
	        				len += mm.length();
	        				String ss = date_source.substring(len, 14);
	        				date_source = y +"-"+m+"-"+d+" "+ hh+":"+mm+":"+ss;
	        			}
		        		String simple_date = DateFormatter.getSimpleDateFormat(date_source);	
				        Date date = new SimpleDateFormat(simple_date, Locale.ENGLISH).parse(date_source);
				        
				        if(type.contains("java.util.Date")){
				        	f.set(object, date);
				        } else if(type.contains("java.sql.Timestamp")){
				        	Timestamp ts = new Timestamp(date.getTime());
			        		f.set(object, ts);
				        }
	        		}
	        	}else if(type.contains("java.math.BigDecimal")){
	        		String val = String.valueOf(fieldValue).trim();
	        		if(!StringUtils.isEmpty(val) && StringUtils.isNumeric(val)){
	        			f.set(object, new BigDecimal(val));
	        		}else{
	        			f.set(object, new BigDecimal(0));
	        		}
	        	}
	            
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
	public static String getField(Object object, String fieldName){
		String val = "";
		Object value = null;
		Class<?> clazz = object.getClass();
	    if (clazz != null) {
	    	try{
	    		Field field = clazz.getDeclaredField(fieldName);
	    		field.setAccessible(true);
	    		value = field.get(object);
	    		if(value != null){
		    		if(value instanceof byte[]){
		        		val = Base64.getEncoder().encodeToString((byte[])value);
		        	}else {
		        		val = value.toString();
		        	}
	    		}
	    	}catch(Exception e){
	    		throw new IllegalStateException(e);
	    	}
	    }
	    return val;
	}
	
	public static String invoke(Object object, String methodName, Class<?>[] clazzParams){
		String val = "";
		Object value = null;
		Class<?> clazz = object.getClass();
	    if (clazz != null) {
	    	try{	    		
	    		Object[] args = null;
	    		if(clazzParams != null){
	    			List<Object> listArgs = new ArrayList<Object>();
	    			for (Class<?> clazzz : clazzParams) {
	    				Constructor<?> constructor = clazzz.getConstructor();
	    				listArgs.add(constructor.newInstance());
					}
	    			args = listArgs.toArray();
	    		}
	    		Method method = clazz.getMethod(methodName, clazzParams);
	    		value = method.invoke(object, args);
	    		if(value != null){
		    		if(value instanceof byte[]){
		        		val = Base64.getEncoder().encodeToString((byte[])value);
		        	}else {
		        		val = value.toString();
		        	}
	    		}
	    	}catch(Exception e){
	    		throw new IllegalStateException(e);
	    	}
	    }
	    return val;
	}
	
	
}
