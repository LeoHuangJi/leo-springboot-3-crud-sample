//package vn.leo.common.config.security;
//import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
//import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//
//public class JasyptUtil {
//	PooledPBEStringEncryptor encryptor;
//	
//	String secretKey;
//	
//	public PooledPBEStringEncryptor getEncryptor() {
//		return encryptor;
//	}
//
//	private JasyptUtil(String secretKey) {
//		this.secretKey = secretKey;
//		
//		encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();      
//        
//        config.setAlgorithm("PBEWithMD5AndDES");
////        config.setPassword("A!B@C#D$E%F^G&H*"); // encryptor's private key
//        config.setPassword(secretKey);
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("base64");
//         
//        encryptor.setConfig(config);
//	}
//	
//	public static class JasyptHelper {
//		public static JasyptUtil getInstance(String secretKey) {
//			JasyptUtil util = new JasyptUtil(secretKey);
//			
//			return util;
//		}
//	}
//
//	public String getStringEncryptor(String original) {
//		String value = this.getEncryptor().encrypt(original);
//		
//		return value;
//	}
//	
//	public String getStringDecryptor(String encrypted) {
//		String value = this.getEncryptor().decrypt(encrypted);
//		
//		return value;
//	}
//}
