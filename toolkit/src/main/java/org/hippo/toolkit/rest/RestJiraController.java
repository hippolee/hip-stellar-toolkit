package org.hippo.toolkit.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hippo.toolkit.util.JiraUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by litengfei on 2017/9/11.
 */
@RestController
@RequestMapping(value = "/rest")
public class RestJiraController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RestJiraController.class);

    @RequestMapping(value = "/jira/webhook", method = RequestMethod.POST, consumes = "application/json")
    public JSONObject webhookPost(HttpServletRequest request, HttpServletResponse response) {
        String opid = UUID.randomUUID().toString();
        logger.info("jirawebhook:--{}--begin", opid);

        // 读请求体内容
        String bodyStr = null;
        try {
            bodyStr = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (StringUtils.isEmpty(bodyStr)) {
            logger.error("jirawebhook--{}--read body error:{}", opid, bodyStr);
        }

        // 请求体对象
        JSONObject bodyObj = null;
        try {
            bodyObj = JSON.parseObject(bodyStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (bodyObj == null) {
            logger.error("jirawebhook--{}--parse body error:{}", opid, bodyStr);
        }

        // 处理事件
        boolean result = JiraUtils.handleJiraEvent(bodyObj);
        if (!result) {
            logger.info("jirawebhook--{}--body:{}", opid, bodyStr);
        }

        logger.info("jirawebhook:--{}--end", opid);
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", result ? "success" : "failure");
        return resultObj;
    }

}
