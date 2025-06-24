package vn.leoo.common.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import vn.leoo.common.dto.FieldResponse;

public class Utils {
	  public static FieldResponse setError(String field_name, String message) {
		  FieldResponse er = new FieldResponse();
	        er.setFieldName(field_name);
	        er.setMessage(message);
	        return er;
	    }
	  
	  public static ObjectMapper mapper = new ObjectMapper();

		static {
			SimpleModule module = new SimpleModule();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			module.addSerializer(Date.class, new DateSerializer(false, sf));
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			mapper.registerModule(module);
		}

		public static Map<String, Object> convertToMap(Object obj) {
			return mapper.convertValue(obj, Map.class);

		}
		
		
}
