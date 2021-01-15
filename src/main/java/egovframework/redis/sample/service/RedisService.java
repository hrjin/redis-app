package egovframework.redis.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Redis 데이터 조회 및 저장을 위한 Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.12
 **/
@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * Redis Server에 Key, Value 저장
     *
     * @param key the key
     * @param val the value
     * @return the String
     */
    public String setKeyValue(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
        return "Set key: " + key + " to value: " + val;
    }


    /**
     * Redis Server에서 key 값으로 value 조회
     *
     * @param key the key
     * @return the String
     */
    public String getValue(String key) {
        boolean isExist = redisTemplate.hasKey(key);
        String result = "no data.";

        if(isExist) {
            ValueOperations<String, Object> vop = redisTemplate.opsForValue();
            result = (String) vop.get(key);
        }

        return result;
    }
}
