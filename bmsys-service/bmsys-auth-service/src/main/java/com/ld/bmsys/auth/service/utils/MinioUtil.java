package com.ld.bmsys.auth.service.utils;

import cn.hutool.core.util.StrUtil;
import com.ld.bmsys.auth.service.config.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author LD
 * @Date 2021/4/22 16:30
 */
@Component
@SuppressWarnings("all")
public class MinioUtil {

    private static MinioClient minioClient;
    private static String DEFAULT_BUCKET_NAME = "default-bucket";
    private static Integer OBJECT_URL_EXPIRE = 7;
    private final MinioProperties properties;

    public MinioUtil(MinioProperties properties) throws Exception {
        this.properties = properties;
        init();
    }

    public void init() throws Exception {

        MinioUtil.minioClient = MinioClient.builder()
                .endpoint(properties.getEndpoint(), properties.getPort(), properties.getSecure())
                .credentials(properties.getAccessKey(), properties.getSecretKey()).build();

        if (StrUtil.isNotBlank(properties.getBucketName())) {
            DEFAULT_BUCKET_NAME = properties.getBucketName();
        }
        createBucket(DEFAULT_BUCKET_NAME);
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName: 桶名
     * @return: boolean
     */
    public static boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建 bucket 桶名
     *
     * @param bucketName
     * @throws Exception
     */
    public static void createBucket(String bucketName) throws Exception {
        boolean exists = bucketExists(bucketName);
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     *
     * @return /
     * @throws Exception /
     */
    public static List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 获取全部bucket名称
     *
     * @return /
     * @throws Exception /
     */
    public static List<String> listBucketsName() throws Exception {
        List<Bucket> buckets = listBuckets();
        return buckets.stream().map(Bucket::name).collect(Collectors.toList());
    }


    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param file       文件
     * @return 文件url地址
     */
    public static String upload(String bucketName, MultipartFile file) throws Exception {
        final InputStream ins = file.getInputStream();
        final String fileName = file.getOriginalFilename();
        return upload(bucketName, fileName, ins);
    }

    /**
     * 文件上传
     *
     * @param file /
     * @return 文件url地址
     * @throws Exception
     */
    public static String upload(MultipartFile file) throws Exception {
        final InputStream ins = file.getInputStream();
        final String fileName = file.getOriginalFilename();
        return upload(DEFAULT_BUCKET_NAME, fileName, ins);
    }


    /**
     * 文件上传 正常上传,不加密
     *
     * @param bucketName  桶名
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return 文件地址
     */
    public static String upload(String bucketName, String fileName, InputStream inputStream) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
        return getObjectUrl(bucketName, fileName);
    }


    /**
     * 删除对象
     *
     * @param bucketName 桶名
     * @param objectName 对象名
     */
    public static void deleteObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 获取对象地址
     *
     * @param bucketName 桶名
     * @param objectName 对象名
     * @return /
     * @throws Exception /
     */
    public static String getObjectUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET)
                .expiry(OBJECT_URL_EXPIRE, TimeUnit.DAYS).build());
    }

}
