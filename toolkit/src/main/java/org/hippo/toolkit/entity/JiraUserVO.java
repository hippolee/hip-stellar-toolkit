package org.hippo.toolkit.entity;

/**
 * JIRA用户对象
 */
public class JiraUserVO {

    /** jira账号 */
    private String name;
    /** jira姓名 */
    private String displayName;
    /** jira头像 */
    private String avatar;
    /** jira邮箱 */
    private String email;

    public JiraUserVO(String name, String displayName, String avatar, String email) {
        this.name = name;
        this.displayName = displayName;
        this.avatar = avatar;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
