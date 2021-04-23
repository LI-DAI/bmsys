package com.ld.bmsys.auth.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author LD
 * @Date 2021/4/22 16:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * minio 地址
     */
    private String endpoint;

    /**
     * 端口号
     */
    private int port;

    /**
     * 账户
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 如果是true，则用的是https而不是http,默认值是true
     */
    private Boolean secure;

    /**
     * 默认存储桶
     */
    private String bucketName;

    /**
     * 默认存储桶
     */
    private String configDir;

    /**
     * 是否启用
     */
    private Boolean enable;

}
