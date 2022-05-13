package cn.ultronxr.qqrobot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author Ultronxr
 * @date 2021/03/12 13:28
 *
 * Redis 配置
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.redisson.singleServerConfig.address}")
    private String address;

    @Value("${spring.redis.redisson.singleServerConfig.password}")
    private String password;


    /**
     * 修改 RedisTemplate 配置
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 修改 RedisTemplate K-V序列化方式，避免存入redis时出现乱码（\xac\xed\x00\x05t\x00）
        // StringRedisSerializer | Jackson2JsonRedisSerializer<>(Object.class)
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedissonClient redisson() throws IOException {
        String resource = "conf/redisson.yaml";
        Config config = Config.fromYAML(RedisConfig.class.getClassLoader().getResource(resource));
        config.useSingleServer().setAddress(address);
        config.useSingleServer().setPassword(password);
        return Redisson.create(config);
    }

}
