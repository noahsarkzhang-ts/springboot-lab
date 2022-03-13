package org.noahsark.common.constant;

/**
 * Created by macro on 2020/6/19.
 */
public class AuthConstant {

    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 认证请求头key
     */
    public static final String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT令牌前缀
     */
    public static final String JWT_PREFIX = "Bearer ";


    /**
     * Basic认证前缀
     */
    public static final String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    public static final String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    public static final String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    public static final String JWT_EXP = "exp";

    /**
     * 黑名单token前缀
     */
    public static final String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    public static final String USER_ID_KEY = "userId";

    public static final String USER_NAME_KEY = "username";

    public static final String CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    public static final String JWT_AUTHORITIES_KEY = "authorities";

    public static final String GRANT_TYPE_KEY = "grant_type";

    public static final String REFRESH_TOKEN_KEY = "refresh_token";

}
