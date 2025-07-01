package com.example.auth.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LettuceRedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    @Value("${spring.data.redis.timeout:60000}")
    private long timeoutMillis;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient(ClientResources clientResources) {
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(redisHost)
                .withPort(redisPort)
                .withTimeout(Duration.ofMillis(timeoutMillis));

        // 비밀번호가 설정된 경우에만 적용
        if (redisPassword != null && !redisPassword.isBlank()) {
            builder.withPassword(redisPassword.toCharArray());
        }

        RedisURI redisUri = builder.build();

        RedisClient client = RedisClient.create(clientResources, redisUri);
        client.setOptions(ClientOptions.builder()
                .autoReconnect(true)
                .build());

        return client;
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
        return connection.sync();
    }
}