package com.inmind.latam.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for the distributed caching system using Redis.
 * <p>
 * This class provides the necessary configuration for:
 * - Redis server connection
 * - CacheManager configuration
 * - RedisTemplate setup
 * - Cache entry TTL (Time To Live) configuration
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.cache.CacheManager
 * @see org.springframework.data.redis.cache.RedisCacheManager
 * @see org.springframework.data.redis.connection.RedisConnectionFactory
 */
@Configuration
@EnableCaching
public class CacheConfig {

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${cache.ttl.minutes}")
	private long cacheTtlMinutes;

	/**
	 * Creates and configures the Redis connection factory.
	 * 
	 * @return configured RedisConnectionFactory instance
	 */
	@Bean
	RedisConnectionFactory redisConnectionFactory() {		
		LettuceConnectionFactory factory = new LettuceConnectionFactory(host, port);
		factory.afterPropertiesSet();
		return factory;
	}

	/**
	 * Creates and configures the Redis cache manager.
	 * Sets up the TTL for cache entries based on the configured value.
	 * 
	 * @param redisConnectionFactory the Redis connection factory
	 * @return configured CacheManager instance
	 */
	@Bean
	CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(cacheTtlMinutes));

		return RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(config)
				.build();
	}

	/**
	 * Creates and configures the Redis template for cache operations.
	 * Sets up serializers for keys and values.
	 * 
	 * @param connectionFactory the Redis connection factory
	 * @return configured RedisTemplate instance
	 */
	@Bean
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
}
