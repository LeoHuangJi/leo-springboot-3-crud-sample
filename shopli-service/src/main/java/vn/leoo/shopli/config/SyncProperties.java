package vn.leoo.shopli.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sync")
public class SyncProperties {
    private int    pageSize    = 5000;
    private int    batchSize   = 1000;
    private String pitKeepAlive = "5m";
}