package org.hippo.toolkit.some;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hippo.toolkit.util.OpenApiUtils;
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

    //    private static final String OPENAPI_DOMAIN = "https://openapi.upesn.com";
    private static final String OPENAPI_DOMAIN = "http://172.20.9.11:10200/openapi";

    /** 获取access_token的url */
    private static final String URL_ACCESS_TOKEN = OPENAPI_DOMAIN + "/token";
    /** 服务号发送文本消息url */
    private static final String URL_SERVICE_TEXT = OPENAPI_DOMAIN + "/rest/message/service/txt";
    /** 服务号发送图文消息url */
    private static final String URL_SERVICE_MIXED = OPENAPI_DOMAIN + "/rest/message/service/mixed";

    /** 待办中心新增接口 */
    private static final String URL_TODOCENTER_ADD = OPENAPI_DOMAIN + "/thirdpart/todo/item";

    /** 服务号发送文本消息url（NC） */
    private static final String URL_PALMYY_SERVICE_TEXT = OPENAPI_DOMAIN + "/operation/palmyy/message/service/txt";

    private static final String[][] TODO_DATA_ARRAY = {
    };



    public static void todoCenterAdd(String accessToken, String qzId, String appId, String businessKey,
                                     String title, String content, String webUrl, String mobileUrl,
                                     String[] memberIds, String oriMemberId, String type) {
        // url string
        String urlString = URL_TODOCENTER_ADD + "?access_token=" + accessToken;

        // request body
        JSONObject body = new JSONObject();
        // 空间ID
        body.put("qzId", qzId);
        // 开放平台应用ID
        body.put("openAppId", appId);
        // 业务ID
        body.put("businessKey", businessKey);
        // 标题
        body.put("title", title);
        // 内容
        body.put("content", content);
        // Web端Url
        body.put("webUrl", webUrl);
        // 移动端Url
        body.put("mUrl", mobileUrl);
        // 接收方Ids
        body.put("memIds", memberIds);
        // 发送方Id
        body.put("originMemId", oriMemberId);
        // 类型
        body.put("typeName", type);
        // 截止时间
        body.put("deadline", 1506700800000L);

        String result = doPost(urlString, body.toJSONString());
        if (!StringUtils.isEmpty(result)) {
            // JSONObject
            JSONObject responseData = JSON.parseObject(result);
            logger.info("response flag: {}", responseData.getInteger("flag"));
            logger.info("response message: {}", responseData.getString("message"));
        } else {
            logger.info("add todoCenter faild");
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
            try {
                // responseReader
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), "utf-8"));

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                logger.info("response code: {}", conn.getResponseCode());
                logger.info("response faild: {}", sb.toString());
            } catch (Exception e1) {
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // 第一步取AccessToken
        //        String accessToken = getAccessToken(APP_ID, SECRET);
        // 第二部发消息
        //        sendServiceText(accessToken);
        //        sendPalmyyServiceText(accessToken);
        //        String accessToken, String qzId, String appId, String businessKey,
        //                String title, String content, String webUrl, String mobileUrl,
        //                String[] memberIds, String oriMemberId, String type

        //        {"917","234","nc000020","NC审批","陈大权 提交了编号为 CG00000000008441 的项目采购申请单，请及时审批。","NC"}
        //        {"169590", "153494", "153493", "117503", "153450", "232402", "3256505"};
        String accessToken = OpenApiUtils.getAccessToken("ad7d4871b3d56c25", "9266f83d8a268a6dabbbb7d230653382cab5ea53288489d3a68e591237fe");

        String[] memberIds = {"169590", "153494", "153493", "117503", "153450", "232402", "3256505"};
        for (String[] todoData : TODO_DATA_ARRAY) {
            todoCenterAdd(accessToken, todoData[0], todoData[1], todoData[2], todoData[3], todoData[4],
                    "", "", memberIds, "", todoData[5]);
        }
    }

}
