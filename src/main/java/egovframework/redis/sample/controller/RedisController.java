package egovframework.redis.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hrjin
 * @version 1.0
 * @since 2021.01.12
 **/
@Controller
public class RedisController {

    @GetMapping
    public String mainPage() {
        return "main";
    }
}
