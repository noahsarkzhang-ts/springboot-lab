package org.noahsark.gateway.bean;

/**
 * AccessToken
 * @author zhangxt
 * @date 2022/03/14 20:33
 **/
public class AccessToken {

    private String access_token;

    private Integer expires_in;

    private String jti;

    private String refresh_token;

    private String scope;

    private String token_type;

    public AccessToken() {
    }

    public AccessToken(String access_token, Integer expires_in, String jti, String refresh_token, String scope, String token_type) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.jti = jti;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", jti='" + jti + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", token_type='" + token_type + '\'' +
                '}';
    }
}
