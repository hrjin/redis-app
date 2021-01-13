package egovframework.redis.sample.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.12
 **/
@Controller
public class RedisController {
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping
    public String mainPage() {
        return "main";
    }

    @GetMapping("/ups")
    public String getUserProvidedServiceInfo() {
        String vcap = System.getenv("VCAP_SERVICES");
        logger.info("VCAP_SERVICES content: " + vcap);

        // now we parse the json in VCAP_SERVICES
        logger.info("Using GSON to parse the json...");
        JsonElement root = new JsonParser().parse(vcap);
        JsonObject ups = null;
        if (root != null) {
            if (root.getAsJsonObject().has("user-provided")) {
                ups = root.getAsJsonObject().get("user-provided").getAsJsonArray().get(0).getAsJsonObject();
                logger.info("instance name: " + ups.get("name").getAsString());
            }
            else {
                logger.info("ERROR: no redis instance found in VCAP_SERVICES");
            }
        }

        if (ups != null) {
            JsonObject creds = ups.get("credentials").getAsJsonObject();
            return ups.get("name").getAsString() + " / " + creds.get("uri").getAsString() + " / " + creds.get("user").getAsString();
        }
        else return "not found!";
    }


//    private RedisInstanceInfo getInfo() {
//        logger.info("Getting Redis Instance Info...");
//
//        String vcap = System.getProperty("VCAP_SERVICES");
//        logger.info("VCAP_SERVICES : " + vcap);
//
//        JsonElement root = new JsonParser().parse(vcap);
//        JsonObject redis = null;
//        if (root != null) {
//            if (root.getAsJsonObject().has("redis")) {
//                redis = root.getAsJsonObject().get("redis").getAsJsonArray().get(0).getAsJsonObject();
//                logger.info("service name: " + redis.get("name").getAsString());
//            }
//            else if (root.getAsJsonObject().has("p-redis")) {
//                redis = root.getAsJsonObject().get("p-redis").getAsJsonArray().get(0).getAsJsonObject();
//                logger.info("service name: " + redis.get("name").getAsString());
//            }
//            else {
//                logger.info("ERROR: no redis instance found in VCAP_SERVICES");
//            }
//        }
//
//        // then we pull out the credentials block and produce the output
//        if (redis != null) {
//            JsonObject creds = redis.get("credentials").getAsJsonObject();
//            RedisInstanceInfo info = new RedisInstanceInfo();
//            info.setHost(creds.get("host").getAsString());
//            info.setPort(creds.get("port").getAsInt());
//            info.setPassword(creds.get("password").getAsString());
//
//            return info;
//        }
//        else return new RedisInstanceInfo();
//    }

//    private RedisClient getLettuceConnection() {
//        RedisInstanceInfo info = getInfo();
//
//        RedisURI redisUri = RedisURI.Builder.redis(info.getHost(), info.getPort())
//                .withPassword(info.getPassword())
//                .build();
//
//        return RedisClient.create(redisUri);
//    }


    @PutMapping("/keys")
    public String setKey(@RequestParam("kn") String key, @RequestParam("kv") String val) {
        logger.info("Called the key set method, going to set key: " + key + " to val: " + val);
        redisTemplate.opsForValue().set(key, val);

        return "Set key: " + key + " to value: " + val;
    }

    @GetMapping("/keys/{key:.+}")
    public String getKey(@PathVariable("key") String key) {
        logger.info("Called the key for getting value : " + key);

        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        String result = (String) vop.get(key);

        logger.info("value : " + result);
        return result;
    }


}
