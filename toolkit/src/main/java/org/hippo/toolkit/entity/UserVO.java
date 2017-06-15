package org.hippo.toolkit.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ofUsers")
public class UserVO extends BasicVO {

    private static final long serialVersionUID = 4172905874403125211L;

    public static final int TYPE_COMMONUSER = 0;
    public static final int TYPE_ADMIN = 3;

    public static final String FIELD_USERID = "userid";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_ETPID = "etpId";
    public static final String FIELD_APPID = "appId";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_LASTACTIVITY = "lastActivity";
    public static final String FIELD_CERTIFICATENAME = "certificateName";
    public static final String FIELD_DEVICETOKEN = "deviceToken";
    public static final String FIELD_ACCOUNTID = "accountId";
    public static final String FIELD_SOUNDID = "soundId";
    public static final String FIELD_PHONEACCOUNT_IDENTIFY = "phoneAccount_identify";
    public static final String FIELD_NETACCOUNT_IDENTIFY = "netAccount_identify";
    public static final String FIELD_HUAWEIDEVICETOKEN = "huaweiDeviceToken";
    public static final String FIELD_HUAWEIPUSHAPPID = "huaweiPushAppId";

    /** 用户ID */
    private String userid;
    /** 密码 **/
    private String password;
    /** 企业ID **/
    private String etpId;
    /** 用户ID */
    private String appId;
    /** 用户类型 **/
    private int type = TYPE_COMMONUSER;
    /** 上次登入时间 **/
    private long lastActivity;
    /** iOS推送证书名称 **/
    private String certificateName;
    /** iOS设备Token **/
    private String deviceToken;

    /** 计费账号ID **/
    private String accountId;
    /** 用户在声网中注册的ID **/
    private int soundId;
    /** 落地电话服务账号 **/
    private String phoneAccount_identify;
    /** 网络电话服务账号 **/
    private String netAccount_identify;
    /** 华为HMS推送设备token **/
    private String huaweiDeviceToken;
    /** 华为消息推送appId **/
    private String huaweiPushAppId;

    public UserVO() {

    }

    public UserVO(String userid, String password) {
        super();
        this.userid = userid;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEtpId() {
        return etpId;
    }

    public void setEtpId(String etpId) {
        this.etpId = etpId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public String getPhoneAccount_identify() {
        return phoneAccount_identify;
    }

    public void setPhoneAccount_identify(String phoneAccount_identify) {
        this.phoneAccount_identify = phoneAccount_identify;
    }

    public String getNetAccount_identify() {
        return netAccount_identify;
    }

    public void setNetAccount_identify(String netAccount_identify) {
        this.netAccount_identify = netAccount_identify;
    }

    public String getHuaweiDeviceToken() {
        return huaweiDeviceToken;
    }

    public void setHuaweiDeviceToken(String huaweiDeviceToken) {
        this.huaweiDeviceToken = huaweiDeviceToken;
    }

    public String getHuaweiPushAppId() {
        return huaweiPushAppId;
    }

    public void setHuaweiPushAppId(String huaweiPushAppId) {
        this.huaweiPushAppId = huaweiPushAppId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + ((certificateName == null) ? 0 : certificateName.hashCode());
        result = prime * result + ((deviceToken == null) ? 0 : deviceToken.hashCode());
        result = prime * result + ((etpId == null) ? 0 : etpId.hashCode());
        result = prime * result + ((huaweiDeviceToken == null) ? 0 : huaweiDeviceToken.hashCode());
        result = prime * result + ((huaweiPushAppId == null) ? 0 : huaweiPushAppId.hashCode());
        result = prime * result + (int) (lastActivity ^ (lastActivity >>> 32));
        result = prime * result + ((netAccount_identify == null) ? 0 : netAccount_identify.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((phoneAccount_identify == null) ? 0 : phoneAccount_identify.hashCode());
        result = prime * result + soundId;
        result = prime * result + type;
        result = prime * result + ((userid == null) ? 0 : userid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserVO other = (UserVO) obj;
        if (accountId == null) {
            if (other.accountId != null)
                return false;
        } else if (!accountId.equals(other.accountId))
            return false;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        if (certificateName == null) {
            if (other.certificateName != null)
                return false;
        } else if (!certificateName.equals(other.certificateName))
            return false;
        if (deviceToken == null) {
            if (other.deviceToken != null)
                return false;
        } else if (!deviceToken.equals(other.deviceToken))
            return false;
        if (etpId == null) {
            if (other.etpId != null)
                return false;
        } else if (!etpId.equals(other.etpId))
            return false;
        if (huaweiDeviceToken == null) {
            if (other.huaweiDeviceToken != null)
                return false;
        } else if (!huaweiDeviceToken.equals(other.huaweiDeviceToken))
            return false;
        if (huaweiPushAppId == null) {
            if (other.huaweiPushAppId != null)
                return false;
        } else if (!huaweiPushAppId.equals(other.huaweiPushAppId))
            return false;
        if (lastActivity != other.lastActivity)
            return false;
        if (netAccount_identify == null) {
            if (other.netAccount_identify != null)
                return false;
        } else if (!netAccount_identify.equals(other.netAccount_identify))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (phoneAccount_identify == null) {
            if (other.phoneAccount_identify != null)
                return false;
        } else if (!phoneAccount_identify.equals(other.phoneAccount_identify))
            return false;
        if (soundId != other.soundId)
            return false;
        if (type != other.type)
            return false;
        if (userid == null) {
            if (other.userid != null)
                return false;
        } else if (!userid.equals(other.userid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserVO [userid=" + userid + ", type=" + type + ", certificateName=" + certificateName + ", deviceToken=" + deviceToken + ", password=" + password + ", accountId=" + accountId
                + ", phoneAccount_identify=" + phoneAccount_identify + ", netAccount_identify=" + netAccount_identify + ", etpId=" + etpId + ", appId=" + appId + ", lastActivity=" + lastActivity
                + ", soundId=" + soundId + ", huaweiDeviceToken=" + huaweiDeviceToken + ", huaweiPushAppId=" + huaweiPushAppId + "]";
    }
}
