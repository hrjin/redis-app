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
import javax.servlet.http.HttpSession;

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
    public ModelAndView getValuePage(HttpServletRequest request, HttpServletResponse response, HttpSession session, @PathVariable("key") String key) {
        ModelAndView mv = new ModelAndView();
        String value = getValue(request, response, session, key);
        mv.addObject("key", key);
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
    public String getValue(HttpServletRequest request, HttpServletResponse response, HttpSession session, @PathVariable("key") String key) {
        String sessionValue = session.getId();
        String cookieKey = CommonUtils.getCookie(request, "SESSION");
        String value = "";

        if(cookieKey != null) {
            value = redisService.getValue(key);
            return "value = " + value + " & session id = " + sessionValue;
        }

        value = redisService.getValue(key);
        session.setAttribute("value", value);

        logger.info(session.getAttribute("value").toString());
        CommonUtils.addCookies(response, "SESSION", sessionValue);
        return "value = " + value + " & session id = " + sessionValue;

    }

}
