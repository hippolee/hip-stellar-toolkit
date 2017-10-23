package org.hippo.toolkit.rest;

import com.alibaba.fastjson.JSONObject;
import org.hippo.toolkit.entity.UserVO;
import org.hippo.toolkit.rest.param.LoginParamVO;
import org.hippo.toolkit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by litengfei on 2017/8/19.
 */
@RestController
@RequestMapping(value = "/rest", consumes = "application/json")
public class RestLoginController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RestLoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody LoginParamVO param) {
        JSONObject result = new JSONObject();
        UserVO userVO = userService.getUserByMobile(param.getMobile());
        //userVO != null &&
        if (userVO.getPassword().equals(param.getPassword())) {
            result.put("result",true);
            return result;
        }

        result.put("result",false);
        return result;
    }

}
