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
     * Set Session Attribute (session 생성)
     * Save Session ID in Cookie (Cookie에 Session ID 저장)
     *
     * @param request  the request
     * @param response the response
     * @param session  the session
     * @param key      the key
     * @return the ModelAndView
     */
    @GetMapping("/setSession/{key:.+}")
    public ModelAndView sessionSetTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, @PathVariable("key") String key) {
        // SESSION에 redis에서 조회한 데이터 값을 저장한다.
        session.setAttribute("value", redisService.getValue(key));

        // Cookie에 SESSION ID가 있는 지 조회한다.(Redis HttpSession은 'SESSION' 이라는 쿠키값을 이용하여 자신의 HttpSession 객체를 식별)
        String cookieKey = CommonUtils.getCookie(request, "SESSION");
        String sessionValue = session.getId();

        // Cookie에 SESSION ID가 없을 경우 현재 SESSION ID를 Cookie 추가
        if(cookieKey == null) {
            CommonUtils.addCookies(response, "SESSION", sessionValue);
        }

        ModelAndView mv = new ModelAndView();

        mv.addObject("key", key);
        mv.addObject("value", "- Set Session Value :: " + redisService.getValue(key) + "\n - session id :: " + sessionValue);
        mv.setViewName("/view");
        return mv;

    }


    /**
     * 현재 요청이 온 브라우저의 SESSION ID와 Cookie에 저장돼있는 SESSION ID를 비교하여 데이터 조회
     *
     * @param request the request
     * @param session the session
     * @return the ModelAndView
     */
    @GetMapping("/getSession")
    public ModelAndView sessionGetTest(HttpServletRequest request, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/session");

        // Cookie에 SESSION ID가 있는 지 조회한다.
        String sessionIdInCookie = CommonUtils.getCookie(request, "SESSION");
        logger.info("sessionIdInCookie >>> " + sessionIdInCookie);
        logger.info("session.getId() >>> " + session.getId());

        // 현재 요청이 온 브라우저의 SESSION ID와 Cookie에 저장돼있는 SESSION ID를 비교하여 조건에 부합하지 않은 경우 데이터 값 조회하지 못한다.
        if((sessionIdInCookie == null) || !(session.getId().equals(sessionIdInCookie))) {
            mv.addObject("result","Current session is not correct before session...");
            return mv;
        }

        // Cookie의 SESSION ID 값과 요청 온 브라우저의 SESSION ID 값이 같기 때문에 SESSION에 저장해 놓은 데이터 값을 정상 조회해온다.
        mv.addObject("result", "- session id :: " + sessionIdInCookie + "\n - value from session :: " + session.getAttribute("value").toString());
        return mv;
    }
}
