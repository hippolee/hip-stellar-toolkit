package org.hippo.toolkit.rest.param;

/**
 * Created by litengfei on 2017/8/19.
 */
public class LoginParamVO {

    private String mobile;

    private String password;

    private Integer clientIntVal;

    private String deviceInfo;

    private String osInfo;

    private String runtimeInfo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getClientIntVal() {
        return clientIntVal;
    }

    public void setClientIntVal(Integer clientIntVal) {
        this.clientIntVal = clientIntVal;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(String osInfo) {
        this.osInfo = osInfo;
    }

    public String getRuntimeInfo() {
        return runtimeInfo;
    }

    public void setRuntimeInfo(String runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
    }
}
