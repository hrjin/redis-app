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
import org.springframework.web.bind.annotation.*;

/**
 * Redis MVC를 위한 Controller
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


    @PutMapping("/keys")
    @ResponseBody
    public String setKey(@RequestParam("kn") String key, @RequestParam("kv") String val) {
        logger.info("Called the key set method, going to set key: " + key + " to val: " + val);
        redisTemplate.opsForValue().set(key, val);

        return "Set key: " + key + " to value: " + val;
    }

    @GetMapping("/keys/{key:.+}")
    @ResponseBody
    public String getKey(@PathVariable("key") String key) {
        logger.info("Called the key for getting value : " + key);

        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        String result = (String) vop.get(key);

        logger.info("value : " + result);
        return result;
    }

}
