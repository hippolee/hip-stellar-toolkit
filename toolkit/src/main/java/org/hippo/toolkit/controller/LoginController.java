package org.hippo.toolkit.controller;

import org.hippo.toolkit.entity.UserVO;
import org.hippo.toolkit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * LoginController
 * Created by litengfei on 2017/5/22.
 */
@Controller
@RequestMapping()
public class LoginController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(String param) {
        logger.debug("aaa");
        logger.info("bbb", new RuntimeException());
        logger.warn("ccc{}ccc{}ccc", "A", "B");
        logger.error("ddd");
        UserVO userVO = userService.getUserById(2L);
        logger.info(userVO.toString());
        return "login";
    }



}
