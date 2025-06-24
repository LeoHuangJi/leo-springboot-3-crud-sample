//
//package vn.leoo.common.config.security;
//
//import org.jasypt.encryption.StringEncryptor;
//import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//
//@Configuration
//public class JasyptConfiguration {
//
//	@Autowired
//	Environment env;
//
//	@Bean(name = "jasyptStringEncryptor")
//	public StringEncryptor getStringEncryptor() {
//		String secretKey = env.getProperty("security.oauth2.client.base64-secret");
//		JasyptUtil jasyptUtil = JasyptHelper.getInstance(secretKey);
//		PooledPBEStringEncryptor encryptor = jasyptUtil.getEncryptor();
//
//		return encryptor;
//	}
//}
