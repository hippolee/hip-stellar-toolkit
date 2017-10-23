package org.hippo.toolkit.rest.advice;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by litengfei on 2017/8/19.
 */
@ControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject processException(HttpServletRequest request, Exception e) {
        JSONObject result = new JSONObject();
        result.put("result", false);
        result.put("exception", e.getClass().getSimpleName());
        result.put("message", e.getMessage());
        return result;
    }

}
