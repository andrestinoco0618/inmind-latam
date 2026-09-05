package com.inmind.latam.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CacheConfig class.
 * Tests the Redis cache configuration and related beans.
 */
@ExtendWith(MockitoExtension.class)
class CacheConfigTest {

    @InjectMocks
    private CacheConfig cacheConfig;

    @Mock
    private RedisConnectionFactory redisConnectionFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(cacheConfig, "host", "localhost");
        ReflectionTestUtils.setField(cacheConfig, "port", 6379);
        ReflectionTestUtils.setField(cacheConfig, "cacheTtlMinutes", 30L);
    }

    @Test
    void shouldCreateRedisConnectionFactory() {
        // Act
        RedisConnectionFactory factory = cacheConfig.redisConnectionFactory();

        // Assert
        assertThat(factory).isNotNull();
        assertThat(factory).isInstanceOf(LettuceConnectionFactory.class);
    }

    @Test
    void shouldCreateCacheManager() {
        // Act
        var cacheManager = cacheConfig.cacheManager(redisConnectionFactory);

        // Assert
        assertThat(cacheManager).isNotNull();
    }

    @Test
    void shouldCreateRedisTemplate() {
        // Act
        RedisTemplate<String, Object> template = cacheConfig.redisTemplate(redisConnectionFactory);

        // Assert
        assertThat(template).isNotNull();
        assertThat(template.getConnectionFactory()).isEqualTo(redisConnectionFactory);
        assertThat(template.getKeySerializer()).isNotNull();
        assertThat(template.getValueSerializer()).isNotNull();
    }
} 