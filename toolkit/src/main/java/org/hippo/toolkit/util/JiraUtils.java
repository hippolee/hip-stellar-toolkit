package org.hippo.toolkit.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hippo.toolkit.entity.JiraChangeLogVO;
import org.hippo.toolkit.entity.JiraMessageVO;
import org.hippo.toolkit.entity.JiraUserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Jira工具类
 */
public class JiraUtils {

    /**
     * 根据JiraWebhookEvent发送消息
     *
     * @param eventObject
     */
    public static boolean handleJiraEvent(JSONObject eventObject) {
        if (eventObject == null) {
            return false;
        }

        // event类型
        String eventType = eventObject.getString("webhookEvent");
        if (!JiraMessageVO.JIRA_EVENT_ISSUE_CREATED.equals(eventType) && !JiraMessageVO.JIRA_EVENT_ISSUE_UPDATED
                .equals(eventType)) {
            return false;
        }

        // 消息对象
        JiraMessageVO message = new JiraMessageVO(eventType);
        // 时间戳
        message.setTimestamp(eventObject.getLong("timestamp"));
        // event用户
        JSONObject userObj = eventObject.getJSONObject("creator");
        if (userObj != null) {
            String userName = userObj.getString("name");
            String userDisplayName = userObj.getString("displayName");
            String userEmail = userObj.getString("emailAddress");
            JSONObject userAvatarObj = userObj.getJSONObject("avatarUrls");
            String userAvatar = userAvatarObj.getString("48x48");
            message.setUser(new JiraUserVO(userName, userDisplayName, userAvatar, userEmail));
        }

        // issue对象
        JSONObject issueObj = eventObject.getJSONObject("issue");
        if (issueObj == null) {
            return false;
        }
        // issue url
        message.setIssueUrl(issueObj.getString("self"));
        // issue key
        message.setIssueKey(issueObj.getString("key"));

        // fields对象
        JSONObject fieldsObj = issueObj.getJSONObject("fields");
        if (fieldsObj == null) {
            return false;
        }

        // 主题
        message.setSummary(fieldsObj.getString("summary"));
        // 创建人
        JSONObject creatorObj = fieldsObj.getJSONObject("creator");
        if (creatorObj != null) {
            String creatorName = creatorObj.getString("name");
            String creatorDisplayName = creatorObj.getString("displayName");
            String creatorEmail = creatorObj.getString("emailAddress");
            JSONObject creatorAvatarObj = creatorObj.getJSONObject("avatarUrls");
            String creatorAvatar = creatorAvatarObj.getString("48x48");
            message.setCreator(new JiraUserVO(creatorName, creatorDisplayName, creatorAvatar, creatorEmail));
        }
        // 问题类型
        JSONObject issueTypeObj = fieldsObj.getJSONObject("issuetype");
        if (issueTypeObj != null) {
            message.setIssueType(issueTypeObj.getString("name"));
        }
        // 所在项目
        JSONObject projectObj = fieldsObj.getJSONObject("project");
        if (projectObj != null) {
            message.setProject(projectObj.getString("name"));
        }
        // bug类型
        JSONObject bugTypeObj = fieldsObj.getJSONObject("customfield_10158");
        if (bugTypeObj != null) {
            message.setBugType(bugTypeObj.getString("name"));
        }
        // 优先级
        JSONObject priorityObj = fieldsObj.getJSONObject("priority");
        if (priorityObj != null) {
            message.setPriority(priorityObj.getString("name"));
        }
        // 环境
        JSONObject envObj = fieldsObj.getJSONObject("customfield_11309");
        if (envObj != null) {
            message.setEnv(envObj.getString("name"));
        }
        // 客户端
        JSONObject clientObj = fieldsObj.getJSONObject("customfield_11125");
        if (clientObj != null) {
            message.setClient(clientObj.getString("name"));
        }
        // 浏览器
        JSONObject browserObj = fieldsObj.getJSONObject("customfield_10203");
        if (browserObj != null) {
            message.setBrowser(browserObj.getString("name"));
        }
        // 操作系统
        JSONObject osObj = fieldsObj.getJSONObject("customfield_10202");
        if (osObj != null) {
            message.setOs(osObj.getString("name"));
        }
        // 影响版本
        JSONArray versionsArr = fieldsObj.getJSONArray("versions");
        if (versionsArr != null && versionsArr.size() > 0) {
            List<String> versions = new ArrayList<>();
            for (int i = 0; i < versionsArr.size(); i++) {
                JSONObject versionObj = versionsArr.getJSONObject(i);
                versions.add(versionObj.getString("name"));
            }
            message.setVersions(versions);
        }
        // 指派人
        JSONObject assigneeObj = fieldsObj.getJSONObject("assignee");
        if (assigneeObj != null) {
            String assigneeName = assigneeObj.getString("name");
            String assigneeDisplayName = assigneeObj.getString("displayName");
            String assigneeEmail = assigneeObj.getString("emailAddress");
            JSONObject assigneeAvatarObj = assigneeObj.getJSONObject("avatarUrls");
            String assigneeAvatar = assigneeAvatarObj.getString("48x48");
            message.setAssignee(new JiraUserVO(assigneeName, assigneeDisplayName, assigneeAvatar, assigneeEmail));
        }
        // 模块
        JSONArray componentsArr = fieldsObj.getJSONArray("components");
        if (componentsArr != null && componentsArr.size() > 0) {
            List<String> components = new ArrayList<>();
            for (int i = 0; i < componentsArr.size(); i++) {
                JSONObject componentObj = componentsArr.getJSONObject(i);
                components.add(componentObj.getString("name"));
            }
            message.setComponents(components);
        }
        // 报告人
        JSONObject reporterObj = fieldsObj.getJSONObject("reporter");
        if (reporterObj != null) {
            String reporterName = reporterObj.getString("name");
            String reporterDisplayName = reporterObj.getString("displayName");
            String reporterEmail = reporterObj.getString("emailAddress");
            JSONObject reporterAvatarObj = reporterObj.getJSONObject("avatarUrls");
            String reporterAvatar = reporterAvatarObj.getString("48x48");
            message.setReporter(new JiraUserVO(reporterName, reporterDisplayName, reporterAvatar, reporterEmail));
        }
        // 状态
        JSONObject statusObj = fieldsObj.getJSONObject("status");
        if (statusObj != null) {
            message.setStatus(statusObj.getString("name"));
        }
        // 抄送人
        JSONArray ccArr = fieldsObj.getJSONArray("customfield_10129");
        if (ccArr != null && ccArr.size() > 0) {
            List<JiraUserVO> ccList = new ArrayList<>();
            for (int i = 0; i < ccArr.size(); i++) {
                JSONObject ccObj = ccArr.getJSONObject(i);
                String ccName = ccObj.getString("name");
                String ccDisplayName = ccObj.getString("displayName");
                String ccEmail = ccObj.getString("emailAddress");
                JSONObject ccAvatarObj = ccObj.getJSONObject("avatarUrls");
                String ccAvatar = ccAvatarObj.getString("48x48");
                ccList.add(new JiraUserVO(ccName, ccDisplayName, ccAvatar, ccEmail));
            }
            message.setCcList(ccList);
        }
        // 修改日志
        JSONObject changelogObj = envObj.getJSONObject("changelog");
        if (changelogObj != null && changelogObj.size() > 0) {
            JSONArray itemsArr = changelogObj.getJSONArray("items");
            if (itemsArr != null && itemsArr.size() > 0) {
                List<JiraChangeLogVO> changeLogList = new ArrayList<>();
                for (int i = 0; i < itemsArr.size(); i++) {
                    JSONObject itemObj = itemsArr.getJSONObject(i);
                    String field = itemObj.getString("field");
                    JSONObject fromObj = itemObj.getJSONObject("from");
                    String fromString = itemObj.getString("fromString");
                    JSONObject toObj = itemObj.getJSONObject("to");
                    String toString = itemObj.getString("toString");

                    String from = null;
                    if (!StringUtils.isEmpty(fromString)) {
                        from = fromString;
                    } else if (fromObj != null) {
                        if (!StringUtils.isEmpty(fromObj.getString("displayName"))) {
                            from = fromObj.getString("displayName");
                        } else {
                            from = fromObj.getString("name");
                        }
                    }
                    String to = null;
                    if (!StringUtils.isEmpty(toString)) {
                        to = toString;
                    } else if (toObj != null) {
                        if (StringUtils.isEmpty(toObj.getString("displayName"))) {
                            to = toObj.getString("displayName");
                        } else {
                            to = toObj.getString("name");
                        }
                    }
                    changeLogList.add(new JiraChangeLogVO(field, from, to));
                }
                message.setChangeLogList(changeLogList);
            }
        }

        // 获取accessToken
        String accessToken = OpenApiUtils.getAccessToken(OpenApiUtils.OPEN_APP_ID_YONYOU, OpenApiUtils
                .OPEN_SECRET_YONYOU);
        // 发消息
        return OpenApiUtils.sendPalmyyShare(accessToken, OpenApiUtils.ESN_QZID, OpenApiUtils.ECJIRA_PUBACCID, message
                        .getTo(), OpenApiUtils.PALMYY_TO_EMALI, message.getTitle(), message.getDesc(),
                message.getDetailUrl(), message.getHightlight());
    }

}
