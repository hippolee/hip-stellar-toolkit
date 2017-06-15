package org.hippo.toolkit.some;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by litengfei on 2017/5/31.
 */
public class EsnMessageDemo {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(EsnMessageDemo.class);

    /** 设成你的appid */
    private static final String APP_ID = "40e3ae822629e6f1";
    /** 设成你的secret */
    private static final String SECRET = "e089863374a2585df2017b41990e1dfce9b788037270cec8056529dbf102";

    /** 获取access_token的url */
    private static final String URL_ACCESS_TOKEN = "https://openapi.upesn.com/token";
    /** 服务号发送文本消息url */
    private static final String URL_SERVICE_TEXT = "https://openapi.upesn.com/rest/message/service/txt";
    /** 服务号发送文本消息url（NC） */
    private static final String URL_PALMYY_SERVICE_TEXT = "https://open.upesn.com/operation/palmyy/message/service/txt";

    public static String getAccessToken() {
        // url string
        String urlString = URL_ACCESS_TOKEN + "?appid=" + APP_ID + "&secret=" + SECRET;
        // request result
        String result = doGet(urlString);
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

    public static void sendServiceText(String accessToken) {
        // url string
        String urlString = URL_SERVICE_TEXT + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 传你的空间ID
        body.put("spaceId", "7945");
        // 传你的服务号ID
        body.put("pubAccId", "hiptest");
        // 传你要怎么发，list:发给to的列表;all:发给所有人
        body.put("sendScope", "list");
        // 传你要发给谁，sendScope为list时生效
        body.put("to", new String[]{"155359"});
        // 传你要发的内容
        body.put("content", "这里是消息内容");
        String requestBody = body.toJSONString();

        // request result
        String result = doPost(urlString, requestBody);
        if (!StringUtils.isEmpty(result)) {
            // JSONObject
            JSONObject responseData = JSON.parseObject(result);
            logger.info("response flag: {}", responseData.getInteger("flag"));
            logger.info("response message: {}", responseData.getString("message"));
        } else {
            logger.info("send service text faild");
        }
    }

    public static void sendPalmyyServiceText(String accessToken) {
        // url string
        String urlString = URL_PALMYY_SERVICE_TEXT + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 传你的空间ID
        body.put("spaceId", "7945");
        // 传你的服务号ID
        body.put("pubAccId", "hiptest");
        // 传你要怎么发，list:发给to的列表;all:发给所有人
        body.put("sendScope", "list");
        // 传你要发给谁，sendScope为list时生效
        body.put("to", new String[]{"13581587602"});
        // toUserType,1是手机号
        body.put("toUserType", "1");
        // 传你要发的内容
        body.put("content", "张添催办了您,主题为:张添提交流程类型为:晨会准备表;单据编号为:0000020的单据!,请尽快处理!");
        String requestBody = body.toJSONString();

        // request result
//        requestBody = "{\"content\":\"张添催办了您,主题为:张添提交流程类型为:晨会准备表;单据编号为:0000020的单据!,请尽快处理!\",\"pubAccId\":\"ncapppubaccount\",\"sendScope\":\"list\",\"spaceId\":\"101615\",\"to\":[\"18111624781\"],\"toUserType\":\"1\"}";
//        urlString = "https://open.upesn.com/operation/palmyy/message/service/txt?access_token=3f1d5d6556e6a9cd2d4708e287493f0be808ee089529f53a3b363bb5c96fcc30";
        String result = doPost(urlString, requestBody);
        if (!StringUtils.isEmpty(result)) {
            // JSONObject
            JSONObject responseData = JSON.parseObject(result);
            logger.info("response flag: {}", responseData.getInteger("flag"));
            logger.info("response message: {}", responseData.getString("message"));
        } else {
            logger.info("send palmyy service text faild");
        }
    }

    private static String doGet(String urlString) {
        HttpURLConnection conn = null;
        String result = null;
        try {
            URL tokenUrl = new URL(urlString);
            conn = (HttpURLConnection) tokenUrl.openConnection();
            // RequestMethod:GET
            conn.setRequestMethod("GET");
            // Content-Type
            conn.setRequestProperty("Content-Type", "application/json");
            // ConnectTimeout
            conn.setConnectTimeout(5000);
            // ReadTimeout
            conn.setReadTimeout(5000);
            // 开启连接
            conn.connect();

            // responseReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            result = sb.toString();

            logger.info("response code: {}", conn.getResponseCode());
            logger.info("response data: {}", sb.toString());
        } catch (IOException e) {
            logger.error("do http get error with url:" + urlString, e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static String doPost(String urlString, String requestBody) {
        HttpURLConnection conn = null;
        String result = null;
        try {
            URL tokenUrl = new URL(urlString);
            conn = (HttpURLConnection) tokenUrl.openConnection();
            // RequestMethod:POST
            conn.setRequestMethod("POST");
            // Content-Type
            conn.setRequestProperty("Content-Type", "application/json");
            // ConnectTimeout
            conn.setConnectTimeout(5000);
            // ReadTimeout
            conn.setReadTimeout(5000);
            // DoOutput
            conn.setDoOutput(true);
            // 开启连接
            conn.connect();

            // 得到请求的输出流对象
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), Charset.forName("utf-8"));
            // 把数据写入请求的Bodyhttps://openapi.upesn.com/rest/message/service/txt
            writer.write(requestBody);
            writer.flush();
            writer.close();
            logger.info("request body: {}", requestBody);

            // responseReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            logger.info("response code: {}", conn.getResponseCode());

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                logger.info("response success: {}", sb.toString());
            } else {
                logger.info("response faild: {}", sb.toString());
            }
            result = sb.toString();
        } catch (IOException e) {
            logger.error("do http post error with url:" + urlString, e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // 第一步取AccessToken
        String accessToken = getAccessToken();
        // 第二部发消息
//        sendServiceText(accessToken);
        sendPalmyyServiceText(accessToken);
    }

}
