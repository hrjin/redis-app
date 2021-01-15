package egovframework.redis.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import egovframework.redis.sample.model.RedisInstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * Redis Config 설정
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.12
 **/
@Configuration
@EnableRedisHttpSession
public class RedisConfig extends AbstractHttpSessionApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);


    /**
     * Redis 접근을 위한 Connection 객체
     *
     * @return the RedisConnectionFactory
     */
    @Bean
    @Order(1)
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("localhost", 6379);

        if(getInfo() != null) {
            RedisInstanceInfo instanceInfo = getInfo();

            lettuceConnectionFactory.setHostName(instanceInfo.getHost());
            lettuceConnectionFactory.setPort(instanceInfo.getPort());
            lettuceConnectionFactory.setPassword(instanceInfo.getPassword());

            return lettuceConnectionFactory;
        }

        return lettuceConnectionFactory;
    }


    /**
     * RedisTemplate을 통해 RedisConnection에서 넘겨준 byte 값을 객체 직렬화
     *
     * @return the RedisTemplate
     */
    @Bean
    @Order(2)
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }


    /**
     * CF상에서 바인딩된 Redis Service 접속 정보 조회
     *
     * @return the RedisInstanceInfo
     */
    @Bean
    @Order(0)
    public RedisInstanceInfo getInfo() {
        logger.info("Getting Redis Instance Info...");

        String vcap = System.getenv("VCAP_SERVICES");
        logger.info("VCAP_SERVICES : " + vcap);
        if(vcap != null) {
            JsonElement root = new JsonParser().parse(vcap);
            JsonObject redis = null;
            if (root != null) {
                if (root.getAsJsonObject().has("redis")) {
                    redis = root.getAsJsonObject().get("redis").getAsJsonArray().get(0).getAsJsonObject();
                    logger.info("service name: " + redis.get("name").getAsString());
                }
                else if (root.getAsJsonObject().has("p-redis")) {
                    redis = root.getAsJsonObject().get("p-redis").getAsJsonArray().get(0).getAsJsonObject();
                    logger.info("service name: " + redis.get("name").getAsString());
                }
                else {
                    logger.info("ERROR: no redis instance found in VCAP_SERVICES");
                }
            }

            // then we pull out the credentials block and produce the output
            if (redis != null) {
                JsonObject creds = redis.get("credentials").getAsJsonObject();
                RedisInstanceInfo info = new RedisInstanceInfo();
                info.setHost(creds.get("host").getAsString());
                info.setPort(creds.get("port").getAsInt());
                info.setPassword(creds.get("password").getAsString());

                return info;
            }
            else return new RedisInstanceInfo();
        }
        return null;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

}
