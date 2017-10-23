package org.hippo.toolkit.entity;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JIRA事件消息对象
 */
public class JiraMessageVO {

    /** JIRA时间类型-创建 */
    public static final String JIRA_EVENT_ISSUE_CREATED = "jira:issue_created";

    /** JIRA事件类型 */
    private String eventType;
    /** JIRA事件用户 */
    private JiraUserVO user;
    /** 问题url */
    private String issueUrl;
    /** 问题key */
    private String issueKey;
    /** 问题主题 */
    private String summary;
    /** 问题创建人 */
    private JiraUserVO creator;
    /** 问题类型 */
    private String issueType;
    /** 所在项目 */
    private String project;
    /** bug类型 */
    private String bugType;
    /** 优先级 */
    private String priority;
    /** 环境 */
    private String env;
    /** 客户端 */
    private String client;
    /** 浏览器 */
    private String browser;
    /** 操作系统 */
    private String os;
    /** 影响版本 */
    private List<String> versions;
    /** 经办人 */
    private JiraUserVO assignee;
    /** 模块 */
    private List<String> components;
    /** 报告人 */
    private JiraUserVO reporter;
    /** 时间戳 */
    private Long timestamp;

    public JiraMessageVO(String eventType) {
        this.eventType = eventType;
    }

    /**
     * 获取消息标题
     *
     * @return
     */
    public String getTitle() {
        if (JIRA_EVENT_ISSUE_CREATED.equals(getEventType())) {
            return getCreator().getDisplayName() + "created an issue";
        }
        return "unknown jira event";
    }

    /**
     * 获取消息内容
     *
     * @return
     */
    public String getDesc() {
        if (JIRA_EVENT_ISSUE_CREATED.equals(getEventType())) {
            StringBuffer sb = new StringBuffer();
            // 友空间 / UPESN-XXXX
            sb.append(getProject()).append(" / ").append(getIssueKey()).append("\n");
            // 主题
            if (!StringUtils.isEmpty(getSummary())) {
                sb.append(getSummary()).append("\n");
            }
            // 问题类型
            sb.append("问题类型：").append(getIssueType()).append("\n");
            // 影响版本
            if (getVersions() != null && getVersions().size() > 0) {
                sb.append("影响版本：");
                for (String version : getVersions()) {
                    sb.append(version).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
            }
            // 经办人
            if (getAssignee() != null) {
                sb.append("经办人：").append(getAssignee().getDisplayName()).append("\n");
            }
            // 模块
            if (getComponents() != null && getComponents().size() > 0) {
                sb.append("模块：");
                for (String component : getComponents()) {
                    sb.append(component).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
            }
            // 创建
            sb.append("创建：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getTimestamp())).append("\n");
            // 优先级
            if (!StringUtils.isEmpty(getPriority())) {
                sb.append("优先级：").append(getPriority()).append("\n");
            }
            // 报告人
            if (getReporter() != null) {
                sb.append("").append(getReporter().getDisplayName()).append("\n");
            }
            return sb.toString();
        }
        return "unknown jira event";
    }

    /**
     * 获取消息url
     *
     * @return
     */
    public String getDetailUrl() {
        return getIssueUrl();
    }

    /**
     * 获取高亮信息
     *
     * @return
     */
    public String getHightlight() {
        if (JIRA_EVENT_ISSUE_CREATED.equals(getEventType())) {
            StringBuffer sb = new StringBuffer();
            sb.append(getProject()).append(" / ").append(getIssueKey());
            if (!StringUtils.isEmpty(getSummary())) {
                sb.append("|").append(getSummary());
            }
            if (getAssignee() != null) {
                sb.append("|").append(getAssignee().getDisplayName());
            }
            if (getReporter() != null) {
                sb.append("|").append(getReporter().getDisplayName());
            }
        }
        return "";
    }

    /**
     * 获取消息接收方email
     *
     * @return
     */
    public String getTo() {
        //        return getUser().getEmail();
        return "litfb@yonyou.com";
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public JiraUserVO getUser() {
        return user;
    }

    public void setUser(JiraUserVO user) {
        this.user = user;
    }

    public String getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public JiraUserVO getCreator() {
        return creator;
    }

    public void setCreator(JiraUserVO creator) {
        this.creator = creator;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public JiraUserVO getAssignee() {
        return assignee;
    }

    public void setAssignee(JiraUserVO assignee) {
        this.assignee = assignee;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public JiraUserVO getReporter() {
        return reporter;
    }

    public void setReporter(JiraUserVO reporter) {
        this.reporter = reporter;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
