package org.hippo.toolkit.rest;

import org.hippo.toolkit.entity.UserVO;
import org.hippo.toolkit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by litengfei on 2017/6/29.
 */
@RestController
@RequestMapping(value = "/rest", consumes = "application/json")
public class RestRobotController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RestRobotController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/robot", method = RequestMethod.POST)
    public UserVO robot(@RequestBody String body) {
        logger.info(body);
        UserVO userVO = userService.getUserById(2L);
        return userVO;
    }

}
