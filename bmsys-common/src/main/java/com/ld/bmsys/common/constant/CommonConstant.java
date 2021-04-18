package com.ld.bmsys.common.constant;

/**
 * @Author LD
 * @Date 2020/3/5 11:01
 */
public interface CommonConstant {

    int ENABLED = 0;
    int DISABLED = 1;

    int USER_LOCKED = 1;
    int USER_UNLOCKED = 0;

    String DEFAULT_NULL_MESSAGE = "暂无数据";
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    String DEFAULT_FAILURE_MESSAGE = "操作失败";
    String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";


    String AUTH_SERVICE_NAME = "bmsys-auth-service";
    String DICT_SERVICE_NAME = "bmsys-dict-service";

    String REGEX_EMAIL = "^\\s*?(.+)@(.+?)\\s*$";
    String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    String UTF_8 = "UTF-8";
    String AUTHENTICATION = "Token";

    String AUTHORITIES_CACHE_PREFIX = "authorities_";


    /**
     * 获取用户缓存key
     *
     * @param username 用户名或其他用户唯一字符串
     * @return redis key
     */
    static String getAuthoritiesCacheName(String username) {
        return AUTHORITIES_CACHE_PREFIX + username;
    }

}
