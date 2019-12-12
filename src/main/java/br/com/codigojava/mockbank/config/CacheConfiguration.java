package br.com.codigojava.mockbank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "!test" })
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Value("${cache.maximum-size:500}")
    private String maximumSize;

    @Value("${cache.expire-after-write:600s}")
    private String expireAfterWrite;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheSpecification("maximumSize=" + maximumSize + ",expireAfterWrite=" + expireAfterWrite);
        return cacheManager;
    }
}
