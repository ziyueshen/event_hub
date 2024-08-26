package org.szy.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonConfig {

    private String host;

    private String port;
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        // 1. create config
        Config config = new Config();
        System.out.println("host:" + host + ",port:" + port + ",password:" + password);
        String redisAddress = String.format("redis://%s:%s", host, port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(3).setPassword(password);
        // 2. create instance
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
