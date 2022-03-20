package org.noahsark.gateway.bean;

/**
 * 登陆请求信息
 * @author zhangxt
 * @date 2022/03/15 09:40
 **/
public class LoginRequest {

    /**
     * 标识一次登陆请求
     */
    private String uuid;

    /**
     * clientId
     */
    private String clientId;

    /**
     * 重定向 uri
     */
    private String redirectUri;

    public LoginRequest() {
    }

    public LoginRequest(String uuid, String clientId, String redirectUri) {
        this.uuid = uuid;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "uuid='" + uuid + '\'' +
                ", clientId='" + clientId + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                '}';
    }
}
