package vn.leoo.common.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import vn.leoo.common.i18n.UTF8Control;

/**
 * @author nhnam
 *
 */
public class ResourceUtils {
	private static ResourceBundle RB = null; //ResourceBundle.getBundle("properties.config");
	
	public static ResourceBundle getResourceBundleFromClassPath(String bundleName, Locale locale){
		return ResourceBundle.getBundle(bundleName, locale, new UTF8Control());
	}
	
	public static ResourceBundle getResourceBundleFromClassLoader(String bundleName, Locale locale) throws Exception{
		ResourceBundle rb = null;
		try {
			String homeDir = "";//System.getenv("APP_CONFIG_LOCATION");
			File file = new File(homeDir);  
			URL[] urls = {file.toURI().toURL()};  
			ClassLoader loader = new URLClassLoader(urls);  
			rb = ResourceBundle.getBundle(bundleName, locale, loader, new UTF8Control());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return rb;
	}
	
	public static void loadFromClassPath(String bundleName, Locale locale) throws Exception{
		try {
			if(RB == null){
				RB = ResourceBundle.getBundle(bundleName, locale, new UTF8Control());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void loadFromClassLoader(String bundleName, Locale locale, String folderName) throws Exception{
		try {
			if(RB == null){
				String homeDir = "";//System.getenv("APP_CONFIG_LOCATION");
				if(StringUtils.isNotEmpty(folderName)){
					homeDir += "/" + folderName;
				}
				File file = new File(homeDir);  
				URL[] urls = {file.toURI().toURL()};  
				ClassLoader loader = new URLClassLoader(urls);  
				RB = ResourceBundle.getBundle(bundleName, locale, loader, new UTF8Control());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void loadFromClassLoader(String bundleName, Locale locale) throws Exception {
		loadFromClassLoader(bundleName, locale, null);
    }

    //This method doesn't use localization
	public static void loadFromFile(String fileName) throws Exception {
		if(RB == null){
			String homeDir = "";//System.getenv("APP_CONFIG_LOCATION");
	        FileInputStream fis = new FileInputStream(homeDir + "/" + fileName);
	        try {
	        	RB = new PropertyResourceBundle(new InputStreamReader(fis, "UTF-8"));
	        } finally {
	            fis.close();
	        }
		}
    }
	
	public static String getString(String key){
    	String result=null;
        try{
           result = RB.getString(key);
        }catch (MissingResourceException e){
            return null;
        }
        return result; 
    }
	
	public static String getString(ResourceBundle rb, String key){
    	String result=null;
        try{
           result = rb.getString(key);
        }catch (MissingResourceException e){
            return null;
        }
        return result; 
    }
}
