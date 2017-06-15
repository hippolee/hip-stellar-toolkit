package org.hippo.toolkit.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "ofVCards")
public class VCardVO extends BasicVO {

    private static final long serialVersionUID = 9167665762903243787L;

    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_ETPID = "etpId";
    public static final String FIELD_APPID = "appId";
    public static final String FIELD_NICKNAME = "nickname";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHOTO = "photo";
    public static final String FIELD_MOBILE = "mobile";
    public static final String FIELD_TELEPHONE = "telephone";
    public static final String FIELD_POSITION = "position";
    public static final String FIELD_LOCATION = "location";
    public static final String FIELD_NUMBER = "number";
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_REMARKS = "remarks";
    public static final String FIELD_TAG = "tag";
    public static final String FIELD_ORGID = "orgId";
    public static final String FIELD_ORGANIZATION = "organization";

    /** 用户ID */
    private String username;
    /** 企业ID */
    private String etpId;
    /** 应用ID */
    private String appId;
    /** 昵称 */
    private String nickname;
    /** 邮箱 */
    private String email;
    /** 头像 */
    private String photo;
    /** 手机号 */
    private String mobile;
    /** 电话 */
    private String telephone;
    /** 职位 */
    private String position;
    /** 办公地点 */
    private String location;
    /** 工号 */
    private String number;
    /** 性别 */
    private String gender;
    /** 备注 */
    private String remarks;
    /** 标签 */
    private Set<String> tag = new HashSet<>();
    /** 部门ID */
    private String orgId;

    public VCardVO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<String> getTag() {
        if (tag == null) {
            tag = new HashSet<>();
        }
        return tag;
    }

    public void setTag(Set<String> tag) {
        this.tag = tag;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((etpId == null) ? 0 : etpId.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        result = prime * result + ((photo == null) ? 0 : photo.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        VCardVO other = (VCardVO) obj;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (etpId == null) {
            if (other.etpId != null)
                return false;
        } else if (!etpId.equals(other.etpId))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        } else if (!mobile.equals(other.mobile))
            return false;
        if (nickname == null) {
            if (other.nickname != null)
                return false;
        } else if (!nickname.equals(other.nickname))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (orgId == null) {
            if (other.orgId != null)
                return false;
        } else if (!orgId.equals(other.orgId))
            return false;
        if (photo == null) {
            if (other.photo != null)
                return false;
        } else if (!photo.equals(other.photo))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (remarks == null) {
            if (other.remarks != null)
                return false;
        } else if (!remarks.equals(other.remarks))
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (telephone == null) {
            if (other.telephone != null)
                return false;
        } else if (!telephone.equals(other.telephone))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VCardVO [username=" + username + ", etpId=" + etpId + ", appId=" + appId + ", email=" + email + ", photo=" + photo + ", position="
                + position + ", location=" + location + ", number=" + number + ", gender=" + gender + ", remarks=" + remarks + ", nickname="
                + nickname + ", mobile=" + mobile + ", telephone=" + telephone + ", orgId=" + orgId + ", tag=" + tag + "]";
    }

}
