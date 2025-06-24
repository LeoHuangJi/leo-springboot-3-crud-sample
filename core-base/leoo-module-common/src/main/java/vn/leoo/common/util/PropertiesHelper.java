package vn.leoo.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;

public class PropertiesHelper {
	Properties _props;
	
	public void load(String propertiesFileName){
		InputStream input = null;
		try {
			String homeDir = "";//System.getenv("APP_CONFIG_LOCATION");		
			input = new FileInputStream(homeDir.trim()+propertiesFileName.trim());
			_props = new Properties();
			_props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
     * get a string property's value
     * @param propKey property key
     * @param defaultValue default value if the property is not found
     * @return value
     */
    public String getProperty(String propKey, String defaultValue) {
        String strProp = _props.getProperty(propKey);
        if (strProp == null) {
            strProp = defaultValue;
        }
        return strProp;
    }

    /**
     * internal recursive method to get string properties (array)
     * @param curResult current result
     * @param paramName property key prefix
     * @param i current indice
     * @return array of property's values
     */
    private List<String> getSystemStringProperties(List<String> curResult, String paramName, int i) {
    	String paramIValue = getProperty(paramName + "." + String.valueOf(i), null);
        if (paramIValue == null) {
            return curResult;
        }
        curResult.add(paramIValue);
        return getSystemStringProperties(curResult, paramName, i+1);
    }
    
    private List<String> getSystemStringProperties(List<String> curResult, String prefix, String paramName, int i) {
    	String paramIValue = getProperty(prefix + "[" + String.valueOf(i) + "]." + paramName, null);
        if (paramIValue == null) {
            return curResult;
        }
        curResult.add(paramIValue);
        return getSystemStringProperties(curResult, prefix, paramName, i+1);
    }

    /**
     * get the values from a property key prefix
     * @param paramName property key prefix
     * @return string array of values
     */
    public String[] getProperties(String paramName) {
         List<String> stringProperties = getSystemStringProperties(new ArrayList<String>(), paramName, 0);
        return stringProperties.toArray(new String[stringProperties.size()]);
    }
    
    /**
     * get the values from a property key prefix
     * @param prefix
     * @param paramName property key prefix
     * @return string array of values
     */
    public List<String> getProperties(String prefix, String paramName) {
         List<String> stringProperties = getSystemStringProperties(new ArrayList<String>(), prefix, paramName, 0);
        return stringProperties;
    }
    
	public <T> T deserialize(String prefix, Class<T> clazz) throws Exception{
    	//Properties props = new Properties();
    	StringBuffer bf = new StringBuffer();
    	Enumeration<Object> keys = _props.keys();
		while(keys.hasMoreElements()){
			String key = (String)keys.nextElement();
			if(key.contains(prefix + ".") && key.contains("[") && key.contains("]")){
				String strProp = _props.getProperty(key);				
				String new_key = key.replaceAll(prefix + ".", "");
				
				//props.setProperty(new_key, strProp);
				bf.append(new_key + "=" + strProp + "\n");
			}
		}
		String str = ""; //props.toString();
		//str = str.substring(1, str.length()-1).replace(",", "\n");
		str = bf.toString().trim();

		JavaPropsMapper mapper = new JavaPropsMapper();

		return (T)mapper.readValue(str, clazz);
    }
}
