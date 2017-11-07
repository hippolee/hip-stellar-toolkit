package org.hippo.toolkit.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by litengfei on 2017/9/12.
 */
public class OpenApiUtils {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(OpenApiUtils.class);

    /** open-api */
    private static final String OPENAPI_DOMAIN = "https://openapi.yonyoucloud.com";
    /** open */
    private static final String OPEN_DOMAIN = "https://open.yonyoucloud.com";
    /** 获取access_token的url */
    private static final String URL_ACCESS_TOKEN = OPENAPI_DOMAIN + "/token";
    /** 服务号发送文本消息url */
    private static final String URL_SERVICE_TEXT = OPENAPI_DOMAIN + "/rest/message/service/txt";
    /** 服务号发送分享消息url */
    private static final String URL_SERVICE_SHARE = OPENAPI_DOMAIN + "/rest/message/service/share";
    /** 应用号发送文本消息url */
    private static final String URL_APP_TEXT = OPENAPI_DOMAIN + "/rest/message/service/txt";
    /** 应用号发送分享消息url */
    private static final String URL_APP_SHARE = OPENAPI_DOMAIN + "/rest/message/service/share";
    /** 服务号发送分享消息url（palmyy） */
    private static final String URL_PALMYY_SHARE = OPEN_DOMAIN + "/operation/palmyy/message/service/share";

    /** 用友集团appid */
    public static final String OPEN_APP_ID_YONYOU = "9ad99e4505b4d4ab";
    /** 用友集团secret */
    public static final String OPEN_SECRET_YONYOU = "dac8be17266b965cafef4912d1cff0383fc9f263b76d354cbff3b3bd6747";

    /** 协同云空间ID */
    public static final String ESN_QZID = "74269";
    /** 服务号ID */
    public static final String ECJIRA_PUBACCID = "ecjira";
    /** PALMYY-手机号 */
    public static final String PALMYY_TO_PHONE = "1";
    /** PALMYY-邮箱 */
    public static final String PALMYY_TO_EMALI = "2";

    /**
     * 获取AccessToken
     *
     * @param appId  appId
     * @param secret appSecret
     * @return accessToken
     */
    public static String getAccessToken(String appId, String secret) {
        // url string
        String urlString = URL_ACCESS_TOKEN + "?appid=" + appId + "&secret=" + secret;
        // request result
        String result = HTTPClientUtils.doGet(urlString);
        // JSONObject
        JSONObject responseData = JSON.parseObject(result);

        // result code
        Integer code = responseData.getInteger("code");
        // result message
        String msg = responseData.getString("msg");

        // data
        JSONObject data = responseData.getJSONObject("data");
        // access_token & expire_seconds
        String accessToken = null;
        Integer expiresIn = null;
        if (data != null) {
            accessToken = data.getString("access_token");
            expiresIn = data.getInteger("expiresIn");
        }

        logger.info("response code: {}", code);
        logger.info("response message: {}", msg);
        logger.info("token: {}", accessToken);
        logger.info("expires_in: {}", expiresIn);
        return accessToken;
    }

    /**
     * 服务号发送文本消息
     *
     * @param accessToken accessToken
     * @param qzId        空间ID
     * @param pubAccId    公共号ID
     * @param toArray     接收方ID数组
     * @param content     文本内容
     * @return 是否成功
     */
    public static boolean sendServiceText(String accessToken, String qzId, String pubAccId, String[] toArray, String
            content) {
        // url string
        String urlString = URL_SERVICE_TEXT + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 空间ID
        body.put("spaceId", qzId);
        // 服务号ID
        body.put("pubAccId", pubAccId);
        // TO
        if (toArray != null && toArray.length > 0) {
            body.put("sendScope", "list");
            body.put("to", toArray);
        } else {
            body.put("sendScope", "all");
        }
        // 消息内容
        body.put("content", content);

        logger.info("sendServiceText:{}:{}", urlString, body.toJSONString());
        // request result
        String result = HTTPClientUtils.doPostJson(urlString, body.toJSONString());
        return checkResult(result, urlString);
    }

    /**
     * 服务号发送分享消息
     *
     * @param accessToken accessToken
     * @param qzId        空间ID
     * @param pubAccId    公共号ID
     * @param toArray     接收方ID数组
     * @param title       标题
     * @param desc        描述
     * @param detailUrl   链接
     * @return 是否成功
     */
    public static boolean sendServiceShare(String accessToken, String qzId, String pubAccId, String[] toArray, String
            title, String desc, String detailUrl) {
        // url string
        String urlString = URL_SERVICE_SHARE + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 空间ID
        body.put("spaceId", qzId);
        // 服务号ID
        body.put("pubAccId", pubAccId);
        // TO
        if (toArray != null && toArray.length > 0) {
            body.put("sendScope", "list");
            body.put("to", toArray);
        } else {
            body.put("sendScope", "all");
        }
        // 消息内容
        body.put("title", title);
        body.put("desc", desc);
        body.put("detailUrl", detailUrl);

        logger.info("sendServiceShare:{}:{}", urlString, body.toJSONString());
        // request result
        String result = HTTPClientUtils.doPostJson(urlString, body.toJSONString());
        return checkResult(result, urlString);
    }

    /**
     * 应用号发送文本消息
     *
     * @param accessToken accessToken
     * @param qzId        空间ID
     * @param appId       应用ID
     * @param toArray     接收方ID数组
     * @param content     文本内容
     * @return 是否成功
     */
    public static boolean sendAppText(String accessToken, String qzId, String appId, String[] toArray, String
            content) {
        // url string
        String urlString = URL_APP_TEXT + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 空间ID
        body.put("spaceId", qzId);
        // 应用ID
        body.put("appId", appId);
        // TO
        if (toArray != null && toArray.length > 0) {
            body.put("sendScope", "list");
            body.put("to", toArray);
        } else {
            body.put("sendScope", "all");
        }
        // 消息内容
        body.put("content", content);

        logger.info("sendAppText:{}:{}", urlString, body.toJSONString());
        // request result
        String result = HTTPClientUtils.doPostJson(urlString, body.toJSONString());
        return checkResult(result, urlString);
    }

    /**
     * 应用号发送分享消息
     *
     * @param accessToken accessToken
     * @param qzId        空间ID
     * @param appId       应用ID
     * @param sendThrough 发送途径
     * @param toArray     接收方ID数组
     * @param title       标题
     * @param desc        描述
     * @param detailUrl   链接
     * @return 是否成功
     */
    public static boolean sendAppShare(String accessToken, String qzId, String appId, String sendThrough, String[]
            toArray, String title, String desc, String detailUrl) {
        // url string
        String urlString = URL_APP_SHARE + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 空间ID
        body.put("spaceId", qzId);
        // 应用ID
        body.put("appId", appId);
        // 发送途径
        body.put("sendThrough", sendThrough);
        // TO
        if (toArray != null && toArray.length > 0) {
            body.put("sendScope", "list");
            body.put("to", toArray);
        } else {
            body.put("sendScope", "all");
        }
        // 消息内容
        body.put("title", title);
        body.put("desc", desc);
        body.put("detailUrl", detailUrl);

        logger.info("sendAppShare:{}:{}", urlString, body.toJSONString());
        // request result
        String result = HTTPClientUtils.doPostJson(urlString, body.toJSONString());
        return checkResult(result, urlString);
    }

    /**
     * 服务号发送分享消息（palmyy）
     *
     * @param accessToken accessToken
     * @param qzId        空间ID
     * @param pubAccId    公共号ID
     * @param toArray     接收方数组
     * @param toUserType  接收方类型
     * @param title       标题
     * @param desc        描述
     * @param detailUrl   链接
     * @param hightlight  高亮
     * @return 是否成功
     */
    public static boolean sendPalmyyShare(String accessToken, String qzId, String pubAccId, String[] toArray, String
            toUserType, String title, String desc, String detailUrl, String hightlight) {
        // url string
        String urlString = URL_PALMYY_SHARE + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 空间ID
        body.put("spaceId", qzId);
        // 服务号ID
        body.put("pubAccId", pubAccId);
        // TO
        if (toArray != null && toArray.length > 0) {
            body.put("sendScope", "list");
            body.put("to", toArray);
        } else {
            body.put("sendScope", "all");
        }
        // toUserType,1是手机号
        body.put("toUserType", toUserType);
        // 消息内容
        body.put("title", title);
        body.put("desc", desc);
        body.put("detailUrl", detailUrl);
        body.put("hightlight", hightlight);

        logger.info("sendPalmyyShare:{}:{}", urlString, body.toJSONString());
        // request result
        String result = HTTPClientUtils.doPostJson(urlString, body.toJSONString());
        return checkResult(result, urlString);
    }

    private static boolean checkResult(String result, String identify) {
        logger.info("checkResult:{}:{}", identify, result);
        if (!StringUtils.isEmpty(result)) {
            // JSONObject
            JSONObject responseData = JSON.parseObject(result);
            if (responseData != null) {
                Integer flag = responseData.getInteger("flag");
                logger.info("response flag: {}", flag);
                logger.info("response message: {}", responseData.getString("message"));
                if (flag == 0) {
                    logger.info("do {} success", identify);
                    return true;
                }
            }
        }
        logger.info("do {} faild", identify);
        return false;
    }

}
