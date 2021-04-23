package com.ld.bmsys.auth.service.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ld.bmsys.auth.service.config.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author LD
 * @Date 2021/4/22 16:30
 */
@Slf4j
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

    private void init() throws Exception {

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
     * @param bucketName 桶名
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
     * 上传文件,带contentType
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String uploadFileWithContentType(MultipartFile file) throws Exception {
        return uploadFileWithContentType(DEFAULT_BUCKET_NAME, file.getOriginalFilename(), file, null);
    }

    /**
     * 上传文件,带contentType
     *
     * @param objectName 对象名
     * @param file       文件
     * @return
     * @throws Exception
     */
    public static String uploadFileWithContentType(String objectName, MultipartFile file) throws Exception {
        return uploadFileWithContentType(DEFAULT_BUCKET_NAME, objectName, file, null);
    }

    /**
     * 上传文件,带contentType
     *
     * @param bucketName  桶名
     * @param objectName  文件名
     * @param ins         文件流
     * @param contentType /
     * @return
     * @throws Exception
     */
    public static String uploadFileWithContentType(String bucketName, String objectName, MultipartFile file, String contentType) throws Exception {
        contentType = StrUtil.isBlank(contentType) ? file.getContentType() : contentType;
        InputStream inputStream = file.getInputStream();
        uploadObjectWithContentType(bucketName, objectName, inputStream, contentType);
        return getObjectUrl(bucketName, objectName);
    }


    /**
     * 上传对象,带contentType
     *
     * @param bucketName  桶名
     * @param fileName    文件名
     * @param ins         文件流
     * @param contentType /
     * @return
     * @throws Exception
     */
    public static ObjectWriteResponse uploadObjectWithContentType(String bucketName, String objectName, InputStream ins, String contentType) throws Exception {
        Assert.notNull(contentType, "contentType can't be null");
        Assert.notNull(contentType, "bucketName can't be null");

        if (!bucketExists(bucketName)) {
            createBucket(bucketName);
        }
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .contentType(contentType)
                        .stream(ins, ins.available(), -1)
                        .build());
    }

    /**
     * 文件上传
     *
     * @param objectName 文件名
     * @param file       文件
     * @return 文件url地址
     */
    public static String uploadAndGetFileUrlWithObjectName(String objectName, MultipartFile file) throws Exception {
        final InputStream ins = file.getInputStream();
        return uploadAndGetObjectUrl(DEFAULT_BUCKET_NAME, objectName, ins);
    }

    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param file       文件
     * @return 文件url地址
     */
    public static String uploadAndGetFileUrl(String bucketName, MultipartFile file) throws Exception {
        final InputStream ins = file.getInputStream();
        final String fileName = file.getOriginalFilename();
        return uploadAndGetObjectUrl(bucketName, fileName, ins);
    }

    /**
     * 文件上传
     *
     * @param file /
     * @return 文件url地址
     * @throws Exception
     */
    public static String uploadAndGetFileUrl(MultipartFile file) throws Exception {
        return uploadAndGetFileUrl(DEFAULT_BUCKET_NAME, file);
    }

    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @param file       文件
     * @return
     * @throws Exception
     */
    public static String uploadAndGetFileUrl(String bucketName, String objectName, File file) throws Exception {
        Assert.isTrue(FileUtil.isEmpty(file), "当前文件为空");

        return uploadAndGetObjectUrl(bucketName, objectName, new FileInputStream(file));
    }

    /**
     * 对象上传 正常上传,不加密
     *
     * @param bucketName  桶名
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return 文件地址
     */
    public static String uploadAndGetObjectUrl(String bucketName, String objectName, InputStream inputStream) throws Exception {
        uploadObject(bucketName, objectName, inputStream);
        return getObjectUrl(bucketName, objectName);
    }

    /**
     * 上传对象
     *
     * @param bucketName  桶名
     * @param fileName    文件名
     * @param inputStream 对象流
     * @return
     * @throws Exception
     */
    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, InputStream inputStream) throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    /**
     * 加密上传
     *
     * @param bucketName
     * @param objectName
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static ObjectWriteResponse uploadObjectEncrypt(String bucketName, String objectName, InputStream inputStream) throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .sse(new ServerSideEncryptionS3())
                        .build());
    }

    /**
     * 删除对象
     *
     * @param bucketName 桶名
     * @param objectName 对象名
     */
    public static void removeObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }


    /**
     * 移除桶内所有对象
     *
     * @param bucketName 桶名
     * @throws Exception
     */
    public static void removeObjects(String bucketName) throws Exception {
        if (!bucketExists(bucketName)) return;

        List<String> buketObjects = listObjects(bucketName);

        removeObjects(bucketName, buketObjects);
    }

    /**
     * 批量移除
     *
     * @param bucketName  桶名
     * @param objectNames 对象名集合
     */
    public static void removeObjects(String bucketName, List<String> objectNames) throws Exception {
        if (CollectionUtil.isNotEmpty(objectNames)) {
            List<DeleteObject> objects = objectNames.stream().map(DeleteObject::new).collect(Collectors.toList());
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                    RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());

            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("Error in deleting object -> {};  error message -> {};", error.objectName(), error.message());
            }

        }
    }


    /**
     * 获取桶下所有objects
     *
     * @param buketName 桶名
     * @return
     * @throws Exception
     */
    public static List<String> listObjects(String buketName) throws Exception {
        if (StrUtil.isNotBlank(buketName)) {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(buketName).build());

            List<String> res = new ArrayList<>(10);
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();
                res.add(objectName);
            }
            return res;
        }
        return Collections.emptyList();
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
