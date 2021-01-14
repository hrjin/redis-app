package egovframework.redis.sample.controller;

import egovframework.redis.cmmn.CommonUtils;
import egovframework.redis.sample.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redis 데이터 조회 및 저장을 위한 Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.12
 **/
@Controller
public class RedisController {
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);
    private static final String MY_KEY = "MY_KEY";

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }


    /**
     * main 페이지로 이동
     *
     * @return the String
     */
    @GetMapping
    public String mainPage() {
        return "main";
    }


    /**
     * Key, Value를 저장
     *
     * @param key the key
     * @param val the value
     * @return the String
     */
    @PostMapping("/keys")
    @ResponseBody
    public String setKeyValue(@RequestParam("kn") String key, @RequestParam("kv") String val) {
        logger.info("Called the key set method, going to set key: " + key + " to val: " + val);
        return redisService.setKeyValue(key, val);
    }


    /**
     * value 값을 조회하는 페이지로 이동
     *
     * @param request  the request
     * @param response the response
     * @param key      the key
     * @return the ModelAndView
     */
    @GetMapping("/{key:.+}")
    public ModelAndView getValuePage(HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key) {
        ModelAndView mv = new ModelAndView();
        String value = getValue(request, response, key);

        mv.addObject("value", value);
        mv.setViewName("/view");
        return mv;

    }


    /**
     * value 값을 조회
     *
     * @param request  the request
     * @param response the response
     * @param key      the key
     * @return the String
     */
    @GetMapping("/keys/{key:.+}")
    @ResponseBody
    public String getValue(HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key) {
        String cookieKey = CommonUtils.getCookie(request, MY_KEY);

        if(cookieKey != null && cookieKey.equals(key)) {
            String valueFromCookie = redisService.getValue(cookieKey);
            return valueFromCookie;

        } else if(cookieKey != null && !cookieKey.equals(key)) {
            CommonUtils.removeCookies(response, cookieKey);
        }

        String value = redisService.getValue(key);
        CommonUtils.addCookies(response, MY_KEY, key);

        return value;

    }

}
