package vn.leoo.shopli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.annotation.PostConstruct;

import java.util.TimeZone;

/**
 * @author hiephv
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableTransactionManagement
@EnableScheduling
@ComponentScan(basePackages = { "vn.leoo", "vn.leoo.common.config.security","vn.leoo.audit.log" })
@EntityScan(basePackages = { "vn.leoo.entity","vn.leoo.audit.log.entity"  })
@EnableJpaRepositories({
		"vn.leoo.audit.log.repo",
		"vn.leoo.shopli.repository"
})
public class ShopliServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopliServiceApplication.class, args);
	}

	/*
	 * @Bean public ApplicationProperties applicationProperties() { return new
	 * ApplicationProperties(); }
	 */

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+0700"));
		// AsposeLib.init();
	}
	


}
